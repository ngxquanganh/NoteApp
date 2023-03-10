package com.example.noteapplication.network;

import com.example.noteapplication.note.Note;

import java.time.LocalDateTime;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Interface {

    @GET("user/login")
    Call<ResponseJson> getUser(@Query("username") String username,
                               @Query("password") String password);

    @POST("user/register")
    Call<ResponseJson> registerUser(
            @Query("username") String username,
            @Query("password") String password,
            @Query("gender") int gender,
            @Query("fullname") String fullname
    );

    @GET("notes/fetchallnotes")
    Call<ResponseNote> getNotes(@Query("id_user") int idUser);

    @FormUrlEncoded
    @POST("notes/create")
    Call<Note> createNote(
            @Field("id_user") int idUser,
            @Field("title") String title,
            @Field("content") String content,
            @Field("date_create") String dateCreate,
            @Field("date_modify") String dateModify
    );
    @DELETE("notes/deletenote")
    Call<Void> deleteNote(@Query("id_note") int id_note);
    @PUT("notes/updatenote2")
    Call<Void> updateNote(
            @Query("id_note") int id_note,
            @Query("content") String content,
            @Query("date_modify") String dateModify);
    @PUT("notes/setpass")
    Call<Void> setPass(
            @Query("id_note") int id_note,
            @Query("pass") String password
    );

    @PUT("notes/deletepass")
    Call<Void> deletePass
            (
                    @Query("id_note") int id_note
            );

}
