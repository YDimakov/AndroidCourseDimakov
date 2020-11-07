package com.example.contactrequest;


public class Contact {
    private String name;
    private String information;
    private int photo;

    public Contact(String name, String information, int photo) {
        this.name = name;
        this.information = information;
        this.photo = photo;
    }

    public String getInformation() {
        return information;
    }

    public String getName() {
        return name;
    }

    public int getPhoto() {
        return photo;
    }

}
