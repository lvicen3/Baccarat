import java.util.*;

public class BaccaratDealer implements BaccaratInfo{
	ArrayList<Card> deck;
	
	public BaccaratDealer(){
		generateDeck();
		
	}
	
	//Fill deck with all 52 cards 
	//gives values 1 to 13 to represent all cards
	public void generateDeck() {
		deck = new ArrayList<Card>();
		
		for(int i = 0; i < 4; i++) {
			for(int j = 1; j < 14; j++) {
				deck.add(new Card(suits[i],j));
			}
		}
	}
	
	//returns an ArrayList<Card> of the 2 top cards
	//call shuffle first to make sure cards are random
	public ArrayList<Card> dealHand(){
		ArrayList<Card> hand = new ArrayList<Card>();
		
		if(deck.size() > 1) {
			hand.add(drawOne());
			hand.add(drawOne());
			return hand;
		} else {
			return null;
		}
		
	}
	
	///returns the top card in the deck, and removes it from the deck
	public Card drawOne() {
		Card card; 
		
		try{
			card = deck.get(0);
			deck.remove(0);
			return card;
		} catch(IndexOutOfBoundsException e) {
			return null;
		}
	}
	
	//randomize card order
	public void shuffleDeck() {
		Collections.shuffle(deck);
	}
	
	//return size of the deck
	public int deckSize() {
		return deck.size();
	}
	
	//return deck
	public ArrayList<Card> getDeck() {
		return deck;
	}
}
