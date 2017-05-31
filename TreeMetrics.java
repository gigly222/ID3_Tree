import java.util.ArrayList;

//Utility Class
public class TreeMetrics {
	
	public static void printTree(Node root) {
		
		if(root !=  null) {	
			if(root.isLeaf()){
				System.out.println(root.getClassLabel());

			}else{

				System.out.println("");
				for(int i = 0; i < root.getDepth(); i++){
					System.out.print("| ");
				}
				
				//Left

				System.out.print(root.getAttribute());
				System.out.print(" = 0 : ");
				printTree(root.getLeftChild()); //
				
				
				//Right
				for(int i = 0; i < root.getDepth(); i++){
					System.out.print("| ");
				}

				System.out.print(root.getAttribute());
				System.out.print(" = 1 : ");

				
				printTree(root.getRightChild()); //
			}
		}
		return;
	}
	
	public static double accuracy(ArrayList<Attribute> attributeList, ArrayList<Record> recordSet, Node rootOfTree){
		
		int numberOfCorrectLabels = 0;
		int classLabel = -1;
		double totalAccuracy = 0;
		
		for(int i = 0; i < recordSet.size(); i++){
			
			//returnedLabel < traverse the tree and return the target class label for that record set
			classLabel = traverseTree(attributeList, recordSet.get(i), rootOfTree);
			//if returnedLabel equals what ever is in the recordSet's target label, then add one to numberOfCorrectLabels (numberOfCorrectLabel += 1;
			if(classLabel == recordSet.get(i).getClassLabel()){
				numberOfCorrectLabels += 1;
			}
		}
		//To get accuracy, take numberOfCorrectLabels/recordSet.size() * 100 to get a percent
		totalAccuracy = ((double)numberOfCorrectLabels/(double)recordSet.size()) * 100;
		//Return double Accuracy
		return totalAccuracy;
	}
	
	public static int traverseTree(ArrayList<Attribute> attributeList, Record record, Node rootOfTree){

		Node currentNode = rootOfTree;
		while(currentNode.isLeaf() != true ){

			//Get current nodes attribute name, this is the node we are splitting on
			String nameOfSplitAttribute = currentNode.getAttribute();
			int indexOfAttribute = -1;
			
			//get the index of that attribute, and compare with the index in the record at the same index
			for(Attribute singleAttribute : attributeList){
				if (singleAttribute.getAttributeName() == nameOfSplitAttribute){
					indexOfAttribute = singleAttribute.getattributePosition();//what ever is the value returned from the record at that index, return it as value;
				}
			}
			//get value from inside of record at the index retrieved.
			int valueFromRecord = record.getRecordRow()[indexOfAttribute];
			//whatever value is, if it is 0, go left, if 1 go right. update node we are at.
			if(valueFromRecord == 0){//go left
				currentNode = currentNode.getLeftChild();
			}else{//valueFromRecord = 1, go right
				currentNode = currentNode.getRightChild();
			}
		}
		int leafClassLabel = currentNode.getClassLabel();
		return leafClassLabel;
	}
	
	public static int numberOfNodes(Node root){
		if(root == null){
			return 0;
		}
		return 1 + numberOfNodes(root.getLeftChild()) + numberOfNodes(root.getRightChild());
	}
	
	
	public static int numberOfLeafs(Node root){
		
		if(root == null){ //no more
			return 0;
		}else if(root.getLeftChild() == null && root.getRightChild() == null){
			return 1; //is a leaf
		}else{
			return numberOfLeafs(root.getLeftChild()) + numberOfLeafs(root.getRightChild()); //not at leaf yet
		}
		
	}

	public static void printMetrics(int preOrPost, Node rootNodeOfTree, ArrayList<Attribute> allAttributes, ArrayList<Record> allTrainingRecords, ArrayList<Record> allValidationRecords, ArrayList<Record> allTestingRecords){
		String Title = " ";
		String append = " ";
		if(preOrPost == 0){
			Title = "\nPre-Pruned Accuracy ";
			append = "before pruning = ";
		}else{
			Title = "\nPost-Pruned Accuracy";
			append = "after pruning = ";
		}
		
		System.out.println(Title);
		System.out.println("-------------------------------------");
		
		System.out.println("Number of training instances = " + allTrainingRecords.size());
		System.out.println("Number of training attributes = " + allAttributes.size());
		System.out.println("Total number of nodes in the tree = " + TreeMetrics.numberOfNodes(rootNodeOfTree));
		System.out.println("Number of leaf nodes in the tree = " + TreeMetrics.numberOfLeafs(rootNodeOfTree));
		
		double accuracyValueTrain = TreeMetrics.accuracy(allAttributes, allTrainingRecords, rootNodeOfTree);
		System.out.println("Accuracy of the model on the training dataset " + append + accuracyValueTrain + "%");
		
		System.out.println("\nNumber of validation  instances = " + allValidationRecords.size());
		System.out.println("Number of validation attributes = " + allAttributes.size());
		
		double accuracyValueValidation = TreeMetrics.accuracy(allAttributes, allValidationRecords, rootNodeOfTree);
		System.out.println("Accuracy of the model on the validation dataset " + append + accuracyValueValidation + "%");
		
		System.out.println("\nNumber of testing  instances = " + allTestingRecords.size());
		System.out.println("Number of testing attributes = " + allAttributes.size());
		
		double accuracyValueTest = TreeMetrics.accuracy(allAttributes, allTestingRecords, rootNodeOfTree);
		System.out.println("Accuracy of the model on the testing dataset " + append + accuracyValueTest + "%");
	}
}
