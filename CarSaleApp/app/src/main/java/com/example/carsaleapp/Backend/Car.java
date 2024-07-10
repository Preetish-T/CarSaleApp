package com.example.carsaleapp.Backend;

import android.util.Log;

import java.util.Objects;

/**
 * This class represents a Car object with attributes such as ID, make, model, price, and year.
 * @author Yiming Cao,@u5021821
 */
public class Car{
    private int id;
    private String make;
    private String model;
    private int price;
    private int year;

    /**
     * Constructs a Car object with the specified attributes.
     *
     * @param id    The unique identifier for the car.
     * @param make  The make (manufacturer) of the car.
     * @param model The model of the car.
     * @param price The price of the car.
     * @param year  The manufacturing year of the car.
     */
    public Car(int id, String make, String model, int price, int year) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.price = price;
        this.year = year;
    }

    public Car() {

    }

    public Car(String s, int nextInt) {
        this.make = s;
        this.price = nextInt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Compares this car to another car and determines which one is smaller based on make, model, and price.
     *
     * @param car The car to compare with.
     * @return 0 if this car is smaller, 1 if the other car is smaller, 2 if they are equal.
     */
    public int isSmaller(Car car){
        int n1 = compareString(this.getMake(),car.getMake());
        int n2 = compareString(this.getModel(),car.getModel());
        if(n1!=2) return n1;
        if(n2!=2) return n2;
        if(this.price<car.getPrice()) return 0;
        if(this.price>car.getPrice()) return 1;
        else return 2;
    }

    /**
     * Compares two strings ignoring case.
     *
     * @param str1 The first string.
     * @param str2 The second string.
     * @return 0 if str1 is "smaller," 1 if str2 is "smaller," 2 if they are equal.
     */
    public int compareString(String str1, String str2){
        int n1=str1.length();
        int n2=str2.length();
        int minN=Math.min(n1,n2);
        for (int i = 0; i < minN; i++) {
            if(str1.toUpperCase().charAt(i)<str2.toUpperCase().charAt(i)){
                return 0;
            }
            else if(str1.toUpperCase().charAt(i)<str2.toUpperCase().charAt(i)){
                return 1;
            }
        }
        if(n1<n2) return 0;
        else if(n2<n1)return 1;
        else return 2;
    }

    public void printCar(){
        Log.e("TAG","ID:"+this.getId());
        Log.e("TAG","Make:"+this.getMake());
        Log.e("TAG","Model:"+this.getModel());
        Log.e("TAG","Price:"+this.getPrice());
    }
    /**
     * Compares this car object to another object to check if they are equal.
     *
     * @param o The object to compare.
     * @return true if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return id == car.id &&
                price == car.price &&
                year == car.year &&
                Objects.equals(make, car.make) &&
                Objects.equals(model, car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, make, model, price, year);
    }
}