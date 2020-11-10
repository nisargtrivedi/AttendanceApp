package com.app.collegeattendance.student;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.app.collegeattendance.API.AppPreferences;

public class BaseActivity extends AppCompatActivity {

    AppPreferences appPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appPreferences=new AppPreferences(this);
    }
}
