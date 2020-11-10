package com.app.collegeattendance.parsing;

import com.android.volley.toolbox.StringRequest;
import com.app.collegeattendance.model.Faculty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class FacultyParsing implements Serializable {

    @SerializedName("msg")
    public String Message="";

    @SerializedName("code")
    public int Code=0;

    @SerializedName("faculty")
    public ArrayList<Faculty> list=new ArrayList<>();
}
