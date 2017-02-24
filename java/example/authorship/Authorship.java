package example.authorship;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;


public class Authorship {

	public static void main(String[] args) {
		HashMap <String, Integer> twain = getBook("huck.txt");
		HashMap <String, Integer> melville = getBook("mobydick.txt");
		
		HashMap <String, Integer> me = new HashMap <String, Integer> ();
		
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("Twain's vocabulary: " + twain.size());
		System.out.println("Melville's vocabulary: " + melville.size());
		
		// Get the frequency of my own text
		while (keyboard.hasNext()) {
			String word = keyboard.next();
			word = word.toLowerCase();
			word = word.replace("[^a-z]", "");
			if (me.containsKey(word)) {
				me.put(word, me.get(word) + 1);
			}
			else {
				me.put(word, 1);
			}
			
			if (word.equals("done")) {
				break;
			}
		}
		
		int twainScore = 0;
		int melvilleScore = 0;
		
		for (String word : me.keySet()) {
			if (twain.containsKey(word) && melville.containsKey(word)) {
				if (twain.get(word) > melville.get(word)) {
					twainScore++;
				}
				if (melville.get(word) > twain.get(word)) {
					melvilleScore++;
				}
			}
		}
		
		System.out.println("Twain: " + twainScore);
		System.out.println("Melville: " + melvilleScore);
	}

	private static HashMap<String, Integer> getBook(String path) {
		HashMap <String, Integer> frequency = new HashMap <String, Integer> ();
		int count = 0;
		
		// Read author B into the hashmap
		try {
			Scanner scan = new Scanner(new File(path));
			while (scan.hasNext()) {
				String word = scan.next();
				count = count + 1;
				word = word.toLowerCase();
				word = word.replace("[^a-z0-9]", "");

				if (frequency.containsKey(word)) {
					frequency.put(word, frequency.get(word) + 1);
				}
				else {
					frequency.put(word, 1);
				}
			}

		} catch (FileNotFoundException e) {}

		return frequency;
	}

}