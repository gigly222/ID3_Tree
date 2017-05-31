import java.util.ArrayList;

public class Pruning {
	
	//List of all nodes in tree
	private static ArrayList<Node> nodesInTreeList = new ArrayList<Node>();
	
	public static Node treePruning(ArrayList<Attribute> attributeList, ArrayList<Record> allValidationRecords, Node rootOfTree, double pruningFactor){
		
		ArrayList<Node> nodesToPrune = new ArrayList<Node>();
		
		// make a copy of the tree. Updates nodesInTreeList
		Node copyRoot = makeCopyOfTree(rootOfTree);

		Node bestModelRoot = rootOfTree;
		
		int numNodes = TreeMetrics.numberOfNodes(rootOfTree);
		double numToPrune = numNodes * pruningFactor;
		double roundedNumberToPrune = Math.floor(numToPrune); //Takes the floor of the decimal number so that it is always a whole number.

		nodesToPrune = getNRandomNodes(roundedNumberToPrune, nodesInTreeList.size(), nodesInTreeList); //returns a list of Nodes to prune
	
		double accuracyValueValidation = 0;
		
		//Run pruning 1000 times and pick the best model
		for(int i = 0; i < 1000; i++){
			// make a copy of the tree. Reset Nodes in list for new copy of tree
			nodesInTreeList = new ArrayList<Node>();
			copyRoot = makeCopyOfTree(rootOfTree);
			nodesToPrune = getNRandomNodes(roundedNumberToPrune, nodesInTreeList.size(), nodesInTreeList); //returns a list of Nodes to prune
			
			//Start pruning the tree
			startPrune(nodesInTreeList, nodesToPrune);
			
			double accuracy = TreeMetrics.accuracy(attributeList, allValidationRecords, copyRoot);

			if(accuracy > accuracyValueValidation){
				accuracyValueValidation = accuracy;		
				// make a copy of the tree
				bestModelRoot = makeCopyOfTree(copyRoot);
			}
		}

		return bestModelRoot;
		
	}
	
	public static void startPrune(ArrayList<Node> nodesFromTree, ArrayList<Node> nodesToPrune){
		Node pruneNode;

		//iterate through all nodes to prune, and pluck them off one by one
		for(int i = 0; i< nodesToPrune.size(); i++){

			pruneNode = nodesToPrune.get(i);
			
			//Make pruneNode a leaf
			int vote = pruneNode.getMajorityVote();
			pruneNode.setClassLabel(vote); //class label
				
			pruneNode.setLeftChild();
			pruneNode.setRightChild();
					
		}
	}
	
	//Want to add only non-leaf and non- root nodes to list.
	public static ArrayList<Node> getNRandomNodes(double numberToPrune, int totalNumNodes, ArrayList<Node> nodesListInTree){
		
		ArrayList<Node> pruneTheseNodes = new ArrayList<Node>();
		int p = -1;
		
		for(int i = 0; i < numberToPrune; i++){
			p = getRandomNumber(1,totalNumNodes);
			Node pruningNode = nodesListInTree.get(p);
			
			//Want to only add non leaf nodes to list (and we already skipped root)
			while(pruningNode.isLeaf() || pruningNode.getParent() ==  null){
				p = getRandomNumber(1,totalNumNodes); //regenerate until you get a non leaf node
				pruningNode = nodesListInTree.get(p);
			}

			pruneTheseNodes.add(pruningNode); //each time gets a random node from list
		}

		return pruneTheseNodes; //All non-leaf and non-root nodes
	}
	
	public static int getRandomNumber(int numOne, int numTwo ){
		
		int randomInt = (int) (Math.random() * (numTwo - numOne)) + numOne;
		return randomInt;
	}

	public static Node makeCopyOfTree(Node root){

		Node copyRoot = new Node(root); //zero depth
		
		if(root == null){
			return null;
		}
		if(root.getLeftChild() != null){
			copyRoot.setLeftChild(makeCopyOfTree(root.getLeftChild()));
		}
		
		if(root.getRightChild() != null){
			copyRoot.setRightChild(makeCopyOfTree(root.getRightChild()));
		}
		nodesInTreeList.add(copyRoot);
		return copyRoot;
	}
	

}
