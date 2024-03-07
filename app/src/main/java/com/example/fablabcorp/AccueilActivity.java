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
        setContentView(R.layout.activity_accueil); // Utilisez votre fichier de layout activity_accueil

        // Initialisation des boutons en utilisant les ID fournis dans votre layout
        btnLogin = findViewById(R.id.button4);
        btnSignUp = findViewById(R.id.button5);

        // Écouteur pour le bouton Login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent pour ouvrir LoginActivity
                // Remplacez LoginActivity.class par votre activité de connexion
                Intent loginIntent = new Intent(AccueilActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        // Écouteur pour le bouton Sign Up
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent pour ouvrir SignUpActivity
                // Remplacez SignUpActivity.class par votre activité d'inscription
                Intent signUpIntent = new Intent(AccueilActivity.this, SignUpActivity.class);
                startActivity(signUpIntent);
            }
        });
    }
}