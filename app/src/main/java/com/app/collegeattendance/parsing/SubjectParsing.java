package com.app.collegeattendance.parsing;

import com.app.collegeattendance.model.Department;
import com.app.collegeattendance.model.Subject;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class SubjectParsing implements Serializable {

    @SerializedName("msg")
    public String Message="";

    @SerializedName("code")
    public int Code=0;

    @SerializedName("subject")
    public ArrayList<Subject> list=new ArrayList<>();
}
