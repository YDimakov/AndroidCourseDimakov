package com.example.myproject.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myproject.R;

import static com.example.myproject.Activity.RegistrationUserActivity.APP_PREFERENCES;
import static com.example.myproject.Activity.RegistrationUserActivity.APP_PREFERENCES_NAME;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        SharedPreferences mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        ImageButton personId = findViewById(R.id.personId);
        TextView personName = findViewById(R.id.namePerson);
        String nameUser = mSettings.getString(APP_PREFERENCES_NAME, null);
        personName.setText(nameUser);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        buttonLostAnimals();
        personId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PersonalAreaUserActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.addAnimal) {
            Toast.makeText(this, "О программе", Toast.LENGTH_SHORT).show();
            startIntentAddAnimal();
        }
        return super.onOptionsItemSelected(item);
    }

    private void startIntentAddAnimal() {
        Intent intent = new Intent(this, InfoProgramActivity.class);
        startActivity(intent);
    }

    private void buttonLostAnimals() {
        Button lost = findViewById(R.id.lostAnimal);
        final Intent intent = new Intent(this, ListActivity.class);
        lost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }

}