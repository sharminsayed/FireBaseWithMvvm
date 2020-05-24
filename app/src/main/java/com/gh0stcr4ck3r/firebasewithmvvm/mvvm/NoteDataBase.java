package com.gh0stcr4ck3r.firebasewithmvvm.mvvm;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.gh0stcr4ck3r.firebasewithmvvm.model.NoteModel;

/**
 * @author : Sharmin Sayed
 * @date : 23-May-2020 9:00 PM
 * -------------------------------------------
 * Copyright (C) 2020 - All Rights Reserved
 **/
@Database(entities = {NoteModel.class}, version = 1)
public abstract class NoteDataBase extends RoomDatabase {

    public abstract NoteDao noteDao();
    private static volatile NoteDataBase INSTANCE;

    public static NoteDataBase getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (NoteDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NoteDataBase.class, "my_note_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}

