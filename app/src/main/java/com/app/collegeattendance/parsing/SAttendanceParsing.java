package com.app.collegeattendance.parsing;

import com.app.collegeattendance.model.SAttendance;
import com.app.collegeattendance.model.TimeTable;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class SAttendanceParsing implements Serializable {

    @SerializedName("msg")
    public String Message="";

    @SerializedName("code")
    public int Code=0;

    @SerializedName("attendance")
    public ArrayList<SAttendance> list=new ArrayList<>();
}
