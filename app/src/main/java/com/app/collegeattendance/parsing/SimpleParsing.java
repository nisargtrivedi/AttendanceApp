package com.app.collegeattendance.parsing;

import com.app.collegeattendance.model.Department;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class SimpleParsing implements Serializable {

    @SerializedName("msg")
    public String Message="";

    @SerializedName("code")
    public int Code=0;

    @SerializedName("id")
    public int ID=0;

    @SerializedName("per")
    public String per="";


}
