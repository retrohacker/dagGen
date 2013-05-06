package edu.siu.cs.dagGen;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

class DAGFileParser {

	FileReader stream;
	int JobCount;
	boolean[][] dependancies;
	ArrayList<Integer> CurrentValues;
	String currentKeyword;
	
	public DAGFileParser(FileReader stream) {
		this.stream = stream;
		this.CurrentValues = new ArrayList<Integer>();
	}
	
	@SuppressWarnings("unused")
	private DAGFileParser() {
		//disable empty constructor
	}
	
	public void startParse() throws DAGOutOfBounds, IOException{
		//Begins parsing tokens from the stream
		boolean comment=false;
		StringBuilder buffer = new StringBuilder();
		int i;
		while((i=stream.read())!=-1) {
			char c=(char)i;
			if(c=='#') {
				comment=true;
			} else if(comment==true&&c=='\n') {
				comment = false;
			} else if(comment==true) {
				//If comment is true, we do nothing
			} else if(c!=' '&&c!='\n') {
				buffer.append(c);
			} else {
				token(buffer.toString());
				buffer = new StringBuilder();
			}
		}
		token(buffer.toString());
		this.EOF();
	}
	
	/**
	 * Called when the parser encounters a token
	 * @param token
	 * A single string parsed from the file
	 */
	protected void token(String token) throws DAGOutOfBounds{
		if(token.length()==0)
			return;
		int value = toInt(token);
		if(value>=0) {
			this.value(value);
		} else {
			this.keyword(token);
		}
	}
	
	/**
	 * Called by dmfParser when a keyword is encountered
	 * @param keyword
	 */
	protected void keyword(String keyword) {
		if(keyword.equalsIgnoreCase("jobcount")) {
			this.currentKeyword = "jobcount";
		} else if(keyword.equalsIgnoreCase("parent")) {
			this.CurrentValues = new ArrayList<Integer>();
			this.currentKeyword = "parent";
		} else if (keyword.equalsIgnoreCase("child")) {
			this.currentKeyword="child";
		} else {
			this.currentKeyword = "";
		}
	}
	
	/**
	 * Called by dmfParser when a value is encountered
	 * @param value
	 */
	protected void value(int value) throws DAGOutOfBounds {
		//Check current keyword
		if(this.currentKeyword.equalsIgnoreCase("jobcount")) {
			this.JobCount = value;
			this.generateMatrix();
		} else if(this.currentKeyword.equalsIgnoreCase("parent")) {
			if(value >= this.JobCount)
				throw new DAGOutOfBounds();
			this.CurrentValues.add(value);
		} else if(this.currentKeyword.equalsIgnoreCase("child")) {
			if(value >= this.JobCount)
				throw new DAGOutOfBounds();
			Iterator<Integer> it = this.CurrentValues.iterator();
			while(it.hasNext()) {
				this.dependancies[value][it.next().intValue()]=true;
			}
		}
	}
	
	/**
	 * Called when the end of the file is reached
	 */
	protected void EOF() {
	}
	
	/**
	 * Generates a jobcount x jobcount 2D matrix
	 */
	private void generateMatrix() {
		this.dependancies = new boolean[this.JobCount][this.JobCount];
	}
	
	/**
	 * Returns an integer if the string is an integer, else
	 * returns -1
	 * @param value
	 * @return
	 */
	private static int toInt(String value) {
		int result = 0;
		for(int i = (value.length()-1); i>=0;i--) {
			int c = (int)value.charAt(i);
			if(c<=57&&c>=48) {
				//C is a number
				c -= 48;
				result += c*(Math.pow(10, (value.length()-i-1)));
			} else {
				return -1;
			}
		}
		return result;
	}
	
	public boolean[][] getResult() {
		return this.dependancies;
	}
}
