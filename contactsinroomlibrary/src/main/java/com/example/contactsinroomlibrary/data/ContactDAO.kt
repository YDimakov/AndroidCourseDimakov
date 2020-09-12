package com.example.contactsinroomlibrary.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.contactsinroomlibrary.Contact

@Dao
interface ContactDAO {
    @Insert
    fun addContact(contact: Contact): Long

    @Update
    fun updateContact(contact: Contact)

    @Delete
    fun deleteContact(contact: Contact)

    @Query("select * from contacts")
    fun getAllContacts(): List<Contact>

    @Query("select * from contacts where contact_id = :contactId")
    fun getContact(contactId: Long): Contact
}