package com.example.myproject.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myproject.R;

import static com.example.myproject.Activity.RegistrationUserActivity.APP_PREFERENCES;
import static com.example.myproject.Activity.RegistrationUserActivity.APP_PREFERENCES_NAME;
import static com.example.myproject.Activity.RegistrationUserActivity.APP_PREFERENCES_PHONE;

public class PersonalAreaUserActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_area_user);
        TextView personName = findViewById(R.id.userName);
        TextView personPhone = findViewById(R.id.userPhone);
        Button buttonAdd = findViewById(R.id.addAnimalPersonalArea);
        Button buttonDelete = findViewById(R.id.deleteAnimalPersonalArea);
        SharedPreferences mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String name = mSettings.getString(APP_PREFERENCES_NAME, null);
        String phone = mSettings.getString(APP_PREFERENCES_PHONE, null);
        personName.setText(name);
        personPhone.setText(phone);
        buttonAdd.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addAnimalPersonalArea:
               Intent intent = new Intent(PersonalAreaUserActivity.this,AddAnimalActivity.class);
               startActivity(intent);
               finish();
                break;
            case R.id.deleteAnimalPersonalArea:
                Toast.makeText(this, "Раздел недоступен", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
