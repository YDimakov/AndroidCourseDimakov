package com.example.myproject.Activity;


import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myproject.AdapterListAnimal;
import com.example.myproject.Animal;
import com.example.myproject.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity implements AdapterListAnimal.OnNoteListener {
    private AdapterListAnimal adapterListAnimal;
    private List<Animal> arrayListAnimal;
    private String nameInArray;
    private String typeInArray;
    private String ageInArray;
    private String colorInArray;
    private String commentInArray;
    private String photoInArray;
    private String phoneInArray;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        arrayListAnimal = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_animal);
        RecyclerView recyclerView = findViewById(R.id.animalsRecyclerView);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.INVISIBLE);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("message");

        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapterListAnimal = new AdapterListAnimal(arrayListAnimal, this);
        recyclerView.setAdapter(adapterListAnimal);
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                nameInArray = String.valueOf(snapshot.child("nameAnimal").getValue());
                typeInArray = String.valueOf(snapshot.child("typeAnimal").getValue());
                ageInArray = String.valueOf(snapshot.child("ageAnimal").getValue());
                colorInArray = String.valueOf(snapshot.child("colorAnimal").getValue());
                commentInArray = String.valueOf(snapshot.child("commentAnimal").getValue());
                photoInArray = String.valueOf(snapshot.child("photoAnimal").getValue());
                phoneInArray = String.valueOf(snapshot.child("phoneAnimal").getValue());
                arrayListAnimal.add(new Animal
                        (nameInArray, ageInArray, typeInArray, colorInArray, commentInArray, photoInArray, phoneInArray));
                adapterListAnimal.notifyDataSetChanged();
            }


            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String
                    previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String
                    previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        databaseReference.addChildEventListener(childEventListener);
    }

    @Override
    public void onNoteClick(int position) {// Дальнейшее развитие проекта
        //        intentEdit = new Intent(MainActivity.this, EditContact.class);
//        intentEdit.putExtra("name", arrayListContact.get(position).getName());
//        intentEdit.putExtra("emailOrPhone", arrayListContact.get(position).getPhoneOrEmail());
//        intentEdit.putExtra("id", position);
//        startActivityForResult(intentEdit, REQUEST_CODE_MAIN_ACTIVITY_EDIT_CONTACT);
    }
}
