package com.example.carsaleapp.Backend.Tree;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import com.example.carsaleapp.Backend.Car;
import com.example.carsaleapp.Backend.Colour;

/**
 * Skeleton code for Red Black Tree
 *
 * @param <T> data type
 * @author Yiming Cao
 */
public class PriceTree<T extends Comparable<T>> {

    IntNode<Integer> root; // The root node of the tree

    /**
     * Initialize empty RBTree
     */
    public PriceTree() {
        root = null;
    }

    private void insertRecurse(IntNode<Integer> node, IntNode<Integer> x) {
        int cmp = node.key-x.key;
        if(cmp==0){
            node.cars.add(x.cars.get(0));
        }
        if (cmp > 0) {
            if (node.left == null||root.left.key==0) {
                node.left = x;
                x.parent = node;
            } else {
                insertRecurse(node.left, x);
            }
        } else if (cmp < 0) {
            if (node.right == null||root.right.key==0) {
                node.right = x;
                x.parent = node;
            } else {
                insertRecurse(node.right, x);
            }
        }
        // Do nothing if the tree already has a node with the same key.
    }
    public void insert(int key, Car car) {
        IntNode<Integer> node = new IntNode<Integer>(key,car);
        insert(node);
    }

    /**
     * Insert node into RBTree.
     *
     * @param x Node<T> The new node being inserted into the tree.
     */
    private void insert(IntNode<Integer> x) {
        // Insert node into tree
        if (root == null) {
            root = x;
        } else {
            insertRecurse(root, x);
        }
        if (x.parent != null && x.parent.parent != null){
            while (x.key != root.key && x.parent.colour == Colour.RED) {

                boolean left  = x.parent == x.parent.parent.left; // Is parent a left node
                IntNode<Integer> uncle = null;
                if (x.parent != null && x.parent.parent != null) {
                    uncle = left ? x.parent.parent.right : x.parent.parent.left;
                }

                if (uncle != null && uncle.colour == Colour.RED) {
                    // Case 1: Recolour
                    x.parent.colour = Colour.BLACK;
                    uncle.colour = Colour.BLACK;
                    x.parent.parent.colour = Colour.RED;
                    x = x.parent.parent;
                } else {
                    if (x.key == (left ? x.parent.right.key : x.parent.left.key)) {
                        // Case 2: Left Rotation, uncle is right node, x is on the right / Right Rotation, uncle is left node, x is on the left
                        x = x.parent;
                        if (left) {
                            if (x.key == root.key)
                                root = x.right;
                            rotateLeft(x);
                        } else {
                            rotateRight(x);
                        }
                    }
                    x.parent.colour = Colour.BLACK;
                    x.parent.parent.colour = Colour.RED;
                    // Case 3 : Right Rotation, uncle is right node, x is on the left / Left Rotation, uncle is left node, x is on the right
                    if (left) {
                        rotateRight(x.parent.parent);
                    } else {
                        rotateLeft(x.parent.parent);
                    }
                }
            }
            Log.e("PriceTree","id："+x.cars.get(0).getId()+"   key:"+x.key+" Parent key:"+x.parent.key);
            if(x==x.parent.left){
                Log.e("PriceTree","Left child");
            }
            else Log.e("PriceTree","Right child");

        }


        // Ensure property 2 (root and leaves are black) holds
        root.colour = Colour.BLACK;

    }

    /**
     * Rotates the provided node to the left, preserving the binary tree structure.
     * This operation updates the parent and child relationships of the nodes involved
     * to maintain the binary tree properties.
     *
     * @param x The node to be rotated to the left.
     * @author Yiming Cao
     */
    public void rotateLeft(IntNode<Integer> x) {
        if (x.parent != null) {
            if (x.parent.left.key == x.key) {
                x.parent.left = x.right;
            } else {
                x.parent.right = x.right;
            }
        }
        x.right.parent = x.parent;
        x.parent = x.right;
        x.right = x.parent.left;
        x.right.parent = x;
        x.parent.left = x;
    }

    /**
     * Rotates the provided node to the right, preserving the binary tree structure.
     * This operation updates the parent and child relationships of the nodes involved
     * to maintain the binary tree properties.
     *
     * @param x The node to be rotated to the right.
     * @author Yiming Cao
     */
    public void rotateRight(IntNode<Integer> x) {
        if (x.parent != null) {
            if (x.parent.left == x) {
                x.parent.left = x.left;
            } else {
                x.parent.right = x.left;
            }
        }
        x.left.parent = x.parent;
        x.parent = x.left;
        x.left = x.parent.right;
        x.left.parent = x;
        x.parent.right = x;
    }


    /**
     * Return the result of a pre-order traversal of the tree
     *
     * @param tree Tree we want to pre-order traverse
     * @return pre-order traversed tree
     */
    private String preOrder(IntNode<Integer> tree) {
        if (tree != null && tree.key != 0) {
            String leftStr = preOrder(tree.left);
            String rightStr = preOrder(tree.right);
            return tree.key + (leftStr.isEmpty() ? leftStr : " " + leftStr)
                    + (rightStr.isEmpty() ? rightStr : " " + rightStr);
        }
        return "";
    }

    public String preOrder() {
        return preOrder(root);
    }


    /**
     * Returns a node if the key of the node is {@code key}.
     *
     * @param key T The key we are looking for
     * @return Node
     * @author Yiming Cao
     */
    public List<Car> search(int key) {
        IntNode node = root;
        while (node != null) {
            int value = node.key;
            if (value < node.key) {
                node = node.left;
            } else if (value > node.key) {
                node = node.right;
            } else {
                return node.cars;
            }
        }
        return null;
    }

    public List<Car> getAll(int min, int max) {
        List<Car> result = new ArrayList<>();
        retrieveInRange(root, min, max, result);
        return result;
    }

    /**
     * Retrieves all elements within the specified range [min, max] from the binary tree rooted at the given node.
     * Elements that fall within the specified range are added to the result list.
     *
     * @param node   The root node of the binary tree to search.
     * @param min    The minimum value of the range (inclusive).
     * @param max    The maximum value of the range (inclusive).
     * @param result A list to store the elements found within the range.
     * @author Yiming Cao
     */
    private void retrieveInRange(IntNode<Integer> node, int min, int max, List<Car> result) {
        if(node==null){
            return;
        }
        if (min-node.key <= 0 && max-node.key >= 0) {
            retrieveInRange(node.left, min, max, result);
            result.addAll(node.cars);
            retrieveInRange(node.right, min, max, result);
        }
        else if (max-node.key<0) {
            retrieveInRange(node.left, min, max, result);
        }
        // If the current node's key is less than 'b', then there might be some nodes in the right subtree
        // that fall into the range [a, b]
        else if (min-node.key > 0) {
            retrieveInRange(node.right, min, max, result);
        }
    }

    public int getNumberOfNodes() {
        return countNodes(root);
    }

    /**
     * Recursively counts the number of nodes in a binary tree starting from the given node.
     *
     * @param current The node from which to start counting nodes.
     * @return The total number of nodes in the binary tree rooted at the given node.
     * @author Yiming Cao
     */
    private int countNodes(IntNode<Integer> current) {
        if (current == null || current.key == 0) {
            return 0;
        }
        Log.e("PriceTree","Current car size "+current.cars.size());
        // Count the current node, then recursively count nodes in left and right subtrees
        return 1 + countNodes(current.left) + countNodes(current.right);
    }
}
