import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.ArrayList;
import java.util.LinkedList;

import static java.lang.Math.max;

/**
 * Your implementation of a BST.
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
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     * This constructor should initialize an empty BST.
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null");
        }
        for (T curr: data) {
            add(curr);
        }
    }

    /**
     * Adds the data to the tree.
     * This must be done recursively.
     * The data becomes a leaf in the tree.
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        } else {
            root = add(data, root);
        }
        size++;
    }

    /**
     * Helper method for add(T data)
     * @param data to be added
     * @param node is the root of a tree of subtree
     * @return curr node
     */
    private BSTNode<T> add(T data, BSTNode<T> node) {
        if (node == null) {
            return new BSTNode<T>(data);
        } else if (node.getData().compareTo(data) < 0) {
            node.setRight(add(data, node.getRight()));
        } else {
            node.setLeft(add(data, node.getLeft()));
        }
        return node;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     * This must be done recursively.
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * Hint: Should you use value equality or reference equality?
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        } else if (this.root == null) {
            throw new NoSuchElementException("Data is not in the tree.");
        }
        BSTNode<T> curr = new BSTNode<T>(null);
        root = remove(root, data, curr);
        return curr.getData();
    }

    /**
     * The helper method for the remove method.
     * @param curr the current node being compared
     * @param data the data of the node we are trying to find
     * @param temp ...
     * @return the node of the data
     * @throws java.util.NoSuchElementException if the data is not in the tree
     */
    private BSTNode<T> remove(BSTNode<T> curr, T data, BSTNode<T> temp) {
        if (curr == null) {
            throw new NoSuchElementException("Data is not in the tree.");
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(remove(curr.getRight(), data, temp));
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(remove(curr.getLeft(), data, temp));
        } else {
            size--;
            temp.setData(curr.getData());
            if (curr.getRight() == null && curr.getLeft() == null) {
                return null;
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            } else if (curr.getLeft() == null) {
                return curr.getRight();
            } else {
                BSTNode<T> success = new BSTNode<T>(null);
                curr.setRight(successor(curr.getRight(), success));
                curr.setData(success.getData());
            }
        }
        return curr;
    }

    /**
     * Finds teh successor of an inputted node.
     * @param curr the current node being compared.
     * @param success ...
     * @return the node of the successor node.
     */
    private BSTNode<T> successor(BSTNode<T> curr, BSTNode<T> success) {
        if (curr.getLeft() == null) {
            success.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(successor(curr.getLeft(), success));
        }
        return curr;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     * This must be done recursively.
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     * Hint: Should you use value equality or reference equality?
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        BSTNode<T> curr = this.root;
        if (get(curr, data) == null) {
            throw new NoSuchElementException("data is not in tree");
        }
        return get(curr, data);
    }

    /**
     * The helper method for the get method.
     * @param curr the current nodes being compared.
     * @param data the data of the node we are trying to find.
     * @return the data of the targeted node.
     */
    private T get(BSTNode<T> curr, T data) {
        if (curr.getData() == null) {
            return null;
        } else if (data.compareTo(curr.getData()) == 0) {
            return curr.getData();
        } else if (data.compareTo(curr.getData()) < 0) {
            curr = curr.getLeft();
            return get(curr, data);
        } else if (data.compareTo(curr.getData()) > 0) {
            curr = curr.getRight();
            return get(curr, data);
        }
        return null;
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     * This must be done recursively.
     * Hint: Should you use value equality or reference equality?
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
        BSTNode<T> curr = this.root;
        return get(curr, data) != null;
    }

    /**
     * Generate a pre-order traversal of the tree.
     * This must be done recursively.
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        BSTNode<T> curr = this.root;
        List<T> tList = new ArrayList<>();
        return preorder(curr, tList);
    }

    /**
     * The helper method for the preorder traversal method.
     * @param curr the current node being compared.
     * @param tList the list being added to.
     * @return the list being added to.
     */
    private List<T> preorder(BSTNode<T> curr, List<T> tList) {
        if (curr == null) {
            return null;
        }
        tList.add(curr.getData());
        preorder(curr.getLeft(), tList);
        preorder(curr.getRight(), tList);
        return tList;
    }

    /**
     * Generate an in-order traversal of the tree.
     * This must be done recursively.
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        BSTNode<T> curr = this.root;
        List<T> tList = new ArrayList<>();
        return inorder(curr, tList);
    }

    /**
     * The helper method for the inorder traversal method.
     * @param curr the current node being compared.
     * @param tList the list being added to.
     * @return the list being added to.
     */
    private List<T> inorder(BSTNode<T> curr, List<T> tList) {
        if (curr == null) {
            return null;
        }
        inorder(curr.getLeft(), tList);
        tList.add(curr.getData());
        inorder(curr.getRight(), tList);
        return tList;
    }

    /**
     * Generate a post-order traversal of the tree.
     * This must be done recursively.
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        BSTNode<T> curr = this.root;
        List<T> tList = new ArrayList<>();
        return postorder(curr, tList);
    }

    /**
     * The helper method for the post-order traversal method.
     * @param curr the current node being compared to.
     * @param tList the list being added to.
     * @return the list being added to.
     */
    private List<T> postorder(BSTNode<T> curr, List<T> tList) {
        if (curr == null) {
            return null;
        }
        postorder(curr.getLeft(), tList);
        postorder(curr.getRight(), tList);
        tList.add(curr.getData());
        return tList;
    }

    /**
     * Generate a level-order traversal of the tree.
     * This does not need to be done recursively.
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> eQueue = new LinkedList<>();
        List<T> tList = new ArrayList<>();
        eQueue.add(root);
        while (eQueue.peek() != null) {
            BSTNode<T> dQueue = eQueue.remove();
            tList.add(dQueue.getData());
            if (dQueue.getLeft() != null) {
                eQueue.add(dQueue.getLeft());
            }
            if (dQueue.getRight() != null) {
                eQueue.add(dQueue.getRight());
            }
        }
        return tList;
    }

    /**
     * Returns the height of the root of the tree.
     * This must be done recursively.
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        BSTNode<T> curr = this.root;
        if (curr == null) {
            return -1;
        }
        int leftSide = height(curr.getLeft());
        int rightSide = height(curr.getRight());
        return Math.max(leftSide, rightSide);
    }

    /**
     * The helper method for the height method.
     * @param curr the current node being compared.
     * @return an int representing the height of the BST.
     */
    private int height(BSTNode<T> curr) {
        if (curr == null) {
            return 0;
        }
        if (curr.getLeft() == null) {
            return (height(curr.getRight()) + 1);
        }
        return (height(curr.getLeft()) + 1);
    }

    /**
     * Clears the tree.
     * Clears all data and resets the size.
     * Must be O(1).
     */
    public void clear() {
        this.root = null;
        this.size = 0;
    }

    /**
     * Removes all elements strictly greater than the passed in data.
     * This must be done recursively.
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     * EXAMPLE: Given the BST below composed of Integers:
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     * pruneGreaterThan(27) should remove 37, 40, 50, 75. Below is the resulting BST
     *             25
     *            /
     *          12
     *         /  \
     *        10  15
     *           /
     *          13
     * Should have a running time of O(n) for a degenerate tree and O(log(n)) for a balanced tree.
     *
     * @throws java.lang.IllegalArgumentException if data is null
     * @param data the threshold data. Elements greater than data should be removed
     * @param tree the root of the tree to prune nodes from
     * @param <T> the generic typing of the data in the BST
     * @return the root of the tree with all elements greater than data removed
     */
    public static <T extends Comparable<? super T>> BSTNode<T> pruneGreaterThan(BSTNode<T> tree, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data is null.");
        } else if (tree == null) {
            return null;
        }

        BSTNode<T> child;
        if (data.compareTo(tree.getData()) < 0) {
            tree.setRight(null);
            tree.setData(null);
            if (tree.getLeft() != null) {
                child = tree.getLeft();
                return pruneGreaterThan(child, data);
            }
            return null;

        } else {
            if (tree.getRight() != null) {
                child = tree.getRight();
                return pruneGreaterThan(child, data);
            } else {
                return tree;
            }
        }
    }

    /*
     *         if (curr.getData() == null) {
     *             return null;
     *
     *         } else if (data.compareTo(curr.getData()) == 0) {
     *             return curr.getData();
     *
     *         } else if (data.compareTo(curr.getData()) < 0) {
     *             curr = curr.getLeft();
     *             return get(curr, data);
     *
     *         } else if (data.compareTo(curr.getData()) > 0) {
     *             curr = curr.getRight();
     *             return get(curr, data);
     *         }
     *
     */


    /**
     * Returns the root of the tree.
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}