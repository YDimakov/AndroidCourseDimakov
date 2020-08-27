package com.example.contacts.workWithContacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.contacts.R;

public class EditContact extends AppCompatActivity implements View.OnClickListener {

    private EditText editName;
    private EditText editPhoneOrEmail;
    private Button buttonEdit;
    private ImageButton exitButton;
    private String name;
    private String emailOrPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editing_contacts);
        exitButton = findViewById(R.id.buttonExidEditContact);
        editName = findViewById(R.id.textNameEdit);
        editPhoneOrEmail = findViewById(R.id.textPhoneOrEmailEdit);
        buttonEdit = findViewById(R.id.SaveButtonEdit);
        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        emailOrPhone = intent.getStringExtra("emailOrPhone");
        editName.setText(name);
        editPhoneOrEmail.setText(emailOrPhone);
        buttonEdit.setOnClickListener(this);
        exitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.SaveButtonEdit:
                Intent intentEdit = new Intent();
                intentEdit.putExtra("editName", editName.getText().toString());
                intentEdit.putExtra("editPhoneOrEmail", editPhoneOrEmail.getText().toString());
                setResult(RESULT_OK, intentEdit);
                finish();
                break;
            case R.id.buttonExidEditContact:
                finish();
                break;
        }
    }
}