package com.egtactile.e_market;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedbackActivity extends AppCompatActivity {
Button Save;
EditText Feedback;
RatingBar ratingBar;
FirebaseUser user;
DatabaseReference databaseReference;
String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Save = findViewById(R.id.Save_btn);
        Feedback = findViewById(R.id.FeedbackEditText);
        ratingBar = findViewById(R.id.simpleRatingBar);
        user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Feedback");
        email = user.getEmail();
        email = email.replaceAll("@gmail.com" , "");

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String rating = String.valueOf(ratingBar.getRating());
                String feedback = Feedback.getText().toString();

                DatabaseReference newData = databaseReference.child(email);
                newData.child("Feedback").setValue(feedback);
                newData.child("Rate").setValue(rating);

            }
        });


    }
}