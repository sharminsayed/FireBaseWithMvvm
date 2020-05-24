package com.gh0stcr4ck3r.firebasewithmvvm.model;

/**
 * @author : Sharmin Sayed
 * @date : 21-May-2020 9:18 PM
 * -------------------------------------------
 * Copyright (C) 2020 - All Rights Reserved
 **/
public class UserModel {
    private String email;
    private String password;

    public UserModel() {
    }

    public UserModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
