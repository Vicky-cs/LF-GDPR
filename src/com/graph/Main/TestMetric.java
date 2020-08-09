package com.graph.Main;

import com.graph.data.Data;
import com.graph.data.NeighborListRandomization;
import com.graph.method.LCCEstimation;
import com.graph.metric.ClusteringCoefficient;
import com.graph.metric.Modularity;
import com.graph.metric.Tools;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class TestMetric {
	public int userNum;
	public boolean[][] mat;
	
	public static int dataset = 1;    // 1: facebook_combined.txt    
					                  // 2: Email-Enron.txt
	                                  // 3: CA-AstroPh-transform.txt
	                                  // 4: Brightkite_edges.txt (not used)
	                                  // 5: twitter_combined_transform.txt (not used)
	                                  // 6: gplus_combined_transform.txt

	static int metric = 1;     // 1: local clustering coefficient 
					           // 2: modularity 
	
	int init = 1;              // 0: the first run
						       // 1: has got the original real coefficient
	
	public static double epsilon = 1.0;
	public static double percentageForMatrix = 0.9;
	
	
	
	public static int type = 8; 

	
	public TestMetric(int dataset, double per, double[] epsilon_all) throws Exception{
		TestMetric.dataset = dataset;
		TestMetric.percentageForMatrix = Tools.optimalPercentage[dataset-1][metric-1][(int)epsilon-1];
		String filename = Tools.inputFilename[TestMetric.dataset-1]+".txt";
		this.userNum = Tools.inputFileUserNum[TestMetric.dataset-1];
		mat = new boolean[userNum][userNum];
		Data.readGraph(filename, mat); 
			
		
//		double ori_coefficient = getRealCoefficient(mat);
//		System.out.println(ori_coefficient);
		
	
		/*************************************   Clustering coefficient estimation   ***************************************/
//		writeRealCoefficientToFile(); // write real clustering coefficient to file
		testLCCEstimation(mat, type, epsilon_all, per, true);    	 // use optimal alpha : true/false 
//		testLCCEstimation_baseline(mat, type, epsilon_all, false);   // calibration : true/false
//		testLCCEstimation_baseline2(mat, type);	
				
		
		/*******************************************   Community detection   ************************************************/
//		testCommunityDetection(mat, TestMetric.type);
//		testCommunityDetection_cases(mat, epsilon_all, TestMetric.type);
		
	}
	
	
	public double getRealCoefficient(boolean[][] mat){
		double ori_coefficient = 0.0;
		if(dataset == 1){       //dataset: facebook_combined.txt
			if(metric == 1){       //local Clustering Coefficient
				if(init == 0)
					ori_coefficient = ClusteringCoefficient.getLocalClusteringCoefficient(mat);
				else if(init == 1)
					ori_coefficient = 0.6055467186200871;
			}else if(metric == 2){  //Modularity
				if(init == 0){
					Modularity modu = new Modularity(mat, Tools.getDegree(mat));
					ArrayList<HashSet<Integer>> clusters = modu.searchCluster();
					ori_coefficient = modu.getModularity(clusters, 1, 0);   // when type=1/2/3, epsilon=0 does not make sense
				}
				else if(init == 1)
					ori_coefficient = 0.0;
			}
		}else if(dataset == 2){ //dataset: Email-Enron.txt
			if(metric == 1){       //local Clustering Coefficient
				if(init == 0)
					ori_coefficient = ClusteringCoefficient.getLocalClusteringCoefficient(mat);
				else if(init == 1)
					ori_coefficient = 0.496982559599502;   
			}else if(metric == 2){  //Modularity
				if(init == 0){
					Modularity modu = new Modularity(mat, Tools.getDegree(mat));
					ArrayList<HashSet<Integer>> clusters = modu.searchCluster();
					ori_coefficient = modu.getModularity(clusters, 1, 0);   // when type=1/2/3, epsilon=0 does not make sense
				}
				else if(init == 1)
					ori_coefficient = 0.03258547321232037;
			}
		}else if(dataset == 3){ //dataset: CA-AstroPh-transform.txt
			if(metric == 1){       //local Clustering Coefficient
				if(init == 0)
					ori_coefficient = ClusteringCoefficient.getLocalClusteringCoefficient(mat);
				else if(init == 1)
					ori_coefficient = 0.6312765119298741;
			}else if(metric == 2){  //Modularity
				if(init == 0){
					Modularity modu = new Modularity(mat, Tools.getDegree(mat));
					ArrayList<HashSet<Integer>> clusters = modu.searchCluster();
					ori_coefficient = modu.getModularity(clusters, 1, 0);   // type=1/2/3, representing the real modularity, epsilon=0 does not make sense
				}
				else if(init == 1)
					ori_coefficient = 0.010855684580810241;
			}
		}else if(dataset == 4){ //dataset: Brightkite_edges.txt
			if(metric == 1){       //local Clustering Coefficient
				if(init == 0)
					ori_coefficient = ClusteringCoefficient.getLocalClusteringCoefficient(mat);
				else if(init == 1)
					ori_coefficient = 0.17232592744613315;   
			}else if(metric == 2){  //Modularity
				if(init == 0){
					Modularity modu = new Modularity(mat, Tools.getDegree(mat));
					ArrayList<HashSet<Integer>> clusters = modu.searchCluster();
					ori_coefficient = modu.getModularity(clusters, 1, 0);   // type=1/2/3, representing the real modularity, epsilon=0 does not make sense
				}
				else if(init == 1)
					ori_coefficient = 0.010556609265653473;
			}
		}else if(dataset == 5){ //dataset: Brightkite_edges.txt
			if(metric == 1){       //local Clustering Coefficient
				if(init == 0)
					ori_coefficient = ClusteringCoefficient.getLocalClusteringCoefficient(mat);
				else if(init == 1)
					ori_coefficient = 0.565311468612065;   
			}else if(metric == 2){  //Modularity
				if(init == 0){
					Modularity modu = new Modularity(mat, Tools.getDegree(mat));
					ArrayList<HashSet<Integer>> clusters = modu.searchCluster();
					ori_coefficient = modu.getModularity(clusters, 1, 0);   // type=1/2/3, representing the real modularity, epsilon=0 does not make sense
				}
				else if(init == 1)
					ori_coefficient = 0.0;
			}
		}else if(dataset == 6){ //dataset: Brightkite_edges.txt
			if(metric == 1){       //local Clustering Coefficient
				if(init == 0)
					ori_coefficient = ClusteringCoefficient.getLocalClusteringCoefficient(mat);
				else if(init == 1)
					ori_coefficient = 0.4901182850976543;   
			}else if(metric == 2){  //Modularity
				if(init == 0){
					Modularity modu = new Modularity(mat, Tools.getDegree(mat));
					ArrayList<HashSet<Integer>> clusters = modu.searchCluster();
					ori_coefficient = modu.getModularity(clusters, 1, 0);   // type=1/2/3, representing the real modularity, epsilon=0 does not make sense
				}
				else if(init == 1)
					ori_coefficient = 0.0;
			}
		}
		return ori_coefficient;
	}
	
	
	public void writeRealCoefficientToFile() throws Exception{
		double[] coefficient = ClusteringCoefficient.getLocalClusteringCoefficientList(this.mat);
		String filename = "ClusteringCoefficientEstimation/RealClusteringCoefficient/RealClusteringCoefficient_"+this.dataset+".txt";
		PrintStream ps = new PrintStream(new File(filename));
		for(int i=0; i<coefficient.length; i++)
			ps.println(coefficient[i]);
		ps.close();
	}
	
	
    
    // estimate the clustering coefficient of each node with a certain epsilon in epsilon_all, and write each result to file for each epsilon
    public void testLCCEstimation(boolean[][] mat, int type, double[] epsilon_all, double per, boolean optimal) throws Exception{
    	for(int i=0; i<epsilon_all.length; i++){
    		double ep = epsilon_all[i];
    		if(optimal)
    			per = Tools.optimalPercentage[dataset-1][metric-1][(int)ep-1];
    		System.out.println("dataset="+dataset+"\t epsilon="+ep+"\t percentage="+per);
    		testLCCEstimation_list(mat, ep, per, type);
    	}
    }
	
	// For the case with certain epsilon and percentage
    public void testLCCEstimation_list(boolean[][] mat, double epsilon, double percentage, int type) throws Exception{
    	double[] degree = Tools.getDegree(mat);    	
		double[] noisyDegree1 = Tools.addLaplaceNoise_Degree(degree, epsilon*(1.0-percentage));
		boolean[][] perturbedMat = null;
		double[] perturbed_coefficient = null;
		if(type==8){          // triangular data
			perturbedMat = NeighborListRandomization.randomize_half(mat, epsilon*percentage);
			double[] noisyDegreeFromVector = NeighborListRandomization.calibrate_randomize_all_degree(Tools.getDegree(perturbedMat), epsilon*percentage);
			double variance1 = 8.0/(epsilon*(1.0-percentage)*epsilon*(1.0-percentage));
			double[] variance2 = Tools.getVariance_list(noisyDegree1, epsilon*percentage);
			double[] noisyDegree = Tools.mergeTwoNoisyDegree_list(noisyDegree1, variance1, noisyDegreeFromVector, variance2);
			
			perturbed_coefficient = LCCEstimation.LCC_SixCases_list(perturbedMat, noisyDegree, epsilon, percentage);
		}
		
		String filename = "";
		if(percentage==0.3 || percentage==0.5 || percentage==0.7 || percentage==0.9){
			int flag = (int) ((percentage)*100+(1-percentage)*10);   // for fixed alpha: 0.3->37  0.5->55   0.7->73   0.9->91
			filename = "ClusteringCoefficientEstimation/Fix_alpha/ClusteringCoefficient_"+flag+"_"+dataset+"_"+epsilon+".txt";
		} else {
			filename = "ClusteringCoefficientEstimation/LF_GDPR/ClusteringCoefficient_"+dataset+"_"+epsilon+".txt";
		}
		PrintStream ps = new PrintStream(new File(filename));
		for(int i=0; i<mat[0].length; i++)
			ps.println(perturbed_coefficient[i]);
		ps.close();
    }
    
    
    
    
    /*************************  Baseline method (improved version of RNL, 
         where all privacy budget is for vector perturbation, and graph is generated only by vector)  **********************/
    public void testLCCEstimation_baseline2(boolean[][] mat, int type) throws Exception{
    	double[] epsilon_all = {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0};    	
		for(int j=0; j<epsilon_all.length; j++){
			double epsilon = epsilon_all[j];
			System.out.println("********  epsilon="+epsilon+"  ********");
			boolean[][] perturbedMat = null;
			if(type==8)       // triangular data
				perturbedMat = NeighborListRandomization.randomize_half(mat, epsilon);
			double[] perturbed_coefficient = ClusteringCoefficient.getLocalClusteringCoefficientList(perturbedMat);
			String filename = "ClusteringCoefficientEstimation/Baseline2_no_degree/Baseline2_ClusteringCoefficient_"+this.dataset+"_"+epsilon+".txt";
			Tools.writeVectorToFile(filename, perturbed_coefficient);
		}
    }
    
 
    
    
    /*************************  Baseline method (noisy degree is calculated and calibrated (or not) from perturbed matrix)   **********************/
    public void testLCCEstimation_baseline(boolean[][] mat, int type, double[] epsilon_all, boolean calibrated) throws Exception{
		for(int j=0; j<epsilon_all.length; j++){
			double epsilon = epsilon_all[j];
			System.out.println("********   dataset="+dataset+"\t epsilon="+epsilon+"  ********  ");
//			double[] perturbed_coefficient = testLCCEstimation_baseline(mat, epsilon_all[j], type, true);	 // noisy degree is calculated and calibrated from perturbed matrix
			double[] perturbed_coefficient = baseline_oneNode(mat, epsilon_all[j], type, calibrated);  // noisy degree is directly calculated from perturbed matrix
			String filename = "ClusteringCoefficientEstimation/Baseline_no_calibration_degree/ClusteringCoefficient_"+this.dataset+"_"+epsilon+".txt";
			Tools.writeVectorToFile(filename, perturbed_coefficient);
		}
    }
   
    
    /*************************  Baseline method for one node (noisy degree is calculated and calibrated (or not) from perturbed matrix)  **********************/
    public double[] baseline_oneNode(boolean[][] mat, double epsilon, int type, boolean calibrated){
    	boolean[][] perturbedMat = null;
		double[] perturbed_coefficient = new double[mat[0].length];
		double[] noisyDegree;
		if(type==8){          // triangular data
			perturbedMat = NeighborListRandomization.randomize_half(mat, epsilon);
			noisyDegree = Tools.getDegree(perturbedMat);
			
			if(calibrated){
				for(int i=0; i<noisyDegree.length; i++)
					noisyDegree[i] = NeighborListRandomization.calibrate_randomize(noisyDegree[i], noisyDegree.length, epsilon);
			}
			perturbed_coefficient = LCCEstimation.LCC_SixCases_list(perturbedMat, noisyDegree, epsilon, 1.0);
		}
		return perturbed_coefficient;
    }
    
    
	// test the graph after neighbor list randomization
	public void testNeighborListRandomization(double ori_coefficient, boolean[][] mat){		
		double[] epsilon_all = {8.0, 7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0, 0.5, 0.0};
		for(int i=0; i<epsilon_all.length; i++){
			TestMetric.epsilon = epsilon_all[i];
			System.out.println("epsilon="+TestMetric.epsilon);
			NeighborListRandomization.randomize_half(mat, epsilon);
			double new_coefficient = 0.0;
			if(metric == 1){       //1:local Clustering Coefficient 
				new_coefficient = ClusteringCoefficient.getLocalClusteringCoefficient(mat);
			}else if(metric == 2){  //2:modularity
				new_coefficient = 0.0; 
				System.out.println("Need to implement the calculation of modularity!!!");
			}
			double relErr = Math.abs(ori_coefficient-new_coefficient)/Math.abs(ori_coefficient);
			System.out.println(ori_coefficient+"  "+new_coefficient+"    relative error="+relErr);
		}
	}
	
	
	public static void main(String[] args){
		try {			
			int dataset = 2;
			double fixPercentage = 0.3;
			double[] epsilon_all = {8.0, 7.0, 6.0, 5.0, 4.0, 3.0, 2.0, 1.0};

			new TestMetric(dataset, fixPercentage, epsilon_all);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
