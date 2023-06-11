package com.example.booklibrary.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.booklibrary.MainActivity;
import com.example.booklibrary.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends AppCompatActivity {

    private FirebaseAuth fireBaseAuth;
    private Intent firstPage;
    private EditText etEmail;
    private EditText etPassword;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = fireBaseAuth.getCurrentUser();
        if (currentUser != null) {
            startActivity(firstPage);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView tvRegistration;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        FirebaseApp.initializeApp(AuthActivity.this);

        Button loginButton = findViewById(R.id.logInButton);
        etEmail = findViewById(R.id.emailTextEdit);
        etPassword = findViewById(R.id.editTextPassword);
        tvRegistration = (TextView) findViewById(R.id.textViewRegistation);

        firstPage = new Intent(this, MainActivity.class);
        Intent registrationPage = new Intent(this, RegistrationActivity.class);

        fireBaseAuth = FirebaseAuth.getInstance();
        tvRegistration.setOnClickListener(view -> startActivity(registrationPage));

        loginButton.setOnClickListener(view -> loginUser());
    }

    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Почта не может быть пустой");
            etEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            etPassword.setError("Пароль не может быть пустым");
            etPassword.requestFocus();
        } else {
            fireBaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            startActivity(firstPage);
                        } else if (task.isComplete()) {
                            etPassword.setError("Неправильный пароль или логин");
                        }
                    }
            );
        }
    }
}