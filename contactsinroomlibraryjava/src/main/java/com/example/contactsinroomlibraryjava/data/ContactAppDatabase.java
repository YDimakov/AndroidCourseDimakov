package com.example.contactsinroomlibraryjava.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.contactsinroomlibraryjava.Contact;
import com.example.contactsinroomlibraryjava.utils.Util;

import static com.example.contactsinroomlibraryjava.utils.Util.DATABASE_NAME;


@Database(entities = {Contact.class}, version = Util.DATABASE_VERSION)
public abstract class ContactAppDatabase extends RoomDatabase {
    private static ContactAppDatabase INSTANCE;

    public static ContactAppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room
                    .databaseBuilder(context, ContactAppDatabase.class, DATABASE_NAME)
                    .build();
        }
        return INSTANCE;
    }

    public abstract ContactDAO getContactDAO();
}
