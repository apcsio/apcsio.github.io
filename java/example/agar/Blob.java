package example.agar;

import apcs.Window;

public class Blob {
	
	int x = 10 + Window.rollDice(Window.width() - 20);
	int y = 10 + Window.rollDice(Window.height() - 20);
	int color = Window.rollDice(6);
	int radius = Window.rollDice(10) + 5;
	
	int xspeed = Window.rollDice(7) - 4;
	int yspeed = Window.rollDice(7) - 4;
	
	boolean controlled = false;
	boolean dead = false;
	
	public void control() {
		controlled = true;
	}

	public void die() {
		dead = true;
	}
	
	public void draw() {
		if (dead == false) {
			if (color == 1) { Window.out.color("red"); }
			if (color == 2) Window.out.color("green");
			if (color == 3) Window.out.color("yellow");
			if (color == 4) Window.out.color("blue");
			if (color == 5) Window.out.color("orange");
			if (color == 6) Window.out.color("purple");
			Window.out.circle(x, y, radius);
		}
	}

	public void move() {
		if (controlled) {
			int dx = Window.mouse.getX() - x;
			int dy = Window.mouse.getY() - y;
			
			x = x + dx / 10;
			y = y + dy / 10;
		}
		else {
			x = x + xspeed;
			y = y + yspeed;
		}
	}
}