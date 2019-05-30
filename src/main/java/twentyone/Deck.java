package twentyone;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
	private final ArrayList<Card> deckCards;

	public Deck() {
		ArrayList<Card> deckCards = new ArrayList<Card>();
		for (Card.Suit suit : Card.Suit.values()) {
			for (int i = 2; i <= 10; i++) {
				deckCards.add(new Card(i, suit, Card.Type.STANDARD));
			}
			deckCards.add(new Card(3, suit, Card.Type.KING));
			deckCards.add(new Card(2, suit, Card.Type.QUEEN));
			deckCards.add(new Card(1, suit, Card.Type.JACK));
			// The ace is 1 or 11 points, here it is 1.
			// When calculating the total points in the Hand class the 11 points are introduced when it is beter for the player
			deckCards.add(new Card(1, suit, Card.Type.ACE));
		}
		this.deckCards = deckCards;
		shuffleDeck();
	}

	public void shuffleDeck() {
		Collections.shuffle(deckCards);
	}

	public Card pickCard() {
		// I think by using a lot of split moves it might be possible to empty the deck
		if (deckCards.isEmpty()) throw new IllegalStateException();
		Card card = deckCards.get(deckCards.size() - 1);
		deckCards.remove(deckCards.size() - 1);
		return card;
	}
}
