package edu.siu.cs.FileIO.dam;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Stack;

public class DAMSmith {


	public boolean[][] generateRandomFile(int jobCount, int frequency, String FileName) {
		boolean[][] matrix = this.generateMatrix(jobCount, frequency);
		matrix = DAMFunctions.removeSelfDependencies(matrix);
		String result = smithFileContents(matrix);
		if(matrix.length>1000) {
			System.out.println("File exceeds 1000 entries, setting matrix to null to free up space (function will return null)...");
			matrix = null;
		} 
		System.out.println("Writing File To Disk");
		writeFile(result, FileName);
		return matrix;
	}
	
	private void writeFile(String contents, String FileName) {
		try {
			OutputStream stream = new FileOutputStream(FileName);
			BufferedOutputStream output = new BufferedOutputStream(stream,4096);
			for(int i = 0; i < contents.length(); i+=2048) {
				if(i+2048<contents.length())
					output.write(contents.substring(i, i+2048).getBytes());
				else
					output.write(contents.substring(i).getBytes());
			}
			System.out.println("Write Finished, Closing Stream");
			output.close();
			System.out.println("Stream closed");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String smithFileContents(boolean[][] matrix) {
		System.out.println("Assembling File Contents...");
		StringBuilder result = new StringBuilder("JOBCOUNT " + ((matrix[0].length>matrix.length)?matrix[0].length:matrix.length));
		Stack<Integer> stack = new Stack<Integer>();
		for(int i = 0; i < matrix[0].length; i++) {
			for(int j = 0; j < matrix.length; j++) {
				if(matrix[j][i]) {
					stack.push(new Integer(j));
				}
			}
			if(!stack.isEmpty()) {
				result.append("\n");
				result.append("PARENT " + i + " CHILD");
				while(stack.size()>0) {
					result.append(" " + stack.pop().toString());
				}
			}
			stack.clear();
		}
		return result.toString();
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
