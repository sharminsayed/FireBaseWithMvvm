package com.gh0stcr4ck3r.firebasewithmvvm.mvvm.repository;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.gh0stcr4ck3r.firebasewithmvvm.model.NoteModel;
import com.gh0stcr4ck3r.firebasewithmvvm.mvvm.NoteDao;
import com.gh0stcr4ck3r.firebasewithmvvm.mvvm.NoteDataBase;
import com.gh0stcr4ck3r.firebasewithmvvm.ui.NoteDetails;
import com.gh0stcr4ck3r.firebasewithmvvm.ui.NoteListActivity;
import com.gh0stcr4ck3r.firebasewithmvvm.utils.SharedPref;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.util.List;

/**
 * @author : Sharmin Sayed
 * @date : 23-May-2020 9:14 PM
 * -------------------------------------------
 * Copyright (C) 2020 - All Rights Reserved
 **/
public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<NoteModel>> noteModelList;

    private DatabaseReference databaseReference;
    private FirebaseUser fUser;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private String uid,nid;
    private NoteModel noteModel;
    private MutableLiveData<NoteModel> mutableLiveData;
    private Context context;


    public NoteRepository(Application application) {
        NoteDataBase noteDataBase = NoteDataBase.getDatabase(application);
        this.noteDao = noteDataBase.noteDao();
        this.noteModelList = noteDao.getALLData();
        mAuth = FirebaseAuth.getInstance();
        fUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        noteModel=new NoteModel();
        mutableLiveData = new MutableLiveData<>();
        uid = fUser.getUid();
        databaseReference = database.getReference().child(uid).child("notes");


    }




    public LiveData<List<NoteModel>> getAllnotes() {
        return noteModelList;
    }









    public void insert(NoteModel noteModel) {
        new insertAsyncTask(noteDao).execute(noteModel);
    }
    public void update(NoteModel noteModel) {
        new updatenoteAsyctask(noteDao).execute(noteModel);
    }
    public void addNote(final NoteModel noteModel) {
        String noteId = databaseReference.push().getKey();
        assert noteId != null;
        noteModel.setUid(noteId);
        databaseReference.child(noteId).setValue(noteModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                new insertAsyncTask(noteDao).execute(noteModel);


            }
        });
    }

    public void updateNote(final NoteModel noteModel){
        new updatenoteAsyctask(noteDao).execute(noteModel);
        databaseReference.child(noteModel.getUid()).setValue(noteModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {


            }
        });


    }


    private static class insertAsyncTask extends AsyncTask<NoteModel, Void, Void> {

        private NoteDao noteDao;

        insertAsyncTask(NoteDao dao) {
            noteDao = dao;
        }

        @Override
        protected Void doInBackground(final NoteModel... params) {
            noteDao.insert(params[0]);
            return null;
        }
    }

    public MutableLiveData<NoteModel>retriveData(String nid){

        databaseReference.child(nid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mutableLiveData.setValue(dataSnapshot.getValue(NoteModel.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return mutableLiveData;

    }

    private static class updatenoteAsyctask extends AsyncTask<NoteModel,Void,Void>{
        private NoteDao noteDao;

        public updatenoteAsyctask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(NoteModel... noteModels) {
            noteDao.update(noteModels[0]);
            return null;
        }
    }









}
