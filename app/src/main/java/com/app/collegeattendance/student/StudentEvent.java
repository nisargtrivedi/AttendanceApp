package com.app.collegeattendance.student;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.collegeattendance.API.AppPreferences;
import com.app.collegeattendance.API.Constants;
import com.app.collegeattendance.R;
import com.app.collegeattendance.adapter.EventAdapter;
import com.app.collegeattendance.listeners.onEventDetailClick;
import com.app.collegeattendance.model.Events;
import com.app.collegeattendance.parsing.EventParsing;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentEvent extends BaseActivity {

    ListView lvAnnouncement;

    EventAdapter adapter;
    ArrayList<Events> list=new ArrayList<>();
    AppPreferences appPreferences;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tools);
        lvAnnouncement=findViewById(R.id.lvEvent);

        appPreferences=new AppPreferences(StudentEvent.this);
        adapter=new EventAdapter(StudentEvent.this,list);
        lvAnnouncement.setAdapter(adapter);
        API();
        adapter.onDetailCick(new onEventDetailClick() {
            @Override
            public void onClick(Events events) {
                showDetails(events.Details);
            }
        });
    }

    public void API() {
        try {
            final ProgressDialog dialog=new ProgressDialog(StudentEvent.this);
            dialog.setMessage("loading...");
            dialog.show();
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    Constants.URL+"action=event",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.dismiss();
                            System.out.println("Response-->"+ response.toString());
                            Gson gson=new Gson();
                            EventParsing departmentParsing= gson.fromJson(response,EventParsing.class);
                            list.addAll(departmentParsing.list);
                            adapter.notifyDataSetChanged();

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
            RequestQueue queue = Volley.newRequestQueue(StudentEvent.this);
            queue.add(request);
        }catch (Exception ex){
            System.out.println("Error-->"+ ex.toString());
        }
    }

    public void showDetails(String msg){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(StudentEvent.this);
        LayoutInflater inflater = StudentEvent.this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.details, null);
        dialogBuilder.setView(dialogView);

        TextView editText = (TextView) dialogView.findViewById(R.id.details);
        editText.setText(msg);
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
}
