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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    FirebaseUser user;
    int quantity;
    String email ;
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

        user = FirebaseAuth.getInstance().getCurrentUser();
        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Cart");
        database = FirebaseDatabase.getInstance();


        email = user.getEmail();
        email = email.replaceAll("@gmail.com" , "");

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
                if (quantity != 0)
                {
                    DatabaseReference newData = databaseReference.child(email).child(name);
                    newData.child("Category").setValue(type);
                    newData.child("Quantity").setValue(String.valueOf(quantity));
                    newData.child("Price").setValue(price);
                    newData.child("Picture").setValue(image);
                    newData.child("Name").setValue(name);
                    newData.child("Description").setValue(description);
                    Toast.makeText(ProductDetails.this, "Added To Cart", Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(ProductDetails.this, "Quantity of items = 0", Toast.LENGTH_LONG).show();
                }
                }
        });

    }

}