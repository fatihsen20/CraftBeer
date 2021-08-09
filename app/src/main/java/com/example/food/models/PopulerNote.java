package com.example.food.models;

import java.io.Serializable;

public class PopulerNote implements Serializable {
    private String name, type, img, desc;

    public PopulerNote(String name, String type, String img, String desc) {
        this.name = name;
        this.type = type;
        this.img = img;
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
