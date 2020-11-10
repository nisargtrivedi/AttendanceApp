package com.app.collegeattendance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.collegeattendance.R;
import com.app.collegeattendance.model.Department;
import com.app.collegeattendance.model.TimeTable;

import java.util.ArrayList;

public class TimeTableAdapter extends BaseAdapter {

    public ArrayList<TimeTable> list;
    LayoutInflater inflater;
    Context context;
    public TimeTableAdapter(Context context, ArrayList<TimeTable> list){
        this.context=context;
        this.list=list;
        inflater=LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if(view==null){
            view=inflater.inflate(R.layout.row_timetable,viewGroup,false);
            holder=new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        holder.tvDay.setText(list.get(i).DayName);
        //holder.tvFaculty.setText(list.get(i).FacultyName);
        holder.tvSubject.setText(list.get(i).SubjectName);
        holder.tvTime.setText(list.get(i).Time);
        return view;
    }

    public static class ViewHolder{
        TextView tvDay,tvSubject,tvFaculty,tvTime;
        public ViewHolder(View v){

            tvDay=v.findViewById(R.id.tvDay);
            tvSubject=v.findViewById(R.id.tvSubject);
            //tvFaculty=v.findViewById(R.id.tvFaculty);
            tvTime=v.findViewById(R.id.tvTime);
        }
    }
}
