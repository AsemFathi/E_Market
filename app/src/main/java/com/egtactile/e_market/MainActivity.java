package com.egtactile.e_market;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    Button Login;
    Button Signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Login = findViewById(R.id.Login);
        Signup = findViewById(R.id.Signup);

        Paper.init(this);
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Register.class);
                startActivity(intent);
            }
        });

    }
      /*  String email = Paper.book().read("UserEmail");
        String password = Paper.book().read("UserPassword");

        if (email != "" && password !="")
        {
            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password))
            {
                login(email , password);
            }
        }
    }
    private void login (String email, String password)
    {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Login Done", Toast.LENGTH_SHORT).show();
                    if (email.equals("asem12@gmail.com")) {
                        Intent intent = new Intent(MainActivity.this, Admin_Page.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(MainActivity.this, Home.class);
                        startActivity(intent);
                    }
                } else
                    Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }*/
}
