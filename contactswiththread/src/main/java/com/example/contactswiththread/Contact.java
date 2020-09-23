package com.example.contactswiththread;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contacts")
public class Contact {

    public Contact(int id, String name, String information, int photo) {
        this.id = id;
        this.name = name;
        this.information = information;
        this.photo = photo;
    }


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "contact_id")
    private int id;

    @ColumnInfo(name = "contact_name")
    private String name;

    @ColumnInfo(name = "contact_information")
    private String information;

    @ColumnInfo(name = "contact_photo")
    private int photo;


    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public int getPhoto() {
        return photo;
    }

}
