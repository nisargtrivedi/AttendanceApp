package com.app.collegeattendance.parsing;

import com.app.collegeattendance.model.Announcement;
import com.app.collegeattendance.model.FilAttendance;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class AttendanceParsing implements Serializable {

    @SerializedName("msg")
    public String Message="";

    @SerializedName("code")
    public int Code=0;

    @SerializedName("fillattendance")
    public ArrayList<FilAttendance> list=new ArrayList<>();
}
