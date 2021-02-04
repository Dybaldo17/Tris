package com.carrabba.tris;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registrazione extends AppCompatActivity {

    private EditText nome;
    private EditText cognome;
    private EditText email;
    private EditText password;
    private Button registrazione;
    private TextView login;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrazione);

        mAuth = FirebaseAuth.getInstance();

        nome = findViewById(R.id.txtNome);
        cognome = findViewById(R.id.txtCognome);
        email = findViewById(R.id.txtEmail);
        password = findViewById(R.id.txtPassword);
        registrazione = findViewById(R.id.btnRegistrazione);
        login = findViewById(R.id.txtRegistrato);

        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if(mAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), GiocoActivity.class));
            finish();
        }

        registrazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtEmail = email.getText().toString().trim();
                String txtPassword = password.getText().toString().trim();

                if (TextUtils.isEmpty(txtEmail)) {
                    email.setError("Email obbligatoria");
                    return;
                }

                if (TextUtils.isEmpty(txtPassword)) {
                    password.setError("Password obbligatoria");
                    return;
                }

                if (txtPassword.length() < 6) {
                    password.setError("La password deve essere di almeno 6 caraterri");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(txtEmail, txtPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Registrazione.this, "Utente creato", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), GiocoActivity.class));
                            finish();
                        } else {
                            Toast.makeText(Registrazione.this, "Errore!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });
    }

}