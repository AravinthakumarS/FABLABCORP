package com.example.fablabcorp;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    private EditText userEdt, passEdt;
    private Button loginBtn;
    private TextView signUpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        userEdt = findViewById(R.id.editTextEmail); // ID pour le champ E-mail
        passEdt = findViewById(R.id.editTextPassword); // ID pour le champ Password
        loginBtn = findViewById(R.id.button2); // ID pour le bouton Log in
        signUpText = findViewById(R.id.textView3); // ID pour le texte "Sign up"

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userEdt.getText().toString();
                String password = passEdt.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Please Fill your user and password", Toast.LENGTH_SHORT).show();
                } else if (username.equals("test") && password.equals("test")) {
                    startActivity(new Intent(LoginActivity.this, MemberActivity.class));
                } else if (username.equals("toto") && password.equals("toto")) {
                    startActivity(new Intent(LoginActivity.this, AdministratorActivity.class));
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        signUpText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }
}
