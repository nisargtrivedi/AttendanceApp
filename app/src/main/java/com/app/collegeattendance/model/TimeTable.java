package com.app.collegeattendance.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TimeTable implements Serializable {

    @SerializedName("day_name")
    public String DayName;

    @SerializedName("subject_name")
    public String SubjectName;

    @SerializedName("faculty_name")
    public String FacultyName;

    @SerializedName("time")
    public String Time;

}
