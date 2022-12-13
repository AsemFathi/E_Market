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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.egtactile.e_market.ui.home.HomeFragment;
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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class CategoryItems extends AppCompatActivity implements RecyclerViewInterface{
    TextView Category;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    FirebaseUser user;
    StorageReference storageReference;
    String urldisplay;
    EditText searchText;
    Button searchView_btn;
    String searchInput;
    Map<String , List<String>> data = new HashMap<>();
    List<String> info = new ArrayList<>();
    List<items> itemsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_items);
        Category = findViewById(R.id.CategoryNameItems);

        String type = getIntent().getStringExtra("Category");

        Category.setText("Items of Category : " + type);
        itemsList = new ArrayList<items>();
        recyclerView = findViewById(R.id.recyclerViewCategory);
        recyclerView.setLayoutManager(new LinearLayoutManager( CategoryItems.this));

        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Products");
        storageReference = FirebaseStorage.getInstance().getReference();

        //get data from firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               /* Map<String , Object> map = (Map) snapshot.getValue();  // get type
                for (Map.Entry<String,Object > pair : map.entrySet()) {
                    //System.out.println(String.format("Key (name) is: %s, Value (age) is : %s", pair.getKey(), pair.getValue()));
                    //itemsList.add(new items("10" , pair.getKey(), R.drawable.main_app));
                    Map<String , Object> map1 = (Map) snapshot.child(pair.getKey()).getValue(); // get name
                    Log.i(TAG,"Type "+ pair.getKey() + "  " +pair.getValue());
                    for (Map.Entry<String , Object> pair1 : map1.entrySet())
                    {
                        Log.i(TAG,"Name "+ pair1.getKey() + "  " +pair1.getValue());
                        Map<String , Object> map2 = (Map) snapshot.child(pair.getKey()).child(pair1.getKey()).getValue();
                        int i =0;
                        for (Map.Entry<String , Object> pair2 : map2.entrySet())
                        {
                           // Map<String , Object> map2 = (Map)snapshot.child(pair.getKey()).child(pair1.getKey()).child(pair2.getKey()).getValue();

                            Log.i(TAG,"Price "+ pair2.getKey() + "  " +pair2.getValue());
                            if (i == 0)
                                itemsList.add(new items(pair2.getValue().toString() , pair1.getKey(), R.drawable.main_app));
                            //
                            i++;

                        }

                    }

                }

                */
                for(DataSnapshot datax: snapshot.getChildren())
                {

                    String ProductName = datax.getKey();
                    String ProductType = datax.child("Category").getValue().toString();
                    if (ProductType.toLowerCase().equals(type.toLowerCase())) {


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
                        info.add(ProPrice.toLowerCase());
                        info.add(ProNum.toLowerCase());
                        info.add(des.toLowerCase());
                        info.add(ProImageUrl.toLowerCase());

                        data.put(ProductName.toLowerCase(), info);
                        itemsList.add(new items(ProPrice, ProductName, des, ProImageUrl, ProductType, ProNum));
                        Log.i(TAG, "onDataChange: URL" + urldisplay);

                    }
                }
                recyclerView.setAdapter(new MyAdapter(CategoryItems.this ,itemsList ,  CategoryItems.this));
                //Navigation.findNavController(navView).navigate(R.id.navigation_home);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CategoryItems.this, "Failll", Toast.LENGTH_SHORT).show();
            }

        });



    }

    @Override
    public void onItemClick(int pos) {
        Intent intent = new Intent(CategoryItems.this , ProductDetails.class);
        intent.putExtra("Name" , itemsList.get(pos).getName());
        intent.putExtra("Type" , itemsList.get(pos).getCategory());
        intent.putExtra("Price" , itemsList.get(pos).getPrice());
        intent.putExtra("Num" , itemsList.get(pos).getNum());
        intent.putExtra("Description" , itemsList.get(pos).getDescription());
        intent.putExtra("image" , itemsList.get(pos).getImage());
        startActivity(intent);
    }
}