package com.example.noteapplication.note;

import android.os.Parcel;
import android.os.Parcelable;

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
public class Note implements Serializable, Parcelable {
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

    public Note(int idUser, String title, String content, String dateCreated, String dateModified) {
        this.idUser = idUser;
        this.title = title;
        this.content = content;
        this.dateCreated = dateCreated;
        this.dateModified = dateModified;
    }

    public String getTitle() {
        return title;
    }
    public void setDateModified(String dateModified){this.dateModified = dateModified;}

    public String getContent() {
        return content;
    }
    public String getDateModified(){return dateModified;};
    public String getDateCreated() {
        return dateCreated.toString();
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeString(this.dateCreated);
    }
    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel source) {
            return new Note(source);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
    public Note(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.content = in.readString();
        this.dateCreated = in.readString();
    }

    public int getId() {
        return id;
    }
}
