package edu.siu.cs.dagGen;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Stack;

public class DAGTools {
	
	public static String printDAG(boolean[][] matrix) {
		System.out.println("Generating String For STD Output...");
		StringBuilder result = new StringBuilder(matrix.length*(matrix[0].length*3));
		for(int i = 0; i < matrix.length; i++) {
			result.append("|");
			for(int j = 0; j<matrix[0].length;j++) {
				char c;
				if(matrix[i][j])
					c = '1';
				else
					c = '0';
				result.append(c+"|");
			}
			result.append("\n");
		}
		
		return result.toString();
	}
	
	public static int getEdges(boolean[][] matrix) {
		int count = 0;
		for(int i = 0; i < matrix.length; i++)
			for(int j = 0; j < matrix[0].length; j++)
				if(matrix[i][j]) count++;
		return count;
	}

	
	public static void saveToFile(boolean[][] matrix, String FileName) {
		System.out.println("Smithing File Contents...");
		String result = smithFileContents(matrix);
		System.out.println("Writing File To Disk");
		writeFile(result, FileName);
	}
	
	private static String smithFileContents(boolean[][] matrix) {
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
	
	private static void writeFile(String contents, String FileName) {
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
}
