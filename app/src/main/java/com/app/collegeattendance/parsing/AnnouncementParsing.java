package com.app.collegeattendance.parsing;

import com.app.collegeattendance.model.Announcement;
import com.app.collegeattendance.model.Department;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class AnnouncementParsing implements Serializable {

    @SerializedName("msg")
    public String Message="";

    @SerializedName("code")
    public int Code=0;

    @SerializedName("announcement")
    public ArrayList<Announcement> list=new ArrayList<>();
}
