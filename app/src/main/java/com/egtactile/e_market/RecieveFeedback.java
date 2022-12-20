package com.egtactile.e_market;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

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

public class RecieveFeedback extends AppCompatActivity implements RecyclerViewInterface{
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    FirebaseUser user;
    StorageReference storageReference;
    List<Feedback> feedbackList = new ArrayList<Feedback>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieve_feedback);

        user = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Feedback");
        storageReference = FirebaseStorage.getInstance().getReference();

        recyclerView = findViewById(R.id.FeedbackList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                feedbackList = new ArrayList<Feedback>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    String feedback = dataSnapshot.child("Feedback").getValue().toString();
                    String username = dataSnapshot.getKey().toString();
                    Float rate = Float.parseFloat(dataSnapshot.child("Rate").getValue().toString());

                    Log.i(TAG, "onDataChange: User" + username);
                    feedbackList.add(new Feedback(feedback , rate , username));


                }

                recyclerView.setAdapter(new FeedbackAdapter(RecieveFeedback.this , feedbackList , RecieveFeedback.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    public void onItemClick(int pos) {

    }
}