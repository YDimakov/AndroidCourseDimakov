package com.example.contactsinroomlibraryjava;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contacts")
public class Contact {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "contact_id")
    private int id;

    @ColumnInfo(name = "contact_name")
    private String name;

    @ColumnInfo(name = "contact_information")
    private String information;

    @ColumnInfo(name = "contact_photo")
    private int photo;

    @ColumnInfo(name = "contact_photo_select")
    private int photoSelection;

    public Contact(int id, String name, String information, int photo, int photoSelection) {
        this.id = id;
        this.name = name;
        this.information = information;
        this.photo = photo;
        this.photoSelection = photoSelection;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getInformation() {
        return information;
    }

    public int getPhoto() {
        return photo;
    }

    public int getPhotoSelection() {
        return photoSelection;
    }
}
