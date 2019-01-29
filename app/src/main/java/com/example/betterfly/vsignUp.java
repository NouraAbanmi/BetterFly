package com.example.betterfly;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class vsignUp extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "vsignUp";
    private ProgressBar progressBar;
    EditText editTextEmail, editTextPassword , editTextRepeatPassword , editTextName, editTextDoB;
    DatePickerDialog.OnDateSetListener datePickerDoB;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vsign_up);

        editTextEmail = (EditText) findViewById(R.id.email_signup);
        editTextPassword = (EditText) findViewById(R.id.password_signup);
        editTextName = (EditText) findViewById(R.id.name);
        editTextDoB = (EditText) findViewById(R.id.DoB);

        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.sign_up).setOnClickListener(this);
        editTextDoB.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(
                        vsignUp.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        datePickerDoB,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        datePickerDoB= new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Log.d(TAG,"onDateSet: dd/mm/yyyy:"+ dayOfMonth+"/"+month+"/"+year);

                String date=dayOfMonth+"/"+month+"/"+year;
                editTextDoB.setText(date);

            }
        };


    }
    private void registerUser(){
        final String email = editTextEmail.getText().toString().trim();

       final String password = editTextPassword.getText().toString().trim();
        String repaetPassword = editTextRepeatPassword.getText().toString().trim();
        final String name = editTextName.getText().toString().trim();
        final Date DoB= (Date) datePickerDoB;
        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }


        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }


        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if(password.length()<6){
            editTextPassword.setError("Minimum length of password should be 6 characters");
            editTextPassword.requestFocus();
            return;
        }

        if(name.isEmpty()){
            editTextName.setError("Name is required");
            editTextName.requestFocus();
            return;
        }


        if(name.isEmpty()){
            editTextName.setError("Please enter your name");
            editTextName.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    Volunteer volUser = new Volunteer(
                            name,
                            email,
                            DoB

                    );

                    FirebaseDatabase.getInstance().getReference("Volunteer")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(volUser).addOnCompleteListener(new OnCompleteListener<Void>() {

                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressBar.setVisibility(View.GONE);
                            if (task.isSuccessful()) {
                                Toast.makeText(vsignUp.this, getString(R.string.registration_success), Toast.LENGTH_LONG).show();
                                finish();
                                Intent intent = new Intent(vsignUp.this, OrgProcessActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);

                            } else {
                                //display a failure message
                                Toast.makeText(vsignUp.this, getString(R.string.registration_success), Toast.LENGTH_LONG).show();
                            }


                        }
                    });
                }
                else {
                    Toast.makeText(vsignUp.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }

            }

        });
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.sign_up:
                registerUser();
                break;

        }
    }

}
