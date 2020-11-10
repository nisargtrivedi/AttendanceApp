package com.app.collegeattendance.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FilAttendance implements Serializable {


    @SerializedName("studentname")
    public String StudentName;

    @SerializedName("departmentname")
    public String DepartmentName;

    @SerializedName("fname")
    public String FacultyName;

    @SerializedName("semname")
    public String SemesterName;

    @SerializedName("sname")
    public String Subject;

    @SerializedName("present")
    public String Present;

    @SerializedName("date")
    public String FDate;




}
