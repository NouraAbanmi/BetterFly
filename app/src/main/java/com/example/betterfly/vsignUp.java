package com.example.betterfly;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class vsignUp extends AppCompatActivity implements View.OnClickListener {

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

        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
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
                //Log.d(Tag,"onDateSet: dd/mm/yyyy:"+ dayOfMonth+"/"+month+"/"+year);

                String date=dayOfMonth+"/"+month+"/"+year;
                editTextDoB.setText(date);

            }
        };


    }
    private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String repaetPassword = editTextRepeatPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();

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
