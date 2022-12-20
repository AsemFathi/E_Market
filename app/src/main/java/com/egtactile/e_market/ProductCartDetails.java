package com.egtactile.e_market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.egtactile.e_market.ui.basket.BasketFragment;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductCartDetails extends AppCompatActivity implements RecyclerViewInterface {
    List<items> itemsList = new ArrayList<items>();
    RecyclerView recyclerView;
    TextView TotalPrice;
    Button Confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_cart_details);

        BasketFragment basketFragment = new BasketFragment() ;
        itemsList = basketFragment.getItemsList();
        //String total_price = getIntent().getStringExtra("Price");
        int total_price = basketFragment.getTotal_Price();
        recyclerView = findViewById(R.id.recyclerViewCartDetails);
        Confirm = findViewById(R.id.btn_Confirm);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductCartDetails.this));
        recyclerView.setAdapter(new CartAdapter(this , itemsList , this));
        TotalPrice = findViewById(R.id.total_price_Activity);
        TotalPrice.setText("Total Price : " + String.valueOf(total_price));

        Confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Confirm activity
                Intent intent = new Intent(ProductCartDetails.this ,MapsActivity.class );
                startActivity(intent);
            }
        });
    }



    @Override
    public void onItemClick(int pos) {

    }
}