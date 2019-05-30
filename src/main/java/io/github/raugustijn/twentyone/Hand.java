package io.github.raugustijn.twentyone;

import java.util.ArrayList;

public class Hand {
	private final ArrayList<Card> ownCards;
	private State state;
	
	public enum State {
		BUSTED, PLAYING, STANDING
	}
	
	public Hand() {
		ownCards = new ArrayList<Card>();
		state = State.PLAYING;
	}
	
	public void addCard(Card card) {
		ownCards.add(card);
	}
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public int cardPointTotal() {
		int amount = ownCards.stream().mapToInt(Card::getValue).sum();
		// implement ace rule: ace can be 1 or 11 points
		// in this implementation: if 11 point for an ace does not make the total go over 21 then the ace is 11 points
		int aces = amountAces(); 
		for(int i = 0; i < aces; i++) {
			if((amount + 10) <= 21) {
				amount = amount + 10;
			}
		}
		return amount;
	}
	
	public int numberCards() {
		return ownCards.size();
	}
	
	public boolean isSplitAllowed() {
		if(ownCards.size() != 2) return false;
		return (ownCards.get(0).getType().equals(ownCards.get(1).getType())) && 
		(ownCards.get(0).getValue() == (ownCards.get(1).getValue()));
	}
	
	public Hand split() {
		if(ownCards.size() != 2) throw new IllegalStateException();
		Card card = ownCards.get(1);
		ownCards.remove(1);
		Hand newHand = new Hand();
		newHand.addCard(card);
		return newHand;
	}
	
	public Card get(int index) {
		if(index >= 0 && index < ownCards.size()) return ownCards.get(index);
		return null;
	}
	
	public int amountAces() {
		int aces = 0;
		for(Card card: ownCards) {
			if(card.getType().equals(Card.Type.ACE)) aces++;
		}
		return aces;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		result.append("State: " + state.toString() + "\nCards:");
		for(Card card : ownCards) {
			result.append("\n" + card.toString());
		}
		return result.toString();
	}
}
