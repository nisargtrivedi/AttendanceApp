package com.app.collegeattendance.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Student implements Serializable {

    @SerializedName("student_id")
    public String StudentID="0";

    @SerializedName("full_name")
    public String FullName="0";

    @SerializedName("dob")
    public String DOB="0";

    @SerializedName("address")
    public String Address="0";

    @SerializedName("enroll_no")
    public String EnrollNo="0";

    @SerializedName("student_photo")
    public String Photo="0";


}
