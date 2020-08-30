package com.example.contacts.workWithContacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contacts.R;

public class AddContact extends AppCompatActivity implements View.OnClickListener {
    private EditText addName;
    private EditText addEmailOrPhone;
    private CheckBox сheckBoxPhone;
    private CheckBox сheckBoxEmail;
    private Button addButton;
    private ImageButton exitButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);
        addName = findViewById(R.id.TextNameAdd);
        addButton = findViewById(R.id.buttonAdd);
        addEmailOrPhone = findViewById(R.id.textEmailOrPhoneAdd);
        сheckBoxPhone = findViewById(R.id.checkBoxPhone);
        сheckBoxEmail = findViewById(R.id.checkBoxEmail);
        exitButton = findViewById(R.id.buttonBackAddContact);
        addButton.setOnClickListener(this);
        exitButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAdd:
                Intent intent = new Intent();
                intent.putExtra("name", addName.getText().toString());
                intent.putExtra("phoneOrEmail", addEmailOrPhone.getText().toString());
                if (сheckBoxPhone.isChecked()) {
                    intent.putExtra("flag", true);
                } else if (сheckBoxEmail.isChecked()) {
                    intent.putExtra("flag", false);
                }
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.buttonBackAddContact:
                finish();
                break;
        }
    }
}
