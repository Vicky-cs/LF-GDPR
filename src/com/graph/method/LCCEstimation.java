package com.graph.method;

import com.graph.metric.ClusteringCoefficient;
import com.graph.metric.Tools;

public class LCCEstimation {
	
	public static double[] LCC_SixCases_list(boolean[][] noisyMat, double[] noisyDegree, double epsilon, double percentage) {
		int userNum = noisyMat[0].length;
		double edgeDensity = Tools.getEdges(noisyMat)/(0.5*userNum*(userNum-1));
		double[] lcc = new double[userNum];
		double[] first_second = new double[userNum];
		double[] third_fourth = new double[userNum];
		double[] fifth_sixth = new double[userNum];	
		
		double p = Math.exp(epsilon*percentage)/(1.0+Math.exp(epsilon*percentage));
		for(int i=0; i<userNum; i++){
			third_fourth[i] = noisyDegree[i]*(userNum-noisyDegree[i]-1)*p*(1-p)*edgeDensity;
			fifth_sixth[i] = 0.5*(userNum-noisyDegree[i]-1)*(userNum-noisyDegree[i]-2)*Math.pow(1-p, 2)*edgeDensity; 
		}
		double[] perturbedTriangle = ClusteringCoefficient.getTriangle(noisyMat);
		for(int i=0; i<userNum; i++){
			if(third_fourth[i]+fifth_sixth[i] < perturbedTriangle[i])
				first_second[i] = perturbedTriangle[i] - third_fourth[i] - fifth_sixth[i];
		}
		for(int i=0; i<userNum; i++){
			double estimatedTriangle = ( 2.0*first_second[i]-noisyDegree[i]*(noisyDegree[i]-1)*p*p*(1-p) ) / (2.0*Math.pow(p, 2)*(2*p-1));
			estimatedTriangle = Math.max(estimatedTriangle, 0);
			if(noisyDegree[i]>1){
				double onelcc = 2.0*estimatedTriangle / (noisyDegree[i]*(noisyDegree[i]-1));				
//				lcc[i] = Math.min(onelcc, 1.0);
				if(onelcc>1.0)
					lcc[i] = 0.5;
				else
					lcc[i] = onelcc;
			}
		}		
		return lcc;
	}
	
	
	// Aligned Perturbation
	public static double[] LCC_SixCases_list_Alignment(boolean[][] noisyMat, double[] noisyDegree, double epsilon, double percentage){
		int userNum = noisyMat[0].length;
		double edgeDensity = Tools.getEdges(noisyMat)/(0.5*userNum*(userNum-1));
		double[] lcc = new double[userNum];
		double[] first_second = new double[userNum];
		double[] third_fourth = new double[userNum];
		double[] fifth_sixth = new double[userNum];	
	
		double p = Math.exp(epsilon*percentage)/(1.0+Math.exp(epsilon*percentage));
		for(int i=0; i<userNum; i++){
			third_fourth[i] = noisyDegree[i]*(userNum-noisyDegree[i]-1)*p*p*(1-p)*(1-p)*edgeDensity;
			fifth_sixth[i] = 0.5*(userNum-noisyDegree[i]-1)*(userNum-noisyDegree[i]-2)*Math.pow(1-p, 4)*edgeDensity;
		}
		double[] perturbedTriangle = ClusteringCoefficient.getTriangle(noisyMat);
		for(int i=0; i<userNum; i++){
			if(third_fourth[i] + fifth_sixth[i] < perturbedTriangle[i])
				first_second[i] = perturbedTriangle[i] - third_fourth[i] - fifth_sixth[i];
		}
		for(int i=0; i<userNum; i++){
			double estimatedTriangle = ( 2.0*first_second[i]-noisyDegree[i]*(noisyDegree[i]-1)*Math.pow(p, 4)*Math.pow(1-p, 2) ) / (2.0*Math.pow(p, 4)*(2*p-1.0));
			estimatedTriangle = Math.max(estimatedTriangle, 0);
			if(noisyDegree[i]>1){				
				double onelcc = 2.0*estimatedTriangle / (noisyDegree[i]*(noisyDegree[i]-1));								
				lcc[i] = Math.min(onelcc, 1.0);
			}
		}
		return lcc;
	}
	
	
	public static double LCC_SixCases(boolean[][] noisyMat, double[] noisyDegree, double epsilon, double percentage) {
		double[] lccs = LCC_SixCases_list(noisyMat, noisyDegree, epsilon, percentage);
		double lcc = 0;
		for(double onelcc : lccs)
			lcc += onelcc;
		return lcc/lccs.length;
	}
	
}
