package com.egtactile.e_market;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class Chart extends AppCompatActivity {
    BarChart barchart;
    PieChart piechart;
    DatabaseReference databaseReference;
    Map<String, Integer> products;
    int i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        databaseReference = FirebaseDatabase
                .getInstance().getReference().child("Sold");
        barchart = findViewById(R.id.bar_chart);
        piechart = findViewById(R.id.pie_chart);
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        ArrayList<PieEntry> pieEntries = new ArrayList<>();
        products = new HashMap<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot datax : snapshot.getChildren()) {
                    for (DataSnapshot orders : datax.getChildren()) {
                        String productName = orders.getKey();
                        String productQuantity = orders.child("Quantity").getValue().toString();
                        if (products.containsKey(productName)) {
                            int data = Integer.parseInt(productQuantity);
                            int result = products.get(productName) + data;

                            products.replace(productName, result);
                        } else {
                            products.put(productName, Integer.parseInt(productQuantity));
                        }
                    }
                }
                for (Map.Entry<String,Integer> entry : products.entrySet()){
                    int value= entry.getValue();
                    BarEntry barEntry = new BarEntry(i, value);
                    PieEntry pieEntry = new PieEntry(value, entry.getKey());
                    barEntries.add(barEntry);
                    pieEntries.add(pieEntry);
                    i++;
                }
                //Bar chart
                BarDataSet barDataSet=new BarDataSet(barEntries,"Products");
                barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                //hide draw values
                barDataSet.setDrawValues(false);
                //set bar data
                barchart.setData(new BarData(barDataSet));

                //set animation
                barchart.animateY(5000);
                //set description
                barchart.getDescription().setText("Products with best sellers");
                barchart.getDescription().setTextColor(Color.BLACK);
                //pie chart
                PieDataSet pieDataSet=new PieDataSet(pieEntries,"Products");
                pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                piechart.setData(new PieData(pieDataSet));
                piechart.animateXY(5000,5000);
                barchart.getDescription().setEnabled(true);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}
