package com.graph.data;

import com.graph.metric.Tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.HashMap;

public class Data {
	
	public static void readGraph(String filename, boolean[][] mat) throws Exception{
		// clear the matrix
		for(int i=0; i<mat.length; i++)
			for(int j=0; j<mat[0].length; j++)
				mat[i][j] = false;

		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line;		    
		while((line = br.readLine())!=null){
			String[] node = line.split(",");
			if(node.length==1)
				node = line.split(" ");
			if(node.length==1)
				node = line.split("\t");
			if(node.length!=2){
				System.out.println("line !=2!!");
				throw new RuntimeException("File Input Error!"+line+","+node.length+","+node[0]+","+"\n");	
			}
			int sNode = Integer.parseInt(node[0]);
			int dNode = Integer.parseInt(node[1]);
			mat[sNode][dNode] = true;
			mat[dNode][sNode] = true;
		}
		br.close();
	}
	
	// pre-processing: AstroPh
	public static void readGraph_AstroPh(String filename, int edgeNum) throws Exception{
		HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
		int cnt = 0;
		int[][] array = new int[edgeNum][2];
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line;		    
		while((line = br.readLine())!=null){
			String[] node = line.split(",");
			if(node.length==1)
				node = line.split(" ");
			if(node.length==1)
				node = line.split("\t");
			if(node.length!=2){
				System.out.println("line !=2!!");
				throw new RuntimeException("File Input Error!"+line+","+node.length+","+node[0]+","+"\n");	
			}
			array[cnt][0] = Integer.parseInt(node[0]);
			array[cnt][1] = Integer.parseInt(node[1]);
			cnt++;
		}
		br.close();
		
		cnt = 0;
		for(int i=0; i<edgeNum; i++){
			if(!hm.containsKey(array[i][0]))
				hm.put(array[i][0], cnt++);
			if(!hm.containsKey(array[i][1]))
				hm.put(array[i][1], cnt++);
		}
		System.out.println("userNum="+cnt);
		
		PrintStream ps = new PrintStream(new File("CA-AstroPh-transform.txt"));
		for(int i=0; i<edgeNum; i++){
			int first = hm.get(array[i][0]); 
			int second = hm.get(array[i][1]); 
			ps.println(first+"\t"+second);
		}
		ps.close();
	}
	
	
	//pre-processing: twitter and google
	public static void readGraph_twitter_google(String filename, boolean[][] mat) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		String line;	
		int cnt = 0;
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		while((line = br.readLine())!=null){
			String[] nodes = line.split(" ");
			if(!map.containsKey(nodes[0]))
				map.put(nodes[0], cnt++);
			if(!map.containsKey(nodes[1]))
				map.put(nodes[1], cnt++);
		}
		System.out.println("number of nodes = "+cnt);
		br.close();
		
		br = new BufferedReader(new FileReader(filename));
		while((line = br.readLine())!=null){
			String[] nodes = line.split(" ");
			mat[map.get(nodes[0])][map.get(nodes[1])] = true;
			mat[map.get(nodes[1])][map.get(nodes[0])] = true;
		}
		br.close();
		
		int edges = 0;
		PrintStream ps = new PrintStream(new File("gplus_combined_transform.txt"));
		for(int i=0; i<mat[0].length; i++){
			for(int j=i+1; j<mat[0].length; j++){
				if(mat[i][j]){
					edges++;
					ps.println(i+"\t"+j);
				}
			}
		}
		ps.close();
		System.out.println("number of edges = "+edges);
	}
	
	public static void main(String args[]) throws Exception{
		
		int dataset = 3;
		String filename = Tools.inputFilename[dataset-1]+".txt";
		int userNum = Tools.inputFileUserNum[dataset-1];
		boolean[][] mat = new boolean[userNum][userNum];	
		Data.readGraph(filename, mat);	
		double[] degree = Tools.getDegree(mat);
		
		Tools.getPopularDegree(degree);
		
//		// calculate the edge density
//		double sum = Tools.getEdges(mat);
//		System.out.println("edges="+sum);
//		System.out.println("edge density="+2*sum/userNum/(userNum-1));		
//		
//		
//		// average of the degree
//		double sum_degree = 0.0;
//		for(int i=0; i<degree.length; i++)
//			sum_degree += degree[i];
//		System.out.println("average degree: "+sum_degree/userNum);
//		
//		// median of degree
//		Arrays.sort(degree);
//		System.out.println("median of degree: "+degree[userNum/2]);
//				
//		// find the most popular degree	
//		int[] degreeCount = new int[userNum];
//		for(int i=0; i<userNum; i++)
//			degreeCount[(int) degree[i]]++;
//		int max = 0;
//		int index = 0;
//		for(int i=0; i<userNum; i++){
//			if(max<degreeCount[i]){
//				max = degreeCount[i];
//				index = i;		
//			}
//		}
//		System.out.println("most frequent degree: "+index);		
			
		
	}
}
