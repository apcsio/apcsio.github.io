package example.connect4;

import apcs.Window;

public class Connect4 {

	static int[][] board;
	
	// Determine the look and size of the game
	static int size = 50;
	static int width = 15;
	static int height = 10;
	
	// "active" column
	static int active = 1;
	// active player
	static int player = 1;
	
	public static void main(String[] args) {
		
		initialize();
		
		while (true) {
			draw();
			active = Window.mouse.getX() / size;
			
			if (Window.mouse.clicked() && canDrop(active)) {
				drop(active);
				
				// Wait for the mouse to be released
				while (Window.mouse.clicked()) { 
					// do nothing
				}
			}
			
			// If there is a winner
			if (getWinner() > 0) {
				// Show the winner
				drawWinner(getWinner());
				Window.frame(2000);
				
				// Reset the board
				initialize();
			}
			
			Window.frame();
		}
		
	}
	
	private static void drawWinner(int winner) {
		Window.out.font("Arial black", 60);
		if (winner == 1) {
			Window.out.color("red");
		}
		else {
			Window.out.color("blue");
		}
		Window.out.print("Player " + winner + " wins!", width * size / 2 - 250, height * size / 2 - 25);
	}

	private static int getWinner() {
		// Horizontal four in a row
		for (int x = 0 ; x < width - 3 ; x++) {
			for (int y = 0 ; y < height ; y++) {
				
				// If all pieces in this four in a row are the same and not empty
				if (board[x][y] > 0 &&
					board[x][y] == board[x + 1][y] &&
					board[x][y] == board[x + 2][y] &&
					board[x][y] == board[x + 3][y]) {
					return board[x][y];
				}
			}
		}
		
		// Vertical four in a row
		for (int x = 0 ; x < width ; x++) {
			for (int y = 0 ; y < height - 3 ; y++) {
				
				// If all pieces in this four in a row are the same and not empty
				if (board[x][y] > 0 &&
					board[x][y] == board[x][y + 1] &&
					board[x][y] == board[x][y + 2] &&
					board[x][y] == board[x][y + 3]) {
					return board[x][y];
				}
			}
		}
		
		// Diagonal four in a row
		for (int x = 0 ; x < width - 3 ; x++) {
			for (int y = 0 ; y < height - 3 ; y++) {
				
				// If all pieces in this four in a row are the same and not empty
				if (board[x][y] > 0 &&
					board[x][y] == board[x + 1][y + 1] &&
					board[x][y] == board[x + 2][y + 2] &&
					board[x][y] == board[x + 3][y + 3]) {
					return board[x][y];
				}
			}
		}
		
		for (int x = 0 ; x < width - 3 ; x++) {
			for (int y = 3 ; y < height ; y++) {
				
				// If all pieces in this four in a row are the same and not empty
				if (board[x][y] > 0 &&
					board[x][y] == board[x + 1][y - 1] &&
					board[x][y] == board[x + 2][y - 2] &&
					board[x][y] == board[x + 3][y - 3]) {
					return board[x][y];
				}
			}
		}
		
		// There was no winner (default)
		return 0;
	}

	/**
	 * Drops a piece in the given column.
	 * @param column - the column to drop the piece in
	 */
	private static void drop(int column) {
		// put the piece into the top space
		board[column][0] = player;
		
		int next = 1;
		// while the space underneath is valid and is empty,
		while (next < height && board[column][next] == 0) {
			
			// move the piece from its current space to the space underneath
			board[column][next - 1] = 0;
			board[column][next] = player;
			next = next + 1;
			
			// Draw the change
			draw();
			Window.frame();
		}
		// Redraw the state of the board
		draw();
		
		// Switch the current player
		if (player == 1) {
			player = 2;
		}
		else {
			player = 1;
		}
	}

	/**
	 * Returns true if a piece can be dropped in the given column.
	 * @param column - the column to drop a piece in
	 * @return true or false
	 */
	private static boolean canDrop(int column) {
		// Is the top space empty?
		if (board[column][0] == 0) {
			return true;
		}
		// Otherwise, not empty
		else {
			return false;
		}
	}

	/**
	 * Draw the Connect4 grid.
	 */
	private static void draw() {
		Window.out.background("yellow");
		
		// Go to every x, y position
		for (int x = 0 ; x < width ; x++) {
			for (int y = 0 ; y < height ; y++) {
				
				// Pick the drawing color based on what is in the board position.
				if (board[x][y] == 0) {
					if (active == x) {
						Window.out.color("gray");
					}
					else {
						Window.out.color("black");
					}
				}
				else if (board[x][y] == 1) {
					Window.out.color("red");
				}
				else {
					Window.out.color("blue");
				}
				
				// Draw a black circle
				Window.out.circle(x * size + size / 2, y * size + size / 2, size * 4 / 9);
			}
		}
	}

	private static void initialize() {
		Window.size(width * size, height * size);
		board = new int[width][height];
	}

}