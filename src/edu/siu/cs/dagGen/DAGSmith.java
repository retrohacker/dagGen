package edu.siu.cs.dagGen;

public class DAGSmith {


	public boolean[][] generateRandomFile(int jobCount, int frequency, String FileName) {
		boolean[][] matrix = this.generateRandomDAG(jobCount, frequency);
		DAGTools.saveToFile(matrix,FileName);
		return matrix;
	}
	
	public boolean[][] generateRandomDAG(int jobCount, int frequency) {
		boolean[][] matrix = this.generateMatrix(jobCount, frequency);
		matrix = DAGFunctions.removeSelfDependencies(matrix);
		return matrix;
	}
	
	private boolean[][] generateMatrix(int jobCount, int frequency) {
		System.out.println("Generating Matrix...");
		boolean[][] result = new boolean[jobCount][jobCount];
		for(int i = 0; i < jobCount; i++) {
			for(int j = 0; j < jobCount; j++)
				if(i!=j)
					result[i][j] = random(frequency);
		}
		return result;
	}
	
	private boolean random(int frequency) {
		return  ((int) (Math.random() * (frequency+1))%frequency==0)? true : false;
	}
}
