import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BaccaratGameTest {
	static BaccaratGame game;
	
	@BeforeAll
	static void init() {
		game = new BaccaratGame();
		game.playerHand = new ArrayList<Card>();
		game.bankerHand = new ArrayList<Card>();
		game.gameLogic = new BaccaratGameLogic();
		game.theDealer = new BaccaratDealer();
		game.theDealer.shuffleDeck();
		game.currentBet = 100;
	}
	
	@Test
	void constructorTest() {
		assertEquals(BaccaratGame.class, game.getClass());
	}
	
	@Test
	void testEvaluateWinnings1() {
		game.betTarget = "Draw";
		game.playerHand.add(new Card("Spades",8));
		game.playerHand.add(new Card("Spades",1));
		game.bankerHand.add(new Card("Spades",8));
		game.bankerHand.add(new Card("Spades",1));
		assertEquals(200,game.evaluateWinnings());
	}
	
	@Test
	void testEvaluateWinnings2() {
		game.betTarget = "Player";
		game.playerHand.add(new Card("Spades",4));
		game.playerHand.add(new Card("Spades",1));
		game.playerHand.add(new Card("Spades",8));
		game.playerHand.add(new Card("Spades",1));
		assertEquals(-100,game.evaluateWinnings());
		
	
	}
}
