package com.app.collegeattendance.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Announcement implements Serializable {

    @SerializedName("id")
    public String AnnouncementID;

    @SerializedName("date")
    public String ADate;

    @SerializedName("detail")
    public String Details;

}
