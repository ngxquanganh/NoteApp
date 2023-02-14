package com.example.noteapplication.note;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Note implements Serializable {
    @SerializedName("idNotes")
    private int id;

    @SerializedName("idUser")
    private int idUser;

    @SerializedName("title")
    private String title;
    @SerializedName("dateCreate")
    private String dateCreated;

    @SerializedName("dateModify")
    private String dateModified;

    @SerializedName("content")
    private String content;

    boolean lock_status;

    String password;

    public Note(int idUser, String title, String content, String dateCreated, String dateModified) {
        this.idUser = idUser;
        this.title = title;
        this.content = content;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
    }

}
