package edu.siu.cs.dagGen;

public class DAMSelfDependent extends Exception {

	private static final long serialVersionUID = 1L;

	public DAMSelfDependent() {
		super("DAM file contains a job that depends on itself!");
	}
}
