package com.example.fablabcorp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private Button buttonResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonResetPassword = findViewById(R.id.button7);

        buttonResetPassword.setOnClickListener(view -> resetPassword());
    }

    private void resetPassword() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(ForgotPasswordActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(ForgotPasswordActivity.this, "Les mots de passe ne correspondent pas", Toast.LENGTH_SHORT).show();
            return;
        }

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"email\":\"" + email + "\",\"password\":\"" + password + "\"}");

        Request request = new Request.Builder()
                .url("https://5d71313e-8972-40f3-82bb-374f96d9a8a1.mock.pstmn.io")
                .put(body)
                .build();

        new Thread(() -> {
            try {
                Response response = client.newCall(request).execute();
                String responseData = response.body().string();
                runOnUiThread(() -> {
                    if (response.isSuccessful()) {
                        Toast.makeText(ForgotPasswordActivity.this, "Mot de passe réinitialisé avec succès", Toast.LENGTH_SHORT).show();
                        // Retourner à LoginActivity après la réinitialisation réussie
                        Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish(); // Fin de cette activité pour ne pas revenir en arrière dans l'historique
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "Échec de la réinitialisation du mot de passe", Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (IOException e) {
                runOnUiThread(() -> Toast.makeText(ForgotPasswordActivity.this, "Erreur: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }).start();
    }
}
