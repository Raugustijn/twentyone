package io.github.raugustijn.twentyone;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class TwentyOneTest {
	//TODO: in all assertEquals the arguments are in the wrong order, flip them

	@Test
	public void testExecutePlayOptionHit() {
		TwentyOne game = new TwentyOne();
		Deck testDeck = new Deck();
		Player testPlayer = new Player("test");
		game.executePlayOption(testPlayer.getHand(0), testPlayer, TwentyOne.PlayOption.HIT, testDeck);
		assertEquals(testPlayer.getHand(0).numberCards(), 1);
		assertEquals(testPlayer.getHand(0).getState(), Hand.State.PLAYING);
	}
	
	@Test
	public void testExecutePlayOptionHitBusted() {
		TwentyOne game = new TwentyOne();
		Deck testDeck = new Deck();
		Player testPlayer = new Player("test");
		testPlayer.getHand(0).addCard(new Card(10, Card.Suit.DIAMONDS, Card.Type.STANDARD));
		testPlayer.getHand(0).addCard(new Card(10, Card.Suit.CLUBS, Card.Type.STANDARD));
		testPlayer.getHand(0).addCard(new Card(10, Card.Suit.HEARTS, Card.Type.STANDARD));
		game.executePlayOption(testPlayer.getHand(0), testPlayer, TwentyOne.PlayOption.HIT, testDeck);
		assertEquals(testPlayer.getHand(0).numberCards(), 4);
		assertEquals(testPlayer.getHand(0).getState(), Hand.State.BUSTED);
	}
	
	@Test(expected=IllegalStateException.class)
	public void testExecutePlayOptionSplitNoSplitAllowed() {
		TwentyOne game = new TwentyOne();
		Deck testDeck = new Deck();
		Player testPlayer = new Player("test");
		testPlayer.getHand(0).addCard(new Card(10, Card.Suit.DIAMONDS, Card.Type.STANDARD));
		testPlayer.getHand(0).addCard(new Card(9, Card.Suit.CLUBS, Card.Type.STANDARD));
		game.executePlayOption(testPlayer.getHand(0), testPlayer, TwentyOne.PlayOption.SPLIT, testDeck);
	}
	
	@Test
	public void testExecutePlayOptionSplit() {
		TwentyOne game = new TwentyOne();
		Deck testDeck = new Deck();
		Player testPlayer = new Player("test");
		testPlayer.getHand(0).addCard(new Card(10, Card.Suit.DIAMONDS, Card.Type.STANDARD));
		testPlayer.getHand(0).addCard(new Card(10, Card.Suit.CLUBS, Card.Type.STANDARD));
		game.executePlayOption(testPlayer.getHand(0), testPlayer, TwentyOne.PlayOption.SPLIT, testDeck);
		assertEquals(testPlayer.getNumberHand(), 2);
		assertEquals(testPlayer.getHand(0).numberCards(), 1);
		assertEquals(testPlayer.getHand(1).numberCards(), 1);
		assertEquals(testPlayer.getHand(0).getState(), Hand.State.PLAYING);
		assertEquals(testPlayer.getHand(1).getState(), Hand.State.PLAYING);
	}
	
	@Test
	public void testExecutePlayOptionStand() {
		TwentyOne game = new TwentyOne();
		Deck testDeck = new Deck();
		Player testPlayer = new Player("test");
		game.executePlayOption(testPlayer.getHand(0), testPlayer, TwentyOne.PlayOption.STAND, testDeck);
		assertEquals(testPlayer.getHand(0).numberCards(), 0);
		assertEquals(testPlayer.getHand(0).getState(), Hand.State.STANDING);
	}
	
	@Test
	public void testRoundBankHit() {
		TwentyOne game = new TwentyOne();
		Deck testDeck = new Deck();
		Player testPlayer = new Player("test");
		testPlayer.getHand(0).setState(Hand.State.STANDING);
		Player bankPlayer = new Player("bank");
		bankPlayer.getHand(0).addCard(new Card(10, Card.Suit.DIAMONDS, Card.Type.STANDARD));
		Player[] players = {testPlayer};
		game.roundBank(players, bankPlayer, testDeck);
		assertTrue(bankPlayer.getHand(0).cardPointTotal() >= 17);
	}
	
	@Test
	public void testRoundBankBustedPlayer() {
		TwentyOne game = new TwentyOne();
		Deck testDeck = new Deck();
		Player testPlayer = new Player("test");
		testPlayer.getHand(0).setState(Hand.State.BUSTED);
		Player bankPlayer = new Player("bank");
		bankPlayer.getHand(0).addCard(new Card(10, Card.Suit.DIAMONDS, Card.Type.STANDARD));
		Player[] players = {testPlayer};
		game.roundBank(players, bankPlayer, testDeck);
		assertEquals(bankPlayer.getHand(0).cardPointTotal(), 10);
	}
	
	@Test
	public void testStandingPlayers() {
		TwentyOne game = new TwentyOne();
		Player testPlayer = new Player("test");
		testPlayer.getHand(0).setState(Hand.State.BUSTED);
		Player testPlayer2 = new Player("test2");
		testPlayer2.getHand(0).setState(Hand.State.STANDING);
		Player testPlayer3 = new Player("test3");
		testPlayer3.getHand(0).setState(Hand.State.PLAYING);
		Player[] players = {testPlayer, testPlayer2, testPlayer3};
		ArrayList<Player> standingPlayers = game.standingPlayers(players);
		assertEquals(standingPlayers.size(), 1);
		assertEquals(standingPlayers.get(0).getName(), "test2");
	}
	
	@Test
	public void testDetermineWinnersNoStanding() {
		TwentyOne game = new TwentyOne();
		Player testPlayer = new Player("Bank");
		List<Player> winners = game.determineWinners(new ArrayList<Player>(), testPlayer);
		assertEquals(winners.size(), 1);
		assertEquals(((Player)winners.get(0)).getName(), "Bank");
	}
	
	@Test
	public void testDetermineWinnersBankBusted() {
		TwentyOne game = new TwentyOne();
		Player testPlayer = new Player("Bank");
		testPlayer.getHand(0).setState(Hand.State.BUSTED);
		Player testPlayer2 = new Player("test");
		testPlayer2.getHand(0).setState(Hand.State.STANDING);
		List<Player> winners = game.determineWinners(Collections.singletonList(testPlayer2), testPlayer);
		assertEquals(winners.size(), 1);
		assertEquals(((Player)winners.get(0)).getName(), "test");
	}
	
	@Test
	public void testDetermineWinnersPlayerMorePoints() {
		TwentyOne game = new TwentyOne();
		Player bankPlayer = new Player("Bank");
		bankPlayer.getHand(0).addCard(new Card(3, Card.Suit.DIAMONDS, Card.Type.STANDARD));
		bankPlayer.getHand(0).setState(Hand.State.STANDING);
		// Generate multiple winning hands to check if the player is not duplicated in the winners list
		Player testPlayer2 = new Player("test");
		testPlayer2.getHand(0).addCard(new Card(10, Card.Suit.HEARTS, Card.Type.STANDARD));
		testPlayer2.getHand(0).addCard(new Card(10, Card.Suit.CLUBS, Card.Type.STANDARD));
		testPlayer2.split(testPlayer2.getHand(0));
		testPlayer2.getHand(0).setState(Hand.State.STANDING);
		testPlayer2.getHand(1).setState(Hand.State.STANDING);
		Player testPlayer3 = new Player("test2");
		
		testPlayer3.getHand(0).addCard(new Card(2, Card.Suit.HEARTS, Card.Type.STANDARD));
		testPlayer3.getHand(0).setState(Hand.State.STANDING);
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(testPlayer2);
		players.add(testPlayer3);
		List<Player> winners = game.determineWinners(players, bankPlayer);
		assertEquals(winners.size(), 1);
		assertEquals(((Player)winners.get(0)).getName(), "test");
	}
	
	@Test
	public void testDetermineWinnersBankMorePoints() {
		TwentyOne game = new TwentyOne();
		Player bankPlayer = new Player("Bank");
		bankPlayer.getHand(0).addCard(new Card(10, Card.Suit.DIAMONDS, Card.Type.STANDARD));
		bankPlayer.getHand(0).addCard(new Card(10, Card.Suit.CLUBS, Card.Type.STANDARD));
		bankPlayer.getHand(0).setState(Hand.State.STANDING);
		Player testPlayer2 = new Player("test");
		testPlayer2.getHand(0).addCard(new Card(10, Card.Suit.HEARTS, Card.Type.STANDARD));
		testPlayer2.getHand(0).setState(Hand.State.STANDING);
		List<Player> winners = game.determineWinners(Collections.singletonList(testPlayer2), bankPlayer);
		assertEquals(winners.size(), 1);
		assertEquals(((Player)winners.get(0)).getName(), "Bank");
	}

}
