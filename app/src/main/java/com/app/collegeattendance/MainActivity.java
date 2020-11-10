package com.app.collegeattendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.collegeattendance.API.AppPreferences;
import com.app.collegeattendance.API.Constants;
import com.app.collegeattendance.model.Faculty;
import com.app.collegeattendance.parsing.FacultyParsing;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText edtUsername;
    AppCompatButton btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSignIn=findViewById(R.id.btnSignIn);
        edtUsername=findViewById(R.id.edtUsername);

        AppPreferences appPreferences=new AppPreferences(this);
        if(!TextUtils.isEmpty(appPreferences.getString("facultyid"))){
            finish();
            startActivity(new Intent(this,HomeActivity.class));
        }
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(edtUsername.getText().toString())){
                    Toast.makeText(MainActivity.this,"please enter username",Toast.LENGTH_LONG).show();
                }else
                    API();
            }
        });
    }

    public void API() {
        try {
            final ProgressDialog dialog=new ProgressDialog(this);
            dialog.setMessage("loading...");
            dialog.show();
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    Constants.URL+"action=faculty_login&fid="+edtUsername.getText().toString(),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.dismiss();
                            System.out.println("Response-->"+ response.toString());
                            Gson gson=new Gson();
                            FacultyParsing faculty= gson.fromJson(response,FacultyParsing.class);
                            AppPreferences appPreferences=new AppPreferences(MainActivity.this);
                            appPreferences.set("facultyid",faculty.list.get(0).FacultyID);
                            appPreferences.set("name",faculty.list.get(0).FacultyName);
                            appPreferences.set("code",faculty.list.get(0).FacultyCode);
                            appPreferences.set("photo",faculty.list.get(0).FacultyPhoto);

                            finish();
                            startActivity(new Intent(MainActivity.this,HomeActivity.class));

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
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        }catch (Exception ex){
            System.out.println("Error-->"+ ex.toString());
        }
    }
}
