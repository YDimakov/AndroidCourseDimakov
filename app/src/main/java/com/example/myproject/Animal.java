package com.example.myproject;

public class Animal {
    private String nameAnimal;
    private String ageAnimal;
    private String typeAnimal;
    private String colorAnimal;
    private String commentAnimal;
    private String photoAnimal;
    private String phoneAnimal;

    public Animal(String nameAnimal, String ageAnimal, String typeAnimal, String colorAnimal, String commentAnimal, String photoAnimal, String phoneAnimal) {
        this.nameAnimal = nameAnimal;
        this.ageAnimal = ageAnimal;
        this.typeAnimal = typeAnimal;
        this.colorAnimal = colorAnimal;
        this.commentAnimal = commentAnimal;
        this.photoAnimal = photoAnimal;
        this.phoneAnimal = phoneAnimal;
    }

    public String getPhoneAnimal() {
        return phoneAnimal;
    }

    public String getNameAnimal() {
        return nameAnimal;
    }

    public String getAgeAnimal() {
        return ageAnimal;
    }

    public String getTypeAnimal() {
        return typeAnimal;
    }

    public String getColorAnimal() {
        return colorAnimal;
    }

    public String getCommentAnimal() {
        return commentAnimal;
    }

    public String getPhotoAnimal() {
        return photoAnimal;
    }

}