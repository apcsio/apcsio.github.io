package example.sorting;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;

/**
 * A simple JFrame for visualizing sorting algorithms.
 * @author keshav (keshav@techlabeducation.com)
 */
public class SortingJFrame extends JFrame {

	// Create an instance of this JFrame on run. No need to modify this part of the
	// program unless you are adding new sorting algorithms that can be selected.
	public static void main(String[] args) {

		int size = 10000;
		int width = 800;
		int speed = 1;
		int buffer = 1;

		float hue = (float) Math.random();
		float saturation = (float) Math.random();

		String algorithm = "quick";
		for (int i = 0 ; i < args.length ; i++) {
			// Check for command line flags
			if (i + 1 < args.length) {
				String argtype = args[i];
				String arg = args[i + 1];

				// Select the algorithm
				if (argtype.equals("-algorithm"))
					algorithm = arg.toLowerCase()
						.replaceAll("[^a-z\\s]", "")
						.replaceAll("sort$", "");

				// Select display parameters
				else if (argtype.equals("-speed") && arg.matches("\\d+"))
					speed = Integer.parseInt(arg);
				else if (argtype.equals("-width") && arg.matches("\\d+"))
					width = Integer.parseInt(arg);
				else if (argtype.equals("-buffer") && arg.matches("\\d+"))
					buffer = Integer.parseInt(arg);
				else if (argtype.equals("-hue") && arg.matches("\\d*(\\.\\d*|\\d)"))
					hue = Float.parseFloat(arg);
				else if (argtype.equals("-saturation") && arg.matches("\\d*(\\.\\d*|\\d)"))
					saturation = Float.parseFloat(arg);

				// Select the array size
				else if (argtype.equals("-size") && arg.matches("\\d+"))
					size = Integer.parseInt(arg);
			}
		}

		SortingJFrame visualization = new SortingJFrame(width, buffer, hue, saturation);

		int[] array = visualization.randomize(size);

		// Sort the array with the given algorithm
		if (algorithm.equals("bubble"))
			visualization.bubbleSort(array, speed);
		else if (algorithm.equals("selection"))
			visualization.selectionSort(array, speed);
		else if (algorithm.equals("quick"))
			visualization.quicksort(array, speed);
		else if (algorithm.equals("insertion"))
			visualization.insertionSort(array, speed);
		else if (algorithm.equals("merge"))
			visualization.mergeSort(array, speed);
		else
			visualization.visualize(array);
	}

	// Reference to the current array being drawn.
	private int[] currentArray = { 0 };

	// Drawing options
	private int width = 800;
	private float hue = 0.5f;
	private float saturation = 0.5f;
	private int buffer = 1;
	private int frame = 0;

	/**
	 * Constructs the JFrame for the sorting animation.
	 * @param width - the width of the window
	 * @param buffer - a number n such that every nth frame is drawn (default 1)
	 */
	public SortingJFrame(int width, int buffer, float hue, float saturation) {
		// Create the window.
		super("visual sort");
		setSize(width, 22 + width);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// Store display parameters
		this.width = width;
		this.buffer = buffer;
		this.hue = hue;
		this.saturation = saturation;

		setVisible(true);
		setResizable(false);
	}

	/**
	 * Creates a random array of values, where each value is between 0 and 255.
	 * @param size - the length of the array
	 */
	private int[] randomize(int size) {
		// Create an array of that size
		int[] a = new int[size];
		// Go to each position in the array
		for (int i = 0 ; i < a.length ; i++) {
			a[i] = (int) (Math.random() * 256);
		}
		return a;
	}

	/**
	 * Selection sort.
	 * @param array
	 */
	public void selectionSort(int[] array, boolean visualize, int speed) {

		// Find the correct value for each index
		for (int currentIndex = 0 ; currentIndex < array.length ; currentIndex++) {
			int minIndex = currentIndex;

			// Test all values in the unsorted segment of the list, and put the smallest value
			// into the current value's position.
			for (int testIndex = currentIndex + 1 ; testIndex < array.length ; testIndex++) {
				if (array[testIndex] < array[minIndex]) {
					minIndex = testIndex;
				}
			}

			// Swap the current value with the smallest value in the unsorted list
			int save = array[currentIndex];
			array[currentIndex] = array[minIndex];
			array[minIndex] = save;

			// Visualize swaps
			if (visualize)
				visualize(array, speed);
		}
	}

	public void insertionSort(int[] array, boolean visualize, int speed) {
		// The index that the value should be placed in
		int correctIndex = 0;

		// The first element by itself is sorted, so check every element beyond the
		// first one and move it into the correct place.
		for (int checkIndex = 1 ; checkIndex < array.length ; checkIndex++) {

			// Start by assuming the value is in the right place
			correctIndex = checkIndex;

			// Keep pushing the value to the left until it is actually in the right place
			while (correctIndex > 0 && array[correctIndex - 1] > array[correctIndex] ) {
				int save = array[correctIndex];
				array[correctIndex] = array[correctIndex - 1];
				array[correctIndex - 1] = save;

				// The assumed index is now one to the left
				correctIndex = correctIndex - 1;

				// Visualize swaps
				if (visualize)
					visualize(array, speed);
			}
		}
	}

	/**
	 * Quicksort.
	 */
	private void quicksort(int[] array, int start, int end, boolean visualize, int speed) {
		// Don't animate small intervals
		if ((end - start) * 100 < array.length)
			visualize = false;

		if (start < end) {
			// Pick a center value to divide the list in half
			int pivot = array[end];
			// Go through the list and partition into two segments
			// One segment has all values less than pivot
			// The other segment has all values greater or equal
			int nextSwap = start;
			for (int checkIndex = start ; checkIndex < end ; checkIndex++) {
				// If it should be on the smaller partition
				if (array[checkIndex] <= pivot) {
					// Swap into the swap position
					int save = array[nextSwap];
					array[nextSwap] = array[checkIndex];
					array[checkIndex] = save;

					// Next swap goes to the position on the right
					nextSwap = nextSwap + 1;

					// Visualize swaps
					if (visualize)
						visualize(array, speed);
				}
			}
			// Swap the pivot into the center position
			array[end] = array[nextSwap];
			array[nextSwap] = pivot;

			// Sort the partitions on the left and right
			quicksort(array, start, nextSwap - 1,
				visualize, speed);
			quicksort(array, nextSwap + 1, end,
				visualize, speed);
		}
	}

	/**
	 * Bubble sort with display parameters.
	 */
	private void bubbleSort(int[] array, boolean visualize, int speed) {

		for (int repeat = 0 ; repeat < array.length ; repeat++) {
			// Go to every index in the list except the last one
			for (int checkIndex = 0 ; checkIndex < array.length - 1 ; checkIndex++) {
				if (array[checkIndex] > array[checkIndex + 1]) {
					// Swap if there is an unordered pair
					int save = array[checkIndex];
					array[checkIndex] = array[checkIndex + 1];
					array[checkIndex + 1] = save;

					// Visualize swaps
					if (visualize)
						visualize(array, speed);
				}
			}
		}
	}

	/**
	 * Contributed by Zachary Mayhew (@zacklukem)
	 */
	private int[] tempMergArr;
	
	private void mergeSort(int[] array, boolean visualize, int speed) {
		doMergeSort(array, 0, array.length-1, visualize, speed);
	}

	private void doMergeSort(int[] array, int lowerIndex, int higherIndex, boolean visualize, int speed) {

		if (lowerIndex < higherIndex) {
			int middle = lowerIndex + (higherIndex - lowerIndex) / 2;
			// Below step sorts the left side of the array
			doMergeSort(array, lowerIndex, middle, visualize, speed);
			// Below step sorts the right side of the array
			doMergeSort(array, middle + 1, higherIndex, visualize, speed);
			// Now merge both sides
			mergeParts(array, lowerIndex, middle, higherIndex);

			if (visualize)
				visualize(array, speed);
		}
	}

	private void mergeParts(int[] array, int lowerIndex, int middle, int higherIndex) {

		for (int i = lowerIndex; i <= higherIndex; i++) {
			tempMergArr[i] = array[i];
		}
		int i = lowerIndex;
		int j = middle + 1;
		int k = lowerIndex;
		while (i <= middle && j <= higherIndex) {
			if (tempMergArr[i] <= tempMergArr[j]) {
				array[k] = tempMergArr[i];
				i++;
			} else {
				array[k] = tempMergArr[j];
				j++;
			}
			k++;
		}
		while (i <= middle) {
			array[k] = tempMergArr[i];
			k++;
			i++;
		}

	}

	/**
	 * 	Helper methods for calling sorting algorithms with or without animations.
	 */
 	public void bubbleSort(int[] array) {
 		bubbleSort(array, false, 0);
 		visualize(array);
 	}

 	// With speed
 	public void bubbleSort(int[] array, int speed) {
 		bubbleSort(array, true, speed);
 	}

	public void selectionSort(int[] array) {
		selectionSort(array, false, 0);
		visualize(array);
	}

	public void selectionSort(int[] array, int speed) {
		selectionSort(array, true, speed);
	}

	public void insertionSort(int[] array) {
		insertionSort(array, false, 0);
		visualize(array);
	}

	public void mergeSort(int[] array, int speed) {
		tempMergArr = new int[array.length];
		mergeSort(array, true, speed);
	}

	public void insertionSort(int[] array, int speed) {
		insertionSort(array, true, speed);
	}

	public void quicksort(int[] array) {
		quicksort(array, 0, array.length - 1, false, 0);
	}

	public void quicksort(int[] array, int speed) {
		quicksort(array, 0, array.length - 1, true, speed);
	}

	/**
	 * Override the visualize method to draw the array with no delay.
	 * @param a
	 */
	public void visualize(int[] a) {
		visualize(a, 0);
	}

	/**
	 * Saves a reference to the array, then draws the visualization of the array
	 * and waits for the optional amount of time.
	 * @param a
	 * @param delay
	 */
	public void visualize(int[] a, int delay) {
		frame++;
		if (frame % buffer == 0) {
			currentArray = a;
			repaint();

			// Optional delay
			if (delay > 0) {
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {}
			}
		}
	}

	/**
	 * Draws each value in the array onto a square in the grid.
	 */
	public void paint(Graphics g) {
		int size = (int) Math.ceil(Math.sqrt(currentArray.length));
		int scale = width / size;

		// Go to each index in the array, and fill a square in the
		// animation with the brightness representing the magnitude of the value.
		for (int i = 0 ; i < currentArray.length ; i++ ) {
			g.setColor(Color.getHSBColor(hue, saturation, currentArray[i] / 255f));
			g.fillRect((i % size) * scale, 22 + i / size * scale, scale, scale);
		}
	}
}
