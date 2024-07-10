package com.example.carsaleapp.UI;

import static com.example.carsaleapp.Backend.SearchInvalid.suggestCorrections;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.carsaleapp.Backend.Car;
import com.example.carsaleapp.Backend.CarXmlParser;
import com.example.carsaleapp.Backend.FirebaseService;
import com.example.carsaleapp.Backend.ListViewAdapter;
import com.example.carsaleapp.Backend.OnCarsListLoadedListener;
import com.example.carsaleapp.Backend.Tree.RBTreeSingleton;
import com.example.carsaleapp.Backend.User;
import com.example.carsaleapp.R;

import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;



/**
 * @author Yiming Cao u5021821
 * @author Zhe Feng u7133440
 * @author Zhangheng Xu u6818456
 * @author Preetish Thirumalai u7157098
 */

public class MainPage extends AppCompatActivity implements SearchView.OnQueryTextListener{
    private TextView recommendationsTextView;
    private final List<String> suggestions = new ArrayList<>();
    ListView list;
    ListViewAdapter adapter;
    SearchView editsearch;
    List<Car> cars = new ArrayList<Car>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        updateData();
        list = (ListView) findViewById(R.id.listview);
        cars = createCars();
        // Pass results to ListViewAdapter Class
        adapter = new ListViewAdapter(this, cars);

        // Binds the Adapter to the ListView
        list.setAdapter(adapter);

        // Locate the EditText in listview_main.xml
        editsearch = (SearchView) findViewById(R.id.search);
        editsearch.setOnQueryTextListener(this);

        User user = (User) getIntent().getExtras().getSerializable("USER");

        View btnMain = findViewById(R.id.btnMain);
        View btnSell = findViewById(R.id.btnSell);
        View btnData = findViewById(R.id.btnData);
        View btnProfile = findViewById(R.id.btnProfile);

        // Handle button clicks---------------------------------------------------------------------//
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPage.this, QueryActivity.class));
            }
        });
//
        btnSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPage.this, UpdateActivity.class));
            }
        });
//
        btnData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainPage.this, DataViewActivity.class));
            }
        });
//

        //add the user object as an extra to the intent, Zhe
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPage.this, ProfileSectionActivity.class);
                intent.putExtra("USER", user);
                startActivity(intent);
            }
        });
        //--------------------------------------------------------------------------------------------------//
    }
    // Check if a spelling was incorrect, correct it and return a toast if it was
    // author: Preetish Thirumalai u7157098
    @Override
    public boolean onQueryTextSubmit(String query) {
        List<String> corrections = suggestCorrections(query);
        String correction = corrections.get(0);
        if (!correction.equals(query)) {
            Toast.makeText(getApplicationContext(), "We did not understand " + query +
                    " , did you mean " + correction + "?", Toast.LENGTH_LONG).show();
            adapter.filter(correction);
        }
        else {
            adapter.filter(query);
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filter(newText);
        return false;
    }
    protected List<Car> createCars(){
        CarXmlParser carXmlParser=new CarXmlParser();
        List<Car> carList=new ArrayList<>();
        try {
            InputStream inputStream = getAssets().open("Cars.XML");
            carList=carXmlParser.parse(inputStream);
            inputStream.close();
            return  carList;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public void updateData(){
        // Initialize the FirebaseService and start listening to changes
        FirebaseService firebaseService = FirebaseService.getInstance();
        RBTreeSingleton rbTreeSingleton=RBTreeSingleton.getInstance();
        firebaseService.startListening(new OnCarsListLoadedListener() {
            @Override
            public void onCarsListLoaded(List<Car> cars) {
                // This callback will be triggered whenever there's a change in Firebase's car data.
                // Update your UI or other data structures as needed here.
                for(Car car:cars){
                    rbTreeSingleton.updateCars(car);
                    rbTreeSingleton.updatePrice(car);
                }
            }
        });

    }

}





