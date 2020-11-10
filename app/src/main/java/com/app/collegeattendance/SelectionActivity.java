package com.app.collegeattendance;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.app.collegeattendance.API.AppPreferences;
import com.app.collegeattendance.student.StudentDashboard;
import com.app.collegeattendance.student.StudentLogin;

public class SelectionActivity extends AppCompatActivity {

    ImageView teacher,student;
    AppCompatButton btnGo;
    AppPreferences appPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appPreferences=new AppPreferences(this);
        setContentView(R.layout.select_user_type);
        teacher=findViewById(R.id.teacher);
        student=findViewById(R.id.student);
        btnGo=findViewById(R.id.btnGo);

        if(!TextUtils.isEmpty(appPreferences.getString("student_id"))){
            finish();
            startActivity(new Intent(SelectionActivity.this, StudentDashboard.class));
        }else if(!TextUtils.isEmpty(appPreferences.getString("facultyid"))){
            finish();
            startActivity(new Intent(SelectionActivity.this,HomeActivity.class));
        }
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(teacher.isSelected()){
                        finish();
                        startActivity(new Intent(SelectionActivity.this,MainActivity.class));
                }else if(student.isSelected()){
                    finish();
                    startActivity(new Intent(SelectionActivity.this, StudentLogin.class));
                }
            }
        });
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teacher.setSelected(true);
                student.setSelected(false);
            }
        });
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teacher.setSelected(false);
                student.setSelected(true);
            }
        });
    }
}
