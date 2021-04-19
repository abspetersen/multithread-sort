import java.util.Arrays;

public class MultithreadedMergeSort {
	public static void main(String[] args) throws InterruptedException {
		int[] arr = {7,12,19,3,18,4,2,6,15,8};
				
		System.out.println("Before sort:");
		for(int elem : arr) {
			System.out.println(elem);
		}
		
		int middle = arr.length/2;
		
		int[] leftArr = Arrays.copyOfRange(arr, 0, middle);
		int[] rightArr = Arrays.copyOfRange(arr, middle, arr.length);
		
		sortingThread left = new sortingThread(leftArr);
		sortingThread right = new sortingThread(rightArr);
		left.start();
		right.start();
		left.join();
		right.join();
		
		System.out.println("\nLeft sorted array:");
		for(int elem : leftArr) {
			System.out.println(elem);
		}
		
		System.out.println("\nRight sorted array:");
		for(int elem : rightArr) {
			System.out.println(elem);
		}
		
		mergeThread merge = new mergeThread(left.getArrToSort(), right.getArrToSort());
		merge.start();
		
		
	}
}

class sortingThread extends Thread {
	private int[] arrToSort;
	
	public sortingThread(int[] a) {
		arrToSort = a;
	}

	public int[] getArrToSort() {
		return arrToSort;
	}
	
	public void mergeSort(int[] array) {
		if (array.length == 0 || array.length == 1) {
			return;
		}

		int middle = (array.length) / 2;
		int leftArr[] = Arrays.copyOfRange(array, 0, middle);
		int rightArr[] = Arrays.copyOfRange(array, middle, array.length);
		
		mergeSort(leftArr);
		mergeSort(rightArr);
		
		merge(array, leftArr, rightArr);
		
	}
	
	public static void merge(int[] resultArr, int arrayLeft[], int arrayRight[]) {
		// Left array: start -> middle
		// Right array: middle + 1 -> end
		
		int i = 0;
		int left = 0;
		int right = 0;

		while (left < arrayLeft.length && right < arrayRight.length) {
			if (arrayRight[right] <= arrayLeft[left]) {
				resultArr[i] = arrayRight[right];
				i++;
				right++;
			} else {
				resultArr[i] = arrayLeft[left];
				i++;
				left++;
			}
		}

		while (left < arrayLeft.length) {
			resultArr[i] = arrayLeft[left];
			i++;
			left++;
		}

		while (right < arrayRight.length) {
			resultArr[i] = arrayRight[right];
			i++;
			right++;
		}

	}
	
	public void run() {
		mergeSort(arrToSort);
	}
	
}


class mergeThread extends Thread {
	private int[] leftArray;
	private int[] rightArray;
	
	public mergeThread(int[] l, int[] r) {
		leftArray = l;
		rightArray = r;
	}

	public int[] getLeftArray() {
		return leftArray;
	}

	public int[] getRightArray() {
		return rightArray;
	}
	
	public int[] merge(int arrayLeft[], int arrayRight[]) {
		// Left array: start -> middle
		// Right array: middle + 1 -> end
		int resultArr[] = new int[arrayLeft.length + arrayRight.length];
		int i = 0;
		int left = 0;
		int right = 0;

		while (left < arrayLeft.length && right < arrayRight.length) {
			if (arrayRight[right] <= arrayLeft[left]) {
				resultArr[i] = arrayRight[right];
				i++;
				right++;
			} else {
				resultArr[i] = arrayLeft[left];
				i++;
				left++;
			}
		}

		while (left < arrayLeft.length) {
			resultArr[i] = arrayLeft[left];
			i++;
			left++;
		}

		while (right < arrayRight.length) {
			resultArr[i] = arrayRight[right];
			i++;
			right++;
		}

		return resultArr;

	}
	
	public void run() {
		int[] sortedArray = merge(leftArray, rightArray);
		System.out.println("\nAfter sort:");
		for(int elem : sortedArray) {
			System.out.println(elem);
		}
	}
	
	
}
