package jonathzw_CSCI201_Assignment2;

public class Card {
	private String name;
	private String description;
	
	public Card(String n, String d){
		name = n;
		description = d;
	}
	
	public String getName(){
		return name;
	}
	
	public String getDescription(){
		return description;
	}
}
