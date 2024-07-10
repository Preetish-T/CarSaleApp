package com.example.carsaleapp.UI;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.carsaleapp.Backend.Car;
import com.example.carsaleapp.Backend.Tree.PriceTree;
import com.example.carsaleapp.Backend.Tree.RBTree;
import com.example.carsaleapp.Backend.Tree.RBTreeSingleton;
import com.example.carsaleapp.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * @author Yiming Cao u5021821
 */


public class DataViewActivity extends AppCompatActivity {

    BarChart barChart;
    List<Car> cars=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_view);
        updateChartData();


        View backToMainPage2 = findViewById(R.id.backToMainPage2);

        backToMainPage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void updateChartData() {
        barChart=findViewById(R.id.bar_chart);
        ArrayList<BarEntry> barEntries=new ArrayList<>();
        //cars.addAll(RBTreeSingleton.getCarsInstance());
        //cars.addAll(RBTreeSingleton.getPriceInstance().getAll(0,80000));
        RBTree<String> makeTree=RBTreeSingleton.getMakeInstance();
        PriceTree priceTree=RBTreeSingleton.getPriceInstance();
        //cars.addAll(makeTree.search("Toyota"));
        cars.addAll(priceTree.getAll(0,100000));

        if(cars.size()>0){
            HashMap<Integer,Integer> carMap = new HashMap<>();
            int binWidth = 2500;
            for(Car car:cars){
                int p = car.getPrice()/binWidth;
                if(carMap.containsKey(p)) {
                    carMap.put(p,carMap.get(p)+1);
                }
                else{
                    carMap.put(p,1);
                }
            }

            for (int k:carMap.keySet()) {
                //convert
                //initial bar entry
                BarEntry  barEntry=new BarEntry((float)k*binWidth,carMap.get(k));
                //initial pie entry
                barEntries.add(barEntry);
            }

            //initialize bar data set
            BarDataSet barDataSet=new BarDataSet(barEntries,"Price");
            //Set colors
            barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            //set bar width
            barDataSet.setBarBorderWidth(10f);
            //Hide draw value
            barDataSet.setDrawValues(false);
            //Set bar data
            barChart.setData((new BarData(barDataSet)));
            //Set animation
            barChart.animateY(500);
            //Set description
            barChart.getDescription().setText("Bin width: "+binWidth);
            barChart.getDescription().setTextColor(Color.BLUE);
            //Set x axis
            int minAxis=Integer.MAX_VALUE;
            int maxAxis=Integer.MIN_VALUE;
            for(int d: carMap.keySet()){
                if (minAxis>d*binWidth) minAxis=(int)d*binWidth;
                if (maxAxis<d*binWidth) maxAxis=(int)d*binWidth;
            }
            XAxis xAxis = barChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);



            barChart.getXAxis().setAxisMinimum(minAxis-binWidth);
            barChart.getXAxis().setAxisMaximum(maxAxis+binWidth);
            barChart.getXAxis().setGranularity(2500f);
            barChart.getXAxis().setGranularityEnabled(true);
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getFormattedValue(float value) {
                    return String.valueOf((int) value);
                }
            });

            barChart.getXAxis().setDrawLabels(true);
            barChart.getAxisLeft().setGranularity(1f);
            barChart.getAxisRight().setEnabled(false);
            barDataSet.setDrawValues(true);
            barDataSet.setValueTextSize(10f);
        }
    }

}