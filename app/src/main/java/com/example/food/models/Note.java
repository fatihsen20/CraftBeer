package com.example.food.models;

public class Note {
    private String title, desc , beertype;
    private int image;

    public Note(String title, String desc, String beertype, int image) {
        this.title = title;
        this.desc = desc;
        this.beertype = beertype;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBeertype() {
        return beertype;
    }

    public void setBeertype(String beertype) {
        this.beertype = beertype;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
