package com.example.betterfly;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class osignUp extends AppCompatActivity implements View.OnClickListener {

    ProgressBar progressBar;
    EditText editTextEmail, editTextPassword, editTextRepeatPassword, editTextIdApproval;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_osign_up);

        editTextEmail = (EditText) findViewById(R.id.email_signup);
        editTextPassword = (EditText) findViewById(R.id.password_signup);
        editTextIdApproval = (EditText) findViewById(R.id.Aproval_ID);
        //progressBar = (ProgressBar) findViewById(R.id.progressbar);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.sign_up).setOnClickListener(this);
        //findViewById(R.id.textViewLogin).setOnClickListener(this);

    }

    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String repaetPassword = editTextRepeatPassword.getText().toString().trim();
        String approvalId = editTextIdApproval.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }


        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }


        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Minimum length of password should be 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        if (repaetPassword.isEmpty()) {
            editTextRepeatPassword.setError("Re-write your password");
            editTextRepeatPassword.requestFocus();
            return;
        }

        if (!password.equals(repaetPassword)) {
            editTextPassword.setError("Your password does not match");
            editTextPassword.setText("");
            editTextRepeatPassword.setText("");
            return;
        }


        if (approvalId.isEmpty()) {
            editTextIdApproval.setError("Approval ID is required");
            editTextIdApproval.requestFocus();
            return;
        }


        if (approvalId.length() < 4) {
            editTextIdApproval.setError("Minimum length of approval ID should be 4 characters");
            editTextIdApproval.requestFocus();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    finish();
                    startActivity(new Intent(osignUp.this, MainActivity.class));
                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(getApplicationContext(), "You are already registered", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_up:
                registerUser();
                break;

        }
    }
}