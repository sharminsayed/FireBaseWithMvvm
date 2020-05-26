package com.gh0stcr4ck3r.firebasewithmvvm.mvvm;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.gh0stcr4ck3r.firebasewithmvvm.model.NoteModel;

import java.util.List;

/**
 * @author : Sharmin Sayed
 * @date : 23-May-2020 8:56 PM
 * -------------------------------------------
 * Copyright (C) 2020 - All Rights Reserved
 **/
@Dao
public interface NoteDao {
    @Insert
    void insert(NoteModel noteModel);
    @Update
    void update(NoteModel noteModel);
    @Delete
    void delete(NoteModel noteModel);

    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    @Query("SELECT * FROM note_table ORDER By uid Desc ")
    LiveData<List<NoteModel>> getALLData();
}
