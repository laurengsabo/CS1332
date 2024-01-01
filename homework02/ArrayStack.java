import java.util.NoSuchElementException;

/**
 * Your implementation of an ArrayStack.
 *
 * @author LAUREN SABO
 * @version 1.0
 * @userid lsabo8
 * @GTID 903669960
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class ArrayStack<T> {

    /*
     * The initial capacity of the ArrayQueue.
     *
     * DO NOT MODIFY THIS VARIABLE.
     */
    public static final int INITIAL_CAPACITY = 9;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayStack.
     */
    public ArrayStack() {
        this.backingArray = (T[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    /**
     * Adds the data to the top of the stack.
     * If sufficient space is not available in the backing array, resize it to
     * double the current length.
     * Must be amortized O(1).
     *
     * @param data the data to add to the top of the stack
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        }

        if (size == backingArray.length) {
            T[] newBack = (T[]) new Object[2 * this.size];
            for (int i = 0; i < size - 1; i++) {
                newBack[i] = backingArray[i];
            }
            this.backingArray = newBack;
        }

        int curr = size % backingArray.length;
        backingArray[curr] = data;
        size++;
    }

    /**
     * Removes and returns the data from the top of the stack.
     * Do not shrink the backing array.
     * Replace any spots that you pop from with null.
     * Must be O(1).
     *
     * @return the data formerly located at the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    public T pop() {
        if (size == 0) {
            throw new NoSuchElementException("Stack is empty.");
        }
        T curr = this.backingArray[size - 1];
        this.backingArray[size - 1] = null;
        size--;
        return curr;
    }

    /**
     * Returns the data from the top of the stack without removing it.
     * Must be O(1).
     *
     * @return the data from the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    public T peek() {
        if (size == 0) {
            throw new NoSuchElementException("Stack is empty.");
        }
        return this.backingArray[size - 1];
    }

    /**
     * Returns the backing array of the stack.
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the stack
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the stack.
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the stack
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
