package com.example.noteapplication;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class User implements Serializable {

    @SerializedName("idUser")
    public int idUser;

    @SerializedName("name")
    public String name;

    @SerializedName("pwd")
    public String password;

    @SerializedName("gender")
    public int gender;

    @SerializedName("full_name")
    public String full_name;
}
