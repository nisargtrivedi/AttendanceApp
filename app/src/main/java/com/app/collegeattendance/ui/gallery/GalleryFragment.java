package com.app.collegeattendance.ui.gallery;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

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
import com.app.collegeattendance.adapter.AttendanceAdapter;
import com.app.collegeattendance.model.FilAttendance;
import com.app.collegeattendance.parsing.AttendanceParsing;
import com.app.collegeattendance.parsing.SimpleParsing;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GalleryFragment extends Fragment {

    ListView lvFillAttendance;

    AppPreferences appPreferences;
    ArrayList<FilAttendance> list=new ArrayList<>();
    AttendanceAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        lvFillAttendance=root.findViewById(R.id.lvFillAttendance);
        appPreferences=new AppPreferences(getActivity());

        adapter=new AttendanceAdapter(getActivity(),list);
        lvFillAttendance.setAdapter(adapter);
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
                    Constants.URL+"action=get_fill_attendance&fid="+appPreferences.getString("facultyid"),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.dismiss();
                            System.out.println("Response-->"+ response.toString());
                            Gson gson=new Gson();
                            AttendanceParsing departmentParsing= gson.fromJson(response,AttendanceParsing.class);
                            if(departmentParsing.Code==200){
                               list.addAll(departmentParsing.list);
                            }
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