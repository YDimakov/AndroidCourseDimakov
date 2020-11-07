package com.example.contactsinroomlibraryjava.data;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.contactsinroomlibraryjava.Contact;

import java.util.List;

@Dao
public interface ContactDAO {

    @Insert
    long addContact(Contact contact);

    @Update
    void updateContact(Contact contact);

    @Delete
    void deleteContact(Contact contact);

    @Query("select * from contacts")
    List<Contact> getAllContacts();

    @Query("SELECT * FROM contacts")
    Cursor findAll();

    @Query("select * from contacts where contact_id==:contactId ")
    Contact getContact(long contactId);
}
