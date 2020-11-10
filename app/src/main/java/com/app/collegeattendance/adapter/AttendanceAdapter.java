package com.app.collegeattendance.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.collegeattendance.R;
import com.app.collegeattendance.listeners.onDetailClick;
import com.app.collegeattendance.model.Announcement;
import com.app.collegeattendance.model.FilAttendance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AttendanceAdapter extends BaseAdapter {

    public ArrayList<FilAttendance> list;
    LayoutInflater inflater;
    Context context;
    onDetailClick detailClick;
    public AttendanceAdapter(Context context, ArrayList<FilAttendance> list){
        this.context=context;
        this.list=list;
        inflater=LayoutInflater.from(context);
    }
    public void onCLick(onDetailClick detailClick){
        this.detailClick=detailClick;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if(view==null){
            view=inflater.inflate(R.layout.row_attendance,viewGroup,false);
            holder=new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        holder.tvDate.setTextColor(Color.parseColor("#ff00ff"));


        holder.tvDepartment.setTextColor(Color.parseColor("#0000ff"));
        holder.tvDepartment.setText(list.get(i).DepartmentName);
        if(list.get(i).Present.equalsIgnoreCase("present")){
            holder.tvPresent.setTextColor(Color.parseColor("#006200"));
            holder.tvStudentName.setBackgroundColor(Color.parseColor("#006200"));
        }else{
            holder.tvStudentName.setBackgroundColor(Color.parseColor("#ff0000"));
            holder.tvPresent.setTextColor(Color.parseColor("#ff0000"));
        }
        holder.tvPresent.setText(list.get(i).Present.toUpperCase().toString());

        holder.tvSemester.setTextColor(Color.parseColor("#00b300"));
        holder.tvSemester.setText(list.get(i).SemesterName);
        holder.tvSubject.setText(list.get(i).Subject);


        holder.tvStudentName.setText(list.get(i).StudentName);

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh a");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy hh:00 a");
        String inputDateStr=list.get(i).FDate;
        Date date = null;
        try {
            date = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr = outputFormat.format(date);

        holder.tvDate.setText(outputDateStr);
        return view;
    }

    public static class ViewHolder{
        TextView tvStudentName,tvDepartment,tvSemester,tvSubject,tvPresent,tvDate;
        LinearLayout tvDName;
        public ViewHolder(View v){

            tvStudentName=v.findViewById(R.id.tvStudentName);
            tvDepartment=v.findViewById(R.id.tvDepartment);
            tvSemester=v.findViewById(R.id.tvSemester);
            tvSubject=v.findViewById(R.id.tvSubject);
            tvPresent=v.findViewById(R.id.tvPresent);
            tvDate=v.findViewById(R.id.tvDate);
        }
    }


}
