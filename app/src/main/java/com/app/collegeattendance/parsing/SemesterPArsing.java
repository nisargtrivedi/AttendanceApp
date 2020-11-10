package com.app.collegeattendance.parsing;

import com.app.collegeattendance.model.Department;
import com.app.collegeattendance.model.Semester;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class SemesterPArsing implements Serializable {

    @SerializedName("msg")
    public String Message="";

    @SerializedName("code")
    public int Code=0;

    @SerializedName("semester")
    public ArrayList<Semester> list=new ArrayList<>();
}
