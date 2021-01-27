import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class DealerTest {
	static BaccaratDealer dealer;
	
	@BeforeAll
	static void init() {
		dealer = new BaccaratDealer();
	}
	
	@BeforeEach
	void refillDeck() {
		dealer.generateDeck();
	}
	
	@Test
	void cardConstructorTest() {
		assertEquals(Card.class, new Card("Hearts",1).getClass());
	}
	
	@Test
	void dealerConstructorTest() {
		assertEquals(BaccaratDealer.class,dealer.getClass());
	}
	
	@Test
	void generateDeckTest1() {
		assertEquals(ArrayList.class,dealer.deck.getClass());
	}

	@Test
	void generateDeckTest2() {
		assertEquals(Card.class, dealer.deck.get(0).getClass());
	}

	@Test
	void generateDeckTest3() {
		assertEquals("Hearts", dealer.deck.get(0).suite);
	}
	
	@Test
	void generateDeckTest4() {
		assertEquals(1, dealer.deck.get(0).value);
	}
	
	@Test
	void dealHandTest1() {
		assertEquals(ArrayList.class,dealer.dealHand().getClass());
	}
	
	@Test
	void dealHandTest2() {
		ArrayList<Card> hand = dealer.dealHand();
		assertTrue(hand.get(0).value > 0 && hand.get(0).value < 14,"Card 1 value is not valid");
		assertTrue(BaccaratInfo.suits[0] == hand.get(0).suite 
				|| BaccaratInfo.suits[1] == hand.get(0).suite
				|| BaccaratInfo.suits[2] == hand.get(0).suite
				|| BaccaratInfo.suits[4] == hand.get(0).suite, "Card 1 string is not a suite");
	}
	
	@Test
	void dealHandTest3() {
		ArrayList<Card> hand = dealer.dealHand();
		assertTrue(hand.get(0).value > 0 && hand.get(1).value < 14,"Card 2 value is not valid");
		assertTrue(BaccaratInfo.suits[0] == hand.get(1).suite 
				|| BaccaratInfo.suits[1] == hand.get(1).suite
				|| BaccaratInfo.suits[2] == hand.get(1).suite
				|| BaccaratInfo.suits[4] == hand.get(1).suite, "Card 2 string is not a suite");
	}
	
	@Test
	void drawOneTest1() {
		Card c = dealer.drawOne();
		assertTrue(c.value > 0 && c.value < 14,"Card value is not valid");
		assertTrue(BaccaratInfo.suits[0] == c.suite 
				|| BaccaratInfo.suits[1] == c.suite
				|| BaccaratInfo.suits[2] == c.suite
				|| BaccaratInfo.suits[4] == c.suite, "Card string is not a suite");
	}
	
	@Test
	void drawOneTest2() {
		dealer.deck.clear();
		assertNull(dealer.drawOne());
	}
	
	@Test
	void shuffleDeckTest1() {
		ArrayList<Card> deckCopy = new ArrayList<Card>(dealer.deck);
		dealer.shuffleDeck();
		assertThat(dealer.deck.equals(deckCopy),is(false));
	}
	
	@Test
	void shuffleDeckTest2() {
		dealer.deck.clear();
		try{
			dealer.shuffleDeck();
		} catch(Exception e) {
			fail("Shuffle empty deck caused exception");
		}
	}
	
	@Test
	void deckSizeTest1() {
		assertEquals(52,dealer.deckSize());
	}
	
	@Test
	void deckSizeTest2() {
		dealer.drawOne();
		assertEquals(51,dealer.deckSize());
	}
	
	@Test
	void deckSizeTest3() {
		dealer.dealHand();
		assertEquals(50,dealer.deckSize());
	}
}
