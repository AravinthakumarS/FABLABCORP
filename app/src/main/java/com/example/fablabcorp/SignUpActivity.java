package com.example.fablabcorp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword, editTextConfirmPassword, editTextUsername;
    private Button signUpButton;
    private TextView loginLink;

    // Remplacez ceci par votre URL de serveur mock fournie par Postman.
    private final String url = "https://46b15cfb-cbb4-459e-b200-e0252f8636ca.mock.pstmn.io/createUser";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialiser les composants de la vue
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        signUpButton = findViewById(R.id.button2);
        loginLink = findViewById(R.id.textView2);

        // Définir l'écouteur du bouton d'inscription
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSignUp();
            }
        });

        // Ecouteur pour le lien de connexion
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remplacez ceci par l'intention de démarrer votre activité de connexion
                // Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                // startActivity(intent);
            }
        });
    }

    private void attemptSignUp() {
        String username = editTextUsername.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String confirmPassword = editTextConfirmPassword.getText().toString();

        if (!isEmailValid(email)) {
            showToast("L'email n'est pas valide.");
            return;
        }

        if (!isPasswordValid(password)) {
            showToast("Le mot de passe n'est pas valide.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showToast("Les mots de passe ne correspondent pas.");
            return;
        }

        if (username.isEmpty()) {
            showToast("Le nom d'utilisateur ne peut pas être vide.");
            return;
        }

        // Créer le JSON pour la requête POST
        JSONObject json = new JSONObject();
        try {
            json.put("email", email);
            json.put("password", password);
            json.put("name", username);
            json.put("role", "Agent"); // Remplacez par la logique de votre rôle si nécessaire
        } catch (JSONException e) {
            showToast("Erreur lors de la création de la requête JSON.");
            return;
        }

        // Créer et exécuter la requête POST
        postRequest(url, json);
    }

    private void postRequest(String url, JSONObject json) {
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                showToast("Échec de la connexion au serveur: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    showToast("Inscription réussie !");
                } else {
                    showToast("Inscription échouée: " + response.message());
                }
            }
        });
    }

    private void showToast(final String text) {
        new Handler(Looper.getMainLooper()).post(() ->
                Toast.makeText(SignUpActivity.this, text, Toast.LENGTH_LONG).show());
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 6;
    }
}