package com.gh0stcr4ck3r.firebasewithmvvm.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.gh0stcr4ck3r.firebasewithmvvm.model.NoteModel;
import com.gh0stcr4ck3r.firebasewithmvvm.R;
import com.gh0stcr4ck3r.firebasewithmvvm.mvvm.viewmodel.NoteViewModel;
import com.gh0stcr4ck3r.firebasewithmvvm.utils.SharedPref;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class NoteDetails extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private FirebaseUser fUser;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    EditText updateTitle, UpdateDescription;
    String uid, nid;
    NoteViewModel noteViewModel;
    private NoteModel noteModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_details);
        updateTitle = findViewById(R.id.updatetitle);
        UpdateDescription = findViewById(R.id.updatedesc);
        Intent intent = getIntent();
        nid = intent.getStringExtra("nid");
        mAuth = FirebaseAuth.getInstance();
        fUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        uid = fUser.getUid();
        databaseReference = database.getReference().child(uid).child("notes").child(nid);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        noteViewModel.retriveNoteData(nid).observe(this, new Observer<NoteModel>() {
            @Override
            public void onChanged(NoteModel noteModel) {
                updateTitle.setText(noteModel.getTitle());
                UpdateDescription.setText(noteModel.getDescription());


            }
        });


    }


//    public void UpdateNoteData(final NoteModel noteModel) {
//        noteModel.setUid(nid);//ei line ta bujhini
//        databaseReference.setValue(noteModel).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                startActivity(new Intent(NoteDetails.this, NoteListActivity.class));
//
//            }
//        });
//
//
//    }

    public void updateNote(View view) {
        String uTitle = updateTitle.getText().toString();
        String uDescription = UpdateDescription.getText().toString();

        NoteModel noteModel = new NoteModel();
        noteModel.setTitle(uTitle);
        noteModel.setDescription(uDescription);

        noteViewModel.getUpdateData(noteModel);

    }

    public void deleteNote(View view) {


        DelteNoteData();
    }


    public void DelteNoteData() {

        if (databaseReference.removeValue().isComplete()) {
            Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_SHORT).show();


        }


    }
}
