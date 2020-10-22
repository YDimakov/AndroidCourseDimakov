package com.example.myproject.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegistrationUserActivity extends AppCompatActivity {
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_EMAIL = "email";
    public static final String APP_PREFERENCES_NAME = "name";
    public static final String APP_PREFERENCES_PHONE = "phone";
    private FirebaseAuth firebaseAuth;
    private EditText registrationEmail;
    private EditText registrationName;
    private EditText registrationPhone;
    private EditText passwordRegistrationIntegerOne;
    private EditText passwordRegistrationIntegerTwo;
    private Button registrationButton;
    private ProgressBar progressbarRegistrationUser;
    private SharedPreferences mSettings;
    private String emailRegistration;
    private String passwordOne;
    private String passwordTwo;
    private String nameRegistration;
    private String phoneRegistration;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.registration_user);
        firebaseAuth = FirebaseAuth.getInstance();
        registrationEmail = findViewById(R.id.registrationEmail);
        registrationName = findViewById(R.id.registrationName);
        registrationPhone = findViewById(R.id.registrationPhone);
        progressbarRegistrationUser = findViewById(R.id.progressbarRegistrationUser);
        passwordRegistrationIntegerOne = findViewById(R.id.registrationPassword1);
        passwordRegistrationIntegerTwo = findViewById(R.id.registrationPassword2);
        registrationButton = findViewById(R.id.registrationButton);
        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailRegistration = registrationEmail.getText().toString().trim();
                passwordOne = passwordRegistrationIntegerOne.getText().toString().trim();
                passwordTwo = passwordRegistrationIntegerTwo.getText().toString().trim();
                nameRegistration = registrationName.getText().toString().trim();
                phoneRegistration = registrationPhone.getText().toString().trim();
                loginSignUpUser(emailRegistration, passwordOne, passwordTwo, nameRegistration, phoneRegistration);
            }
        });
    }

    private void loginSignUpUser(final String email, final String passwordOne, final String passwordTwo, final String name, final String phone) {
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getApplicationContext(),
                    "Введите имя!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(),
                    "Введите почту!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(getApplicationContext(),
                    "Введите номер телефона!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(passwordOne)) {
            Toast.makeText(getApplicationContext(),
                    "Введите пароль!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (TextUtils.isEmpty(passwordTwo)) {
            Toast.makeText(getApplicationContext(),
                    "Введите подтверждение пароля!",
                    Toast.LENGTH_LONG)
                    .show();
            return;
        }
        if (passwordOne.equals(passwordTwo)) {
            firebaseAuth
                    .createUserWithEmailAndPassword(email, passwordOne)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Вы успешно зарегистрированны",
                                        Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(RegistrationUserActivity.this,
                                        InputUserActivity.class);
                                SharedPreferences.Editor editor = mSettings.edit();
                                editor.putString(APP_PREFERENCES_EMAIL, email);
                                editor.putString(APP_PREFERENCES_NAME, name);
                                editor.putString(APP_PREFERENCES_PHONE, phone);
                                editor.apply();
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Регистрация не прошла!!"
                                        + " Пожалуйста попробуйте сново!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "Введенные пароли не совпадают", Toast.LENGTH_LONG).show();
        }
    }
}

