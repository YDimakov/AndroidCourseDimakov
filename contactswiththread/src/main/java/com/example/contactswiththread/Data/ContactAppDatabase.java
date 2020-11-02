package com.example.contactswiththread.Data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.contactswiththread.Contact;
import com.example.contactswiththread.Utils.Util;


@Database(entities = {Contact.class}, version = Util.DATABASE_VERSION)
public abstract class ContactAppDatabase extends RoomDatabase {

    public abstract ContactDAO getContactDAO();
}
