package example.gameoflife;

import apcs.Window;

public class GameOfLife {

	static int width = 50;
	static int height = 50;
	static int scale = 10;
	static int speed = 10;
	
	static String dead = "white";
	static String alive = "black";
	
	public static void main(String[] args) {
		Window.size(width * scale, height * scale);
		Window.setFrameRate(speed);
		
		boolean[][] world = newWorld(0.5);
		
		while (true) {
			draw(world);
			world = getNext(world);
		}
	}
	
	/**
	 * Generates a random world with alive cells at the given frequency.
	 * @param aliveProbability
	 * @return
	 */
	public static boolean[][] newWorld(double aliveProbability) {
		boolean[][] world = new boolean[width][height];
		for (int x = 0 ; x < width ; x++) {
			for (int y = 0 ; y < height ; y++) {
				if (Math.random() < aliveProbability)
					world[x][y] = true;
			}
		}
		return world;
	}
	
	/**
	 * Gets the next state of Conway's Game of Life.
	 * @param state - the current state of the game
	 * @return the next state
	 */
	public static boolean[][] getNext(boolean[][] state) {
		boolean[][] next = new boolean[width][height];
		
		for (int x = 0 ; x < width ; x++) {
			for (int y = 0 ; y < height ; y++) {
				
				// Count the number of neighboring cells.
				int count = 0;
				if (isAlive(state, x - 1, y - 1)) count++;
				if (isAlive(state, x, y - 1)) count++;
				if (isAlive(state, x + 1, y - 1)) count++;
				if (isAlive(state, x - 1, y)) count++;
				if (isAlive(state, x + 1, y)) count++;
				if (isAlive(state, x - 1, y + 1)) count++;
				if (isAlive(state, x, y + 1)) count++;
				if (isAlive(state, x + 1, y + 1)) count++;
				
				// Survival rule
				if (isAlive(state, x, y)) {
					if (count == 2 || count == 3)
						next[x][y] = true;
				}
				// Reproduction rule
				else {
					if (count == 3)
						next[x][y] = true;
				}
			}
		}
		
		return next;
	}
	
	public static boolean isAlive(boolean[][] world, int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) {
			return false;
		}
		return world[x][y];
	}
	
	/**
	 * Draws the given world onto the window. 
	 */
	public static void draw(boolean[][] world) {
		// Set all squares to dead, then draw the alive ones.
		Window.out.background(dead);
		Window.out.color(alive);
		for (int x = 0 ; x < width ; x++) {
			for (int y = 0 ; y < height ; y++) {
				// If this cell (x, y) is alive
				if (world[x][y])
					Window.out.square(x * scale + scale / 2, y * scale + scale / 2, scale);
			}
		}
		// Complete one frame.
		Window.frame();
	}

}
