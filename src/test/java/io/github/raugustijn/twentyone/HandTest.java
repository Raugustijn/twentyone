package io.github.raugustijn.twentyone;

import static org.junit.Assert.*;

import org.junit.Test;

import io.github.raugustijn.twentyone.Card.Suit;
import io.github.raugustijn.twentyone.Card.Type;

public class HandTest {

	@Test
	public void testToStringWithTwoAces() {
		Hand testHand = new Hand();
		testHand.addCard(new Card(1, Suit.HEARTS, Type.ACE));
		testHand.addCard(new Card(1, Suit.DIAMONDS, Type.ACE));
		assertEquals("State: PLAYING\nCards:\n(HEARTS,ACE,1)\n(DIAMONDS,ACE,1)", testHand.toString());
	}

	@Test
	public void testCardPointTotalWithAcesLow() {
		Hand testHand = new Hand();
		testHand.addCard(new Card(10, Suit.DIAMONDS, Type.STANDARD));
		testHand.addCard(new Card(1, Suit.DIAMONDS, Type.ACE));
		assertEquals(21, testHand.cardPointTotal());
	}
	
	@Test
	public void testCardPointTotalWithAcesHigh() {
		Hand testHand = new Hand();
		testHand.addCard(new Card(10, Suit.DIAMONDS, Type.STANDARD));
		testHand.addCard(new Card(10, Suit.HEARTS, Type.STANDARD));
		testHand.addCard(new Card(1, Suit.DIAMONDS, Type.ACE));
		assertEquals(21, testHand.cardPointTotal());
	}
	
	@Test
	public void testCardPointTotalEmpty() {
		Hand testHand = new Hand();
		assertEquals(0, testHand.cardPointTotal());
	}
	
	@Test
	public void testCardPointTotalWithoutAces() {
		Hand testHand = new Hand();
		testHand.addCard(new Card(10, Suit.DIAMONDS, Type.STANDARD));
		testHand.addCard(new Card(10, Suit.HEARTS, Type.STANDARD));
		assertEquals(20, testHand.cardPointTotal());
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
		assertEquals(0, testHand.amountAces());
	}
	
	@Test
	public void testAmountAcesOne() {
		Hand testHand = new Hand();
		testHand.addCard(new Card(10, Suit.DIAMONDS, Type.STANDARD));
		testHand.addCard(new Card(10, Suit.HEARTS, Type.STANDARD));
		testHand.addCard(new Card(1, Suit.CLUBS, Type.ACE));
		assertEquals(1, testHand.amountAces());
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
		assertEquals(1, testHand.numberCards());
		assertEquals(Suit.DIAMONDS, testHand.get(0).getSuit());
		assertEquals(1, newHand.numberCards());
		assertEquals(Suit.HEARTS, newHand.get(0).getSuit());
	}
}
