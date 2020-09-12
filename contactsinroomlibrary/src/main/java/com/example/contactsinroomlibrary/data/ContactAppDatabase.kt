package com.example.contactsinroomlibrary.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.contactsinroomlibrary.Contact
import com.example.contactsinroomlibrary.utils.Util

@Database(entities = [Contact::class], version = Util.DATABASE_VERSION, exportSchema = false)
abstract class ContactAppDatabase : RoomDatabase() {
    abstract fun getContactDAO(): ContactDAO
}

