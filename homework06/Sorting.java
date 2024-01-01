import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author LAUREN SABO
 * @version 1.0
 * @userid lsabo8
 * @GTID 903669960
 *
 * Collaborators: n/a
 *
 * Resources: n/a
 */
public class Sorting {

    /**
     * Implement selection sort.
     * It should be:
     * in-place
     * unstable
     * not adaptive
     * Have the worst case running time of:
     * O(n^2)
     * And the best case running time of:
     * O(n^2)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("array || comparator is null");
        }
        for (int i = arr.length - 1; i > 0; i--) {
            int max = 0;
            for (int j = 1; j <= i; j++) {
                if (comparator.compare(arr[j], arr[max]) > 0) {
                    max = j;
                }
            }
            T temp = arr[i];
            arr[i] = arr[max];
            arr[max] = temp;
        }
        
    }

    /**
     * Implement insertion sort.
     * It should be:
     * in-place
     * stable
     * adaptive
     * Have the worst case running time of:
     * O(n^2)
     * And the best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || arr.length <= 1 || comparator == null) {
            throw new IllegalArgumentException("data || comparator is null");
        }
        for (int i = 1; i < arr.length; i++) {
            int j = i;
            while (j > 0 && comparator.compare(arr[j - 1], arr[j]) > 0)    {
                T temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
                j--;
            }
        }
    }

    /**
     * Implement bubble sort.
     * It should be:
     * in-place
     * stable
     * adaptive
     * Have the worst case running time of:
     * O(n^2)
     * And the best case running time of:
     * O(n)
     * NOTE: See pdf for last swapped optimization for bubble sort. You
     * MUST implement bubble sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void bubbleSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("array || comparator is null.");
        }

        int swap;
        for (int i = 0; i < arr.length - 1; i++) {
            swap = 0;
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (comparator.compare(arr[j], arr[j + 1]) > 0) {
                    swap = 1;
                    T temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;

                }
            }
            if (swap == 0) {
                break;
            }
        }
    }

    /**
     * Implement merge sort.
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * Have the worst case running time of:
     * O(n log n)
     * And the best case running time of:
     * O(n log n)
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     * Hint: If two data are equal when merging, think about which the sub array
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null)  {
            throw new IllegalArgumentException("data || comparator is null");
        }

        int length = arr.length;
        if (length <= 1) {
            return;
        }
        int midI = length / 2;
        T[] left = (T[]) new Object[midI];
        T[] right = (T[]) new Object[length - midI];
        for (int i = 0; i < midI; i++)  {
            left[i] = arr[i];
        }
        for (int i = midI; i < length; i++) {
            right[i - midI] = arr[i];
        }

        mergeSort(left, comparator);
        mergeSort(right, comparator);

        int leftI = 0;
        int rightI = 0;
        int currI = 0;
        while (leftI < midI && rightI < length - midI) {
            if (comparator.compare(left[leftI], right[rightI]) < 0) {
                arr[currI] = left[leftI++];
            } else  {
                arr[currI] = right[rightI++];
            }
            currI++;
        }
        while (leftI < midI)    {
            arr[currI++] = left[leftI++];
        }
        while (rightI < length - midI)    {
            arr[currI++] = right[rightI++];
        }
    }

    /**
     * Implement LSD (the least significant digit) radix sort.
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm, and you may not get full
     * credit if you do not implement the one we have taught you!
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * Have the worst case running time of:
     * O(kn)
     * And the best case running time of:
     * O(kn)
     * You are allowed to make an initial O(n) pass through of the array to
     * determine the number of iterations you need.
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     * Refer to the PDF for more information on LSD Radix Sort.
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null)  {
            throw new IllegalArgumentException("data is null");
        }
        if (arr.length <= 1)    {
            return;
        }
        LinkedList<Integer>[] bucks = new LinkedList[19];
        int high = arr[0];
        int low = arr[0];
        int exp = 1;
        for (int i = 0; i < arr.length; i++)    {
            if (arr[i] > high) {
                high = arr[i];
            }
            if (arr[i] < low)   {
                low = arr[i];
            }
        }
        int iterations = 0;
        while (high != 0 || low != 0)   {
            high = high / 10;
            low = low / 10;
            iterations++;
        }
        int leng = arr.length;

        for (int i = 1; i <= iterations; i++)    {
            for (int j = 0; j <= leng - 1; j++)    {
                int buck = (arr[j] / exp) % 10;
                buck += 9;
                if (bucks[buck] == null)    {
                    bucks[buck] = new LinkedList<>();
                }
                bucks[buck].add(arr[j]);
            }
            int k = 0;
            for (int buck = 0; buck < 19; buck++)    {
                if (bucks[buck] == null)    {
                    bucks[buck] = new LinkedList<>();
                }
                while (!bucks[buck].isEmpty())  {
                    arr[k] = bucks[buck].removeFirst();
                    k++;
                }
            }
            exp *= 10;
        }
    }

    /**
     * Implement heap sort.
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     * Have the worst case running time of:
     * O(n log n)
     * And the best case running time of:
     * O(n log n)
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from the smallest
     * element to the largest element.
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        PriorityQueue<Integer> prior = new PriorityQueue<>(data);
        int[] thing = new int[data.size()];
        for (int i = 0; i < thing.length; i++) {
            thing[i] = prior.peek();
            prior.remove();
        }
        return thing;
    }
}