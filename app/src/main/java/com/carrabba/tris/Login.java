package com.carrabba.tris;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText email;
    EditText password;
    ProgressBar progressBar;
    FirebaseAuth database;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        email = findViewById(R.id.txtEmail);
        password = findViewById(R.id.txtPassword);
        progressBar = findViewById(R.id.progressBar);
        database = FirebaseAuth.getInstance();
        login = findViewById(R.id.btnRegistrazione);
    }
}