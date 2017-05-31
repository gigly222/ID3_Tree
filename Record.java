import java.util.*;

public class Record {

	public int[]  recordRow;
	public int classLabel; //This is the target value. (last column)
	
	// Constructor to create a new record. All records will be the same length in a data set. 
	public Record(int numFeatures){
		recordRow = new int[numFeatures]; //New empty array
	}
	
	//Setters and Getters
	public void setRecordRow(int[] row) {
		recordRow = row; //add data to record array. Does not include target label
	}
	
	public int[] getRecordRow() {
		return recordRow; //get entire record array. Does not include target label
	}
	
	public void setClassLabel(int label) {
		classLabel = label; //set target label
	}
	
	public int getClassLabel() {
		return classLabel; //returns target label
	}
	
	public String toString() {
		return "Record (record_row =" + Arrays.toString(recordRow)
				+ ", classLabel =" + classLabel + ")";
	}
}

