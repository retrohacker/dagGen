package edu.siu.cs.dagGen;

public class DAMFileNotFoundException extends Exception {

	private static final long serialVersionUID = 3L;

	public DAMFileNotFoundException(String filename) {
		super("The DMF File \""+filename+"\" Could Not Be Located!");
	}
}
