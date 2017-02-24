package example.sorting;

import apcs.Window;

public class Sorting {

	static int width = 500;
	static int height = 500;
	
	public static void main(String[] args) {
		Window.size(width, height);
		Window.frame();
		int[] array = generate(10000);
		quicksort(array);
//		
//		for (int i = 0 ; i < array.length ; i++) {
//			for (int k = 0 ; k < array.length - 1 ; k++) {
//				if (array[k] > array[k + 1]) {
//					int temp = array[k];
//					array[k] = array[k + 1];
//					array[k + 1] = temp;
//				}
//			}
//			draw(array);
//		}
	}
	
	private static void quicksort(int[] array) {
		quicksort(array, 0, array.length - 1);
	}
	
	/**
	 * Quicksort.
	 */
	private static void quicksort(int[] array, int start, int end) {
		// Don't animate small intervals
		//if ((end - start) * 500 >= array.length)
		if (end - start > array.length / 100)
			draw(array);

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
				}
			}
			// Swap the pivot into the center position
			array[end] = array[nextSwap];
			array[nextSwap] = pivot;

			// Sort the partitions on the left and right
			quicksort(array, start, nextSwap - 1);
			quicksort(array, nextSwap + 1, end);
		}
	}

	
	private static int[] generate(int length) {
		int[] array = new int[length];
		for (int i = 0 ; i < array.length ; i++) {
			array[i] = Window.rollDice(255);
		}
		return array;
	}

	private static void draw(int[] array) {
		int size = (int) Math.ceil(Math.sqrt(array.length));
		int scale = width / size;
		
		for (int i = 0 ; i < array.length ; i++) {
			int x = i % size;
			int y = i / size;
			
			Window.out.color(array[i], array[i], array[i]);
			Window.out.square(x * scale + scale / 2, y * scale + scale / 2, scale);
		}
		Window.frame();
	}

}
