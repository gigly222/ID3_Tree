import java.util.*;
import java.io.*;

//Utility class
public class FileReader {
	
	//Reads in a file to create the set of records we need to build and test the tree.
	public static ArrayList<Record> createRecordSet(String path) {
	
		String filePath = path; 
		ArrayList<Record> allRecords = new ArrayList<Record>(); //Contains all of the records from the file
		BufferedReader reader = null;

		try {
			File dataFile = new File(filePath);
			FileInputStream fileStream = new FileInputStream(dataFile); 
			reader = new BufferedReader(new InputStreamReader(fileStream));;
			String lineFromFile;
	
			//Get first line. This will contain the names of the attributes
			lineFromFile = reader.readLine();
			String[] listOfAttributesPlusClass = lineFromFile.split(",");
			int totalNumFeatures = listOfAttributesPlusClass.length - 1; //Subtract off class label
			
			while((lineFromFile = reader.readLine()) != null){//Data + class label

				Record record = new Record(totalNumFeatures); //Creates a new record object that is empty
				
				String[] allOfLine = lineFromFile.split(",");
							
				if(allOfLine.length != 0){//otherwise skip record because it is empty
					
					record.setClassLabel(Integer.valueOf(allOfLine[allOfLine.length-1])); //Put only class label in record
					
					//Array of only data values without class label
					int[] onlyDataArray = new int[totalNumFeatures];
					for(int i = 0; i < allOfLine.length -1 ; i++){
						onlyDataArray[i] = Integer.valueOf(allOfLine[i]);
					}
					
					//Add data array to record
					record.setRecordRow(onlyDataArray);
					
					//Add records to Record Set
					allRecords.add(record);
				}
							
			}

		reader.close();
		
		} catch (IOException e) { 
			System.out.println("IOException error: " + e.getMessage());
			System.exit(0);
		}
		return allRecords;
	}
	
	public static ArrayList<Attribute> createAttributeSet(String path) {
		
		String filePath = path; 
		ArrayList<Attribute> allAttributes = new ArrayList<Attribute>(); //Contains all of the Attributes from the file
		BufferedReader reader = null;

		try {
			File dataFile = new File(filePath);
			FileInputStream fileStream = new FileInputStream(dataFile); 
			reader = new BufferedReader(new InputStreamReader(fileStream));;
			String lineFromFile;
	
			//Get first line. This will contain the names of the attributes
			lineFromFile = reader.readLine();
			String[] listOfAttributesPlusClass = lineFromFile.split(",");
			int totalNumFeatures = listOfAttributesPlusClass.length - 1; //Subtract off class label
			
			for(int i = 0; i < totalNumFeatures; i++){
				Attribute newAttribute = new Attribute(listOfAttributesPlusClass[i], i);
				allAttributes.add(newAttribute);
			}
			reader.close();

		} catch (IOException e) { 
			System.out.println("IOException error: " + e.getMessage()); 
		}
		
		return allAttributes;
	}
}
	
