//Card class 
public class Card {
	String suite;
	int value;
	
	//Constructor
	Card(String suite, int value){
		this.suite = suite;
		this.value = value;
	}
	
	//print method for debugging
	void print() {
		System.out.println("Card: " + suite + " " + value);
	}
	
	//Getters
	String getSuite() {
		return suite;
	}
	
	int getValue() {
		return value;
	}
}
