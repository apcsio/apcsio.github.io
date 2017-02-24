package example.reversi;

import apcs.Window;

public class Reversi {

	static int length = 8;
	static int size = 100;
	static int player = 2;

	static String boardColor = "green";
	static String player1Piece = "white";
	static String player1Valid = "light gray";
	static String player2Piece = "black";
	static String player2Valid = "dark gray";
	static String gridColor = "dark green";
	
	static int[][] direction = { {-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1} };

	public static void main(String[] args) {
		Window.size(length * size, length * size);
		int[][] board = newBoard();

		while (true) {
			draw(board);
			
			Window.mouse.waitForClick();
			int x = Window.mouse.getX() / size;
			int y = Window.mouse.getY() / size;
			
			
			if (valid(x, y) && board[x][y] == 0 && valid(board, x, y, player)) {
				move(board, x, y, player);
				player = 1 + player % 2;
			}

			Window.mouse.waitForRelease();
		}
	}
	
	public static boolean valid(int x, int y) {
		return x >= 0 && x < length && y >= 0 && y < length;
	}
	
	public static boolean valid(int[][] board, int x, int y, int player) {
		// Next to opposite piece
		int opponent = 1 + player % 2;
		
		for (int[] dir : direction) {
			int dx = dir[0], dy = dir[1];
			if (valid(x + dx, y + dy) && board[x + dx][y + dy] == opponent) {
				for (int i = 2 ; valid(x + dx * i, y + dy * i) ; i++) {
					if (board[x + dx * i][y + dy * i] == player)
						return true;
					if (board[x + dx * i][y + dy * i] == 0)
						break;
				}
			}
		}
		return false;
	}

	// Assume validated
	public static void move(int[][] board, int x, int y, int player) {
		int opponent = 1 + player % 2;
		
		board[x][y] = player;
		for (int[] dir : direction) {
			int dx = dir[0], dy = dir[1];
			if (valid(x + dx, y + dy) && board[x + dx][y + dy] == opponent) {
				for (int i = 2 ; valid(x + dx * i, y + dy * i) ; i++) {
					if (board[x + dx * i][y + dy * i] == player) {
						for (int j = 1 ; j < i ; j++) {
							board[x + dx * j][y + dy * j] = player;
						}
						break;
					}
				}
			}
		}
	}

	public static int[][] newBoard() {
		int[][] b = new int[length][length];
		b[length / 2 - 1][length / 2 - 1] = 1;
		b[length / 2][length / 2 - 1] = 2;
		b[length / 2][length / 2] = 1;
		b[length / 2 - 1][length / 2] = 2;
		return b;
	}

	public static void draw(int[][] board) {
		int l = size * length;

		Window.out.background(boardColor);
		for (int x = 0 ; x < length ; x++) {
			// Draw grid line before every column except first
			if (x > 0) {
				Window.out.color(gridColor);
				Window.out.rectangle(x * size, l / 2, 4, l);
			}

			for (int y = 0 ; y < length ; y++) {
				// For first x, draw grid line for y
				if (x == 0 && y > 0) {
					Window.out.color(gridColor);
					Window.out.rectangle(l / 2, y * size, l, 4);
				}
				if (board[x][y] == 0 && valid(board, x, y, player)) {
					Window.out.color((player == 1) ? player1Valid : player2Valid);
					Window.out.square(x * size + size / 2, y * size + size / 2, size - 4);
				}
				if (board[x][y] == 1) {
					Window.out.color(player1Piece);
					Window.out.circle(x * size + size / 2, y * size + size / 2, size * 4 / 9);
				}
				if (board[x][y] == 2) {
					Window.out.color(player2Piece);
					Window.out.circle(x * size + size / 2, y * size + size / 2, size * 4 / 9);
				}
			}
		}

		Window.frame();
	}
}
