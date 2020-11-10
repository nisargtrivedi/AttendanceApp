package com.app.collegeattendance.parsing;

import com.app.collegeattendance.model.Department;
import com.app.collegeattendance.model.Faculty;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class DepartmentParsing implements Serializable {

    @SerializedName("msg")
    public String Message="";

    @SerializedName("code")
    public int Code=0;

    @SerializedName("department")
    public ArrayList<Department> list=new ArrayList<>();
}
