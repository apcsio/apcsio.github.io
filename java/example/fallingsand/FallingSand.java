package example.fallingsand;

import apcs.Window;

public class FallingSand {

	static int width = 500;
	static int height = 500;
	static int speed = 100;
	
	static int empty = 0;
	static int sand = 1;
	
	static String backgroundColor = "black";
	static String sandColor = "tan";
	
	public static void main(String[] args) {
		Window.size(width, height);
		Window.setFrameRate(speed);
		
		int[][] world = new int[width][height];
		
		while (true) {
			draw(world);
			world = fall(world);
			if (Window.mouse.clicked()) {
				source(world, Window.mouse.getX(), Window.mouse.getY(), 10);
			}
		}
	}
	
	public static void source(int[][] world, int x, int y, int amount) {
		for (int i = 0 ; i < amount ; i++) {
			// Randomly add sand to this location 
			int sx = x + Window.rollDice(amount) - amount / 2;
			int sy = y + Window.rollDice(amount) - amount / 2;
			
			// If it's a valid coordinate, put a sand particle on it
			if (sx >= 0 && sx < width && sy >= 0 && sy < height)
				world[sx][sy] = sand;
		}
	}
	
	/**
	 * Gets the next state of Conway's Game of Life.
	 * @param state - the current state of the game
	 * @return the next state
	 */
	public static int[][] fall(int[][] state) {
		
		// First move everything in the world down.
		int[][] afterFall = new int[width][height];
		for (int x = 0 ; x < width ; x++) {
			for (int y = 0 ; y < height ; y++) {
				if (state[x][y] != empty) {
					// Non-solid particles move down
					if (y + 1 < height && state[x][y + 1] == empty) {
						afterFall[x][y + 1] = state[x][y];
					}
					else {
						afterFall[x][y] = state[x][y];
					}
				}
			}
		}
		
		return afterFall;
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
	public static void draw(int[][] world) {
		// Set all squares to dead, then draw the alive ones.
		Window.out.background(backgroundColor);
		for (int x = 0 ; x < width ; x++) {
			for (int y = 0 ; y < height ; y++) {
				// If this is a grain of sand.
				if (world[x][y] == sand) {
					Window.out.color(sandColor);
					Window.out.square(x, y, 1);
				}
			}
		}
		// Complete one frame.
		Window.frame();
	}

}
