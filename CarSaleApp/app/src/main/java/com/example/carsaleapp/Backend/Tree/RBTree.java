package com.example.carsaleapp.Backend.Tree;

import java.util.ArrayList;
import java.util.List;
import com.example.carsaleapp.Backend.Car;
import com.example.carsaleapp.Backend.Colour;

/**
 * Skeleton code for Red Black Tree
 * 
 * @param <T> data type
 * @author Yiming Cao,@u5021821
 */
public class RBTree<T extends Comparable<T>> {
	
	public Node<T> root; // The root node of the tree

	/**
	 * Initialize empty RBTree
	 */
	public RBTree() {
		root = null;
	}

	/**
	 * Add a new node into the tree with {@code root} node.
	 * 
	 * @param root Node<T> The root node of the tree where x is being inserted.
	 * @param x    Node<T> New node being inserted.
	 * @author Yiming Cao
	 */
	private void insertRecurse(Node<T> root, Node<T> x) {

		int cmp = root.key.compareTo(x.key);
		if(cmp==0){
			root.cars.add(x.cars.get(0));
		}
		if (cmp > 0) {
			if (root.left == null||root.left.key==null) {
				root.left = x;
				x.parent = root;
				System.out.println("Become left child "+root.key+" "+root.left.key);
			} else {
				insertRecurse(root.left, x);
			}
		} else if (cmp < 0) {
			if (root.right == null||root.right.key==null) {
				root.right = x;
				x.parent = root;
				System.out.println("Become right child "+ root.key+" "+root.right.key);
			} else {
				insertRecurse(root.right, x);
			}
		}
		// Do nothing if the tree already has a node with the same key.
	}

	/**
	 * Insert node into RBTree.
	 * 
	 * @param x Node<T> The new node being inserted into the tree.
	 * @author Yiming Cao
	 */
	private void insert(Node<T> x) {

		// Insert node into tree
		if (root == null) {
			root = x;
		} else {
			insertRecurse(root, x);
		}

		// Fix tree
		if (x.parent != null && x.parent.parent != null){
			while (x.key != root.key && x.parent.colour == Colour.RED) {
				boolean left  = x.parent == x.parent.parent.left; // Is parent a left node
				Node<T> uncle = left ? x.parent.parent.right : x.parent.parent.left; // Get opposite "uncle" node to parent
				if (uncle.colour == Colour.RED) {
					x.parent.colour = Colour.BLACK;
					uncle.colour = Colour.BLACK;
					x.parent.parent.colour = Colour.RED;
					x = x.parent.parent;
				} else {
					if (x.key == (left ? x.parent.right.key : x.parent.left.key)) {
						// Case 2: Left Rotation, uncle is right node, x is on the right / Right Rotation, uncle is left node, x is on the left
						x = x.parent;
						if (left) {
							// Perform left rotation
							if (x.key == root.key)
								root = x.right; // Update root
							rotateLeft(x);
						} else {
							rotateRight(x);
						}
					}

					x.parent.colour = Colour.BLACK;
					x.parent.parent.colour = Colour.RED;
					if (left) {
						rotateRight(x.parent.parent);
					} else {
						rotateLeft(x.parent.parent);
					}
				}
			}
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
	public void rotateLeft(Node<T> x) {
		System.out.println("Rotate left");
		// Make parent (if it exists) and right branch point to each other
		if (x.parent != null) {
			// Determine whether this node is the left or right child of its parent
			if (x.parent.left.key == x.key) {
				x.parent.left = x.right;
			} else {
				x.parent.right = x.right;
			}
		}
		x.right.parent = x.parent;

		x.parent = x.right;
		// Take right node's left branch
		x.right = x.parent.left;
		x.right.parent = x;
		// Take the place of the right node's left branch
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
	public void rotateRight(Node<T> x) {
		System.out.println("Rotate right");
		if (x.parent != null) {
			if (x.parent.left == x) {
				x.parent.left = x.left;
			} else {
				x.parent.right = x.left;
			}
		}
		x.left.parent = x.parent;

		x.parent = x.left;
		// Take left node's right branch
		x.left = x.parent.right;
		x.left.parent = x;
		// Take the place of the left node's right branch
		x.parent.right = x;

	}

	/**
	 * Demo functions (Safely) insert a key into the tree
	 * 
	 * @param key T The key of the new node being inserted.
	 * @author Yiming Cao
	 */
	public void insert(T key, Car car) {
		Node<T> node = new Node<T>(key,car);
		insert(node);
	}

	/**
	 * Return the result of a pre-order traversal of the tree
	 * 
	 * @param tree Tree we want to pre-order traverse
	 * @return pre-order traversed tree
	 * @author Yiming Cao
	 */
	private String preOrder(Node<T> tree) {
		if (tree != null && tree.key != null) {
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
	 * Return the corresponding node of a key, if it exists in the tree
	 * 
	 * @param x Node<T> The root node of the tree we search for the key {@code v}
	 * @param v Node<T> The node that we are looking for
	 * @return node
	 * @author Yiming Cao
	 */
	private List<Car> find(Node<T> x, T v) {
		if (x == null || x.key == null)
			return null;
		int cmp = v.compareTo(x.key);
		if (cmp < 0)
			return find(x.left, v);
		else if (cmp > 0)
			return find(x.right, v);
		else
			return x.cars;
	}

	/**
	 * Returns a node if the key of the node is {@code key}.
	 * 
	 * @param key T The key we are looking for
	 * @return a list of car
	 * @author Yiming Cao
	 */
	public List<Car> search(T key) {
		return find(root, key);
	}

	public List<Car> getAll(T min, T max) {
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
	private void retrieveInRange(Node<T> node, T min, T max, List<Car> result) {
		if (node == null) {
			return;
		}
		if (min.compareTo(node.key) <= 0 && max.compareTo(node.key) >= 0) {
			result.addAll(node.cars);
			if(node.left.key!=null){
				retrieveInRange(node.left, min, max, result);
			}
			if(node.right.key!=null){
				retrieveInRange(node.right, min, max, result);
			}
		}
		else if (max.compareTo(node.key)<0) {
			if(node.left.key!=null){
				retrieveInRange(node.left, min, max, result);
			}
		}
		else if (min.compareTo(node.key) > 0) {
			if(node.right.key!=null){
				retrieveInRange(node.right, min, max, result);
			}
		}
	}


	/**
	 * Check if the RBTree is empty.
	 *
	 * @return true if the tree is empty, false otherwise.
	 * @author Zhangheng Xu, u6818456
	 */
	public boolean isEmpty() {
		return root == null;
	}
}
