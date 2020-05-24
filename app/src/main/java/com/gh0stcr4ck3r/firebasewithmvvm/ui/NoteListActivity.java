package com.gh0stcr4ck3r.firebasewithmvvm.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gh0stcr4ck3r.firebasewithmvvm.adapter.NoteAdapter;
import com.gh0stcr4ck3r.firebasewithmvvm.model.NoteModel;
import com.gh0stcr4ck3r.firebasewithmvvm.R;
import com.gh0stcr4ck3r.firebasewithmvvm.mvvm.viewmodel.NoteViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NoteListActivity extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private FirebaseUser fUser;
    private FirebaseAuth mAuth;
    private NoteAdapter adapter;
    private String uid;
    private RecyclerView recyclerView;
    private List<NoteModel> noteModelList;
    private FirebaseDatabase database;
    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        mAuth=FirebaseAuth.getInstance();
        fUser = mAuth.getCurrentUser();
        database=FirebaseDatabase.getInstance();
        uid=fUser.getUid();
        databaseReference = database.getReference().child(uid).child("notes");


        noteModelList=new ArrayList<>();
        recyclerView = findViewById(R.id.noteRecycle);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter=new NoteAdapter(noteModelList, this, new NoteAdapter.OnItemClicked() {
            @Override
            public void onClicked(NoteModel noteModel) {

                String gId = noteModel.getUid().toString();
                Intent intent = new Intent(getApplicationContext(), NoteDetails.class);
                intent.putExtra("nid", gId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            @Override
            public void onLongClick(NoteModel noteModel) {

            }
        });
        adapter.setNoteModelList(noteModelList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        noteViewModel= ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNoteData().observe(this, new Observer<List<NoteModel>>() {
            @Override
            public void onChanged(List<NoteModel> noteModels) {
                adapter.setNoteModelList(noteModels);
            }
        });


    }



    public void openContactDialog(View view) {
        startActivity(new Intent(NoteListActivity.this, TakeNoteActivity.class));
    }


}
