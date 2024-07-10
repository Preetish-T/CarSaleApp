package com.example.carsaleapp.Backend;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.carsaleapp.Backend.Tree.RBTreeSingleton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * The FirebaseService class is responsible for managing interactions with Firebase Realtime Database to retrieve
 * and listen to a list of Car objects. It follows the singleton pattern to ensure a single instance of the service.
 * The class provides methods for starting and stopping data retrieval, as well as obtaining the current list of cars.
 * @author Yiming Cao u5021821
 */
public class FirebaseService {

    private static FirebaseService instance;
    private OnCarsListLoadedListener listener;
    private final DatabaseReference mDatabase;
    private final List<Car> carsList = new ArrayList<>();
    private ValueEventListener carDataListener;

    // Private constructor for singleton pattern
    private FirebaseService() {
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://assignment-login-a46d6-default-rtdb.firebaseio.com/");
        mDatabase = database.getReference("cars");  // Access the 'cars' node
        if (mDatabase == null) {
            Log.e("FirebaseService", "mDatabase is null");
            // Handle the null case, perhaps reinitializing it or returning.
        } else {
            Log.e("FirebaseService", "mDatabase is fetched");
        }
    }

    /**
     * Retrieves the singleton instance of the FirebaseService.
     *
     * @return The singleton instance of the FirebaseService.
     */
    public static synchronized FirebaseService getInstance() {
        if (instance == null) {
            instance = new FirebaseService();
        }
        return instance;
    }
    /**
     * Initiates listening to changes in the Firebase database and updates the internal list of cars.
     * Additionally, it updates the singleton RBTreeSingleton instance with the new car data.
     *
     * @param listener An implementation of OnCarsListLoadedListener to handle the loaded car data.
     */
    public void startListening(OnCarsListLoadedListener listener) {
        this.listener = listener;

        if (carDataListener == null) {
            carDataListener = new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    carsList.clear();
                    RBTreeSingleton rbTreeSingleton=new RBTreeSingleton();
                    for (DataSnapshot carSnapshot : dataSnapshot.getChildren()) {
                        Car car = carSnapshot.getValue(Car.class);
                        carsList.add(car);
                        rbTreeSingleton.updateCars(car);
                        rbTreeSingleton.updatePrice(car);
                        rbTreeSingleton.updateMake(car);
                        assert car != null;
                        Log.e("FirebaseService","Car inserted, id:"+car.getId()+" price: "+car.getPrice());
                    }
                    // Notify via the callback
                    if (FirebaseService.this.listener != null) {
                        FirebaseService.this.listener.onCarsListLoaded(carsList);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle errors as necessary
                }
            };
            mDatabase.addValueEventListener(carDataListener);
        }
    }

    public void stopListening() {
        if (carDataListener != null) {
            mDatabase.removeEventListener(carDataListener);
            carDataListener = null;
        }
    }

    /**
     * Retrieves a copy of the current list of cars.
     *
     * @return A copy of the list of Car objects.
     */
    public List<Car> getCarsList() {
        Log.e("Car Size in getCarsList", "" + carsList.size());
        return new ArrayList<>(carsList);  // Return a copy of the list for encapsulation
    }
}