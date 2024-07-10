package com.example.carsaleapp.Backend.Tree;

import java.util.ArrayList;
import java.util.List;
import com.example.carsaleapp.Backend.Car;
import com.example.carsaleapp.Backend.Colour;
/**
 * This class represents a node in a binary tree with an integer key and associated properties.
 * Each node can store a list of cars.
 *
 * @param <Integer> The type of the key in the node.
 * @author Yiming Cao,@u5021821
 */
public class IntNode<Integer> {

    Colour colour;			// Node colour
    public int key; 				// Node key
    IntNode<Integer> parent; 		// Parent node
    IntNode<Integer> left, right; 	// Child nodes
    public List<Car> cars=new ArrayList<Car>();

    public IntNode(int key, Car car) {
        this.key  = key;
        this.colour = Colour.RED; //property 3 (if a node is red, both children are black) may be violated if parent is red

        this.parent = null;

        // Initialise children leaf nodes
        this.left 			= new IntNode<>();  //leaf node
        this.right 			= new IntNode<>();  //leaf node
        this.left.parent 	= this; //reference to parent
        this.right.parent 	= this; //reference to parent
        this.cars.add(car);
    }

    // Leaf node
    public IntNode() {
        this.key 	= 0; //leaf nodes are null
        this.colour = Colour.BLACK; //leaf nodes are always black
    }


}