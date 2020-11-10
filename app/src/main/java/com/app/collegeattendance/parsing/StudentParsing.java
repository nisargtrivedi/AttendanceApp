package com.app.collegeattendance.parsing;

import com.app.collegeattendance.model.Faculty;
import com.app.collegeattendance.model.Student;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class StudentParsing implements Serializable {

    @SerializedName("msg")
    public String Message="";

    @SerializedName("code")
    public int Code=0;

    @SerializedName("student")
    public ArrayList<Student> list=new ArrayList<>();
}
