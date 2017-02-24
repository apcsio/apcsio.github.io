package example.flappybird;

import apcs.Window;

/**
 * An example of the physics behind Flappy Bird. 
 * 
 * @author keshav
 */
public class FlappyBirdPhysics {

	public static void main(String[] args) {
		Window.size(400, 600);
		
		// Initial position of the bird.
		int x = 200;
		int y = 200;
		
		// Initial speed of the bird.
		int dy = 0;
		
		// Infinitely draw frames
		while (true) {
			Window.out.background("light blue");
			
			// Draw the bird
			Window.out.color("yellow");
			Window.out.oval(x, y, 40, 30);
			
			/** @see BouncingBall.java */
			// Move the bird
			y = y + dy;
			dy = dy + 1;
			
			// Space key to move up
			if (Window.key.pressed("space")) {
				dy = -15;
			}
			
			Window.frame();
		}
	}

}
