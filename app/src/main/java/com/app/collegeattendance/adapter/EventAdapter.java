package com.app.collegeattendance.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.collegeattendance.R;
import com.app.collegeattendance.listeners.onEventDetailClick;
import com.app.collegeattendance.model.Announcement;
import com.app.collegeattendance.model.Events;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EventAdapter extends BaseAdapter {

    public ArrayList<Events> list;
    LayoutInflater inflater;
    Context context;
    onEventDetailClick detailClick;
    public EventAdapter(Context context, ArrayList<Events> list){
        this.context=context;
        this.list=list;
        inflater=LayoutInflater.from(context);
    }
    public void onDetailCick(onEventDetailClick events){
        this.detailClick=events;
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
            view=inflater.inflate(R.layout.row_event,viewGroup,false);
            holder=new ViewHolder(view);
            view.setTag(holder);
        }else{
            holder= (ViewHolder) view.getTag();
        }
        holder.tvDetails.setText(list.get(i).Details);

        Picasso.with(context).load(list.get(i).Photo).into(holder.img);

        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
        String inputDateStr=list.get(i).EventDate;
        Date date = null;
        try {
            date = inputFormat.parse(inputDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String outputDateStr = outputFormat.format(date);
        holder.tvDate.setText(outputDateStr);

        holder.tvDName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailClick.onClick(list.get(i));
            }
        });
        return view;
    }

    public static class ViewHolder{
        TextView tvDate,tvDetails;
        ImageView img;
        LinearLayout tvDName;
        public ViewHolder(View v){

            tvDetails=v.findViewById(R.id.tvDetails);
            tvDate=v.findViewById(R.id.tvDate);
            img=v.findViewById(R.id.img);
            tvDName=v.findViewById(R.id.tvDName);
        }
    }
}
