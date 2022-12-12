package com.egtactile.e_market;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;


public class ProductDetails extends AppCompatActivity {
EditText Name , Num , Description , Price;

ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Name = findViewById(R.id.ProductName);
        Num = findViewById(R.id.ProductNum);
        Price = findViewById(R.id.ProductPrice);
        Description = findViewById(R.id.ProductDescription);
        imageView = findViewById(R.id.ProductImage);


    }
}