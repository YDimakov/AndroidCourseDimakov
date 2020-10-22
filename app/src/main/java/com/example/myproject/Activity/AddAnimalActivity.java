package com.example.myproject.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myproject.Animal;
import com.example.myproject.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddAnimalActivity extends AppCompatActivity {
    private static final int RC_IMAGE = 1;
    private ImageButton imageButtonPhoto;
    private ImageButton imageButtonOk;
    private EditText editTextName;
    private EditText phoneText;
    private ImageView newPhotoAnimal;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private Spinner spinnerType;
    private Spinner spinnerAge;
    private Spinner spinnerColor;
    private EditText textComment;
    private String addNameAnimal;
    private String addTypeAnimal;
    private String addAgeAnimal;
    private String addColorAnimal;
    private String addCommentAnimal;
    private String addPhotoAnimal;
    private String addPhoneAnimal;
    private Uri downloadUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_animals);
        super.onCreate(savedInstanceState);
        animalSelection();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        databaseReference = firebaseDatabase.getReference().child("message");
        storageReference = firebaseStorage.getReference().child("animal_images");
        imageButtonPhoto = findViewById(R.id.photoAdd);
        newPhotoAnimal = findViewById(R.id.photoAnimal);
        imageButtonOk = findViewById(R.id.buttonCheck);
        editTextName = findViewById(R.id.nameAnimal);
        phoneText = findViewById(R.id.phoneText);
        spinnerType = findViewById(R.id.spinnerAnimalSelection);
        spinnerAge = findViewById(R.id.spinnerAgeAnimal);
        textComment = findViewById(R.id.additionalInformation);
        spinnerColor = findViewById(R.id.colorAnimal);
        imageButtonPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Choose an image"), RC_IMAGE);
            }
        });
        imageButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNameAnimal = editTextName.getText().toString();
                addTypeAnimal = spinnerType.getSelectedItem().toString();
                addAgeAnimal = spinnerAge.getSelectedItem().toString();
                addColorAnimal = spinnerColor.getSelectedItem().toString();
                addCommentAnimal = textComment.getText().toString();
                addPhoneAnimal = phoneText.getText().toString();
                if (downloadUri != null) {
                    addPhotoAnimal = downloadUri.toString();
                }else{
                    Toast.makeText(AddAnimalActivity.this, "Подождите, загружается фото!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Animal animal = new Animal(addNameAnimal, addTypeAnimal, addAgeAnimal, addColorAnimal, addCommentAnimal, addPhotoAnimal, addPhoneAnimal);
                databaseReference.push().setValue(animal);
                finish();
            }
        });
    }

    private void animalSelection() {
        Spinner autoCompleteAnimalTypeTextView = findViewById(R.id.spinnerAnimalSelection);
        Spinner autoCompleteAnimalAgeTextView = findViewById(R.id.spinnerAgeAnimal);
        Spinner autoCompleteAnimalColorTextView = findViewById(R.id.colorAnimal);

        String[] animalType = getResources().getStringArray(R.array.animals_array);
        String[] animalAge = getResources().getStringArray(R.array.animals_array_age);
        String[] animalColor = getResources().getStringArray(R.array.animals_array_color);
        ArrayAdapter<String> adapterType = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, animalType);
        ArrayAdapter<String> adapterAge = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, animalAge);
        ArrayAdapter<String> adapterColor = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, animalColor);

        autoCompleteAnimalTypeTextView.setAdapter(adapterType);
        autoCompleteAnimalAgeTextView.setAdapter(adapterAge);
        autoCompleteAnimalColorTextView.setAdapter(adapterColor);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_IMAGE && resultCode == RESULT_OK && data != null) {
            Uri uriPhoto = data.getData();
            newPhotoAnimal.setImageURI(uriPhoto);
            final StorageReference imageReference = storageReference.child(uriPhoto.getLastPathSegment());
            UploadTask uploadTask = imageReference.putFile(uriPhoto);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return imageReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        downloadUri = task.getResult();
                    } else {
                        Toast.makeText(AddAnimalActivity.this, "Не удалось загрузить фото!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
