package com.app.collegeattendance.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SAttendance implements Serializable {

    @SerializedName("said")
    public String AID="0";

    @SerializedName("departmentname")
    public String DayName;

    @SerializedName("sname")
    public String SubjectName;

    @SerializedName("fname")
    public String FacultyName;

    @SerializedName("time")
    public String Time;

}
