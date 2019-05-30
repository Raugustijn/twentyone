package twentyone;

import static org.junit.Assert.*;

import org.junit.Test;

import twentyone.Card.Suit;
import twentyone.Card.Type;

public class HandTest {

	@Test
	public void testCardPointTotalWithAcesLow() {
		Hand testHand = new Hand();
		testHand.addCard(new Card(10, Suit.DIAMONDS, Type.STANDARD));
		testHand.addCard(new Card(1, Suit.DIAMONDS, Type.ACE));
		assertEquals(testHand.cardPointTotal(), 21);
	}
	
	@Test
	public void testCardPointTotalWithAcesHigh() {
		Hand testHand = new Hand();
		testHand.addCard(new Card(10, Suit.DIAMONDS, Type.STANDARD));
		testHand.addCard(new Card(10, Suit.HEARTS, Type.STANDARD));
		testHand.addCard(new Card(1, Suit.DIAMONDS, Type.ACE));
		assertEquals(testHand.cardPointTotal(), 21);
	}
	
	@Test
	public void testCardPointTotalEmpty() {
		Hand testHand = new Hand();
		assertEquals(testHand.cardPointTotal(), 0);
	}
	
	@Test
	public void testCardPointTotalWithoutAces() {
		Hand testHand = new Hand();
		testHand.addCard(new Card(10, Suit.DIAMONDS, Type.STANDARD));
		testHand.addCard(new Card(10, Suit.HEARTS, Type.STANDARD));
		assertEquals(testHand.cardPointTotal(), 20);
	}
	
	@Test
	public void testIsSplitAllowedTrue() {
		Hand testHand = new Hand();
		testHand.addCard(new Card(10, Suit.DIAMONDS, Type.STANDARD));
		testHand.addCard(new Card(10, Suit.HEARTS, Type.STANDARD));
		assertTrue(testHand.isSplitAllowed());
	}
	
	@Test
	public void testIsSplitAllowedWrongCards() {
		Hand testHand = new Hand();
		testHand.addCard(new Card(10, Suit.DIAMONDS, Type.STANDARD));
		testHand.addCard(new Card(9, Suit.HEARTS, Type.STANDARD));
		assertFalse(testHand.isSplitAllowed());
	}
	
	@Test
	public void testIsSplitAllowedThreeCards() {
		Hand testHand = new Hand();
		testHand.addCard(new Card(10, Suit.DIAMONDS, Type.STANDARD));
		testHand.addCard(new Card(10, Suit.HEARTS, Type.STANDARD));
		testHand.addCard(new Card(10, Suit.CLUBS, Type.STANDARD));
		assertFalse(testHand.isSplitAllowed());
	}
	
	@Test
	public void testAmountAcesNone() {
		Hand testHand = new Hand();
		testHand.addCard(new Card(10, Suit.DIAMONDS, Type.STANDARD));
		testHand.addCard(new Card(10, Suit.HEARTS, Type.STANDARD));
		testHand.addCard(new Card(10, Suit.CLUBS, Type.STANDARD));
		assertEquals(testHand.amountAces(), 0);
	}
	
	@Test
	public void testAmountAcesOne() {
		Hand testHand = new Hand();
		testHand.addCard(new Card(10, Suit.DIAMONDS, Type.STANDARD));
		testHand.addCard(new Card(10, Suit.HEARTS, Type.STANDARD));
		testHand.addCard(new Card(1, Suit.CLUBS, Type.ACE));
		assertEquals(testHand.amountAces(), 1);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testSplitHandNotAllowed() {
		Hand testHand = new Hand();
		testHand.addCard(new Card(10, Suit.DIAMONDS, Type.STANDARD));
		testHand.addCard(new Card(10, Suit.HEARTS, Type.STANDARD));
		testHand.addCard(new Card(1, Suit.CLUBS, Type.ACE));
		testHand.split();
	}
	
	@Test
	public void testSplitHand() {
		Hand testHand = new Hand();
		testHand.addCard(new Card(10, Suit.DIAMONDS, Type.STANDARD));
		testHand.addCard(new Card(10, Suit.HEARTS, Type.STANDARD));
		Hand newHand = testHand.split();
		assertEquals(testHand.numberCards(), 1);
		assertEquals(testHand.get(0).getSuit(), Suit.DIAMONDS);
		assertEquals(newHand.numberCards(), 1);
		assertEquals(newHand.get(0).getSuit(), Suit.HEARTS);
	}
}
