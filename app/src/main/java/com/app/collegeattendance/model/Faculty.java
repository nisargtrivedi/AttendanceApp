package com.app.collegeattendance.model;

import com.android.volley.toolbox.StringRequest;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Faculty implements Serializable {

    @SerializedName("faculty_id")
    public String FacultyID="0";

    @SerializedName("faculty_name")
    public String FacultyName="";

    @SerializedName("code")
    public String FacultyCode="0";

    @SerializedName("faculty_photo")
    public String FacultyPhoto="";



}
