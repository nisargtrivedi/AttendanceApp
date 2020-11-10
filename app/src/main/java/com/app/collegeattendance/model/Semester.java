package com.app.collegeattendance.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Semester implements Serializable {

    @SerializedName("sem_id")
    public String SemesterID;

    @SerializedName("semname")
    public String SemesterName;

}
