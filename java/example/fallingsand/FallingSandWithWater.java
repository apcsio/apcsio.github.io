package example.fallingsand;

import apcs.Window;

public class FallingSandWithWater {

	static int width = 500;
	static int height = 500;
	static int speed = 200;
	
	static int empty = 0;
	static int sand = 1;
	static int water = 10;
	
	// physics configurations
	static int viscosity = 50;
	
	static String backgroundColor = "black";
	static String sandColor = "tan";
	static String waterColor = "blue";
	
	public static void main(String[] args) {
		Window.size(width, height);
		Window.setFrameRate(speed);
		
		int[][] world = new int[width][height];
		
		while (true) {
			draw(world);
			world = fall(world);
			if (Window.key.pressed("w")) {
				source(world, Window.mouse.getX(), Window.mouse.getY(), 10, water);
			}
			if (Window.mouse.clicked()) {
				source(world, Window.mouse.getX(), Window.mouse.getY(), 10, sand);
			}
			if (Window.key.pressed("r")) {
				rain(world, 0.1);
			}
			if (Window.key.pressed("space")) {
				eraser(world, Window.mouse.getX(), Window.mouse.getY(), 2);
			}
		}
	}
	
	public static void rain(int[][] world, double intensity) {
		for (int x = 0 ; x < width ; x++) {
			for (int y = 0 ; y < intensity * 50 ; y++) {
				if (Math.random() < intensity / 10)
					world[x][y] = water;
			}
		}
	}
	
	public static void eraser(int[][] world, int x, int y, int radius) {
		for (int xx = x - radius ; xx <= x + radius ; xx++) {
			for (int yy = y - radius ; yy <= y + radius ; yy++) {
				if (xx >= 0 && yy >= 0 && xx < width && yy < width)
					world[xx][yy] = empty;
			}
		}
	}
	
	public static void source(int[][] world, int x, int y, int amount, int element) {
		for (int i = 0 ; i < amount ; i++) {
			// Randomly add sand to this location 
			int sx = x + Window.rollDice(amount) - amount / 2;
			int sy = y + Window.rollDice(amount) - amount / 2;
			
			// If it's a valid coordinate, put a sand particle on it
			if (sx >= 0 && sx < width && sy >= 0 && sy < height)
				world[sx][sy] = element;
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
					// Sand sinks through water
					else if (y + 1 < height && state[x][y] == sand && state[x][y + 1] == water) {
						afterFall[x][y] = water;
						afterFall[x][y + 1] = sand;
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
				else if (y > 1 && afterFall[x][y] == water ||
						(afterFall[x][y] == sand && afterFall[x][y - 1] == sand && afterFall[x][y - 2] == sand)) {
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
						int offsetx = 1, offsety = 0;
						// Water can shift multiple spaces
						if (afterFall[x][y] == water) {
							while (offsetx < Window.rollDice(viscosity) && x - offsetx - 1 >= 0 &&
								   afterFall[x - offsetx - 1][y + offsety] == empty &&
								   afterCompress[x - offsetx - 1][y + offsety] == empty) {
								
								offsetx++;
								if (x - offsetx >= 0 && y + offsety + 1 < height &&
									afterFall[x - offsetx][y + offsety + 1] == empty &&
									afterCompress[x - offsetx][y + offsety + 1] == empty) {
									offsety++;
									//break;
								}
								
							}
						}
						afterCompress[x - offsetx][y + offsety] = afterFall[x][y];
					}
					// Move to the right
					else if (emptyRight) {
						int offsetx = 1, offsety = 0;
						// Water can shift multiple spaces
						if (afterFall[x][y] == water) {
							while (offsetx < Window.rollDice(viscosity) && x + offsetx + 1 < width &&
								   afterFall[x + offsetx + 1][y + offsety] == empty &&
								   afterCompress[x + offsetx + 1][y + offsety] == empty) {
								
								offsetx++;
								if (x + offsetx < width && y + offsety + 1 < height &&
									afterFall[x + offsetx][y + offsety + 1] == empty &&
									afterCompress[x + offsetx][y + offsety + 1] == empty) {
									offsety++;
									//break;
								}
								
							}
						}
						afterCompress[x + offsetx][y + offsety] = afterFall[x][y];
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
				if (world[x][y] == water) {
					Window.out.color(waterColor);
					Window.out.square(x, y, 1);
				}
			}
		}
		// Complete one frame.
		Window.frame();
	}

}
