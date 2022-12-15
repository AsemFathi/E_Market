package com.egtactile.e_market;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import static android.content.ContentValues.TAG;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


public class ProductDetails extends AppCompatActivity {
EditText Name , Num , Description , Price , Type;
TextView Quantity;
ImageView imageView , Add , Reduce ;

Button AddToCart;

    FirebaseAuth auth;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    int quantity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        AddToCart = findViewById(R.id.AddToCart);
        Type = findViewById(R.id.ProductType);
        Name = findViewById(R.id.ProductName);
        Num = findViewById(R.id.ProductNum);
        Price = findViewById(R.id.ProductPrice);
        Description = findViewById(R.id.ProductDescription);
        imageView = findViewById(R.id.ProductImage);
        Add = findViewById(R.id.im_add);
        Reduce = findViewById(R.id.reduce);
        Quantity = findViewById(R.id.tv_num);
        quantity = 0;

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
        DatabaseReference newData = databaseReference.child("Cart");

        Log.i(TAG, "onCreate: ID: "+auth);

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
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(Quantity.getText().toString()) >= Integer.parseInt(num))
                {
                    Quantity.setText(num);
                }
                else
                {
                    quantity++;
                    Quantity.setText(String.valueOf(quantity));
                }

            }
        });
        Reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(Quantity.getText().toString()) <= 0)
                {
                    Quantity.setText("0");
                }
                else
                {
                    quantity--;
                    Quantity.setText(String.valueOf(quantity));
                }
            }
        });

        AddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("Name" , name);
                intent.putExtra("Type" , type);
                intent.putExtra("Price" , price);
                intent.putExtra("Quantity" , Quantity.getText().toString());
                intent.putExtra("Description" , description);
                intent.putExtra("image" , image);

                //intent.putExtra();
            }
        });

    }

}