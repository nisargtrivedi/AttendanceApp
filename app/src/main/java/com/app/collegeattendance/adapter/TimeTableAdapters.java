package com.app.collegeattendance.adapter;

import android.content.Context;
import android.graphics.Color;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.app.collegeattendance.API.AppPreferences;

import com.app.collegeattendance.R;
import com.app.collegeattendance.model.TimeTable;

import java.util.ArrayList;

/**
 * Created by admin on 7/31/2017.
 */

public class TimeTableAdapters extends  RecyclerView.Adapter<TimeTableAdapters.ViewHolder> {

    private String[] colors={"#3498db","#2ecc71","#9b59b6","#f1c40f","#1abc9c","#2980b9","#8e44ad","#e41c1c","#752ecc","#2ecc53"};
    public ArrayList<TimeTable> list;
    Context context;
    AppPreferences preferences;
    public TimeTableAdapters(Context context, ArrayList<TimeTable> list){
        this.context=context;
        this.list=list;
        preferences=new AppPreferences(context);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView teacher_name,time,subject_name;

        public ViewHolder(View view) {
            super(view);
            teacher_name = (TextView) view.findViewById(R.id.teacher);
            time = (TextView) view.findViewById(R.id.time);
            subject_name = (TextView) view.findViewById(R.id.subjects);
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timetable_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final TimeTable timeTable_model = list.get(position);


   // for(int i=0;i<colors.length;i++){

   // }


            holder.teacher_name.setText(timeTable_model.FacultyName);
            holder.subject_name.setText(timeTable_model.SubjectName);
            holder.time.setText(timeTable_model.Time);
            holder.time.setBackgroundColor(Color.parseColor(colors[position]));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
