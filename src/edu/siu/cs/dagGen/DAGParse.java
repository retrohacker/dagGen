package edu.siu.cs.dagGen;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class DAGParse {
	
	/**
	 * This function will generate a 2D matrix representing the
	 * edges of a directional digraph were 1 means the job
	 * corresponding to the row number depends on the job
	 * corresponding to the col number and 0 means the job 
	 * corresponding to the row number does not depend on the job
	 * corresponding to the col number.
	 * <br><br> 
	 * For clarification:<br>
	 * In the matrix matrix[i][j] <br>
	 * i = child <br>
	 * j = parent <br>
	 * OR you could say: job i depends on job j <br> 
	 * @param filename
	 * The file name relative to the program that the
	 * Dependancy Mapping File will be imported from
	 * @return
	 * The array representing job dependancies.
	 * @throws DAGFileNotFoundException 
	 */
	public boolean[][] parseDependancies(String filename) throws DAGFileNotFoundException, DAGSelfDependent, DAGOutOfBounds, IOException {
		System.out.println("Opening File to Parse");
		FileReader file = DAGParse.openFile(filename);
		DAGParser parse = new DAGParser(file);
		System.out.println("File Opened, Parsing File");
		parse.startParse();
		System.out.println("File Parsed and Closed");
		return parse.getResult();
	}
	
	/**
	 * Returns a pointer to a character file stream
	 * @param filename
	 * @return
	 */
	private static FileReader openFile(String filename) throws DAGFileNotFoundException {
		try {
			return new FileReader(filename);
		} catch (FileNotFoundException e) {
			throw new DAGFileNotFoundException(filename);
		}
		
	}
}
