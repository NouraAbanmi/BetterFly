package com.example.betterfly;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }


    @Override
    public void onClick(View view) {
        //  FirebaseApp.initializeApp(this);
        switch (view.getId()) {
            case R.id.o:
                finish();
                startActivity(new Intent(this, osignUp.class));
                break;

            case R.id.v:
                finish();
                startActivity(new Intent(this, vsignUp.class));
                break;
        }
    }
}
