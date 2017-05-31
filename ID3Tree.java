import java.util.ArrayList;

public class ID3Tree {
		
	//Tree Constructor. Should be an empty tree at first.
	public ID3Tree(ArrayList<Attribute> attributes, ArrayList<Record> trainingRec) {
	}

	//Build ID3 Tree following ID3 Algorithm
	public Node buildID3Tree(ArrayList<Record> records, ArrayList<Attribute> remainingattributes, int depth) {

		Node root = new Node(depth);
		
		//Get majority target class per node. Used for Tree Pruning later.
		int vote = getMajorityClass(records);
		root.setMajorityVote(vote);
		
		//Termination Conditions
		int[] posNegCountArray = countPosAndNeg(records);
		int isAll =  checkAllPosorNeg(posNegCountArray);

		if(isAll == 1){//If all Examples are positive, Return the single-node tree Root with label = 
			root.setClassLabel(1);
		}
		else if(isAll == 0){//If all Examples are negative, Return the single-node tree Root with label = -
			root.setClassLabel(0);
		}
		else if(remainingattributes.size() == 0 ){//If attributeList empty, no more attributes left, return root with label = to most common target value of attribute
			if(posNegCountArray[0] > posNegCountArray[1]){ //posNegCountArray holds positive, then negative, and last recSize
				root.setClassLabel(1);
			}else{
				root.setClassLabel(0);
			}
		}
		else{//Make attribute "A" from attributeList that best classifies example data. This is based on Information Gain Calculation
	
			double currentEntropy = DetermineBestAttribute.calculateEntropy(records); //Get current subsets entropy before split
			int bestAttributesIndex = DetermineBestAttribute.informationGain(currentEntropy, remainingattributes, records);

			if(bestAttributesIndex != -1){ //There was some sort of information gain

				//Set name of best Attribute
				String attrName = findAttributeName(remainingattributes, bestAttributesIndex);
				root.setAttribute(attrName);
				
				//Remove attribute from list
				remainingattributes = removeUsedAttribute(remainingattributes, bestAttributesIndex);
				
				//For each possible value of A "0 or 1"
				for(int j = 0; j< 2;j++){				
					//Split on Attribute into subsets again
					ArrayList<Record> splitRecordOnNewAttr = DetermineBestAttribute.getSubSetrecord(records, j, bestAttributesIndex);
					
					//If subset of examples is empty, make new leaf node with majority rules label
					if(splitRecordOnNewAttr.size() == 0){
						Node child = new Node(root.getDepth() + 1);
						child.setClassLabel(getMajorityClass(records));
						//splitRecordOnNewAttr is empty.......
						if(j == 0){
							root.setLeftChild(child);
						}else{
							root.setRightChild(child);
						}

					}else{//else, this will be a new branch of the tree. Recursively call the ID3Algoirthm again
						if(j == 0){
							root.setLeftChild(buildID3Tree(splitRecordOnNewAttr, remainingattributes, root.getDepth() + 1));
						}else{
							root.setRightChild(buildID3Tree(splitRecordOnNewAttr, remainingattributes, root.getDepth() + 1));
						}
					}
					
				}//end for loop
			}else{
				root.setClassLabel(getMajorityClass(records)); //no information gain
			}
		}//end else
		
		return root;
	}
	
	public int getMajorityClass(ArrayList<Record> records){
		int[] posNegArray = countPosAndNeg(records);
		if(posNegArray[0] > posNegArray[1]){
			return 1;
		}else{
			return 0;
		}	
	}
	
	public int[] countPosAndNeg(ArrayList<Record> records){
		int positiveCount = 0;
		int recsize = records.size();
		
		for(int i = 0; i < records.size(); i++) {
			Record singleRecord = records.get(i);

			int classLabel = singleRecord.getClassLabel();

			if(classLabel == 1) { //Class Target label is positive
				positiveCount += 1;	
			}
		}	
		int values[] = {positiveCount, recsize-positiveCount, recsize};
		return values;
	}
	
	//checkValues array holds postiveCount, negativeCount, record Size
	public int checkAllPosorNeg(int[] checkValues){
		
		if(checkValues[0] == checkValues[2]){ //means all positive, b/c it matches record size
			return 1;
		}
		else if (checkValues[1] == checkValues[2]){//means al; negative, b/c it matches record size
			return 0;
		}
		else{
			return 2; //mix of 0 and 1
		}
	}
	
	public String findAttributeName(ArrayList<Attribute> attributeList, int attrIndex){
		
		for(Attribute singleAttribute : attributeList){
			if (singleAttribute.getattributePosition() == attrIndex){
				return singleAttribute.getAttributeName();
			}
		}
		return "ERROR, Name Not Found";
	}
	
	public ArrayList<Attribute> removeUsedAttribute(ArrayList<Attribute> attributeList, int attrIndex){
		ArrayList<Attribute> newAttributeList = new ArrayList<Attribute>(); 
		
		for(Attribute singleAttribute : attributeList){
			if (singleAttribute.getattributePosition() == attrIndex){ //Don't add to new list
			}else{
				newAttributeList.add(singleAttribute);
			}
			
		}
		return newAttributeList;
	}
	

}
