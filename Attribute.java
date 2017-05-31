public class Attribute {
	
	public int  attributePosition;
	public String attributeName;
	
	
	// Constructor to create a new Attribute
	public Attribute(String name, int value){
		attributeName = name; //Name of attribute
		attributePosition = value; //Position in Array
	}
	
	//Setters and Getters
	public int getattributePosition() {
		return attributePosition;
	}
		
	public String getAttributeName() {
		return attributeName; //get entire record array. Does not include target label
	}
	
	public String toString() {
		return "Attribute (Name =" + attributeName
				+ ", position =" + attributePosition + ")";
	}

}
