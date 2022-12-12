package com.egtactile.e_market;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class ProductDetails extends AppCompatActivity {
EditText Name , Num , Description , Price , Type;

ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        Type = findViewById(R.id.ProductType);
        Name = findViewById(R.id.ProductName);
        Num = findViewById(R.id.ProductNum);
        Price = findViewById(R.id.ProductPrice);
        Description = findViewById(R.id.ProductDescription);
        imageView = findViewById(R.id.ProductImage);

        String name = getIntent().getStringExtra("Name");
        String type = getIntent().getStringExtra("Type");
        String price = getIntent().getStringExtra("Price");
        String num = getIntent().getStringExtra("Num");
        String description = getIntent().getStringExtra("Description");
        String image = getIntent().getStringExtra("image");

        Picasso.get()
                .load(image)
                .into(imageView);

        Name.setText(name);
        Num.setText(num);
        Price.setText(price);
        Description.setText(description);
        Type.setText(type);

    }
}