package com.app.collegeattendance.student;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.app.collegeattendance.R;
import com.squareup.picasso.Picasso;

public class StudentProfile extends BaseActivity {

    EditText edtFullname,edtEnroll;
    ImageView img;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_profile);
        edtEnroll=findViewById(R.id.edtEnroll);
        edtFullname=findViewById(R.id.edtFullname);
        img=findViewById(R.id.img);

        edtFullname.setText(appPreferences.getString("name"));
        edtEnroll.setText(appPreferences.getString("enroll"));
        Picasso.with(this).load(appPreferences.getString("photo")).into(img);
    }
}
