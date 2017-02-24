package example.cards;

import apcs.Window;

public class PokerTable {
	
	public static void main(String[] args) {
		Window.size(1000, 600);
		PokerTable table = new PokerTable(4);
		
		table.deal();
		table.draw();
	}
	
	Deck deck;
	Player[] player;
	
	public PokerTable(int players) {
		deck = new Deck();
		player = new Player[players];
		for (int i = 0 ; i < players ; i++)
			player[i] = new Player(i);
	}
	
	public void deal() {
		deck.shuffle();
		for (Player p : player) {
			p.fold();
			p.deal(deck.draw());
			p.deal(deck.draw());
		}
	}
	
	public void draw() {
		Window.out.background("green");
		
		int width = player.length * 2 * (Card.width + 6);
		
		for (int i = 0 ; i < player.length ; i++) {
			player[i].draw(100 + Window.width() / 2 - width / 2 + i * (Card.width * 2 + 50), Window.height() - Card.width);
		}
		
	}
}
