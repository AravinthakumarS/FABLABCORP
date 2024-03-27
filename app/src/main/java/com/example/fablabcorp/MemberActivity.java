package com.example.fablabcorp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MemberActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        // Trouvez la CardView pour le cadenas dans le layout
        CardView cardLock = findViewById(R.id.card2);
        // Trouvez la CardView pour le temps de présence dans le layout
        CardView cardAttendanceTime = findViewById(R.id.card3); // Assurez-vous que R.id.card3 est le bon ID

        // Configurez l'écouteur de clic pour démarrer LockActivity
        cardLock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créez l'intention de démarrer LockActivity
                Intent lockIntent = new Intent(MemberActivity.this, LockActivity.class);
                startActivity(lockIntent);
            }
        });

        // Configurez l'écouteur de clic pour démarrer AttendanceTimeActivity
        cardAttendanceTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Créez l'intention de démarrer AttendanceTimeActivity
                Intent attendanceTimeIntent = new Intent(MemberActivity.this, AttendanceTimeActivity.class);
                startActivity(attendanceTimeIntent);
            }
        });
    }
}
