package com.app.collegeattendance.ui.logout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
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
import com.app.collegeattendance.MainActivity;
import com.app.collegeattendance.R;
import com.app.collegeattendance.adapter.DepartmentAdapter;
import com.app.collegeattendance.adapter.SemesterAdapter;
import com.app.collegeattendance.adapter.SubjectAdapter;
import com.app.collegeattendance.model.Department;
import com.app.collegeattendance.model.Semester;
import com.app.collegeattendance.model.Subject;
import com.app.collegeattendance.parsing.DepartmentParsing;
import com.app.collegeattendance.parsing.SemesterPArsing;
import com.app.collegeattendance.parsing.SimpleParsing;
import com.app.collegeattendance.parsing.SubjectParsing;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Logout extends Fragment {

    AppPreferences appPreferences;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.logout, container, false);
        appPreferences=new AppPreferences(getActivity());

        appPreferences.set("facultyid","");
        appPreferences.set("name","");
        appPreferences.set("code","");
        appPreferences.set("photo","");
        getActivity().finish();
        getActivity().startActivity(new Intent(getActivity(), MainActivity.class));
        return root;
    }
}