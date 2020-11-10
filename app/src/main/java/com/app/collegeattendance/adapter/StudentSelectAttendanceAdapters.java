package com.app.collegeattendance.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.collegeattendance.API.AppPreferences;
import com.app.collegeattendance.R;
import com.app.collegeattendance.model.SAttendance;
import com.app.collegeattendance.model.TimeTable;
import com.app.collegeattendance.student.AttendanceActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by admin on 7/31/2017.
 */

public class StudentSelectAttendanceAdapters extends  RecyclerView.Adapter<StudentSelectAttendanceAdapters.ViewHolder> {

    private String[] colors={"#3498db","#2ecc71","#9b59b6","#f1c40f","#1abc9c","#2980b9","#8e44ad","#e41c1c","#752ecc","#2ecc53"};
    public ArrayList<SAttendance> list;
    Context context;
    AppPreferences preferences;
    public StudentSelectAttendanceAdapters(Context context, ArrayList<SAttendance> list){
        this.context=context;
        this.list=list;
        preferences=new AppPreferences(context);
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView teacher_name,time,subject_name;
        public CardView card;
        public ViewHolder(View view) {
            super(view);
            teacher_name = (TextView) view.findViewById(R.id.teacher);
            time = (TextView) view.findViewById(R.id.time);
            subject_name = (TextView) view.findViewById(R.id.subjects);
            card=view.findViewById(R.id.card);
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
        final SAttendance timeTable_model = list.get(position);


   // for(int i=0;i<colors.length;i++){

   // }


        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh a");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy hh:00 a");
        String inputDateStr=list.get(position).Time;
        Date date = null;
        try {
            date = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr = outputFormat.format(date);
            holder.teacher_name.setText(timeTable_model.FacultyName);
            holder.subject_name.setText(timeTable_model.SubjectName);
            holder.time.setText(outputDateStr);
            if(position<10){
                holder.time.setBackgroundColor(Color.parseColor(colors[position]));
            }else{
                holder.time.setBackgroundColor(Color.parseColor(colors[position/10]));
            }


            holder.card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(context, AttendanceActivity.class)
                    .putExtra("aid",timeTable_model.AID)
                    );
                }
            });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
