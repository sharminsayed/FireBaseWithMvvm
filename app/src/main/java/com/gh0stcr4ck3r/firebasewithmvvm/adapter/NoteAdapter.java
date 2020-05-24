package com.gh0stcr4ck3r.firebasewithmvvm.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.gh0stcr4ck3r.firebasewithmvvm.model.NoteModel;
import com.gh0stcr4ck3r.firebasewithmvvm.R;

import java.util.List;

/**
 * @author : Sharmin Sayed
 * @date : 21-May-2020 12:31 AM
 * -------------------------------------------
 * Copyright (C) 2020 - All Rights Reserved
 **/
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private List<NoteModel>noteModelList;
    private Context mcontext;
    private OnItemClicked listener;

    public NoteAdapter(List<NoteModel> noteModelList, Context mcontext, OnItemClicked listener) {
        this.noteModelList = noteModelList;
        this.mcontext = mcontext;
        this.listener = listener;
    }

    public void setNoteModelList(List<NoteModel> noteModelList) {
        this.noteModelList = noteModelList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(mcontext).inflate(R.layout.note_single_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final NoteModel noteModel=noteModelList.get(position);
      holder.title.setText(noteModel.getTitle());
      holder.desc.setText(noteModel.getDescription());
      holder.cardView.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              listener.onClicked(noteModel);

          }
      });
      holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
          @Override
          public boolean onLongClick(View v) {
              listener.onLongClick(noteModel);
              return true;
          }
      });




    }

    @Override
    public int getItemCount() {
        return noteModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView title,desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView=itemView.findViewById(R.id.card);
            title=itemView.findViewById(R.id.cardTitle);
            desc=itemView.findViewById(R.id.cardDescription);
        }
    }

    public interface OnItemClicked {
        void onClicked(NoteModel noteModel);
        void onLongClick(NoteModel noteModel);
    }
}
