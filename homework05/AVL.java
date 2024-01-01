import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL Tree.
 *
 * @author LAUREN SABO
 * @userid lsabo8
 * @GTID 903669960
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @throws IllegalArgumentException if data or any element in data is null
     * @param data the data to add to the tree
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        size = 0;
        for (T temp: data) {
            add(temp);
        }
    }

    /**
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        root = add(data, root);
    }

    /**
     * The recursive helper method for the add method.
     * @param data the data to add
     * @param parent the parent/ root of the tree
     * @return the balanced node
     */
    private AVLNode<T> add(T data, AVLNode<T> parent) {
        if (parent == null) {
            size++;
            return new AVLNode<>(data);
        }
        if (parent.getData().compareTo(data) == 0) {
            return parent;
        } else if (parent.getData().compareTo(data) > 0) {
            parent.setLeft(add(data, parent.getLeft()));
        } else if (parent.getData().compareTo(data) < 0) {
            parent.setRight(add(data, parent.getRight()));
        }
        return balance(parent);
    }

    /**
     * Balances the node by rotation.
     * @param focus the node in focus
     * @return the node once balanced
     */
    private AVLNode<T> balance(AVLNode<T> focus) {
        hbfHelper(focus);
        if (focus.getBalanceFactor() >= -1 && focus.getBalanceFactor() <= 1) {
            return focus;
        } else if (focus.getBalanceFactor() == Math.abs(2)) {
            if (focus.getLeft().getBalanceFactor() != 1 && focus.getLeft().getBalanceFactor() != 0) {
                focus.setLeft(rotLeft(focus.getLeft()));
            }
            return rotRight(focus);
        } else {
            if (focus.getRight().getBalanceFactor() != -1 && focus.getRight().getBalanceFactor() != 0) {
                focus.setRight(rotRight(focus.getRight()));
            }
            return rotLeft(focus);
        }
    }

    /**
     * Helper method to find the node in focus's height and balance factor.
     * @param focus the node in focus
     */
    private void hbfHelper(AVLNode<T> focus) {
        int left;
        int right;
        if (focus.getLeft() == null) {
            left = -1;
        } else {
            left = focus.getLeft().getHeight();
        }

        if (focus.getRight() == null) {
            right = -1;
        } else {
            right = focus.getRight().getHeight();
        }

        focus.setHeight(Math.max(left, right) + 1);
        focus.setBalanceFactor(left - right);
    }

    /**
     * Right rotation method.
     * @param focus the node in focus
     * @return node in focus -> rotated right
     */
    private AVLNode<T> rotRight(AVLNode<T> focus) {
        AVLNode<T> child = focus.getLeft();
        focus.setLeft(child.getRight());
        child.setRight(focus);
        hbfHelper(focus);
        hbfHelper(child);
        return child;
    }

    /**
     * Left rotation method.
     * @param focus the node in focus
     * @return node in focus -> rotated left
     */
    private AVLNode<T> rotLeft(AVLNode<T> focus) {
        AVLNode<T> child = focus.getRight();
        focus.setRight(child.getLeft());
        child.setLeft(focus);
        hbfHelper(focus);
        hbfHelper(child);
        return child;
    }


    /**
     * Removes the data from the tree. There are 3 cases to consider:
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the predecessor to replace the data,
     * not the successor. As a reminder, rotations can occur after removing
     * the predecessor node.
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        } else if (size == 0) {
            throw new NoSuchElementException("Data is not found.");
        } else if (!contains(data)) {
            throw new NoSuchElementException("Data is not found.");
        }
        AVLNode<T> outNode = new AVLNode<>(null);
        root = remove(root, outNode, data);
        size--;
        return outNode.getData();
    }

    /**
     * The helper method for the recursive remove method.
     * @param node the root node for inspection
     * @param outNode the node to with the removed data
     * @param data the data we are looking for
     * @return the outNode
     */
    private AVLNode<T> remove(AVLNode<T> node, AVLNode<T> outNode, T data) {
        if (node == null) {
            throw new NoSuchElementException("Data is not found.");
        }
        if (node.getData().compareTo(data) < 0) {
            node.setRight(remove(node.getRight(), outNode, data));
        } else if (node.getData().compareTo(data) > 0) {
            node.setLeft(remove(node.getLeft(), outNode, data));
        } else {
            outNode.setData(node.getData());
            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            } else {
                AVLNode<T> predecessor = new AVLNode<>(null);
                node.setLeft(predecessor(node.getLeft(), predecessor));
                node.setData(predecessor.getData());
            }
        }
        hbfHelper(node);
        return balance(node);

    }

    /**
     * Method to return the predecessor of a targetted node.
     * @param node the node being inspected
     * @param predecessorNode predecessor node of the root node
     * @return the predecessor node of the node passed in
     */
    private AVLNode<T> predecessor(AVLNode<T> node, AVLNode<T> predecessorNode) {
        if (node.getRight() == null) {
            predecessorNode.setData(node.getData());
            return node.getLeft();
        } else {
            node.setRight(predecessor(node.getRight(), predecessorNode));
            hbfHelper(node);
            return balance(node);
        }
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        return get(data, root);
    }

    /**
     * Recursive helper method for the get method.
     * @param data the data to search for in the tree
     * @param focus the node in focus
     * @return the data of the node containing the data in question
     */
    private T get(T data, AVLNode<T> focus) {
        if (focus == null) {
            throw new NoSuchElementException("Data is not found");
        }
        if (data.compareTo(focus.getData()) > 0) {
            return get(data, focus.getRight());
        } else if (data.compareTo(focus.getData()) < 0) {
            return get(data, focus.getLeft());
        } else {
            return focus.getData();
        }
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        return contains(root, data);
    }

    /**
     * The recursive helper method for the contains method.
     * @param focus the node in focus
     * @param data the data to look for
     * @return whether the AVL contains the data
     */
    private boolean contains(AVLNode<T> focus, T data) {
        if (focus == null) {
            return false;
        } else if (focus.getData().equals(data)) {
            return true;
        } else {
            if (data.compareTo(focus.getData()) > 0) {
                return contains(focus.getRight(), data);
            } else {
                return contains(focus.getLeft(), data);
            }
        }
    }

    /**
     * Finds and retrieves the median data of the passed in AVL.
     * This method will not need to traverse the entire tree to
     * function properly, so you should only traverse enough branches of the tree
     * necessary to find the median data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     * Ex:
     * Given the following AVL composed of Integers
     *              50
     *            /    \
     *         25      75
     *        /  \     / \
     *      13   37  70  80
     *    /  \    \      \
     *   12  15    40    85
     * findMedian() should return 40
     *
     * @throws NoSuchElementException if the tree is empty or contains an even number of data
     * @return the median data of the AVL
     */
    public T findMedian() {
        if (size == 0) {
            throw new NoSuchElementException("tree is empty.");
        } else if (size % 2 == 0) {
            throw new NoSuchElementException("there is an even number of data");
        }
        AVLNode<Integer> count = new AVLNode<>(0);
        AVLNode<T> median = new AVLNode<>(null);
        findMedian(count, root, median);
        return median.getData();
    }

    /**
     * The recursive helper method for the findMedian method.
     * @param dummy the count node
     * @param curr the current node in focus
     * @param median the median node to be returned
     * @return the median node
     */
    private AVLNode<T> findMedian(AVLNode<Integer> dummy, AVLNode<T> curr, AVLNode<T> median) {
        if (curr == null || median.getData() != null) {
            return null;
        }
        else {
            findMedian(dummy, curr.getLeft(), median);
            dummy.setData(dummy.getData() + 1);
            if (dummy.getData() == ((size/2) + 1)) {
                median.setData(curr.getData());
                return null;
            }
            return findMedian(dummy, curr.getRight(), median);
        }
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the height of the root of the tree.
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Returns the size of the AVL tree.
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}