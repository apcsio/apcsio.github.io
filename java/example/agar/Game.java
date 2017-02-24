package example.agar;

import java.util.ArrayList;

import apcs.Window;

public class Game {

	public static void main(String[] args) {
		Window.size(1000, 800);
		Blob myself = new Blob();
		myself.control();

		ArrayList<Blob> blobs = new ArrayList<Blob>();
		blobs.add(myself);

		while (true) {
			Window.out.background("white");

			for (Blob b : blobs) {
				b.draw();
				b.move();
			}

			// For every blob in the list
			for (int test = 0 ; test < blobs.size() ; test++) {

				// Go to every other blob in the list
				for (int other = 0 ; other < blobs.size() ; other++) {

					// If the test position is different from the other blob.
					if (test != other) {
						// Check if they are colliding.
						Blob testBlob = blobs.get(test);
						Blob otherBlob = blobs.get(other);

						// Pythagorean theorem.
						int a = testBlob.x - otherBlob.x;
						int b = testBlob.y - otherBlob.y;
						int c = testBlob.radius + (int) Math.log(otherBlob.radius);

						// If they are touching
						if (a * a + b * b <= c * c) {
							if (testBlob.radius > otherBlob.radius) {
								otherBlob.die();
								testBlob.radius += otherBlob.radius / 3;
								// test blob eats other blob
							}
							else if (testBlob.radius < otherBlob.radius) {
								testBlob.die();
								otherBlob.radius += testBlob.radius / 3;
								// other blob eats test blob
							}
						}
					}
				}
			}
			
			// Remove all dead blobs.
			for (int i = 0 ; i < blobs.size() ; i++) {
				if (blobs.get(i).dead) {
					blobs.remove(i);
					i = i - 1;
				}
			}
			
			if (Window.rollDice(10) == 1) {
				Blob b = new Blob();
				b.radius = Window.rollDice(myself.radius + 20);
				blobs.add(b);
			}

			Window.frame();
		}
	}

}