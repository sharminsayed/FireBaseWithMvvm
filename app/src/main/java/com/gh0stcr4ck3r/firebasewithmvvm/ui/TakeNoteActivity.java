package com.gh0stcr4ck3r.firebasewithmvvm.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gh0stcr4ck3r.firebasewithmvvm.model.NoteModel;
import com.gh0stcr4ck3r.firebasewithmvvm.R;
import com.gh0stcr4ck3r.firebasewithmvvm.mvvm.viewmodel.NoteViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class TakeNoteActivity extends AppCompatActivity {
    EditText title,desc;

    private FirebaseUser fUser;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private String uid;
    private NoteViewModel noteViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_note);
        title=findViewById(R.id.title);
        desc=findViewById(R.id.desc);
        mAuth=FirebaseAuth.getInstance();
        fUser = mAuth.getCurrentUser();
        database=FirebaseDatabase.getInstance();
        uid=fUser.getUid();
        noteViewModel=ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNoteData().observe(this, new Observer<List<NoteModel>>() {
           @Override
           public void onChanged(List<NoteModel> noteModels) {

           }
       });

    }


    public void saveNote(View view) {

        String titleadd=title.getText().toString();
        String description=desc.getText().toString();
        NoteModel noteModel=new NoteModel();
        noteModel.setTitle(titleadd);
        noteModel.setDescription(description);
        noteViewModel.getAddData(noteModel);
        Toast.makeText(TakeNoteActivity.this, "new note added", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(getApplicationContext(),NoteListActivity.class);
        startActivity(intent);
    }

}
