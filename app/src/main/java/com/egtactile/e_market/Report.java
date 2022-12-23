package com.egtactile.e_market;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.egtactile.e_market.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Repo;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Report extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener,RecyclerViewInterface{

    TextInputEditText DateString,username;
    RadioGroup ChooseUser;
    CheckBox alltimecheck;
    Button search;
    boolean specificuser=false,allusers=false;
    boolean specificdate=false,alldates=false;
    private RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    List<String> users = new ArrayList<>();
    List<items> products=new ArrayList<items>();
    StorageReference storageReference;
    String urldisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        DateString = (TextInputEditText)findViewById(R.id.datetxt);
        username = (TextInputEditText)findViewById(R.id.username_search);
        ChooseUser = (RadioGroup)findViewById(R.id.UserPreview);
        alltimecheck = (CheckBox) findViewById(R.id.alltimecheckBox);
        search = (Button) findViewById(R.id.confirmbtn);
        recyclerView = (RecyclerView) findViewById(R.id.orders_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        storageReference = FirebaseStorage.getInstance().getReference();

        username.setEnabled(false);
        DateString.setEnabled(true);

        ///////////////////////////////////////////////////
        //choose (1) specific date / (2) all time
        alltimecheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //All time
                if(alltimecheck.isChecked()){
                    DateString.setEnabled(false);
                    alldates=true;specificdate=false;
                }
                //Specific date
                else {
                    DateString.setEnabled(true);
                    specificdate=true;alldates=false;
                }
            }
        });
        //choose (1) specific user / (2) all users
        ChooseUser.setOnCheckedChangeListener(this);
        ///////////////////////////////////////////////////

        //String getDate = DateString.getText().toString();
        //senario(1) --> specific date
        //subsenario<1> --> specific user
        //subsenario<1> --> all user

        //senario(2)--> all times
        //subsenario <1> --> specific user
        //String choosedUser = username.getText().toString();
        //subsenario <2> --> all user
        //get all sold data from firebase
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(alldates){
                    //subsenario <2> --> all user
                    if(allusers){
                        //get all sold data from firebase
                        //1.get users
                        users = new ArrayList<>();
                        DatabaseReference databaseReference1 = FirebaseDatabase
                                .getInstance().getReference().child("Sold");
                        databaseReference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot datax: snapshot.getChildren())
                                {
                                    String userName = datax.getKey();
                                    users.add(userName);
                                    //2.get products
                                }
                                recyclerView.setAdapter(new OrderAdapter(getApplicationContext() ,users,"no",Report.this));
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                    //subsenario <2> --> specific user
                    else{
                        String user = username.getText().toString().toLowerCase(Locale.ROOT);
                        users = new ArrayList<>();
                        DatabaseReference databaseReference1 = FirebaseDatabase
                                .getInstance().getReference().child("Sold");
                        databaseReference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot datax: snapshot.getChildren())
                                {
                                    String userName = datax.getKey().toLowerCase(Locale.ROOT);
                                    if(user.contains(userName)){
                                        users.add(userName);
                                    //2.get products
                                    }
                                }
                                recyclerView.setAdapter(new OrderAdapter(getApplicationContext() ,users,"no" ,Report.this));
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                }
                //specific date
                else{
                    String date = DateString.getText().toString();
                    users = new ArrayList<>();
                    DatabaseReference databaseReference1 = FirebaseDatabase
                            .getInstance().getReference().child("Sold");
                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot datax: snapshot.getChildren())
                            {
                                String userName = datax.getKey();
                                users.add(userName);
                                //2.get products
                            }
                            recyclerView.setAdapter(new OrderAdapter(getApplicationContext(),users,date,Report.this));
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
                }
            }
        });
    }

    /*private void setupRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.orders_recycler);
        orderAdminAdapter = new OrderAdminAdapter(this, new ArrayList<Order>());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(orderAdminAdapter);
    }*/
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.specificuser:
                username.setEnabled(true);
                specificuser=true;allusers=false;
                break;
            case R.id.allusers:
                username.setEnabled(false);
                allusers=true;specificuser=false;
                break;
        }
    }

    @Override
    public void onItemClick(int pos) {

    }
}