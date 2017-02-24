package example.cards;

import apcs.Window;

public class Card {
	
	public static final int CLUBS = 11;
	public static final int DIAMONDS = 12;
	public static final int HEARTS = 13;
	public static final int SPADES = 14;
	
	public static final int JACK = 11;
	public static final int QUEEN = 12;
	public static final int KING = 13;
	public static final int ACE = 14;
	
	private int number = 0;
	private int suit = 0;
	public static final int width = 100;
	public static final int rounding = 4;
	
	public Card(int number, int suit) {
		this.number = number;
		this.suit = suit;
	}
	
	public void draw(int x, int y) {
		int height = width * 7 / 5;
		Window.out.color("white");
		Window.out.rectangle(x, y, width - rounding * 2, height);
		Window.out.rectangle(x, y, width, height - rounding * 2);
		Window.out.circle(x - width / 2 + rounding, y - height / 2 + rounding, rounding);
		Window.out.circle(x + width / 2 - rounding, y - height / 2 + rounding, rounding);
		Window.out.circle(x - width / 2 + rounding, y + height / 2 - rounding, rounding);
		Window.out.circle(x + width / 2 - rounding, y + height / 2 - rounding, rounding);
		
		Window.out.color("black");
		Window.out.font("Monaco", 30);
		String label = "";
		switch(number) {
		case ACE: label = " A"; break;
		case KING: label = " K"; break;
		case QUEEN: label = " Q"; break;
		case JACK: label = " J"; break;
		case 10: label = "10"; break;
		default: label = " " + number; break;
		}
		Window.out.print(label, x + width / 2 - 40, y - height / 2 + 30);
		Window.out.print(label, x - width / 2 + 5, y + height / 2 - 5);
		switch(suit) {
		case CLUBS: label = "♣"; break;
		case DIAMONDS: label = "♦"; Window.out.color("red"); break;
		case HEARTS: label = "♥"; Window.out.color("red"); break;
		case SPADES: label = "♠"; break;
		}
		Window.out.fontSize(60);
		Window.out.print(label, x - 25, y + 20);
	}
}
