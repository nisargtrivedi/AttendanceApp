package com.app.collegeattendance.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Events implements Serializable {

    @SerializedName("event_id")
    public String EventID;

    @SerializedName("date")
    public String EventDate;

    @SerializedName("detail")
    public String Details;

    @SerializedName("photo")
    public String Photo;

}
