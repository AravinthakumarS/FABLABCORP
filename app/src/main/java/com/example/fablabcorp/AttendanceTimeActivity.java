package com.example.fablabcorp;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class AttendanceTimeActivity extends AppCompatActivity {

    private TextView textPresenceTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attandence_time);

        textPresenceTime = findViewById(R.id.text_presence_time);

        // Appeler la méthode pour récupérer le temps de présence dès que l'activité démarre
        getAttendanceTime();
    }

    private void getAttendanceTime() {
        OkHttpClient client = new OkHttpClient();

        // Construire la requête
        Request request = new Request.Builder()
                .url("https://8f5c7810-61e7-476a-80c7-31a5dfb3ed93.mock.pstmn.io/Presence")
                .build();

        // Asynchrone call
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                // Gestion de l'échec
                e.printStackTrace();
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseData = response.body().string();

                    // Attention: La mise à jour de l'interface utilisateur doit se faire sur le thread principal
                    AttendanceTimeActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Mettre à jour l'interface utilisateur avec les données de réponse
                            textPresenceTime.setText("Temps de présence total : " + responseData + " min");
                        }
                    });
                }
            }
        });
    }
}
