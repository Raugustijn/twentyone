package io.github.raugustijn.twentyone;

public class Card {
	private final int value;
	private final Suit suit;
	private final Type type;

	public Card(int value, Suit suit, Type type) {
		this.value = value;
		this.suit = suit;
		this.type = type;
	}

	public enum Suit {
		DIAMONDS, SPADES, HEARTS, CLUBS
	}

	public enum Type {
		KING, QUEEN, JACK, ACE, STANDARD
	}

	public int getValue() {
		return value;
	}

	public Suit getSuit() {
		return suit;
	}

	public Type getType() {
		return type;
	}
	
	@Override
	public String toString() {
		return "(" + suit.toString() + "," + type.toString() + "," + value + ")";
	}
}
