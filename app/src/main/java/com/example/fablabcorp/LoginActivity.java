package com.example.fablabcorp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button loginButton;
    private TextView signUpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.button2);
        signUpText = findViewById(R.id.textView3);

        loginButton.setOnClickListener(view -> attemptLogin());

        signUpText.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    private void attemptLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Email or password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }
        performLoginRequest(email, password);
    }

    private void performLoginRequest(String email, String password) {
        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("https://46b15cfb-cb64-459e-b200-e0252f8636ca.mock.pstmn.io/connexion").newBuilder();
        urlBuilder.addQueryParameter("email", email);
        urlBuilder.addQueryParameter("mdp", password);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Login failed: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    Gson gson = new Gson();
                    Utilisateur utilisateur = gson.fromJson(responseData, Utilisateur.class);

                    runOnUiThread(() -> {
                        Intent intent;
                        switch (utilisateur.getRole()) {
                            case "Responsable Agent":
                                intent = new Intent(LoginActivity.this, AdministratorActivity.class);
                                break;
                            case "Agent":
                                intent = new Intent(LoginActivity.this, MemberActivity.class);
                                break;
                            default:
                                Toast.makeText(LoginActivity.this, "Role non reconnu", Toast.LENGTH_SHORT).show();
                                return;
                        }
                        startActivity(intent);
                        Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(LoginActivity.this, "Login failed with code: " + response.code(), Toast.LENGTH_SHORT).show());
                }
                response.close(); // Important to close the response after use
            }
        });
    }

    private class Utilisateur {
        private int id;
        private String email;
        private String mdp; // mot de passe
        private String prenom;
        private String nom;
        private String etablissement;
        private String role;

        public String getRole() {
            return role;
        }

        // ... Ajoutez le reste des getters et setters si nécessaire
    }

}
