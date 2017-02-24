package example.fallingsand;

import apcs.Window;

public class FallingSandWithCompression {

	static int width = 500;
	static int height = 500;
	static int speed = 200;
	
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
			
			// Clicking adds more sand to the world.
			if (Window.mouse.clicked()) {
				int x = Window.mouse.getX();
				int y = Window.mouse.getY();
				
				// Add the sand to the world
				source(world, x, y, 10);
			}
		}
	}
	
	/**
	 * Adds random sand particles to the world around the given x, y position.
	 * @param world - the world
	 * @param x - the x position
	 * @param y - the y position
	 * @param amount - the amount of sand to add
	 */
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
			for (int y = height - 1 ; y >= 0 ; y--) {
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
		
		// Then push sand sideways if there is enough pressure
		int[][] afterCompress = new int[width][height];
		for (int x = 0 ; x < width ; x++) {
			for (int y = height - 1 ; y >= 0 ; y--) {
				// If this has been occupied as a compressed particle
				if (afterCompress[x][y] != empty) {}
				
				// If this particle can keep moving down
				else if (y + 1 < height && afterFall[x][y + 1] == empty) {
					afterCompress[x][y] = afterFall[x][y];
				}
				
				// If the sand has two or more sand particles on top of it, check if it can be moved
				// to the left or to the right.
				else if (y > 1 && afterFall[x][y] != empty && afterFall[x][y - 1] != empty && afterFall[x][y - 2] != empty) {
					boolean emptyLeft = x > 0 && 
											afterFall[x - 1][y] == empty && 
											afterCompress[x - 1][y] == empty;
					
					boolean emptyRight = x + 1 < width && 
											afterFall[x + 1][y] == empty && 
											afterCompress[x + 1][y] == empty;
					
					// Randomly choose which way it goes if both are empty
					if (emptyLeft && emptyRight) {
						// Heads is left
						if (Window.flipCoin()) 
							emptyRight = false;
						// Tails is right
						else 
							emptyLeft = false;
					}
					// Move to the left
					if (emptyLeft) {
						afterCompress[x - 1][y] = afterFall[x][y];
					}
					// Move to the right
					else if (emptyRight) {
						afterCompress[x + 1][y] = afterFall[x][y];
					}
					// Can't move
					else {
						afterCompress[x][y] = afterFall[x][y];
					}
				}
				// Otherwise it is a non-moving particle
				else {
					afterCompress[x][y] = afterFall[x][y];
				}
			}
		}
		
		// Push everything down again.
		afterFall = new int[width][height];
		for (int x = 0 ; x < width ; x++) {
			for (int y = height - 1 ; y >= 0 ; y--) {
				if (afterCompress[x][y] != empty) {
					// Non-solid particles move down
					if (y + 1 < height && afterCompress[x][y + 1] == empty) {
						afterFall[x][y + 1] = afterCompress[x][y];
					}
					else {
						afterFall[x][y] = afterCompress[x][y];
					}
				}
			}
		}
		
		return afterFall;
	}
	
	/**
	 * Returns true if there is a sand particle in the world at the given x, y position.
	 * @param world - the world
	 * @param x - the x position
	 * @param y - the y position
	 * @return
	 */
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
