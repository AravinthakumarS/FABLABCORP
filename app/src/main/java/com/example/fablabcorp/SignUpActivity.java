package com.example.fablabcorp;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialiser le service d'exécuteur pour les opérations en arrière-plan
        executorService = Executors.newSingleThreadExecutor();

        // Récupérer les éléments de l'interface utilisateur par leur ID
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);

        // Définir un écouteur de clic pour le bouton d'inscription
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = editTextUsername.getText().toString();
                final String email = editTextEmail.getText().toString();
                final String password = editTextPassword.getText().toString();
                final String confirmPassword = editTextConfirmPassword.getText().toString();

                // Valider les entrées avant de procéder à l'inscription
                if (validateInput(username, email, password, confirmPassword)) {
                    // Exécuter l'inscription en arrière-plan
                    executeSignUp(username, email, password);
                } else {
                    // Afficher un message si la validation échoue
                    Toast.makeText(SignUpActivity.this, "La validation a échoué", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Arrêter le service d'exécuteur lors de la destruction de l'activité
        executorService.shutdownNow();
    }

    private boolean validateInput(String username, String email, String password, String confirmPassword) {
        // Ajouter ici la logique de validation
        // Exemple : vérifier si le mot de passe et la confirmation du mot de passe correspondent
        // et que l'email a un format valide
        return !username.isEmpty() && !email.isEmpty() && password.equals(confirmPassword);
    }

    private void executeSignUp(String username, String email, String password) {
        executorService.execute(() -> {
            HttpURLConnection conn = null;
            try {
                URL url = new URL("https://57d1313e-8972-40f3-82bb-3749f6d90a81.mock.pstmn.io/inscription");
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                // Construire le corps de la requête JSON
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("user", username);
                jsonParam.put("password", password);
                jsonParam.put("email", email);

                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonParam.toString().getBytes(StandardCharsets.UTF_8);
                    os.write(input, 0, input.length);
                }

                // Lire la réponse du serveur
                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
                    String responseLine;
                    StringBuilder response = new StringBuilder();
                    while ((responseLine = reader.readLine()) != null) {
                        response.append(responseLine.trim());
                    }

                    // Traiter la réponse sur le fil d'exécution de l'interface utilisateur
                    String finalResponse = response.toString();
                    runOnUiThread(() -> handleSignUpResult(finalResponse));
                } else {
                    throw new Exception("Réponse non réussie du serveur : " + responseCode);
                }
            } catch (Exception e) {
                runOnUiThread(() -> Toast.makeText(SignUpActivity.this, "Erreur lors de l'inscription : " + e.getMessage(), Toast.LENGTH_LONG).show());
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        });
    }

    private void handleSignUpResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            String message = response.getString("message");
            Toast.makeText(SignUpActivity.this, message, Toast.LENGTH_LONG).show();
            // Rediriger vers LoginActivity ou une autre activité après une inscription réussie
        } catch (Exception e) {
            Toast.makeText(SignUpActivity.this, "Error parsing the response: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
