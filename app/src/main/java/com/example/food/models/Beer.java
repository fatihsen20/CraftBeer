package com.example.food.models;

public class Beer {
    private String name , description;
    private int Image;

    public Beer(String name, String description, int image) {
        this.description = description;
        this.name = name;
        Image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }
}
