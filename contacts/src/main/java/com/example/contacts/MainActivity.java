package com.example.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contacts.adapter.RecyclerViewAdapter;
import com.example.contacts.workWithContacts.AddContact;
import com.example.contacts.workWithContacts.EditContact;

import java.util.ArrayList;

import static com.example.contacts.R.layout;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.OnNoteListener {
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView recyclerView;
    private ImageButton buttonActivityAddContacts;
    private ImageButton buttonActivityEditContacts;
    private String name;
    private String phoneOrEmail;
    private String editPhoneOrEmail;
    private String editName;
    private Boolean flagBoolean;
    private int positionId;
    private Intent intentEdit;
    private Intent intentAdd;
    private ArrayList<Contact> arrayListContact;
    private final int REQUEST_CODE_MAIN_ACTIVITY_ADD_CONTACT = 1;
    private final int REQUEST_CODE_MAIN_ACTIVITY_EDIT_CONTACT = 2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        arrayListContact = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        initRecyclerView();
        buttonActivityAddContacts = findViewById(R.id.imageButtonAdd);
        buttonActivityEditContacts = findViewById(R.id.editButton);
        buttonActivityAddContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentAdd = new Intent(MainActivity.this, AddContact.class);
                startActivityForResult(intentAdd, REQUEST_CODE_MAIN_ACTIVITY_ADD_CONTACT);

            }
        });

    }

    void initRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));//настройка вертикали
        recyclerViewAdapter = new RecyclerViewAdapter(arrayListContact, this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                if (requestCode == REQUEST_CODE_MAIN_ACTIVITY_ADD_CONTACT) {
                    name = data.getStringExtra("name");
                    phoneOrEmail = data.getStringExtra("phoneOrEmail");
                    flagBoolean = data.getBooleanExtra("flag", false);
                    if (flagBoolean) {
                        arrayListContact.add(new Contact(R.drawable.ic_baseline_contact_phone_24, name, phoneOrEmail));
                    } else {
                        arrayListContact.add(new Contact(R.drawable.ic_baseline_contact_mail_24, name, phoneOrEmail));
                    }
                } else if (requestCode == REQUEST_CODE_MAIN_ACTIVITY_EDIT_CONTACT) {
                    editName = data.getStringExtra("editName");
                    editPhoneOrEmail = data.getStringExtra("editPhoneOrEmail");
                    arrayListContact.get(positionId).setName(editName);
                    arrayListContact.get(positionId).setPhoneOrEmail(editPhoneOrEmail);
                }
            }
        }
        recyclerViewAdapter.notifyDataSetChanged();
    }


    @Override
    public void onNoteClick(int position) {
        positionId = position;
        intentEdit = new Intent(MainActivity.this, EditContact.class);
        intentEdit.putExtra("name", arrayListContact.get(position).getName());
        intentEdit.putExtra("emailOrPhone", arrayListContact.get(position).getPhoneOrEmail());
        intentEdit.putExtra("id", position);
        startActivityForResult(intentEdit, REQUEST_CODE_MAIN_ACTIVITY_EDIT_CONTACT);
    }
}


