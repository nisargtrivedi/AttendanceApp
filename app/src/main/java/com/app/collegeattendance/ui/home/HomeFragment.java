package com.app.collegeattendance.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

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
import com.app.collegeattendance.adapter.TimeTableAdapter;
import com.app.collegeattendance.model.TimeTable;
import com.app.collegeattendance.parsing.DepartmentParsing;
import com.app.collegeattendance.parsing.TimeTableParsing;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.prefs.AbstractPreferences;

public class HomeFragment extends Fragment {


    ListView lvTimeTable;
    TimeTableAdapter adapter;
    ArrayList<TimeTable> list=new ArrayList<>();
    AppPreferences appPreferences;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        lvTimeTable=root.findViewById(R.id.lvTimeTable);

        appPreferences=new AppPreferences(getActivity());
        adapter=new TimeTableAdapter(getContext(),list);
        lvTimeTable.setAdapter(adapter);
        API();
        return root;
    }

    public void API() {
        try {
            final ProgressDialog dialog=new ProgressDialog(getActivity());
            dialog.setMessage("loading...");
            dialog.show();
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    Constants.URL+"action=getfacultytimetable&fid="+appPreferences.getString("facultyid"),
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
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            queue.add(request);
        }catch (Exception ex){
            System.out.println("Error-->"+ ex.toString());
        }
    }
}