import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BaccaratGameLogicTest {
	static BaccaratGameLogic gameLogic;
	static BaccaratDealer dealer;
	ArrayList<Card> playerHand;
	ArrayList<Card> bankerHand;
	@BeforeAll
	static void init() {
		gameLogic = new BaccaratGameLogic();
		dealer = new BaccaratDealer();
	}

	@BeforeEach
	void refillDeck() {
		dealer.generateDeck();
		dealer.shuffleDeck();
		playerHand = new ArrayList<Card>();
		bankerHand = new ArrayList<Card>();
	}
	
	@Test
	void whoWonTest1() {
		playerHand.add(new Card("Hearts",9));
		playerHand.add(new Card("Hearts",9));
		bankerHand.add(new Card("Hearts",0));
		bankerHand.add(new Card("Hearts",7));
		assertEquals("Player",gameLogic.whoWon(playerHand, bankerHand));
	}
	
	@Test
	void whoWonTest2() {
		playerHand.add(new Card("Hearts",4));
		playerHand.add(new Card("Hearts",4));
		bankerHand.add(new Card("Hearts",4));
		bankerHand.add(new Card("Hearts",4));
		assertEquals("Draw",gameLogic.whoWon(playerHand, bankerHand));
	}
	
	@Test
	void handTotalTest1() {
		playerHand.add(new Card("Hearts",4));
		playerHand.add(new Card("Hearts",4));
		assertEquals(8,gameLogic.handTotal(playerHand));
	}
	
	@Test
	void handTotalTest2() {
		playerHand.add(new Card("Hearts",9));
		playerHand.add(new Card("Hearts",9));
		assertEquals(8,gameLogic.handTotal(playerHand));
	}
	
	@Test
	void evaluateBankerDrawTest1() {
		playerHand.add(new Card("Hearts",4));
		playerHand.add(new Card("Hearts",4));
		bankerHand.add(new Card("Hearts",4));
		bankerHand.add(new Card("Hearts",4));
		assertFalse(gameLogic.evaluateBankerDraw(bankerHand, null));
	}
	
	@Test
	void evaluateBankerDrawTest2() {
		bankerHand.add(new Card("Hearts",2));
		bankerHand.add(new Card("Hearts",2));
		assertTrue(gameLogic.evaluateBankerDraw(bankerHand, new Card("Hearts",4)));
	}
	
	@Test
	void evaluatePlayerDrawTest1() {
		playerHand.add(new Card("Hearts",4));
		playerHand.add(new Card("Hearts",4));
		assertFalse(gameLogic.evaluatePlayerDraw(playerHand));
	}
	
	@Test
	void evaluatePlayerDrawTest2() {
		playerHand.add(new Card("Hearts",10));
		playerHand.add(new Card("Hearts",0));
		assertTrue(gameLogic.evaluatePlayerDraw(playerHand));
	}
	
	
}
