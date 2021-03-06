package io.github.raugustijn.twentyone.ui;

import java.util.List;
import java.util.Scanner;

import io.github.raugustijn.twentyone.Hand;
import io.github.raugustijn.twentyone.Player;
import io.github.raugustijn.twentyone.TwentyOne;
import io.github.raugustijn.twentyone.TwentyOne.PlayOption;

public class TwentyOneUi {
	private Scanner input;
	
	public TwentyOneUi() {
		input = new Scanner(System.in);
	}

	public PlayOption askPlayingOption(Player player, Hand hand) {
		while (true) {
			if (hand.isSplitAllowed()) {
				System.out.println("Player " + player.getName() + ": Please enter playing option, [s]tand, [h]it, s[p]lit (enter s,h or p).");
				System.out.println("Playing hand:");
				System.out.println(hand.toString());
			} else {
				System.out.println("Player " + player.getName() + ": Please enter playing option, [s]tand, [h]it (enter s or h).");
				System.out.println("Playing hand:");
				System.out.println(hand.toString());
			}
			String playerOption;
				do {
					playerOption = input.nextLine();
				} while (playerOption.equals(""));
				switch (playerOption) {
				case "s":
					return TwentyOne.PlayOption.STAND;
				case "h":
					return TwentyOne.PlayOption.HIT;
				case "p":
					if (hand.isSplitAllowed()) return TwentyOne.PlayOption.SPLIT;
				default:
					break;
				}
			
		}
	}

	public void notifyPlayerHandBusted(Hand hand, Player player) {
		System.out.println("Player " + player.getName() + ": Hand is busted");
		System.out.println(hand.toString());
		System.out.println();
	}

	public void notifySplitHand(Hand hand, Hand newHand, Player player) {
		System.out.println("Player " + player.getName() + ": Hand is split:");
		System.out.println("Hand 1:");
		System.out.println(hand.toString());
		System.out.println("Hand 2:");
		System.out.println(newHand.toString());
		System.out.println();
	}

	public void notifyPlayerNewHand(Hand hand, Player player) {
		System.out.println("Player " + player.getName() + ": Updated hand");
		System.out.println(hand.toString());
		System.out.println();
	}

	private int getNumberBetween1And3(String line) throws IllegalArgumentException {
		Scanner scanner = new Scanner(line);
		if (!scanner.hasNextInt()) {
			throw new NumberFormatException("Expected an int");
		}
		int result = scanner.nextInt();
		if (result > 3 || result < 1) {
			throw new IllegalArgumentException("Invalid range");
		}
		return result;
	}

	public int askPlayerAmount() {
		int amountPlayers = 0;
		System.out.println("Enter the amount of players (number 1-3):");
		do {
			try {
				amountPlayers = getNumberBetween1And3(input.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("You did not enter a number.");
				System.out.println("Please enter a number between 1 and 3:");
			} catch (IllegalArgumentException e) {
				System.out.println("This game can only handle 1 to 3 players.");
				System.out.println("Please enter a number between 1 and 3:");
			}
		} while (amountPlayers == 0);
		return amountPlayers;
	}

	public void notifyWinningPlayers(List<Player> standingPlayers) {
		System.out.println("The following player(s) win(s):");
		for (Player player: standingPlayers) {
			System.out.println("Player: " + player.getName());
		}
	}
	
	public void notifyNewRound(Player[] players, Player bank) {
		System.out.println("New round, current situation:");
		printTableCurrentGame(players, bank);
	}
	
	public void finalGameTable(Player[] players, Player bank) {
		System.out.println("Final game situation:");
		printTableCurrentGame(players, bank);
	}
	
	public void printTableCurrentGame(Player[] players, Player bank) {
		System.out.println("-------");
		System.out.println("Bank:");
		System.out.println(bank.getHand(0).toString());
		System.out.println("Point total:" + (bank.getHand(0).cardPointTotal()));
		System.out.println();
		for (Player player: players) {
			System.out.println("Player " + player.getName() + ":");
			for (int i = 0; i < player.getNumberHand(); i++) {
				Hand hand = player.getHand(i);
				System.out.println("Hand " + (i + 1));
				System.out.println(hand.toString());
				System.out.println("Total amount of points:" + (hand.cardPointTotal()));
				System.out.println();
			}
		}
		System.out.println("-------");
	}
	
}
