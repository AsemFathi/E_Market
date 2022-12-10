package com.egtactile.e_market;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class Home_activity extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    TextView Name;
    TextView Email;
    TextView Phone;
    TextView Day;
    TextView Month;
    TextView Year;
    FirebaseUser user;
    StorageReference storageReference;
    String urldisplay ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_home);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        List<items> itemsList = new ArrayList<items>();

        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Products");
        storageReference =FirebaseStorage.getInstance().getReference();
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
                Map<String , List<String>> data = new HashMap<>();
                List<String> info = new ArrayList<>();
                int i = 0;

                for(DataSnapshot datas: snapshot.getChildren())
                {
                    String productType = datas.getKey();
                    Log.i(TAG, "onDataChange: First " + productType);

                    for (DataSnapshot datax: datas.getChildren())
                    {
                        String ProductName = datax.getKey();

                        Log.i(TAG, "onDataChange: Second " + datax.getKey());
                        //info.add(datax.getValue().toString());
                        String ProPrice = datax.child("Price").getValue().toString();
                        String ProNum = datax.child("Num").getValue().toString();
                        String ProImageUrl = datax.child("Picture").getValue().toString();
                        //StorageReference gsReferance = storageReference.getStorage().getReferenceFromUrl(ProImageUrl);

                        storageReference.child(ProImageUrl).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                urldisplay = uri.toString();
                            }
                        });
                        info.add(ProPrice);
                        info.add(ProNum);
                        info.add(ProImageUrl);
                        data.put(ProductName , info);
                        itemsList.add(new items(ProPrice , ProductName , ProImageUrl));
                        Log.i(TAG, "onDataChange: URL" + urldisplay);
                    }




                }


                recyclerView.setLayoutManager(new LinearLayoutManager(Home_activity.this));
                recyclerView.setAdapter(new MyAdapter(getApplicationContext() , itemsList));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Home_activity.this, "Failll", Toast.LENGTH_SHORT).show();
            }
        });
    }


}