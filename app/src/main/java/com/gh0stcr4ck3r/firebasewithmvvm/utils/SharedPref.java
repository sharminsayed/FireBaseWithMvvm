package com.gh0stcr4ck3r.firebasewithmvvm.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author : Sharmin Sayed
 * @date : 22-May-2020 10:37 PM
 * -------------------------------------------
 * Copyright (C) 2020 - All Rights Reserved
 **/
public class SharedPref {

    private Context context;

    public SharedPref(Context context) {
        this.context = context;
    }

    public String getID() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(BaseConstant.LOGIN_PREF, Context.MODE_PRIVATE);
        return sharedPreferences.getString("id", "");
    }

    public void saveuID(String id) {
        SharedPreferences.Editor editor=context.getSharedPreferences(BaseConstant.LOGIN_PREF,Context.MODE_PRIVATE).edit();
        editor.putString("id", id);
        editor.apply();

    }

    public boolean isLoggedIn(){
        return !getID().isEmpty();
    }

    public void clearId() {
        SharedPreferences.Editor editor = context.getSharedPreferences(BaseConstant.LOGIN_PREF, Context.MODE_PRIVATE).edit();
        editor.putString("id", "");
        editor.apply();
    }

}
