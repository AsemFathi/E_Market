package com.egtactile.e_market;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Delete extends AppCompatActivity implements RecyclerViewInterface {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    FirebaseUser user;
    StorageReference storageReference;
    String urldisplay;
    static List<items> itemsList=new ArrayList<items>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);
        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Products");
        storageReference = FirebaseStorage.getInstance().getReference();

        recyclerView = findViewById(R.id.AdminProductsList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                itemsList = new ArrayList<items>();
                for (DataSnapshot datax : snapshot.getChildren()) {
                    String ProductName = datax.getKey();
                    Log.i(TAG, "onDataChange: Name" + ProductName);
                    String ProductType = datax.child("Category").getValue().toString();
                    //String ProductName = datax.getKey();
                    String ProPrice = datax.child("Price").getValue().toString();
                    String ProNum = datax.child("Num").getValue().toString();
                    String ProImageUrl = datax.child("Picture").getValue().toString();
                    String des = datax.child("Description").getValue().toString();
                    Log.i(TAG, "onDataChange: Description" + des);
                    storageReference.child(ProImageUrl).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            urldisplay = uri.toString();
                        }
                    });

                    itemsList.add(new items(ProPrice, ProductName, des, ProImageUrl, ProductType, ProNum));
                    Log.i(TAG, "onDataChange: URL" + urldisplay);


                }
                recyclerView.setAdapter(new Adminadapter(Delete.this, itemsList, Delete.this));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
         });
        }
    @Override
    public void onItemClick(int pos) {
        Intent intent = new Intent(Delete.this , EditAdmin.class);
        intent.putExtra("Name" , itemsList.get(pos).getName());
        intent.putExtra("Type" , itemsList.get(pos).getCategory());
        intent.putExtra("Price" , itemsList.get(pos).getPrice());
        intent.putExtra("Num" , itemsList.get(pos).getNum());
        intent.putExtra("Description" , itemsList.get(pos).getDescription());
        intent.putExtra("image" , itemsList.get(pos).getImage());
        startActivity(intent);
    }
}