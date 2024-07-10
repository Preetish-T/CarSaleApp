package com.example.carsaleapp;

import static org.junit.Assert.*;

import com.example.carsaleapp.Backend.Car;

import org.junit.Test;
//@author Zhangheng Xu
public class CarTest {

    @Test
    public void testCarAttributes() {
        Car car = new Car(1, "Toyota", "Camry", 25000, 2023);

        // Test the attributes of the car
        assertEquals(1, car.getId());
        assertEquals("Toyota", car.getMake());
        assertEquals("Camry", car.getModel());
        assertEquals(25000, car.getPrice());
        assertEquals(2023, car.getYear());
    }

    @Test
    public void testCarComparison() {
        Car car1 = new Car(1, "Toyota", "Camry", 25000, 2023);
        Car car2 = new Car(2, "Honda", "Accord", 28000, 2023);
        Car car3 = new Car(3, "Toyota", "Corolla", 22000, 2023);

        // Test the comparison of cars based on make, model, and price
        assertEquals(1, car1.isSmaller(car2)); // car1 is smaller than car2
        assertEquals(0, car1.isSmaller(car3)); // car1 is smaller than car3
        assertEquals(0, car2.isSmaller(car3)); // car2 and car3 are equal
    }



}

