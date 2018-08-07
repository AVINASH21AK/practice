package com.practice.utils;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CommonResponce implements Serializable {

    @SerializedName("status")
    public String status;

    @SerializedName("message")
    public String msg;


}
