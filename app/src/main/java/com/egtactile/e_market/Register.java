package com.egtactile.e_market;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Register extends AppCompatActivity {
    EditText Name;
    EditText Email;
    EditText Phone;
    EditText Password;
    Button Register;
    FirebaseAuth auth;
    DatabaseReference reference;
    CalendarView calendarView;
    FirebaseUser firebaseUser;
    int day , month , year;
    Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Name = findViewById(R.id.editTextTextUsername);
        Email = findViewById(R.id.editTextRegisterEmailAddress);
        Phone = findViewById(R.id.editTextRegisterPhone);
        Password = findViewById(R.id.editTextRegisterPassword);
        Register = findViewById(R.id.ButtonRegister);
        calendarView = findViewById(R.id.calendar);
        auth = FirebaseAuth.getInstance();
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
        calendar = Calendar.getInstance();


        calendar.set(Calendar.MONTH, Calendar.NOVEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 9);
        calendar.set(Calendar.YEAR, 2012);


        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.YEAR, 1);



        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                day = i2;
                month = i1+1;
                year = i;

            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = Name.getText().toString();
                String email = Email.getText().toString();
                String phone = Phone.getText().toString();
                String password = Password.getText().toString();
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            firebaseUser= task.getResult().getUser();
                            DatabaseReference newUser = reference.child(firebaseUser.getUid());
                           // String id = newUser.getKey();
                           // Log.i(TAG, "onComplete: ID " + id);
                            newUser.child("Full Name").setValue(name);
                            newUser.child("Email").setValue(email);
                            newUser.child("Phone").setValue(phone);
                            newUser.child("Password").setValue(password);
                            newUser.child("bithday").child("day").setValue(day);
                            newUser.child("bithday").child("month").setValue(month);
                            newUser.child("bithday").child("yearl").setValue(year);


                            Intent intent = new Intent(Register.this,Login.class);
                            Toast.makeText(Register.this, "Registration Done", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(Register.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });
    }
}