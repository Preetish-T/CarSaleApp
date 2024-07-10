package com.example.carsaleapp.UI;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carsaleapp.Backend.Car;
import com.example.carsaleapp.Backend.DAO.CarDAO;
import com.example.carsaleapp.Backend.DAO.CarDAOImpl;
import com.example.carsaleapp.Backend.FirebaseService;
import com.example.carsaleapp.Backend.OnCarsListLoadedListener;
import com.example.carsaleapp.Backend.Tree.RBTreeSingleton;
import com.example.carsaleapp.R;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashSet;
import java.util.List;

public class UpdateActivity extends AppCompatActivity {

    private EditText editTextMake, editTextModel, editTextPrice, editTextYear, editTextId,showDataSize;
    private CarDAO carDao = new CarDAOImpl();

    private static final FirebaseService firebaseService=FirebaseService.getInstance();
    private final Handler handler=new Handler(Looper.getMainLooper());
    private Runnable updateRunnable;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAutoRunnable();
        setContentView(R.layout.activity_update);
        editTextId = findViewById(R.id.editTextId);
        editTextMake = findViewById(R.id.editTextMake);
        editTextModel = findViewById(R.id.editTextModel);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextYear = findViewById(R.id.editTextYear);
        showDataSize = findViewById(R.id.showDataSize);

    }


    public void onUpdateCarClicked(View view) {
        int id = Integer.parseInt(editTextId.getText().toString());
        String make = editTextMake.getText().toString();
        String model = editTextModel.getText().toString();
        int price = Integer.parseInt(editTextPrice.getText().toString());
        int year = Integer.parseInt(editTextYear.getText().toString());

        Car car = new Car(id, make, model, price, year);
        carDao.updateCar(car);
    }

    public void setAutoRunnable(){
        updateRunnable = new Runnable() {
            @Override
            public void run() {
                showDataSize.setText("The number of cars in database: "+String.valueOf(RBTreeSingleton.getCarsInstance().size())); // Set the current counter value to the TextView// Increment the counter
                handler.postDelayed(this, 5000); // Schedule the next execution in 5 seconds
            }
        };

        handler.post(updateRunnable);
    }
}