package com.example.customview;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.customview.view.CustomViewMain;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    private Switch snackBarOrToast;
    private boolean aBoolean;
    private ConstraintLayout constraintLayout;
    private final String KEY_EDITOR = "myKeyForWork";
    private boolean checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aBoolean = loadText();
        constraintLayout = findViewById(R.id.constraintLayout);
        snackBarOrToast = findViewById(R.id.switchSnackBarOrToast);
        switchListener();
        mainCustomView();
    }

    private void saveText(boolean b) {
        SharedPreferences sharedPreferencesSave = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesSave.edit();
        editor.putBoolean(KEY_EDITOR, b);
        editor.apply();
    }

    private boolean loadText() {
        SharedPreferences sharedPreferencesLoad = getPreferences(Context.MODE_PRIVATE);
        return sharedPreferencesLoad.getBoolean(KEY_EDITOR, true);
    }

    private void showSnackBar(int x, int y) {
        Snackbar myAwesomeSnackBar = Snackbar.make(constraintLayout, "Нажаты координаты [" + x + ";" + y + "]", Snackbar.LENGTH_LONG);
        myAwesomeSnackBar.show();
    }

    private void showToast(int x, int y) {
        Toast.makeText(MainActivity.this, "Нажаты координаты [" + x + ";" + y + "]", Toast.LENGTH_SHORT).show();
    }

    private void switchListener() {
        snackBarOrToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checked = ((Switch) view).isChecked();
                if (checked) {
                    saveText(false);
                    aBoolean = true;
                } else {
                    saveText(true);
                    aBoolean = false;
                }
            }
        });
    }

    private void mainCustomView() {
        ((CustomViewMain) findViewById(R.id.customView)).setOnTouchActionListener(new CustomViewMain.onTouchListener() {
            @Override
            public void onTouch(int x, int y) {
                if (aBoolean) {
                    showToast(x, y);
                } else {
                    showSnackBar(x, y);
                }
            }
        });
    }
}