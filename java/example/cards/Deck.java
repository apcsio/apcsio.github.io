package example.cards;

public class Deck {
	private Card[] cards;
	private int current = 0;
	
	public Deck() {
		cards = new Card[52];
		for (int i = 0 ; i < 13 ; i++) {
			cards[i * 4] = new Card(i + 2, Card.HEARTS);
			cards[i * 4 + 1] = new Card(i + 2, Card.SPADES);
			cards[i * 4 + 2] = new Card(i + 2, Card.CLUBS);
			cards[i * 4 + 3] = new Card(i + 2, Card.DIAMONDS);
		}
	}
	
	// Fisher-Yates shuffle
	public void shuffle() {
        for (int i = 0; i < cards.length ; i++) {
            int r = i + (int) (Math.random() * (cards.length - i));
            Card temp = cards[r];
            cards[r] = cards[i];
            cards[i] = temp;
        }
	}
	
	public Card draw() {
		Card next = cards[current];
		current++;
		if (current >= cards.length) {
			shuffle();
			current = 0;
		}
		return next;
	}
}
