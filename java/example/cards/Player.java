package example.cards;

import java.util.ArrayList;

public class Player {
	
	private ArrayList <Card> cards;
	private int id;
	
	public Player(int id) {
		this.id = id;
		cards = new ArrayList <Card> ();
	}
	
	public void deal(Card c) {
		cards.add(c);
	}
	
	public void fold() {
		cards.clear();
	}
	
	public void draw(int x, int y) {
		int width = cards.size() * Card.width + 6 * (cards.size() - 1);
		for (int i = 0 ; i < cards.size() ; i++)
			cards.get(i).draw(x - width / 2 + i * (Card.width + 6), y);
	}
}
