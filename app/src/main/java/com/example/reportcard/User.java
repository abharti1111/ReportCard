package com.example.reportcard;

public class User {
    public String name,email;
    public boolean isAdmin;

    public  User(){

    }
    public User(String name, String email, boolean isAdmin) {
        this.name = name;
        this.email = email;
        this.isAdmin = isAdmin;
    }
}
