package com.app.collegeattendance.student;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.collegeattendance.API.AppPreferences;
import com.app.collegeattendance.API.Constants;
import com.app.collegeattendance.HomeActivity;
import com.app.collegeattendance.MainActivity;
import com.app.collegeattendance.R;
import com.app.collegeattendance.parsing.FacultyParsing;
import com.app.collegeattendance.parsing.StudentParsing;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class StudentLogin extends BaseActivity {

    AppCompatButton btnSignIn,btnSignUp;
    EditText edtUsername;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_login);
        edtUsername=findViewById(R.id.edtUsername);
        btnSignIn=findViewById(R.id.btnSignIn);
        btnSignUp=findViewById(R.id.btnSignUp);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(edtUsername.getText().toString())){
                    Snackbar.make(view, "Please enter enrollment number", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }else{
                    if(edtUsername.getText().length()<12){
                        Snackbar.make(view, "Please enter proper enrollment number", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }else
                        API();
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();startActivity(new Intent(StudentLogin.this,StudentRegistration.class));
            }
        });

    }

    public void API() {
        try {
            final ProgressDialog dialog=new ProgressDialog(this);
            dialog.setMessage("loading...");
            dialog.show();
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    Constants.URL+"action=student_login&enno="+edtUsername.getText().toString(),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.dismiss();
                            System.out.println("Response-->"+ response.toString());
                            Gson gson=new Gson();
                            StudentParsing faculty= gson.fromJson(response,StudentParsing.class);
                            appPreferences.set("student_id",faculty.list.get(0).StudentID);
                            appPreferences.set("name",faculty.list.get(0).FullName);
                            appPreferences.set("enroll",faculty.list.get(0).EnrollNo);
                            appPreferences.set("photo",faculty.list.get(0).Photo);

                            finish();
                           startActivity(new Intent(StudentLogin.this, StudentDashboard.class));

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismiss();
                    System.out.println("Error Response-->"+ error.toString());
                }
            }) {

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<>();
                    return hashMap;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        }catch (Exception ex){
            System.out.println("Error-->"+ ex.toString());
        }
    }
}
