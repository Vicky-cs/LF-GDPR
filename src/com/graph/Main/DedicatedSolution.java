package com.graph.Main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;

import com.graph.data.Data;
import com.graph.metric.Modularity;
import com.graph.metric.Tools;

public class DedicatedSolution {

	
	public DedicatedSolution() throws Exception{
		// dataset:
		// 1: facebook_combined.txt    
        // 2: Email-Enron.txt
        // 3: CA-AstroPh-transform.txt
        // 4: Brightkite_edges.txt (not used)
        // 5: twitter_combined_transform.txt (not used)
        // 6: gplus_combined_transform.txt
		int dataset = 1;
		double[] epsilon_all = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};
		
		for(int j=0; j<epsilon_all.length; j++){
			double MSE = 0;
			int userNum = Tools.inputFileUserNum[dataset-1];
			for(int i=0; i<userNum; i++){
				double noise = Tools.LaplaceDist(1.0, epsilon_all[j]);
				MSE += noise*noise;
			}
			MSE /= userNum;
			System.out.println(MSE);
		}
		
		getModularity_Laplace();
	}
	
	
	public void getModularity_Laplace() throws Exception{
		
		double[] real_modularity = {0.8355616101672708, 0.6231827808448482, 0.6367426374584712, 0.0, 0.0, 0.4771171516345318};
		
		int dataset = 6;
		String filename = Tools.inputFilename[dataset-1]+".txt";
		int userNum = Tools.inputFileUserNum[dataset-1];
		boolean[][] mat = new boolean[userNum][userNum];
		Data.readGraph(filename, mat); 
		double[] degree = Tools.getDegree(mat);
		
		String filename_cluster = "RealCluster-CommunityDetection/realCluster_"+dataset+".txt";
		ArrayList<HashSet<Integer>> clusters_real = Tools.readClusterFromFile(filename_cluster);
		
		
		double sensitivity = 2.0;
		
		double[] epsilon_all = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};		
		for(int e=0; e<epsilon_all.length; e++){
			double ep = epsilon_all[e]/50.0;
			
			double modularity = 0;
			
			// add Laplace noise to each node degree with half of privacy budget
			for(int i=0; i<degree.length; i++)
				degree[i] += Tools.LaplaceDist(sensitivity, ep/2.0);
			double sum_edges = 0;
			for(int i=0; i<degree.length; i++)
				sum_edges += degree[i];
			sum_edges /= 2.0;
			
			for(int i=0; i<clusters_real.size(); i++){
				HashSet<Integer> cluster = clusters_real.get(i);
				double in_edges_community = 0;
				double in_degree = 0;
				for(int node : cluster){
					double in_edges_node = 0;
					for(int j=0; j<userNum; j++){
						if(mat[node][j] && cluster.contains(j))
							in_edges_node++;
					}
					// add Laplace noise to intra-edge numbers with half of privacy budget
					in_edges_node += Tools.LaplaceDist(sensitivity, ep/2.0);
					
					in_edges_community += in_edges_node;
					in_degree += degree[node];
				}
				in_edges_community /= 2.0;
				
				modularity += in_edges_community/sum_edges - (in_degree/2.0/sum_edges)*(in_degree/2.0/sum_edges);				
			}
			System.out.println(Math.abs(modularity-real_modularity[dataset-1])/real_modularity[dataset-1]);
		}		
	}
	
	
	
	public double[] readClusteringCoefficient(String filename, int dataset) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(filename));
		double[] cc = new double[Tools.inputFileUserNum[dataset-1]];
		int cnt = 0;
		String str = "";
		while((str = br.readLine())!=null)
			cc[cnt++] = Double.parseDouble(str);
		br.close();
		return cc;
	}
	
	
	public static void main(String[] args) throws Exception{
		new DedicatedSolution();
	}
}



