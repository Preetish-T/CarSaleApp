package com.example.carsaleapp.UI;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carsaleapp.Backend.Car;
import com.example.carsaleapp.Backend.CarXmlParser;
import com.example.carsaleapp.Backend.Grammar.IntExp;
import com.example.carsaleapp.Backend.QueryListViewAdapter;
import com.example.carsaleapp.R;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

//author Preetish Thirumalai u7157098


public class QueryActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{
    List<Car> cars;
    ListView list;
    QueryListViewAdapter adapter;
    SearchView editsearch;
    IntExp t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        list = (ListView) findViewById(R.id.listview);
        cars = createCars();
        adapter = new QueryListViewAdapter(this, cars);
        list.setAdapter(adapter);
        editsearch = (SearchView) findViewById(R.id.search);
        editsearch.setOnQueryTextListener(this);

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

    @Override
    public boolean onQueryTextSubmit(String s) {
        String perm = s;
        adapter.filter(perm);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }
}