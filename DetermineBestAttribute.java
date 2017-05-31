import java.util.ArrayList;

//Utility Class to calculate entropy and information gain
public class DetermineBestAttribute {
	
	//Calculate entropy of the record set
	public static double calculateEntropy(ArrayList<Record> recordSet){
		
		double positiveCounter = 0;
		double negativeCounter = 0;
		double probabilityOfPositive = 0;
		double probabilityOfNegative = 0;
		double totalRecordSetSize = recordSet.size();

		if(totalRecordSetSize == 0) //Don't want to be dividing by zero!
			return -1;
		
		for(int i = 0; i < recordSet.size(); i++) {
			Record singleRecord = recordSet.get(i);

			int classLabel = singleRecord.getClassLabel();

			if(classLabel == 1) { //Class Target label is positive
				positiveCounter += 1;
			}
			else{
				negativeCounter += 1; //Class Target label is negative
			}
		}
		probabilityOfPositive = positiveCounter/totalRecordSetSize; 
		probabilityOfNegative = negativeCounter/totalRecordSetSize;
		
		double entropy =  +plog(probabilityOfPositive) + plog(probabilityOfNegative);
		return entropy;
	}
	
	
	//Intermediate log base 2 calculation
	public static double plog(double probability) {
		
		if (probability == 0){ //So we don't get undefined. Assume Log2(0) = 0 
			return 0;
		}
		else{
			double logVal =  -(probability * (Math.log(probability) / Math.log(2))); //Change base to 2
			return logVal;
		}
	}
	
	
	//Calculate information gain from splitting on a particular attribute. Entropy of Parent can be taken from Parent Node
	public static int informationGain(double entropyOfParent, ArrayList<Attribute> remainingAttributes, ArrayList<Record> recordSet){
		double infogain = 0;
		int indexOfBestAttribute =  0;

		for(int i = 0; i< remainingAttributes.size();i++){
			double entropiesAddedTogether = 0;
			
			for(int j=0; j<2; j++){ //Split data set between 0's and 1's
				ArrayList<Record> subRecSet = getSubSetrecord(recordSet, j, remainingAttributes.get(i).getattributePosition());
				double  setEntropy  =  calculateEntropy(subRecSet);
				entropiesAddedTogether = entropiesAddedTogether + ((double)(double)subRecSet.size()/(double)recordSet.size())* setEntropy;
			}

			if(infogain < (entropyOfParent - entropiesAddedTogether)){ //Parent entropy - children entropy
				infogain = entropyOfParent - entropiesAddedTogether; //If entropy gain is larger than current information gain, update
				indexOfBestAttribute = remainingAttributes.get(i).getattributePosition();	//This keeps track of the attribute with the most information gain
			}
			
		}
		//There was no information gained
		if(infogain == 0){
			return -1;
		}

		return indexOfBestAttribute;
	}
	
	//EX: want to split on "whisker" attribute. Take all the 1's and put to right, and all the 0's to left. 
	//Get the subset of records associated with an attribute and a value of 0 or 1, which ever is passed in as attributeVal. (Not target)
	public static ArrayList<Record> getSubSetrecord(ArrayList<Record> allRecords, int zeroOrOne, int attributePosition) {
		//returns array list of records having particular attribute - indicated by attribute position -- as attribute value - 0 or 1

		ArrayList<Record> subsetOfRecords = new ArrayList<Record>();
		
		//iterate through entire set of records
		for(int i = 0; i < allRecords.size(); i++) {

			Record record = allRecords.get(i); //Get record in record set passed in
			if(record.getRecordRow()[attributePosition] == zeroOrOne){ //Get the record row's array, then get the column associated with that attribute.
				subsetOfRecords.add(record);				//If that columns attribute value matches, add it to subset. Such as 0 or 1 is the value. 
			}
		}
		return subsetOfRecords;
	}
	
}
