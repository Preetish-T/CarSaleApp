package com.example.carsaleapp.Backend.Tree;

import java.util.ArrayList;
import java.util.List;
import com.example.carsaleapp.Backend.Car;
import com.example.carsaleapp.Backend.Colour;

/**
 * Base class for node
 *
 * @param <T> data type
 * @author Yiming Cao,@u5021821 Zhangheng Xu,u6818456
 */
public class Node<T> {

	Colour colour;			// Node colour
	T key; 				// Node key
	Node<T> parent; 		// Parent node
	Node<T> left, right; 	// Child nodes
	List<Car> cars=new ArrayList<Car>();

	public Node(T key, Car car) {
		this.key  = key;
		this.colour = Colour.RED; //property 3 (if a node is red, both children are black) may be violated if parent is red

		this.parent = null;

		// Initialise children leaf nodes
		this.left 			= new Node<T>();  //leaf node
		this.right 			= new Node<T>();  //leaf node
		this.left.parent 	= this; //reference to parent
		this.right.parent 	= this; //reference to parent
		this.cars.add(car);
	}

	// Leaf node
	public Node() {
		this.key 	= null; //leaf nodes are null
		this.colour = Colour.BLACK; //leaf nodes are always black
	}

	public List<Car> getCars(){
		return cars;
	}
}