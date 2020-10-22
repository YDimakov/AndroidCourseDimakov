package com.example.myproject.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.myproject.Activity.RegistrationUserActivity.APP_PREFERENCES;

public class InputUserActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText loginEditText;
    private EditText passwordEditText;
    private FirebaseAuth firebaseAuth;
    private String email;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        firebaseAuth = FirebaseAuth.getInstance();
        Button inputButton = findViewById(R.id.inputButton);
        Button registrationButton = findViewById(R.id.registrationButton);
        Button forgottenPasswordButton = findViewById(R.id.forgottenPasswordButton);
        SharedPreferences mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        loginEditText = findViewById(R.id.inputLogin);
        passwordEditText = findViewById(R.id.inputPassword);
        email = mSettings.getString("email", null);
        loginEditText.setText(email);
        inputButton.setOnClickListener(this);
        registrationButton.setOnClickListener(this);
        forgottenPasswordButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.inputButton:
                String login = loginEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                Log.d("1", email + " " + password);
                input(login, password);
                break;
            case R.id.registrationButton:
                Intent intent = new Intent(this, RegistrationUserActivity.class);
                startActivity(intent);
                break;
            case R.id.forgottenPasswordButton:
                Toast.makeText(this, "Раздел недоступен!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void input(String log, String pass) {
        if (TextUtils.isEmpty(log)) {
            Toast.makeText(getApplicationContext(),
                    "Введите почту!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(getApplicationContext(),
                    "Введите пароль!!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        firebaseAuth.signInWithEmailAndPassword(log, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(InputUserActivity.this, "Вход успешно выполнен",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(InputUserActivity.this,
                                    MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(InputUserActivity.this, "Неверный логин или пароль",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
