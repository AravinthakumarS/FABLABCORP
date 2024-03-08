package com.example.fablabcorp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;


public class AccueilActivity extends AppCompatActivity {

    private Button btnLogin;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        btnLogin = findViewById(R.id.button4);
        btnSignUp = findViewById(R.id.button5);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(AccueilActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent signUpIntent = new Intent(AccueilActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });
    }
}