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
public class Note implements Serializable, Parcelable, Comparable<Note> {
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

    @SerializedName("isLocked")
    private int isLocked;

    @SerializedName("password")
    private String password;

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

    @Override
    public int compareTo(Note o) {
        LocalDateTime date1 = LocalDateTime.parse(this.dateModified);
        LocalDateTime date2 = LocalDateTime.parse(o.dateModified);

        if (date1.isAfter(date2)) {
            return -1;
        } else if (date1.isBefore(date2)) {
            return 1;
        }
        return 0;
    }

    public void setIsLocked(int isLocked) {
        this.isLocked = isLocked;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLocked() {
        return isLocked == 1;
    }

    public String getPassword() {
        return password;
    }
}
