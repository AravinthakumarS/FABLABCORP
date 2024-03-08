package com.example.fablabcorp;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private EditText editTextPassword;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        executorService = Executors.newSingleThreadExecutor();

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = editTextEmail.getText().toString();
                final String password = editTextPassword.getText().toString();

                // Exécuter la tâche dans un autre thread
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        final String result = performLogin(email, password);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                handleLoginResult(result);
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdown();
        }
    }

    private String performLogin(String email, String password) {
        try {
            URL url = new URL("https://57d1313e-8972-40f3-82bb-3749f6d90a81.mock.pstmn.io/login?user=" + email + "&pass=" + password);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            } else {
                return "Échec de la requête : " + responseCode;
            }
        } catch (Exception e) {
            return "Exception: " + e.getMessage();
        }
    }

    private void handleLoginResult(String result) {
        try {
            JSONObject response = new JSONObject(result);
            String message = response.getString("message");
            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(LoginActivity.this, "Erreur lors du parsing du résultat : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}