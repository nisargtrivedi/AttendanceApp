package com.app.collegeattendance.student;

import android.os.Bundle;
import android.widget.GridView;

import androidx.annotation.Nullable;

import com.app.collegeattendance.R;
import com.app.collegeattendance.adapter.DashboardAdapter;
import com.app.collegeattendance.model.DashboardItem;

import java.util.ArrayList;
import java.util.List;

public class StudentDashboard extends BaseActivity {


    List<DashboardItem> dashboardItems=new ArrayList<>();
    DashboardAdapter adapter;
    GridView grid_dashboard;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_dashboard);
        grid_dashboard=findViewById(R.id.grid_dashboard);
        addItem();

    }
    public void addItem(){
        dashboardItems.clear();
        dashboardItems.add(new DashboardItem("Time-Table",R.drawable.ic_time_table));
        dashboardItems.add(new DashboardItem("Attendance", R.drawable.ic_attandance));
        dashboardItems.add(new DashboardItem("Event", R.drawable.ic_events));
        dashboardItems.add(new DashboardItem("Notification", R.drawable.ic_notofication));
        dashboardItems.add(new DashboardItem("Report", R.drawable.ic_results));
        dashboardItems.add(new DashboardItem("Profile", R.drawable.ic_remarks));
        dashboardItems.add(new DashboardItem("Logout", R.drawable.ic_logout));
        adapter = new DashboardAdapter(StudentDashboard.this, dashboardItems);
        grid_dashboard.setAdapter(adapter);
    }
}
