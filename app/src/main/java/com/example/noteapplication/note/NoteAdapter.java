package com.example.noteapplication.note;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.noteapplication.R;
import com.example.noteapplication.network.ResponseNote;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder>{

    private ResponseNote responseNote;
    private List<Note> data;

    public NoteAdapter(List<Note> notes) {
        this.data = notes;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = data.get(position);
        if(note == null){
            return;
        }
        holder.titleOutput.setText(note.getTitle());
        holder.descriptionOutput.setText(note.getContent());
        holder.timeOutput.setText(note.getDateCreated());
    }

    @Override
    public int getItemCount() {
        if(data != null){
            return data.size();
        }
        return 0;
    }

    public static class NoteViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleOutput;
        private final TextView descriptionOutput;
        private final TextView timeOutput;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            titleOutput = itemView.findViewById(R.id.titleoutput);
            descriptionOutput = itemView.findViewById(R.id.descriptionoutput);
            timeOutput = itemView.findViewById(R.id.timeoutput);
        }
    }
}