
import java.util.*;

public class Main {

	public static void main(String[] args) {
		
		String trainingPath = args[0]; //Training data file
		String validationPath = args[1]; //Validation data file
		String testPath = args[2]; //Test data file
		String pruneFactor = args[3]; //Used for pruning
		double pruningFactor = Double.valueOf(pruneFactor);
		double postPruneAccuracy = 0;
		
		ArrayList<Attribute> allAttributes = new ArrayList<Attribute>(); //Contains all of the attribute names from the file
		ArrayList<Record> allTrainingRecords = new ArrayList<Record>(); //Contains all of the records from the training file
		ArrayList<Record> allValidationRecords = new ArrayList<Record>(); //Contains all of the records from the validation file
		ArrayList<Record> allTestingRecords = new ArrayList<Record>(); //Contains all of the records from the test file
		
		allAttributes = FileReader.createAttributeSet(trainingPath);
		allTrainingRecords = FileReader.createRecordSet(trainingPath);
		allValidationRecords = FileReader.createRecordSet(validationPath);
		allTestingRecords = FileReader.createRecordSet(testPath);
		
		//Initialize Tree
		ID3Tree tree = new ID3Tree(allAttributes, allTrainingRecords);

		//Build tree
		Node rootNodeOfTree = tree.buildID3Tree(allTrainingRecords, allAttributes, 0);
		
		//Print Tree information - PrePruning
		System.out.println("\nPre-Pruning Tree Model : ");
		TreeMetrics.printTree(rootNodeOfTree);
		double prePruneAccuracy = TreeMetrics.accuracy(allAttributes, allValidationRecords, rootNodeOfTree);
		TreeMetrics.printMetrics(0, rootNodeOfTree, allAttributes, allTrainingRecords, allValidationRecords, allTestingRecords);
		
		
		//Prune the Tree until your validation set accuracy goes up. Method randomly chooses nodes to prune. Process repeats until it has a better model.
		int count = 0; //Only run the pruning algorithm 100 times. This stops it from continuing for ever in the case there is no better model than the original. 
		Node bestModelRoot = rootNodeOfTree;
		while(postPruneAccuracy < prePruneAccuracy && count < 100){//Prune nodes randomly until the tree until Validation set Accuracy goes up
			bestModelRoot = Pruning.treePruning(allAttributes, allValidationRecords, rootNodeOfTree, pruningFactor);
			postPruneAccuracy = TreeMetrics.accuracy(allAttributes, allValidationRecords, bestModelRoot);
			count++;
		}

		//Print Tree information - PostPruning
		TreeMetrics.printMetrics(1, bestModelRoot, allAttributes, allTrainingRecords, allValidationRecords, allTestingRecords);
		System.out.println("\nPost-Pruned Tree Model : ");
		TreeMetrics.printTree(bestModelRoot);

	}

}
