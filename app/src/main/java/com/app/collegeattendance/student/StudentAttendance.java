package com.app.collegeattendance.student;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.collegeattendance.API.Constants;
import com.app.collegeattendance.R;
import com.app.collegeattendance.adapter.AttendanceAdapter;
import com.app.collegeattendance.adapter.StudentSelectAttendanceAdapters;
import com.app.collegeattendance.adapter.TimeTableAdapters;
import com.app.collegeattendance.model.SAttendance;
import com.app.collegeattendance.model.TimeTable;
import com.app.collegeattendance.parsing.SAttendanceParsing;
import com.app.collegeattendance.parsing.TimeTableParsing;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentAttendance extends BaseActivity {

    TabLayout tablayout;
    RecyclerView lvTimeTable;
    StudentSelectAttendanceAdapters adapter;
    TextView msg;
    ArrayList<SAttendance> list=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_time_table);
        tablayout=findViewById(R.id.tablayout);
        lvTimeTable=findViewById(R.id.lvTimeTable);
        msg=findViewById(R.id.msg);

        tablayout.setVisibility(View.GONE);
        adapter=new StudentSelectAttendanceAdapters(StudentAttendance.this,list);
        lvTimeTable.setLayoutManager(new LinearLayoutManager(this));
        lvTimeTable.setAdapter(adapter);
        API();


    }
    public void API() {
        try {
            final ProgressDialog dialog=new ProgressDialog(StudentAttendance.this);
            dialog.setMessage("loading...");
            dialog.show();
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    Constants.URL+"action=get_student_attendance",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.dismiss();
                            list.clear();
                            System.out.println("Response-->"+ response.toString());
                            Gson gson=new Gson();
                            SAttendanceParsing departmentParsing= gson.fromJson(response,SAttendanceParsing.class);
                            list.addAll(departmentParsing.list);
                            adapter.notifyDataSetChanged();

                            if(list.size()==0){
                                msg.setVisibility(View.VISIBLE);
                                lvTimeTable.setVisibility(View.GONE);
                            }else{
                                msg.setVisibility(View.GONE);
                                lvTimeTable.setVisibility(View.VISIBLE);
                            }
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
            RequestQueue queue = Volley.newRequestQueue(StudentAttendance.this);
            queue.add(request);
        }catch (Exception ex){
            System.out.println("Error-->"+ ex.toString());
        }
    }
}
