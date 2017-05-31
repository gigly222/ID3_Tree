import java.util.*;

public class Node {
	
	private String attribute;
	private int classLabel;
	private ArrayList<Record> data;
	private Node parent;
	private Node leftChild;
	private Node rightChild;
	private int depth;
	private int majorityVote; //used for pruning

	public Node(int nodeDepth) {
		data = new ArrayList<Record>();
		classLabel = -1;
		leftChild = null;
		rightChild = null;
		setAttribute("");
		majorityVote = -1;
		this.depth = nodeDepth;
	}
	
	// copy constructor
	public Node(Node oldNode) {
		//values
		depth = oldNode.getDepth();
		attribute = oldNode.getAttribute();
		classLabel = oldNode.getClassLabel();
		majorityVote = oldNode.getMajorityVote();
    } 

	public void setParent(Node parentNode) {
		parent = parentNode;
	}

	public Node getParent() {
		return parent;
	}

	public void setData(ArrayList<Record> dataVal) {
		data = dataVal;
	}

	public ArrayList<Record> getData() {
		return data;
	}

	public void setAttribute(String attr) {
		attribute = attr;
	}

	public String getAttribute() {
		return attribute;
	}

	public int getClassLabel() {
		return classLabel;
	}

	public void setClassLabel(int label) {
		classLabel = label;
	}

	public Node getLeftChild() {
		return leftChild;
	}

	public void setLeftChild(Node left) {
		leftChild = left;
		leftChild.setParent(this);
	}

	public Node getRightChild() {
		return rightChild;
	}

	public void setRightChild(Node right) {
		rightChild = right;
		rightChild.setParent(this);
	}

	public boolean isLeaf() {
		if(this.getRightChild() == null && this.getLeftChild() == null){
			return true;
		}
		else{
			return false;
		}
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getMajorityVote() {
		return majorityVote;
	}

	public void setMajorityVote(int majorityVote) {
		this.majorityVote = majorityVote;
	}
	
	//Used in Pruning
	public void setLeftChild(){
		leftChild = null;
	}
	
	public void setRightChild(){
		rightChild = null;
	}
}
