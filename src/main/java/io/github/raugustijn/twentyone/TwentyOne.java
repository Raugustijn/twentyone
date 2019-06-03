package io.github.raugustijn.twentyone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.github.raugustijn.twentyone.ui.TwentyOneUi;
//TODO: Add javadoc annotation to methods
public class TwentyOne {
	private final TwentyOneUi ui;

	public enum PlayOption {
		STAND, HIT, SPLIT
	}

	TwentyOne() {
		ui = new TwentyOneUi();
	}
	
	void startGame() {
		Deck deck = new Deck();
		Player[] players = generatePlayers();
		Player bank = new Player("Bank");
		giveAllPlayersCard(players, bank, deck);
		//collect bets
		giveAllPlayersCard(players, bank, deck);
		while(!checkFinalStatePlayers(players)) {
			roundPlayers(players, bank, deck);
		}
		ArrayList<Player> standingPlayers = roundBank(players, bank, deck);
		ui.finalGameTable(players, bank);
		ui.notifyWinningPlayers(determineWinners(standingPlayers, bank));
	}
	
	/**
	 * This method determines who has won the game.
	 * @param standingPlayers A list of players (excluding the bank) who have at least one hand that is not busted.
	 * @param bank The player who is the bank.
	 * @return List<Player> A list of players who have won.
	 */
	List<Player> determineWinners(List<Player> standingPlayers, Player bank) {
		// if all players are busted the bank wins
		if (standingPlayers.isEmpty()) {
			Collections.singletonList(bank);
		}
		// if the bank is busted all the players win
		if (Hand.State.BUSTED.equals(bank.getHand(0).getState())) {
			return standingPlayers;
		}
		ArrayList<Player> winners = new ArrayList<>();
		for (Player player : standingPlayers) {
			for (int i = 0; i < player.getNumberHand(); i++) {
				Hand hand = player.getHand(i);
				if (!Hand.State.BUSTED.equals(hand.getState()) && (hand.cardPointTotal() > bank.getHand(0).cardPointTotal())) {
					if(!winners.contains(player)) winners.add(player);
				}
			}
		}
		if (winners.size() > 0) {
					return winners;
		} else {
			return Collections.singletonList(bank);
		}
	}
	
	ArrayList<Player> roundBank(Player[] players, Player bank, Deck deck) {
		ArrayList<Player> standingPlayers = standingPlayers(players);
		while ((!standingPlayers.isEmpty())
				&& (bank.getHand(0).cardPointTotal() < 17)) {
			executePlayOption(bank.getHand(0), bank, PlayOption.HIT,
					deck);
		}
		if(!Hand.State.BUSTED.equals(bank.getHand(0).getState())) executePlayOption(bank.getHand(0), bank, PlayOption.STAND, deck);
		return standingPlayers;
	}
	
	ArrayList<Player> standingPlayers(Player[] players) {
		ArrayList<Player> standingPlayers = new ArrayList<>();
		for (Player player : players) {
			boolean standing = false;
			for (int i = 0; i < player.getNumberHand(); i++) {
				Hand hand = player.getHand(i);
				if (Hand.State.STANDING.equals(hand.getState())) standing = true;
			}
			if (standing) standingPlayers.add(player);
		}
		return standingPlayers;
	}

	void roundPlayers(Player[] players, Player bank, Deck deck) {
		ui.notifyNewRound(players, bank);
		for (Player player : players) {
			int numberHands = player.getNumberHand();
			for (int i = 0; i < numberHands; i++) {
				Hand hand = player.getHand(i);
				if (Hand.State.PLAYING.equals(hand.getState())) {
					executePlayOption(hand, player, ui.askPlayingOption(player, hand), deck);
				}
			}
		}
	}

	void executePlayOption(Hand hand, Player player,
			PlayOption playOption, Deck deck) {
		switch (playOption) {
		case HIT:
			hand.addCard(deck.pickCard());
			if (hand.cardPointTotal() > 21) {
				hand.setState(Hand.State.BUSTED);
				ui.notifyPlayerHandBusted(hand, player);
			} else {
				ui.notifyPlayerNewHand(hand, player);
			}
			break;
		case SPLIT:
			if (!hand.isSplitAllowed()) throw new IllegalStateException();
			Hand newHand = player.split(hand);
			// Here a bet should be requested for the new hand
			ui.notifySplitHand(hand, newHand, player);
			break;
		case STAND:
			hand.setState(Hand.State.STANDING);
			break;
		default:
			break;
		}
	}
	
	void giveAllPlayersCard(Player[] players, Player bank, Deck deck) {
		for (Player player : players) {
			for (int i = 0; i < player.getNumberHand(); i++) {
				Hand hand = player.getHand(i);
				hand.addCard(deck.pickCard());
			}
		}
		bank.getHand(0).addCard(deck.pickCard());
	}

	Player[] generatePlayers() {
		int amountPlayers = ui.askPlayerAmount();
		Player[] players = new Player[amountPlayers];
		for (int i = 0; i < amountPlayers; i++) {
			String name = Integer.toString(i + 1);
			players[i] = new Player(name);
		}
		return players;
	}

	boolean checkFinalStatePlayers(Player[] players) {
		for (Player player : players) {
			for (int i = 0; i < player.getNumberHand(); i++) {
				Hand hand = player.getHand(i);
				if (hand.getState() == Hand.State.PLAYING) {
					return false;
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
		TwentyOne twentyOne = new TwentyOne();
		twentyOne.startGame();
	}
}
