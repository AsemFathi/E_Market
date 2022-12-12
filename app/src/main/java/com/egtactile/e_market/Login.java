package com.egtactile.e_market;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;

public class Login extends AppCompatActivity {
    EditText Email;
    EditText Password;
    Button login;
    Button SignUp;
    Button Forget;
    FirebaseAuth auth;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Email =findViewById(R.id.editTextTextEmailAddress);
        Password =findViewById(R.id.editTextTextPassword);
        login = findViewById(R.id.lgn_btn);
        auth = FirebaseAuth.getInstance();
        checkBox = findViewById(R.id.remember);

        Paper.init(this);

        SignUp = findViewById(R.id.SignUP);
        Forget = findViewById(R.id.forget_password);

        String eemail = Paper.book().read("UserEmail");
        String Ppassword = Paper.book().read("UserPassword");

        if (eemail != "" && Ppassword !="")
        {
            if (!TextUtils.isEmpty(eemail) && !TextUtils.isEmpty(Ppassword))
            {
                Email.setText(eemail);
                Password.setText(Ppassword);
                checkBox.setChecked(true);
            }
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String email = Email.getText().toString();
                String password = Password.getText().toString();
                if (!email.isEmpty() && !password.isEmpty()) {
                    login(email , password);
                    if(checkBox.isChecked())
                    {
                        Paper.book().write("UserEmail",email);
                        Paper.book().write("UserPassword",password);
                    }
                }
            }
        });

        Forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this , forgetPass.class);
                startActivity(intent);
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this , Register.class);
                startActivity(intent);
            }
        });

    }
    private void login (String email, String password)
    {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Login.this, "Login Done", Toast.LENGTH_SHORT).show();
                    if (email.equals("asem12@gmail.com"))
                    {
                        Intent intent = new Intent(Login.this , Admin_Page.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Intent intent = new Intent(Login.this, Home.class);
                        startActivity(intent);
                    }
                } else
                    Toast.makeText(Login.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

}