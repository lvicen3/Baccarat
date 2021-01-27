import java.util.ArrayList;

//GameLogic class, holds methods to implement the BaccaratGame
public class BaccaratGameLogic implements BaccaratInfo{
	
	//Naturalwin Method
	//returns if round is over based of first 4 cards
	public boolean NaturalWin(ArrayList<Card> hand1, ArrayList<Card> hand2) {
		if(handTotal(hand1) >= 8 || handTotal(hand2) >= 8) {
			return true;
		}
		return false;
	}
	
	//whoWon method
	//returns who wins the round depending on the hands, or draw
	//returns null when receiving any null hands
	public String whoWon(ArrayList<Card> hand1, ArrayList<Card> hand2) {
		try {
		if(handTotal(hand1) > handTotal(hand2)) 
			return "Player";
		
		if(handTotal(hand1) == handTotal(hand2)) 
			return "Draw";
			
		return "Banker";
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	//handTotal method
	// returns values of hands based on the rules of Baccarat
	public int handTotal(ArrayList<Card> hand) {
		int total = 0;
		
		for(Card c: hand) {
			if(c.value <= 9) {
				total += c.value;
			}
		}
		
		if(total > 9 && total < 20) {
			return total-10;
		} else if(total >= 20) {
			return total-20;
		}
		return total;
	}
	
	//evaluateBankerDraw method
	//returns if banker should draw a card or not, based on bankerDraw table in the BaccaratInfo interface
	public boolean evaluateBankerDraw(ArrayList<Card> hand, Card playerCard) {
		if(handTotal(hand) >= 7)
			return false;
		
		if(handTotal(hand) < 3)
			return true;
		
		if(playerCard == null)
			return bankerDraw[handTotal(hand)][0];
		
		return bankerDraw[handTotal(hand)][(playerCard.value > 9) ? 1 : playerCard.value + 1];
	}
	
	//evaluatePlayerDraw method
	//returns if player should draw a card based on the value of its current hand
	public boolean evaluatePlayerDraw(ArrayList<Card> hand) {
		if(handTotal(hand) <= 6) {
			return  true;
		} 
		
		return false;
	}
	
	
}
