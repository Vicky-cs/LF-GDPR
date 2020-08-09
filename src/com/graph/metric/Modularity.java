package com.graph.metric;

import java.util.ArrayList;
import java.util.HashSet;

public class Modularity {
	
	boolean[][] mat;
	double[] degree;
	double totalEdgeNum;
	ArrayList<ArrayList<Integer>> allNeighbors;
	
	
	public Modularity(boolean[][] mat, double[] degree){
		this.mat = mat;
		this.degree = degree;
		for(double d : degree)
			totalEdgeNum += d;
		totalEdgeNum /= 2.0;
		
		allNeighbors = new ArrayList<ArrayList<Integer>>();
		for(int i=0; i<mat[0].length; i++){
			ArrayList<Integer> set = Tools.getNeighbors(mat, i);
			allNeighbors.add(set);
		}
	}
	
	
	public ArrayList<ArrayList<Integer>> getAllNeighbors(){
		return this.allNeighbors;
	}
	
	
	// calculate modularity in the perturbed graph with a certain privacy budget
	public double getModularity(ArrayList<HashSet<Integer>> clusters, int type, double epsilon){
		double modu = 0.0;	
		if(type==1 || type==2 || type==3){
			for(HashSet<Integer> hs : clusters)
				modu += getOneModularity(hs, type);
		}else{
			for(HashSet<Integer> hs : clusters)
				modu += getOneModularity(hs, type, epsilon);
		}
		return modu;
	}
	
	
	public double getOneModularity(HashSet<Integer> set, int type){
		if(type!=1 && type!=2 && type!=3)
			System.err.println("Parameters error in Modularity.java  -- should be without an epsilon!!");
		double sumOfDegree = 0.0;
		for(int node : set)
			sumOfDegree += this.degree[node];
		double edgeNum = 0;
		if(type==1 || type==2 || type==3)  // original graph
			edgeNum = getSubGraphEdges(set);
//		System.out.println("edgeNum="+edgeNum+"\ttotalEdgeNum="+totalEdgeNum+"\tsumOfDegree="+sumOfDegree);
		return edgeNum/totalEdgeNum-(sumOfDegree/2.0/totalEdgeNum)*(sumOfDegree/2.0/totalEdgeNum);
	}
	
	
	public double getOneModularity(HashSet<Integer> set, int type, double epsilon){
		if(type==1 || type==2 || type==3)
			System.err.println("Parameters error in Modularity.java  --  should come with an epsilon!!");
		double sumOfDegree = 0.0;
		for(int node : set)
			sumOfDegree += this.degree[node];
		double edgeNum = 0;
		if(type==4 || type==41)    // perturbed graph 
			edgeNum = getSubGraphEdges(set, epsilon);
//		else if(type==5 || type==51)    // perturbed graph with naive alignment
//			edgeNum = getSubGraphEdges_NaiveAlignment(set, epsilon);	
		return edgeNum/totalEdgeNum-(sumOfDegree/2.0/totalEdgeNum)*(sumOfDegree/2.0/totalEdgeNum);
	}

	
	// return the number of edges in a subgraph of the original graph
	public double getSubGraphEdges(HashSet<Integer> set){
		double edges = 0.0;
		for(int i : set){
			for(int j : set){
				if(j>i && mat[i][j]==true)
					edges++;
			}
		}
		return edges;
	}
	
	
	// privacy-preserving calibration
	public double getSubGraphEdges(HashSet<Integer> set, double epsilon){
		double noisyEdges = getSubGraphEdges(set);
		double p = Math.exp(epsilon)/(1.0+Math.exp(epsilon));
		double N = 0.5 * set.size() * (set.size()-1);
		double edges = noisyEdges/(2*p-1) - (1-p)/(2*p-1)*N;
		if(edges<0) edges = 0;
		if(edges>N) edges = N;
		return edges;
	}
	
	
	// privacy-preserving calibration for naive case, where the perturbed matrix is symmetric
	public double getSubGraphEdges_NaiveAlignment(HashSet<Integer> set, double epsilon){
		double noisyEdges = getSubGraphEdges(set);
		double p = Math.exp(epsilon)/(1.0+Math.exp(epsilon));
		double N = 0.5 * set.size() * (set.size()-1);
		double edges = noisyEdges/(2*p-1) - (1-p)*(1-p)/(2*p-1)*N;
		if(edges<0) edges = 0;
		if(edges>N) edges = N;
		return edges;
	}
	
	
	// return the connected components in the original graph
	public ArrayList<HashSet<Integer>> searchCluster(){
		int userNum = mat[0].length;
		ArrayList<HashSet<Integer>> community = new ArrayList<HashSet<Integer>>();		
		boolean[] visited = new boolean[userNum];  // elements in visited are set to false as default
		for(int i=0; i<userNum; i++){
			if(!visited[i]){
				HashSet<Integer> cluster = new HashSet<Integer>();
				DFS(i, visited, cluster);
				community.add(cluster);
			}
		}	
		return community;
	}
	
	
	public void DFS(int node, boolean[] visited, HashSet<Integer> cluster){
		visited[node] = true;
		cluster.add(new Integer(node));
		for(int i=0, len = mat[0].length; i<len; i++){
			if(mat[node][i]==true){
				if(!visited[i])
					DFS(i, visited, cluster);
			}
		}
	}
	
	
	// link: each node is linked to only one node, and overall it represents the graph structure
	// the array link stores these edges, one for each node
	public static ArrayList<HashSet<Integer>> searchCluster(int[] link){	
		int cnt = 0;
		int[] index = new int[link.length];
		for(int i=0, len=link.length; i<len; i++){		
			if(index[i]==0){    // did not visit this node
				if(index[link[i]]==0){
					cnt++;
					index[i] = cnt;
					int k = i;
					while(index[link[k]]==0){
						index[link[k]] = index[k];
						k = link[k];
					}
				}else{
					index[i] = index[link[i]];
				}
			}else{
				int k = i;
				while(index[link[k]]==0){
					index[link[k]] = index[k];
					k = link[k];
				}
			}			
		}
		ArrayList<HashSet<Integer>> clusters = new ArrayList<HashSet<Integer>>();
		for(int i=0; i<cnt; i++)
			clusters.add(new HashSet<Integer>());
		for(int i=0, len=link.length; i<len; i++)
			clusters.get(index[i]-1).add(i);   // index[i] is in {1, 2, ..., cnt}
		return clusters;
	}
	
	
	public static void main(String[] args) throws Exception{
		
	}
	
}



