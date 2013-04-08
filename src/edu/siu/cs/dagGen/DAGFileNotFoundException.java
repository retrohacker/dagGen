package edu.siu.cs.dagGen;

public class DAGFileNotFoundException extends Exception {

	private static final long serialVersionUID = 3L;

	public DAGFileNotFoundException(String filename) {
		super("The DMF File \""+filename+"\" Could Not Be Located!");
	}
}
