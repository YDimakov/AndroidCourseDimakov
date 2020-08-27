package com.example.contacts;

public class Contact {
    private int photoContact;
    private String name;
    private String phoneOrEmail;


    public int getPhotoContact() {
        return photoContact;
    }

    public String getName() {
        return name;
    }

    public String setName(String name) {
        this.name = name;
        return name;
    }

    public String getPhoneOrEmail() {
        return phoneOrEmail;
    }

    public String setPhoneOrEmail(String phoneOrEmail) {
        this.phoneOrEmail = phoneOrEmail;
        return phoneOrEmail;
    }

    public Contact(int photoContact, String name, String phoneOrEmail) {

        this.photoContact = photoContact;
        this.name = name;
        this.phoneOrEmail = phoneOrEmail;
    }
}
