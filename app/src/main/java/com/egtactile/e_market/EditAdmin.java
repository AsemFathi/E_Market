package com.egtactile.e_market;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URI;
import java.util.HashMap;

public class EditAdmin extends AppCompatActivity {
    EditText productype,productprice,productquan,productdesc;
    TextView productname;
    ImageView productimg;
    Uri selectedImageUri;
    StorageReference reference;
    Button edit;
    String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_admin);

        productname = findViewById(R.id.editProductName);
        productype = findViewById(R.id.editProductType);
        productprice = findViewById(R.id.editProductPrice);
        productquan = findViewById(R.id.editProductNum);
        productdesc = findViewById(R.id.editProductDescription);
        productimg = findViewById(R.id.editProductImage);
        edit = findViewById(R.id.Confirm_edit);
        reference = FirebaseStorage.getInstance().getReference("image/");
        selectedImageUri =Uri.fromFile(new File(String.valueOf(productimg.getTag())));

        String name = getIntent().getStringExtra("Name");
        String type = getIntent().getStringExtra("Type");
        String price = getIntent().getStringExtra("Price");
        String quan = getIntent().getStringExtra("Num");
        String description = getIntent().getStringExtra("Description");
        String image = getIntent().getStringExtra("image");

        productname.setText(name);
        productype.setText(type);
        productprice.setText(price);
        productquan.setText(quan);
        productdesc.setText(description);
        Picasso.get().load(image).into(productimg);

        productimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageChoose();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databasereference = FirebaseDatabase.getInstance().getReference().child("Products")
                        .child(productname.getText().toString());
                HashMap map = new HashMap<>();
                map.put("Category" , productype.getText().toString());
                map.put("Description" , productdesc.getText().toString());
                map.put("Num" , productquan.getText().toString());
                map.put("Price" , productprice.getText().toString());
                map.put("Quantity" , productquan.getText().toString());

                databasereference.updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful())
                            Log.i(TAG, "onComplete: Updaaaaaaaaaaaaaaaaaaaated");
                    }
                });

            }
        });
    }
    protected void ImageChoose(){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), 200);
        //path = StorageReference.getPath();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 200) {
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    productimg.setImageURI(selectedImageUri);
                }
            }
        }
    }
}