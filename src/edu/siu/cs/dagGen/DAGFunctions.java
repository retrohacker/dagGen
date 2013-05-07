package edu.siu.cs.dagGen;

import java.util.Iterator;
import java.util.Stack;

class DAGFunctions {
	
	public static boolean log = true;
	
	public static boolean[][] resolveDependancies(boolean[][] matrix) {
		boolean[][] result = new boolean[matrix.length][matrix[0].length];
		for(int i = 0;i<matrix.length;i++) {
			for(int j=0;j<matrix[0].length;j++) {
				if(matrix[i][j])
					result[i][j]=true;
				else
					result[i][j]=false;
			}
		}
		boolean cleanPass = false;
		//Iterate through each job
		while(!cleanPass) {
			cleanPass = true;
			for(int i = 0; i < result.length; i++) {
				//iterate through each dependency
				for(int j = 0; j < result[0].length; j++) {
					if(result[i][j]) {
						//resolve the dependencies of the dependency
						for(int k = 0; k < result[0].length; k++) {
							if(!result[i][k]&&result[j][k]) {
								cleanPass = false;
								result[i][k] = result[j][k];
							}
						}
					}
				}
			}
		}
		return result;
	}
	
	/**
	 * Throws an error if the file does not meet the criteria for being an acyclic
	 * digraph.
	 * 
	 * @param matrix
	 * Workflow Dependency Matrix
	 * @throws DAGSelfDependent
	 * If a cloudlet depends on itself
	 */
	public static void integrityCheck(boolean[][] matrix) throws DAGSelfDependent {
		if(log)
			System.out.print("Checking Integrity of DAG File...");
		boolean[][] temp = DAGFunctions.resolveDependancies(matrix);
		if(temp.length>0) {
			for(int i = 0;i<temp.length&&i<temp[0].length;i++) {
				//if job depends on self
				if(temp[i][i]) {
					throw new DAGSelfDependent();
				}
			}
		}
		if(log)
			System.out.println(" File Passed Integrity Check!");
	}
	
	//Used for the recursive part of removeSelfDependencies()
	private static IntegerStack depends = new IntegerStack();
	
	/**
	 * Navigates the matrix and removes any self dependencies.
	 * @deprecated
	 */
	public static boolean[][] oldRemoveSelfDependencies(boolean[][] matrix) {
		if(log)
			System.out.println("Removing Dependencies...");
		for(int i = 0; i < matrix.length; i++) {
			if(log)
				System.out.println("Checking Row "+i);
			depends.push(i);
			buildStack(matrix, i);
			depends.clear();
		}
		return matrix;
	}
	
	public static boolean[][] removeSelfDependencies(boolean[][] matrix) {
		TreeNode[] modules = DAGFunctions.generateTree(matrix);
		modules = DAGFunctions.shuffleTree(modules);
		if(modules==null)
			return null;
		return cleanTree(modules,matrix);
	}
	
	private static TreeNode[] shuffleTree(TreeNode[] tree) {
		if(log)
			System.out.println("Shuffling Tree References...");
		for(int i=0;i<tree.length;i++) {
			tree[i].shuffle();
		}
		return tree;
	}
	
	/**
	 * Removes any circular references from a dependency adjacency matrix using
	 * its tree representation.
	 * @return
	 * A cleaned tree data structure
	 */
	private static boolean[][] cleanTree(TreeNode[] tree, boolean[][] matrix) {
		if(log)
			System.out.println("Removing Self Dependencies Using Tree...");
		//Keeps track of which nodes have been checked for circular references
		boolean[] checked = new boolean[tree.length];
		
		//Keeps track of the path taken to get to a node
		Stack<TreeNode> path = new Stack<TreeNode>();
		for(int j=0; j < checked.length; j++) {
			if(!checked[j]) {
				path.add(tree[j]);
				while(path.size()!=0) {
					//Get the next step in the path
					TreeNode next = path.peek().getNext();
					if(next!=null) {
						//If the node is already in the path, sever the reference
						//This is/else loop balances the table
						if(path.contains(next)) {
							matrix[next.id][path.peek().id] = false;
						} else {
							//Only do this step if node has not been checked already
							if(checked[next.id])
								continue;
							//Add it to the current path
							path.push(next);
						}
					} else {
						//If we have reached the end of this branch, traverse back up
						checked[path.pop().id] = true;
					}
				}
			}
		}
		return matrix;
	}
	
	/**
	 * Generates a tree datastructure that represents the workflow of modules
	 * derived from a dependency adjacency matrix.
	 * @param matrix
	 * @return
	 */
	private static TreeNode[] generateTree(boolean[][] matrix) {
		if(log)
			System.out.println("Building Tree Data Structure...");
		//Get the number of nodes we will be generating
		int size = matrix.length;
		if(matrix.length>0)
			size = (size>matrix[0].length) ? size : matrix[0].length;
		else
			return null;
		
		//Generate array of nodes
		TreeNode[] result = new TreeNode[size];
		for(int i=0;i<result.length;i++){
			result[i] = new TreeNode(i);
		}
		
		//Populate array of nodes
		for(int j = 0; j < matrix[0].length; j++) {
			for(int i = 0; i < matrix.length; i++) {
				if(matrix[i][j]) {
					result[j].children.add(result[i]);
				}
			}
		}
		
		return result;
	}
	
	private static void buildStack(boolean[][] matrix, int row) {
		for(int j = 0; j < matrix[row].length; j++) {
			if(matrix[row][j]) {
				if(depends.contains(new Integer(j))) {
					matrix[row][j] = false;
				} else {
					depends.push(new Integer(j));
					buildStack(matrix, j);
				}
			}
		}
		depends.pop();
	}
	
	public static void printMatrix(boolean[][] matrix) {
		if(log)
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
		
		System.out.println(result);
	}
	
	private static class IntegerStack extends Stack<Integer> {

		private static final long serialVersionUID = 8650955703278560778L;
		
		@Override
		public boolean contains(Object o) {
			Integer value = (Integer)o;
			Iterator<Integer> it = this.iterator();
			while(it.hasNext()) {
				if(it.next().intValue()==value.intValue())
					return true;
			}
			return false;
		}
	}
}
