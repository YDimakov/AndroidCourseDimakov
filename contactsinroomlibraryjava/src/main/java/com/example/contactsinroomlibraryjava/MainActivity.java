package com.example.contactsinroomlibraryjava;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contactsinroomlibraryjava.data.ContactAppDatabase;
import com.example.contactsinroomlibraryjava.recyclerViewAdapter.AdapterContacts;

import java.util.ArrayList;

import static com.example.contactsinroomlibraryjava.utils.Util.DATABASE_NAME;

public class MainActivity extends AppCompatActivity {
    private long id;
    private AdapterContacts adapterContacts;
    private ArrayList<Contact> arrayListContacts = new ArrayList<>();
    private RecyclerView recyclerView;
    private ImageButton imageButton;
    private EditText nameEditText;
    private TextView newContactTitle;
    private EditText informationEditText;
    private AlertDialog alertDialog;
    private View view;
    private AlertDialog.Builder alertDialogBuilderUserInput;
    private LayoutInflater layoutInflaterAndroid;
    private RecyclerView.LayoutManager mLayoutManager;
    private Contact contact;
    private ContactAppDatabase contactAppDatabase;
    private CheckBox checkBoxPhone, checkBoxEmail;
    private ImageView imageViewPhoto;
    private boolean checkedCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        contactAppDatabase = Room.databaseBuilder(getApplicationContext(),
                ContactAppDatabase.class,
                DATABASE_NAME).allowMainThreadQueries().build();
        arrayListContacts.addAll(contactAppDatabase.getContactDAO().getAllContacts());
        adapterContacts = new AdapterContacts(this, arrayListContacts, MainActivity.this);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapterContacts);
        imageViewPhoto = findViewById(R.id.photoContact);
        imageButton = findViewById(R.id.imageButtonAdd);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAndEditContact(false, null, -1);
            }
        });
    }

    public void addAndEditContact(final boolean isUpdate, final Contact contact, final int position) {

        layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        view = layoutInflaterAndroid.inflate(R.layout.activity_add_contacts, null);
        alertDialogBuilderUserInput = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilderUserInput.setView(view);
        newContactTitle = view.findViewById(R.id.newContactTitle);
        nameEditText = view.findViewById(R.id.nameEditText);
        checkBoxPhone = view.findViewById(R.id.checkBoxPhone);
        checkBoxEmail = view.findViewById(R.id.checkBoxEmail);
        informationEditText = view.findViewById(R.id.informationEditText);
        checkingTwoParameters();
        newContactTitle.setText(!isUpdate ? "Добавить контакт" : "Изменение контакта");
        if (isUpdate && contact != null) {
            nameEditText.setText(contact.getName());
            informationEditText.setText(contact.getInformation());
        }
        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(isUpdate ? "Обновить" : "Сохранить", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                    }
                })
                .setNegativeButton(isUpdate ? "Удалить" : "Отмена", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogBox, int id) {

                        if (isUpdate) {
                            deleteContact(contact, position);
                        } else {
                            dialogBox.cancel();
                        }

                    }
                });

        alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(nameEditText.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Введите имя!", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(informationEditText.getText().toString())) {
                    Toast.makeText(MainActivity.this, "Введите информацию о пользователе", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    alertDialog.dismiss();
                }
                if (isUpdate && contact != null) {

                    updateContact(nameEditText.getText().toString(), informationEditText.getText().toString(), position);
                } else {
                    createContact(nameEditText.getText().toString(), informationEditText.getText().toString());
                }
            }
        });
    }

    private void checkingTwoParameters() {
        checkBoxEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBoxEmail.isChecked()) {
                    checkedCheckBox = true;
                    Toast.makeText(MainActivity.this, "Введите свою почту!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        checkBoxPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBoxPhone.isChecked()) {
                    checkedCheckBox = false;
                    Toast.makeText(MainActivity.this, "Введите свой телефон", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void deleteContact(Contact contact, int position) {
        arrayListContacts.remove(position);
        contactAppDatabase.getContactDAO().deleteContact(contact);
        adapterContacts.notifyDataSetChanged();
    }

    private void updateContact(String name, String info, int position) {
        contact = arrayListContacts.get(position);
        contact.setName(name);
        contact.setInformation(info);
        if (checkedCheckBox) {
            contact.setPhoto(R.drawable.ic_baseline_email_turquoise);
        } else {
            contact.setPhoto(R.drawable.ic_baseline_contact_phone_purple);
        }
        contactAppDatabase.getContactDAO().updateContact(contact);
        arrayListContacts.set(position, contact);
        adapterContacts.notifyDataSetChanged();
    }

    private void createContact(String name, String info) {
        if (checkedCheckBox) {
            id = contactAppDatabase.getContactDAO().addContact(new Contact(0, name, info, R.drawable.ic_baseline_email_turquoise,1));
        } else {
            id = contactAppDatabase.getContactDAO().addContact(new Contact(0, name, info, R.drawable.ic_baseline_contact_phone_purple,2));
        }
        contact = contactAppDatabase.getContactDAO().getContact(id);
        if (contact != null) {
            arrayListContacts.add(0, contact);
            adapterContacts.notifyDataSetChanged();
        }
    }
}



