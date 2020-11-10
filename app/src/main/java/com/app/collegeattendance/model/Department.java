package com.app.collegeattendance.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Department implements Serializable {

    @SerializedName("did")
    public String DepartmentID;

    @SerializedName("dname")
    public String DepartmentName;

}
