package com.example.contactswiththread;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.contactswiththread.Data.ContactAppDatabase;
import com.example.contactswiththread.InterfaceDataProcessing.WorkWithTread;
import com.example.contactswiththread.RecyclerViewAdapter.AdapterContacts;
import com.example.contactswiththread.DataProcessing.CompletableFutureAndThreadPoolExecutor;
import com.example.contactswiththread.DataProcessing.RxJava;
import com.example.contactswiththread.DataProcessing.ThreadPoolExecutorAndHandler;

import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity extends AppCompatActivity {

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
    private ImageButton buttonRefresh;
    private ContactAppDatabase contactAppDatabase;
    private CheckBox checkBoxPhone, checkBoxEmail;
    private ImageView imageViewPhoto;
    private boolean checkedCheckBox;
    private byte choiceThread;
    private Contact contact;
    private ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
    private final WorkWithTread workWithTreadPool = new ThreadPoolExecutorAndHandler();
    private WorkWithTread CompletableFuture = new CompletableFutureAndThreadPoolExecutor();
    private WorkWithTread workWithRxJava = new RxJava();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        recyclerView = findViewById(R.id.recyclerView);
        buttonRefresh = findViewById(R.id.refreshButton);
        buttonRefresh.setOnClickListener(new View.OnClickListener() {// костыль- он обновляет данные в recycler view.
            @Override
            public void onClick(View view) {
                adapterContacts.notifyDataSetChanged();
            }
        });

        contactAppDatabase = Room.databaseBuilder(getApplicationContext(),
                ContactAppDatabase.class, "ContactDB")
                .allowMainThreadQueries()
                .build();

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
                if (choiceThread == 0) {
                    Toast.makeText(MainActivity.this, "Выберите параметр работы приложения!", Toast.LENGTH_SHORT).show();
                    return;
                }
                addAndEditContact(false, null, -1);
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
        switch (item.getItemId()) {
            case R.id.handlerAndThreadPoolExecutor:
                Toast.makeText(this, "Вы нажали ThreadPoolExecutor and Handler", Toast.LENGTH_SHORT).show();
                choiceThread = 1;
                break;

            case R.id.completableFutureAndThreadPoolExecutor:
                Toast.makeText(this, "Вы нажали CompletableFuture and ThreadPoolExecutor", Toast.LENGTH_SHORT).show();
                choiceThread = 2;
                break;

            case R.id.rxJava:
                Toast.makeText(this, "Вы нажали RxJava", Toast.LENGTH_SHORT).show();
                choiceThread = 3;
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void addAndEditContact(final boolean isUpdate, final Contact contact, final int position) {
        if (choiceThread == 0) {
            Toast.makeText(MainActivity.this, "Выберите параметр работы приложения!", Toast.LENGTH_SHORT).show();
            return;
        }
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
                    @RequiresApi(api = Build.VERSION_CODES.N)
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void deleteContact(final Contact contact, final int position) {
        if (choiceThread == 1) { // ThreadPoolExecutor and Handler
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    workWithTreadPool.deleteContact(contact, position, arrayListContacts,
                            contactAppDatabase);
                }
            });

        } else if (choiceThread == 2) { // CompletableFuture and ThreadPoolExecutor
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    CompletableFuture.deleteContact(contact, position, arrayListContacts,
                            contactAppDatabase);
                }
            });

        } else if (choiceThread == 3) { //RxJava
            workWithRxJava.deleteContact(contact, position, arrayListContacts,
                    contactAppDatabase);
        }
        adapterContacts.notifyDataSetChanged();
    }

    private void updateContact(final String name, final String info, final int position) {
        if (choiceThread == 1) { // ThreadPoolExecutor and Handler
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    workWithTreadPool.updateContact(contact, arrayListContacts, checkedCheckBox,
                            contactAppDatabase, name, info, position);
                }
            });

        } else if (choiceThread == 2) { // CompletableFuture and ThreadPoolExecutor
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    CompletableFuture.updateContact(contact, arrayListContacts, checkedCheckBox,
                            contactAppDatabase, name, info, position);
                }
            });

        } else if (choiceThread == 3) { //RxJava

            workWithRxJava.updateContact(contact, arrayListContacts, checkedCheckBox,
                    contactAppDatabase, name, info, position);
        }
        adapterContacts.notifyDataSetChanged();
    }

    private void createContact(final String name, final String info) {
        if (choiceThread == 1) { // ThreadPoolExecutor and Handler
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    workWithTreadPool.createContact(contact, checkedCheckBox, contactAppDatabase,
                            arrayListContacts, name, info);
                }
            });

        } else if (choiceThread == 2) { // CompletableFuture and ThreadPoolExecutor
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    CompletableFuture.createContact(contact, checkedCheckBox, contactAppDatabase,
                            arrayListContacts, name, info);
                }
            });

        } else if (choiceThread == 3) { //RxJava
            workWithRxJava.createContact(contact, checkedCheckBox, contactAppDatabase,
                    arrayListContacts, name, info);
        }
        adapterContacts.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executor.shutdown();
    }
}

