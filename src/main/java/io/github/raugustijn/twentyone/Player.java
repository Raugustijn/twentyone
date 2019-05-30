package io.github.raugustijn.twentyone;

import java.util.ArrayList;

public class Player {
	private final String name;
	private final ArrayList<Hand> hands;
	
	public Player(String name) {
		hands = new ArrayList<Hand>();
		hands.add(new Hand());
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public Hand getHand(int index) {
		return hands.get(index);
	}
	
	public int getNumberHand() {
		return hands.size();
	}
	
	public Hand split(Hand hand) {
		//TODO: check if this hand actually belong to this player
		Hand newHand = hand.split();
		hands.add(newHand);
		return newHand;
	}
}
