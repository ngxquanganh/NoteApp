package com.example.noteapplication.network;

import com.example.noteapplication.note.Note;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class ResponseNote {

    @SerializedName("message")
    private String status;

    @SerializedName("data")
    private List<Note> data;

    public String getStatus() {
        return status;
    }

    public List<Note> getData() {
        return data;
    }
}
