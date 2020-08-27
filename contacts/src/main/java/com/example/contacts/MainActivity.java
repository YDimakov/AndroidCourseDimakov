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

    ArrayList<Contact> arrayListContact = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        initRecyclerView();
        buttonActivityAddContacts = findViewById(R.id.imageButtonAdd);
        buttonActivityEditContacts = findViewById(R.id.editButton);
        buttonActivityAddContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAdd = new Intent(MainActivity.this, AddContact.class);
                startActivityForResult(intentAdd, 1);

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
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    name = data.getStringExtra("name");
                    phoneOrEmail = data.getStringExtra("phoneOrEmail");
                    flagBoolean = data.getBooleanExtra("flag", false);
                    if (flagBoolean) {
                        arrayListContact.add(new Contact(R.drawable.ic_baseline_contact_phone_24, name, phoneOrEmail));
                    } else {
                        arrayListContact.add(new Contact(R.drawable.ic_baseline_contact_mail_24, name, phoneOrEmail));
                    }
                    recyclerViewAdapter.notifyDataSetChanged();
                }
            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    editName = data.getStringExtra("editName");
                    editPhoneOrEmail = data.getStringExtra("editPhoneOrEmail");
                    for (int i = 0; i <= arrayListContact.size(); i++) {
                        if (i == positionId) {
                            arrayListContact.get(i).setName(editName);
                            arrayListContact.get(i).setPhoneOrEmail(editPhoneOrEmail);
                            recyclerViewAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        }
    }


    @Override
    public void onNoteClick(int position) {
        positionId = position;
        Intent intent = new Intent(MainActivity.this, EditContact.class);
        intent.putExtra("name", arrayListContact.get(position).getName());
        intent.putExtra("emailOrPhone", arrayListContact.get(position).getPhoneOrEmail());
        intent.putExtra("id", position);
        startActivityForResult(intent, 2);
    }
}


