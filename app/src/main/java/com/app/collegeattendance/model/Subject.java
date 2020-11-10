package com.app.collegeattendance.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Subject implements Serializable {

    @SerializedName("subject_id")
    public String SubjectID;

    @SerializedName("subject_code")
    public String SubjectCode;

    @SerializedName("subject_name")
    public String SubjectName;

}
