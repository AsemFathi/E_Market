package com.egtactile.e_market;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import static android.content.ContentValues.TAG;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class Add_Products extends AppCompatActivity {
    Uri selectedImageUri;
    ImageView imageView;
    Button Choose;
    Button save;
    EditText CatType;
    EditText CatName;
    EditText CatNum;
    EditText CatPrice , Description;
    FirebaseAuth auth;
    FirebaseDatabase database;
    StorageReference reference;
    DatabaseReference databaseReference;
    //FirebaseUser firebaseUser;
    String path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_products);
        imageView =findViewById(R.id.Choose_img);
        Choose = findViewById(R.id.btn_Choose);
        selectedImageUri =Uri.fromFile(new File(String.valueOf(imageView.getTag())));

        reference = FirebaseStorage.getInstance().getReference("image/");
        save = findViewById(R.id.Save);
        CatName = findViewById(R.id.CatName);
        CatNum = findViewById(R.id.CatNum);
        CatType = findViewById(R.id.CatType);
        CatPrice = findViewById(R.id.CatPrice);
        Description = findViewById(R.id.description);


        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        database = FirebaseDatabase.getInstance();

        Log.i(TAG, "onCreate: ID "  + auth);
        Choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageChoose();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String Name = CatName.getText().toString();
            String Num = CatNum.getText().toString();
            String price = CatPrice.getText().toString();
            String type = CatType.getText().toString();
            String description = Description.getText().toString();
               /* reference.child(selectedImageUri.getPath()).putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(Add_Products.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                        //path = reference.child(selectedImageUri.getPath()).getDownloadUrl().toString();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Add_Products.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });*/
            DatabaseReference newData = databaseReference.child("Products").child(Name);
            //newData.setValue(type);
            //newData.child("Name").setValue(Name);
                StorageReference ImagePath= reference.child(selectedImageUri.getLastPathSegment());
                final UploadTask uploadTask = ImagePath.putFile(selectedImageUri);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Add_Products.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(Add_Products.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                        path = ImagePath.getDownloadUrl().toString();
                        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                            @Override
                            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                                if (!task.isSuccessful())
                                {
                                    throw task.getException();

                                }
                                path = ImagePath.getDownloadUrl().toString();
                                return ImagePath.getDownloadUrl();
                            }
                        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful())
                                {
                                    Toast.makeText(Add_Products.this , "Image Id : " , Toast.LENGTH_LONG );
                                    path = task.getResult().toString();

                                }
                            }
                        });
                    }
                });

            newData.child("Category").setValue(type);
            newData.child("Num").setValue(Num);
            newData.child("Price").setValue(price);
            newData.child("Picture").setValue(path);
            newData.child("Name").setValue(Name);
            newData.child("Description").setValue(description);


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
                    imageView.setImageURI(selectedImageUri);
                }
            }
        }
    }



}