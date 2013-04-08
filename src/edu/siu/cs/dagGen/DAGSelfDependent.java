package edu.siu.cs.dagGen;

public class DAGSelfDependent extends Exception {

	private static final long serialVersionUID = 1L;

	public DAGSelfDependent() {
		super("DAM file contains a job that depends on itself!");
	}
}
