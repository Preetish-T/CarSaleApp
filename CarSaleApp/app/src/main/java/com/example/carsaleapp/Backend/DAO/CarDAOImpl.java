package com.example.carsaleapp.Backend.DAO;
import com.example.carsaleapp.Backend.Car;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * This class provides an implementation of the CarDAO interface
 * for interacting with a Firebase Realtime Database to manage Car entities.
 * @author Yiming Cao,@u5021821
 */
public class CarDAOImpl implements CarDAO {
    private final DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("cars");

    @Override
    public void addCar(Car car) {
        mDatabase.child(String.valueOf(car.getId())).setValue(car);
    }

    @Override
    public void updateCar(Car car) {
        mDatabase.child(String.valueOf(car.getId())).setValue(car);
    }

    @Override
    public void deleteCar(int id) {
        mDatabase.child(String.valueOf(id)).removeValue();
    }
}