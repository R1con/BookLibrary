package com.example.booklibrary.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.booklibrary.MainActivity;
import com.example.booklibrary.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText email;
    private EditText pass;
    private EditText confirmPass;
    private Intent firstPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        FirebaseApp firebaseApp = FirebaseApp.initializeApp(RegistrationActivity.this);
        firebaseAuth = FirebaseAuth.getInstance(firebaseApp);
        firstPage = new Intent(this, MainActivity.class);

        Button register = findViewById(R.id.buttonRegister);
        email = findViewById(R.id.editTextRegEmail);
        pass = findViewById(R.id.editTextRegPassword);
        confirmPass = findViewById(R.id.editTextRegConfermPass);
        TextView tv = (TextView) findViewById(R.id.textViewRegLogin);
        tv.setOnClickListener(view -> startActivity(new Intent(this, AuthActivity.class)));

        register.setOnClickListener(view -> registerUser());
    }

    private void registerUser() {
        String emailTxt = email.getText().toString().trim();
        String passwordTxt = pass.getText().toString().trim();
        String confirmPasswordTxt = confirmPass.getText().toString().trim();

        if (TextUtils.isEmpty(emailTxt)) {
            email.setError("Почта не может быть пустой");
            email.requestFocus();
            return;
        }else if (TextUtils.isEmpty(passwordTxt)) {
            pass.setError("Пароль не может пустой");
            pass.requestFocus();
            return;
        } else if (TextUtils.isEmpty(confirmPasswordTxt)) {
            confirmPass.setError("Подтверждение пароля не может быть пустым");
            confirmPass.requestFocus();
            return;
        } else if (!passwordTxt.equals(confirmPasswordTxt)) {
            confirmPass.setError("Пароль не совпадает");
            confirmPass.requestFocus();
            return;
        } else if (passwordTxt.length() < 6) {
            pass.setError("Пароль должен быть больше 6 символов");
            pass.requestFocus();
            return;
        }

        firebaseAuth.createUserWithEmailAndPassword(emailTxt, passwordTxt)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(RegistrationActivity.this, "Регистрация успешна!", Toast.LENGTH_SHORT).show();
                        Intent mainPage = new Intent(RegistrationActivity.this, MainActivity.class);
                        startActivity(mainPage);
                    } else {
                        Toast.makeText(RegistrationActivity.this, "Регистрация не удалась. Повторите попытку!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(firstPage);
        }
    }
}