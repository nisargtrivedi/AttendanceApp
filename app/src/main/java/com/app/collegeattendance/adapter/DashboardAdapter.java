package com.app.collegeattendance.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.collegeattendance.API.AppPreferences;
import com.app.collegeattendance.R;
import com.app.collegeattendance.Report;
import com.app.collegeattendance.model.DashboardItem;
import com.app.collegeattendance.student.AttendanceActivity;
import com.app.collegeattendance.student.StudentAnnouncement;
import com.app.collegeattendance.student.StudentAttendance;
import com.app.collegeattendance.student.StudentDashboard;
import com.app.collegeattendance.student.StudentEvent;
import com.app.collegeattendance.student.StudentLogin;
import com.app.collegeattendance.student.StudentProfile;
import com.app.collegeattendance.student.StudentTimeTable;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 5/11/2017.
 */

public class DashboardAdapter extends BaseAdapter {


    List<DashboardItem> list;
    Context context;

    AppPreferences appPreferences;
    LayoutInflater inflater;
    public DashboardAdapter(Context context, List<DashboardItem> list){
        this.context=context;
        this.list=list;
        inflater= LayoutInflater.from(context);
        appPreferences=new AppPreferences(context);
    }
    static class ViewHolder {
        TextView tv_item;
        ImageView img;
        LinearLayout main;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public DashboardItem getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final DashboardItem  item  = list.get(position);

        View view = convertView;
        ViewHolder viewHolder;
        if (view == null) {
            view = inflater.inflate(R.layout.dashboard_items, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.img = (ImageView) view.findViewById(R.id.img_item);
            viewHolder.tv_item = (TextView) view.findViewById(R.id.tv_item_name);
            viewHolder.main=(LinearLayout)view.findViewById(R.id.main);
            viewHolder.main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(item.title.equalsIgnoreCase("event")){
                        context.startActivity(new Intent(context, StudentEvent.class));
                    }
                    else if(item.title.equalsIgnoreCase("notification")){
                        context.startActivity(new Intent(context, StudentAnnouncement.class));
                    }
                    else if(item.title.equalsIgnoreCase("time-table")){
                        context.startActivity(new Intent(context, StudentTimeTable.class));
                    }
                    else if(item.title.equalsIgnoreCase("profile")){
                        context.startActivity(new Intent(context, StudentProfile.class));
                    }
                    else if(item.title.equalsIgnoreCase("report")){
                        context.startActivity(new Intent(context, Report.class));
                    }
                    else if(item.title.equalsIgnoreCase("Attendance")){
                        context.startActivity(new Intent(context, StudentAttendance.class));
                    }
                    else if(item.title.equalsIgnoreCase("logout")){
                        appPreferences.set("student_id","");
                        appPreferences.set("name","");
                        appPreferences.set("enroll","");
                        appPreferences.set("photo","");
                        ((StudentDashboard)context).finish();
                        context.startActivity(new Intent(context, StudentLogin.class));
                    }

                }
            });
            view.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tv_item.setText(item.title);
        Picasso.with(context).load(item.icon).into(viewHolder.img);
        viewHolder.tv_item.setTag(item.title);
        viewHolder.img.setTag(item.title);
        //viewHolder.main.setTag(item.title.toString());

        return view;
    }



}
