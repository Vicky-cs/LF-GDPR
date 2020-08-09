package com.graph.metric;

public class ClusteringCoefficient {
 	
 	public static double getLocalClusteringCoefficient(boolean[][] mat){	
 		double[] lcc = getLocalClusteringCoefficientList(mat);
 		double totalLocal = 0.0;
 		for(double oneLCC : lcc)
			totalLocal += oneLCC;
 		return totalLocal/mat[0].length;
 	}
 	
 	public static double[] getLocalClusteringCoefficientList(boolean[][] mat){	
 		int userNum = mat[0].length;
 		double[] lcc = new double[userNum];
 		double[] degree = Tools.getDegree(mat);
 		double[] triangle = getTriangle(mat);		
 		for(int i=0; i<userNum; i++)
 			lcc[i] = oneLocalCofficient(triangle[i], degree[i]);
 		return lcc;
 	}
	
	public static double oneLocalCofficient(double triangle, double degree){
		if(degree==0||degree==1) return 0;
		return 2.0*triangle/(degree*(degree-1));
	}
	
	public static double[] getTriangle(boolean[][] mat){
		int userNum = mat[0].length;
 		double[] triangle = new double[userNum];		
// 		long start = System.currentTimeMillis();		
 		for(int i=0; i<userNum; i++){
 			if(i%10000==0 && i!=0)
				System.out.print(i+" ");
 			int[] neighbors = new int[userNum];
 	 		int cnt = 0;
 			for(int j=0; j<userNum; j++){
 				if(mat[i][j]==true)
 					neighbors[cnt++] = j;
 			}
 			for(int j=0; j<cnt; j++){
 				for(int k=j+1; k<cnt; k++)
 					if(mat[neighbors[j]][neighbors[k]]==true)
 						triangle[i]++;
 			}
 		}
 //		System.out.println();
//		System.out.print("Execution time: ");
//		System.out.println(System.currentTimeMillis()-start);	 		
 		return triangle;
	}
	
}

