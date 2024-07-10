package com.example.carsaleapp.Backend.Tree;

import java.util.HashSet;
import java.util.List;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.carsaleapp.Backend.Car;
import com.example.carsaleapp.Backend.FirebaseService;
import com.example.carsaleapp.Backend.OnCarsListLoadedListener;

/**
 * The RBTreeSingleton class is responsible for managing singleton instances of PriceTree,
 * RBTree, and a HashSet of Car objects. It provides methods for updating and retrieving
 * these singleton instances and handles automatic updates from a Firebase service.
 * @author Yiming Cao,@u5021821
 */
public class RBTreeSingleton{
    private static PriceTree priceTreeInstance = null;
    private static RBTree<String> makeTreeInstance = null;
    private static HashSet<Car> cars = null;
    // Private constructor to prevent instantiation from other classes

    public RBTreeSingleton() { }

    public static synchronized RBTreeSingleton getInstance() {
        if (priceTreeInstance == null) {
            priceTreeInstance = new PriceTree();
        }
        if (makeTreeInstance == null) {
            makeTreeInstance = new RBTree<String>();
        }
        if (cars == null) {
            cars = new HashSet<>();
        }

        return new RBTreeSingleton();
    }

    /**
     * Retrieves the singleton instance of the PriceTree.
     *
     * @return The singleton instance of the PriceTree.
     */
    public static synchronized PriceTree getPriceInstance() {
        if (priceTreeInstance == null) {
            priceTreeInstance = new PriceTree();
        }
        return priceTreeInstance;
    }
    /**
     * Retrieves the singleton instance of the RBTree for makes.
     *
     * @return The singleton instance of the RBTree for makes.
     */
    public static synchronized RBTree<String> getMakeInstance() {
        if (makeTreeInstance == null) {
            makeTreeInstance = new RBTree<>();
        }
        return makeTreeInstance;
    }
    /**
     * Retrieves the singleton instance of the HashSet containing Car objects.
     *
     * @return The singleton instance of the HashSet containing Car objects.
     */
    public static synchronized HashSet<Car> getCarsInstance() {
        if (cars == null) {
            cars = new HashSet<>();
        }
        return cars;
    }


    /**
     * Adds a Car object to the HashSet of Cars.
     *
     * @param car The Car object to be added to the HashSet.
     */
    public void updateCars(Car car){
        cars.add(car);
        Log.e("RBTreeSingleton","Car added, id:"+car.getId());
    }
    /**
     * Inserts a Car object into the PriceTree.
     *
     * @param car The Car object to be inserted into the PriceTree.
     */
    public void updatePrice(Car car){
        priceTreeInstance.insert(car.getPrice(),car);
        Log.e("RBTreeSingleton","Car inserted, id:"+car.getId());
    }

    /**
     * Inserts a Car object into the RBTree for makes.
     *
     * @param car The Car object to be inserted into the RBTree for makes.
     */
    public void updateMake(Car car){
        makeTreeInstance.insert(car.getMake(),car);
        Log.e("RBTreeSingleton","Car inserted, id:"+car.getMake());
    }
    /**
     * Sets up an automatic update runnable to periodically fetch car data from a Firebase service
     * and perform actions with the data.
     */


}