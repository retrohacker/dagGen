package edu.siu.cs.dagGen;

import java.util.ArrayList;

/**
 * A simple node to be used in a tree structure. This class supports the method
 * DAGFunctions.removeSelfDependencies()
 * @author Crackers
 *
 * @param <T>
 */
class TreeNode {
	public int id;
	public ArrayList<TreeNode> children;
	
	public TreeNode(int id) {
		this.id = id;
		this.children = new ArrayList<TreeNode>(0);
	}
	
	@Override
	public String toString() {
		return "["+id+"]";
	}
	
	/**
	 * Returns the next child this tree points too
	 */
	public TreeNode getNext() {
		if(children.isEmpty())
			return null;
		else
			return children.remove(0);
	}
	
	public void shuffle() {
		for(int i = 0; i<this.children.size()*4;i++) {
			this.children.add(this.children.remove(((int)(Math.random()*this.children.size()))));
		}
	}
}
