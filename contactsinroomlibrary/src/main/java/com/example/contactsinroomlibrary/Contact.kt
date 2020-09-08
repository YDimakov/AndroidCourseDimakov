package com.example.contactsinroomlibrary

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
class Contact(
        @field:ColumnInfo(name = "contact_id")
        @field:PrimaryKey(autoGenerate = true)
        val id: Int,

        @field:ColumnInfo(name = "contact_name")
        var name: String,

        @field:ColumnInfo(name = "contact_information")
        var information: String,

        @field:ColumnInfo(name = "contact_photo")
        var photo: Int)