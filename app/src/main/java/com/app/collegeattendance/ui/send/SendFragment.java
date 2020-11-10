package com.app.collegeattendance.ui.send;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
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
import com.app.collegeattendance.HomeActivity;
import com.app.collegeattendance.MainActivity;
import com.app.collegeattendance.R;
import com.app.collegeattendance.adapter.DepartmentAdapter;
import com.app.collegeattendance.adapter.SemesterAdapter;
import com.app.collegeattendance.adapter.SubjectAdapter;
import com.app.collegeattendance.model.Department;
import com.app.collegeattendance.model.Semester;
import com.app.collegeattendance.model.Subject;
import com.app.collegeattendance.parsing.DepartmentParsing;
import com.app.collegeattendance.parsing.FacultyParsing;
import com.app.collegeattendance.parsing.SemesterPArsing;
import com.app.collegeattendance.parsing.SimpleParsing;
import com.app.collegeattendance.parsing.SubjectParsing;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SendFragment extends Fragment {

    ArrayList<Department> parsings=new ArrayList<>();
    ArrayList<Semester> semesterArrayList=new ArrayList<>();
    ArrayList<Subject> subjectArrayList=new ArrayList<>();

    Spinner spDepartment,spSemester,spSubject;
    DepartmentAdapter adapter;
    SemesterAdapter semesterAdapter;
    SubjectAdapter subjectAdapter;

    Semester selectedItem;
    Department department;
    Subject subject;
    AppPreferences appPreferences;
    AppCompatButton btnSignIn;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_attendance, container, false);
        spDepartment=root.findViewById(R.id.spDepartment);
        spSemester=root.findViewById(R.id.spSemester);
        spSubject=root.findViewById(R.id.spSubject);
        btnSignIn=root.findViewById(R.id.btnSignIn);

        appPreferences=new AppPreferences(getActivity());
        adapter=new DepartmentAdapter(getActivity(),parsings);
        spDepartment.setAdapter(adapter);

        semesterAdapter=new SemesterAdapter(getActivity(),semesterArrayList);
        spSemester.setAdapter(semesterAdapter);

        subjectAdapter=new SubjectAdapter(getActivity(),subjectArrayList);
        spSubject.setAdapter(subjectAdapter);

        API();
        spDepartment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                department= (Department) adapterView.getSelectedItem();
                SEMESTERAPI(department.DepartmentID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spSemester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedItem= (Semester) adapterView.getSelectedItem();
                SUBJECTAPI();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                subject= (Subject) adapterView.getSelectedItem();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CREATEAPI();
            }
        });
        return root;
    }

    public void API() {
        try {
            final ProgressDialog dialog=new ProgressDialog(getActivity());
            dialog.setMessage("loading...");
            dialog.show();
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    Constants.URL+"action=getalldepartment",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.dismiss();
                            System.out.println("Response-->"+ response.toString());
                            Gson gson=new Gson();
                            DepartmentParsing departmentParsing= gson.fromJson(response,DepartmentParsing.class);
                            parsings.addAll(departmentParsing.list);
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

    public void SEMESTERAPI(String did) {
        try {
            final ProgressDialog dialog=new ProgressDialog(getActivity());
            dialog.setMessage("loading...");
            dialog.show();
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    Constants.URL+"action=getdepartmentsemester&did="+did,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.dismiss();
                            semesterArrayList.clear();
                            System.out.println("Response-->"+ response.toString());
                            Gson gson=new Gson();
                            SemesterPArsing departmentParsing= gson.fromJson(response,SemesterPArsing.class);
                            semesterArrayList.addAll(departmentParsing.list);
                            semesterAdapter.notifyDataSetChanged();

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

    public void SUBJECTAPI() {
        try {
            final ProgressDialog dialog=new ProgressDialog(getActivity());
            dialog.setMessage("loading...");
            dialog.show();
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    Constants.URL+"action=getallsubject",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.dismiss();
                            subjectArrayList.clear();
                            System.out.println("Response-->"+ response.toString());
                            Gson gson=new Gson();
                            SubjectParsing departmentParsing= gson.fromJson(response,SubjectParsing.class);
                            subjectArrayList.addAll(departmentParsing.list);
                            subjectAdapter.notifyDataSetChanged();

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


    public void CREATEAPI() {
        try {
            final ProgressDialog dialog=new ProgressDialog(getActivity());
            dialog.setMessage("loading...");
            dialog.show();
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    Constants.URL+"action=create_attendance&facultyid="+appPreferences.getString("facultyid")+"&subjectid="+subject.SubjectID+"&departmentid="+department.DepartmentID+"&semesterid="+selectedItem.SemesterID,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.dismiss();

                            System.out.println("Response-->"+ response.toString());
                            Gson gson=new Gson();
                            SimpleParsing departmentParsing= gson.fromJson(response,SimpleParsing.class);


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