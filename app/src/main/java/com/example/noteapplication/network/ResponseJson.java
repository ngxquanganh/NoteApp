package com.example.noteapplication.network;

import com.example.noteapplication.User;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ResponseJson implements Serializable {

    @SerializedName("message")
    public String status;

    @SerializedName("data")
    public User data;
}
