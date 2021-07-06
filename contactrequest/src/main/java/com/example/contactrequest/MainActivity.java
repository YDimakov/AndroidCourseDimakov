package com.example.contactrequest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.example.contactrequest.recyclerViewAdapter.AdapterContacts;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getName();
    private final ArrayList<Contact> arrayListContacts = new ArrayList<>();
    final Uri contactUri = Uri.parse("content://com.example.contactsinroomlibraryjava.contentProvider/contactsDB");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        roomDb(contactUri);
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerViewWeather = findViewById(R.id.recyclerView);
        recyclerViewWeather.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        AdapterContacts adapterContacts = new AdapterContacts(this, arrayListContacts, this);
        recyclerViewWeather.setAdapter(adapterContacts);
    }

    private void roomDb(Uri uri) {
        String[] projection = new String[]{
                "contact_id",
                "contact_name",
                "contact_information",
                "contact_photo"
        };
        Cursor cursor = getContentResolver().query(uri, projection, null,
                null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                if (Integer.parseInt(cursor.getString(4)) == 1) {
                    arrayListContacts.add(new Contact(cursor.getString(1), cursor.getString(2), R.drawable.ic_baseline_email_turquoise));
                } else if (Integer.parseInt(cursor.getString(4)) == 2) {
                    arrayListContacts.add(new Contact(cursor.getString(1), cursor.getString(2), R.drawable.ic_baseline_contact_phone_purple));
                }
            }
            cursor.close();
        } else {
            Log.e(TAG, "cursor null");
        }
    }
}