package com.example.carsaleapp;

import com.example.carsaleapp.Backend.Car;
import com.example.carsaleapp.Backend.Tree.PriceTree;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class PriceTreeTest {

    private PriceTree<Integer> tree;
    private Car car1, car2, car3, car4, car5;

    @Before
    public void setUp() {
        tree = new PriceTree<>();
        car1 = new Car(1, "Audi", "A4", 20000, 2022);
        car2 = new Car(2, "BMW", "X5", 25000, 2021);
        car3 = new Car(3, "Toyota", "Camry", 15000, 2020);
        car4 = new Car(4, "Ford", "Focus", 18000, 2019);
        car5 = new Car(5, "Mercedes", "E-Class", 50000, 2023);
    }

    @Test
    public void testInsertAndPreOrder() {
        tree.insert(car1.getPrice(), car1);
        assertEquals("20000", tree.preOrder());

        tree.insert(car2.getPrice(), car2);
        tree.insert(car3.getPrice(), car3);
        assertEquals("20000 15000 25000", tree.preOrder());
    }

    @Test
    public void testSearch() {
        tree.insert(car1.getPrice(), car1);
        tree.insert(car2.getPrice(), car2);

        List<Car> result = tree.search(20000);
        System.out.println(result.get(0).getPrice());
        assertEquals(1, result.size());
        assertEquals(car1, result.get(0));

        result = tree.search(30000);
        assertNull(result);
    }

    @Test
    public void testGetAll() {
        tree.insert(car1.getPrice(), car1);
        tree.insert(car2.getPrice(), car2);
        tree.insert(car3.getPrice(), car3);
        tree.insert(car4.getPrice(), car4);
        tree.insert(car5.getPrice(), car5);

        List<Car> result = tree.getAll(15000, 30000);
        assertNotNull(result);
        System.out.println(result);
        assertEquals(4, result.size());
    }
}