package com.example.fablabcorp;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.*;

import java.io.IOException;

public class LockActivity extends AppCompatActivity {

    private static final String URL = "https://8f5c7810-61e7-476a-80c7-31a5dfb3ed93.mock.pstmn.io/Cadenas?autorise";
    private Button buttonOpenLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);

        buttonOpenLock = findViewById(R.id.button10);
        buttonOpenLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    openLock();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void openLock() throws Exception {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\n\"id\": 1,\n\"email\": \"jojo@gmail.com\",\n\"password\": \"azerty12\",\n\"role\": \"Member\"\n}");
        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful() && response.body() != null) {
                    final String myResponse = response.body().string();

                    LockActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO: Ajoutez ici la logique pour gérer la réponse positive
                            Toast.makeText(LockActivity.this, "Cadenas ouvert: " + myResponse, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call call, IOException e) {
                LockActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO: Ajoutez ici la logique pour gérer la réponse négative
                        Toast.makeText(LockActivity.this, "Échec de la connexion", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
}
