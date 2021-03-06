package com.app.collegeattendance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.collegeattendance.R;
import com.app.collegeattendance.model.Semester;
import com.app.collegeattendance.model.Subject;

import java.util.ArrayList;

public class SubjectAdapter extends BaseAdapter {

    public ArrayList<Subject> list;
    LayoutInflater inflater;
    Context context;
    public SubjectAdapter(Context context, ArrayList<Subject> list){
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
            view=inflater.inflate(R.layout.row_department,viewGroup,false);
            holder=new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        holder.textView.setText(list.get(i).SubjectName);
        return view;
    }

    public static class ViewHolder{
        TextView textView;
        public ViewHolder(View v){
            textView=v.findViewById(R.id.tvDName);
        }
    }
}
