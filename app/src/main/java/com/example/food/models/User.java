package com.example.food.models;

public class User {
    private String email;
    private String pass;
    private int age;
    private String fullname;

    public User(String email, String pass, int age, String fullname) {
        this.email = email;
        this.pass = pass;
        this.age = age;
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
