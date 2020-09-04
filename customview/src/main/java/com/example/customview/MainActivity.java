package com.example.customview;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.customview.view.CustomViewMain;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private final String KEY_EDITOR = "myKeyForWork";
    private Switch snackBarOrToast;
    private ConstraintLayout constraintLayout;
    private boolean aBoolean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aBoolean = loadBooleanValue();
        constraintLayout = findViewById(R.id.constraintLayout);
        snackBarOrToast = findViewById(R.id.switchSnackBarOrToast);
        switchListener();
        mainCustomView();
    }

    private void saveBooleanValue(boolean b) {
        SharedPreferences sharedPreferencesSave = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesSave.edit();
        editor.putBoolean(KEY_EDITOR, b);
        editor.apply();
    }

    private boolean loadBooleanValue() {
        SharedPreferences sharedPreferencesLoad = getPreferences(Context.MODE_PRIVATE);
        return sharedPreferencesLoad.getBoolean(KEY_EDITOR, true);
    }

    private void showSnackBar(int x, int y) {
        Snackbar myAwesomeSnackBar = Snackbar.make(constraintLayout, getString(R.string.show_text_on_screen, x, y), Snackbar.LENGTH_LONG);
        myAwesomeSnackBar.show();
    }

    private void showToast(int x, int y) {
        Toast.makeText(MainActivity.this, getString(R.string.show_text_on_screen, x, y), Toast.LENGTH_SHORT).show();
    }

    private void switchListener() {
        snackBarOrToast.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    saveBooleanValue(true);
                } else {
                    saveBooleanValue(false);
                }
                aBoolean = ((Switch) buttonView).isChecked();
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