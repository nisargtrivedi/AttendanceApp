package com.app.collegeattendance.student;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.app.collegeattendance.adapter.TimeTableAdapter;
import com.app.collegeattendance.adapter.TimeTableAdapters;
import com.app.collegeattendance.model.TimeTable;
import com.app.collegeattendance.parsing.TimeTableParsing;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentTimeTable extends BaseActivity {

    TabLayout tablayout;
    RecyclerView lvTimeTable;
    TimeTableAdapters adapter;
    TextView msg;
    ArrayList<TimeTable> list=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_time_table);
        tablayout=findViewById(R.id.tablayout);
        lvTimeTable=findViewById(R.id.lvTimeTable);
        msg=findViewById(R.id.msg);
        tablayout.addTab(tablayout.newTab().setText(getString(R.string.TIME_TABLE_MONDAY)));
        tablayout.addTab(tablayout.newTab().setText(getString(R.string.TIME_TABLE_TUESDAY)));
        tablayout.addTab(tablayout.newTab().setText(getString(R.string.TIME_TABLE_WEDNESDAY)));
        tablayout.addTab(tablayout.newTab().setText(getString(R.string.TIME_TABLE_THURSDAY)));
        tablayout.addTab(tablayout.newTab().setText(getString(R.string.TIME_TABLE_FRIDAY)));
        tablayout.addTab(tablayout.newTab().setText(getString(R.string.TIME_TABLE_SATURDAY)));

        adapter=new TimeTableAdapters(StudentTimeTable.this,list);
        lvTimeTable.setLayoutManager(new LinearLayoutManager(this));
        lvTimeTable.setAdapter(adapter);
        API(1);

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // viewPager.setCurrentItem(tab.getPosition());
                // Toast.makeText(TimeTable.this,""+tab.getPosition(),Toast.LENGTH_LONG).show();
                showData(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }


            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void showData(int position){
        switch (position){
            case 0 :
                API(1);
                break;
            case 1 :
                API(2);
                break;
            case 2 :
                API(3);
                break;
            case 3 :
                API(4);
                break;
            case 4 :
                API(5);
                break;
            case 5 :
                API(6);
                break;
            case 6 :
                API(7);
                break;
        }
    }

    public void API(int id) {
        try {
            final ProgressDialog dialog=new ProgressDialog(StudentTimeTable.this);
            dialog.setMessage("loading...");
            dialog.show();
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    Constants.URL+"action=gettimetable&sid="+id,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.dismiss();
                            list.clear();
                            System.out.println("Response-->"+ response.toString());
                            Gson gson=new Gson();
                            TimeTableParsing departmentParsing= gson.fromJson(response,TimeTableParsing.class);
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
            RequestQueue queue = Volley.newRequestQueue(StudentTimeTable.this);
            queue.add(request);
        }catch (Exception ex){
            System.out.println("Error-->"+ ex.toString());
        }
    }
}
