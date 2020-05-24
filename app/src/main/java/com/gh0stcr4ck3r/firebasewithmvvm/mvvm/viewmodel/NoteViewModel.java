package com.gh0stcr4ck3r.firebasewithmvvm.mvvm.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gh0stcr4ck3r.firebasewithmvvm.model.NoteModel;
import com.gh0stcr4ck3r.firebasewithmvvm.mvvm.repository.NoteRepository;

import java.util.List;

/**
 * @author : Sharmin Sayed
 * @date : 23-May-2020 9:14 PM
 * -------------------------------------------
 * Copyright (C) 2020 - All Rights Reserved
 **/
public class NoteViewModel extends AndroidViewModel {
    private NoteRepository mRepository;
    private LiveData<List<NoteModel>>noteModelList;
    public NoteViewModel(@NonNull Application application) {
        super(application);
        mRepository=new NoteRepository(application);
        noteModelList=mRepository.getAllnotes();
    }
    public LiveData<List<NoteModel>> getAllNoteData() {
        return noteModelList;
    }

    public void insert(NoteModel noteModel) {
        mRepository.insert(noteModel);
    }
    public void getAddData(NoteModel noteModel){
        mRepository.addNote(noteModel);
    }
  public MutableLiveData<NoteModel>retriveNoteData(String nid){
        return mRepository.retriveData(nid);
  }


    public void update(NoteModel noteModel) {
        mRepository.update(noteModel);
    }
    public void getUpdateData(NoteModel noteModel){
        mRepository.updateNote(noteModel);
    }
}
