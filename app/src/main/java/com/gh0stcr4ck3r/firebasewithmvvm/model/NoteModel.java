package com.gh0stcr4ck3r.firebasewithmvvm.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @author : Sharmin Sayed
 * @date : 21-May-2020 12:29 AM
 * -------------------------------------------
 * Copyright (C) 2020 - All Rights Reserved
 **/
@Entity(tableName = "note_table")
public class NoteModel {
    @PrimaryKey(autoGenerate = false)
    @NonNull//eikhane tmr kotha anujayi change korsi
    private String uid;
    private String title;
    private String description;


    public NoteModel() {
    }

    public NoteModel(String uid, String title, String description) {
        this.uid = uid;
        this.title = title;
        this.description = description;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "NoteModel{" +
                "uid='" + uid + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
