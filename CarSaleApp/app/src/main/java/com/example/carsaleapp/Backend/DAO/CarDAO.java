package com.example.carsaleapp.Backend.DAO;


import com.example.carsaleapp.Backend.Car;
/**
 * This interface represents a Data Access Object (DAO) for managing Car entities.
 * It defines methods for updating, adding, and deleting car records.
 * @author Yiming Cao,@u5021821
 */
public interface CarDAO {
    void updateCar(Car car);

    void addCar(Car car);

    void deleteCar(int id);
}