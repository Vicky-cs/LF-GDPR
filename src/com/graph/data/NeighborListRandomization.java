package com.graph.data;

import java.util.Random;

public class NeighborListRandomization {
	
	public static boolean[][] randomize(boolean[][] mat, double epsilon){
		int userNum = mat[0].length;
		double p = Math.exp(epsilon)/(1+Math.exp(epsilon));
		Random rnd = new Random();
		boolean[][] mat_per = new boolean[userNum][userNum];
		for(int i=0; i<userNum; i++){
			for(int j=0; j<userNum; j++){
				if(rnd.nextDouble()>p){
					if(mat[i][j])
						mat_per[i][j] = false;
					else
						mat_per[i][j] = true;
				}else
					mat_per[i][j] = mat[i][j];
			}
		}
		return mat_per;
	}
	
	
	public static boolean[][] randomize_half(boolean[][] mat, double epsilon){
		int userNum = mat[0].length;
		double p = Math.exp(epsilon)/(1+Math.exp(epsilon));
		Random rnd = new Random();
		boolean[][] mat_per = new boolean[userNum][userNum];
		for(int i=0; i<userNum; i++){
			for(int j=i+1; j<userNum; j++){
				if(rnd.nextDouble()>p){
					if(mat[i][j])
						mat_per[i][j] = false;
					else
						mat_per[i][j] = true;
				}else{
					mat_per[i][j] = mat[i][j];
				}
				mat_per[j][i] = mat_per[i][j];
			}
		}
		return mat_per;
	}
	
	
	public static double[] calibrate_randomize_all_degree(double[] degree, double epsilon){
		int userNum = degree.length;
		double[] degree_cal = new double[userNum];
		for(int i=0; i<userNum; i++)
			degree_cal[i] = calibrate_randomize(degree[i], userNum, epsilon);
		return degree_cal;
	}
	
	
	public static double calibrate_randomize(double value, double sum, double epsilon){
		double res = 0.0;
		double p = Math.exp(epsilon)/(1.0+Math.exp(epsilon));
		res = (p-1)/(2*p-1)*sum + value/(2*p-1);
		if(res<0)
			res = 0;
		else if(res>sum)
			res = sum;
		return res;
	}
	
}
