import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular SinglyLinkedList with a tail pointer.
 *
 * @author Lauren Sabo
 * @version 1.0
 * @userid lsabo8
 * @GTID 903669960
 */
public class SinglyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private SinglyLinkedListNode<T> head;
    private SinglyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index.
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index to add the new element
     * @param data  the data to add
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index does not exist.");
        } else if (data == null) {
            throw new IllegalArgumentException("Argument data is null.");
        }

        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            SinglyLinkedListNode<T> newNode = new SinglyLinkedListNode<>(data);
            SinglyLinkedListNode<T> curr = head;
            int inc = 0;
            while (inc < (index - 1)) {
                curr = curr.getNext();
                inc++;
            }
            newNode.setNext(curr.getNext());
            curr.setNext(newNode);
            size++;
        }

    }

    /**
     * Adds the element to the front of the list.
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Argument data is null.");
        }
        SinglyLinkedListNode<T> newNode = new SinglyLinkedListNode<>(data);
        newNode.setNext(head);
        head = newNode;
        if (size == 0) {
            tail = newNode;
        }
        size++;

    }

    /**
     * Adds the element to the back of the list.
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Argument data is null.");
        }

        SinglyLinkedListNode<T> newNode = new SinglyLinkedListNode<>(data);
        if (size == 0) {
            head = newNode;
        } else {
            tail.setNext(newNode);
        }
        tail = newNode;
        size++;

    }

    /**
     * Removes and returns the element at the specified index.
     * Must be O(1) for indices 0 and O(n) for all other
     * cases.
     *
     * @param index the index of the element to remove
     * @return the data that was removed
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if ((index >= size) || (index < 0)) {
            throw new IndexOutOfBoundsException("Index does not exist.");
        }

        SinglyLinkedListNode<T> curr = head;
        if (index == 0) {
            return removeFromFront();
        } else if (index == (size - 1)) {
            return removeFromBack();
        }

        int inc = 0;
        while (inc < index - 1) {
            curr = curr.getNext();
            inc++;
        }
        SinglyLinkedListNode<T> before = curr;
        curr = curr.getNext();
        before.setNext(curr.getNext());
        size--;
        return curr.getData();

    }

    /**
     * Removes and returns the first data of the list.
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new NoSuchElementException("Data structure is empty.");
        }

        SinglyLinkedListNode<T> curr = head;
        if (size == 1) {
            head = null;
            tail = null;
            return curr.getData();
        }
        head = head.getNext();
        size--;
        return curr.getData();
    }

    /**
     * Removes and returns the last data of the list.
     * Must be O(n).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new NoSuchElementException("Data structure is empty.");
        }

        SinglyLinkedListNode<T> curr = head;
        SinglyLinkedListNode<T> last = tail;
        if (size == 1) {
            clear();
            return curr.getData();
        } else if (size == 2) {
            tail = head;
            head.setNext(null);
            size--;
            return last.getData();
        }

        int inc = 0;
        int stop = size - 2;
        while (inc < stop) {
            curr = curr.getNext();
            inc++;
        }
        tail = curr;
        curr.setNext(null);
        size--;
        return last.getData();
    }

    /**
     * Returns the element at the specified index.
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index does not exist.");
        }

        SinglyLinkedListNode<T> curr = head;
        int inc = 0;
        while (inc < index) {
            curr = curr.getNext();
            inc++;
        }
        return curr.getData();
    }

    /**
     * Returns whether or not the list is empty.
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list.
     * Clears all data and resets the size.
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     * Must be O(n).
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null from data structure.");
        } else if (size == 0) {
            throw new NoSuchElementException("Data structure is empty.");
        }

        SinglyLinkedListNode<T> curr = head;
        if (size == 1 && (curr.getData()).equals(data)) {
            clear();
            return curr.getData();
        }

        int lastOcc = -1;
        int inc = 0;
        while (inc < size - 1) {
            if (curr.getData() == data) {
                lastOcc = inc;
            }
            inc++;
            curr = curr.getNext();
        }

        if (lastOcc == -1) {
            throw new NoSuchElementException("Data is not present in list.");
        }
        return removeAtIndex(lastOcc);
    }

    /**
     * Returns an array representation of the linked list.
     * Must be O(n) for all cases.
     *
     * @return the array of length size holding all the data (not the
     * nodes) in the list in the same order
     */
    public T[] toArray() {
        T[] arr = (T[]) new Object[size];
        if (size == 0) {
            return arr;
        }
        SinglyLinkedListNode<T> curr = head;
        for (int i = 0; i < size; i++) {
            arr[i] = curr.getData();
            curr = curr.getNext();
        }

        return arr;
    }

    /**
     * Returns the head node of the list.
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public SinglyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public SinglyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}