package com.app.collegeattendance.student;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.collegeattendance.API.Constants;
import com.app.collegeattendance.R;
import com.app.collegeattendance.parsing.SimpleParsing;
import com.app.collegeattendance.parsing.StudentParsing;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class StudentRegistration extends BaseActivity {

    AppCompatButton btnSignIn,btnSignUp;
    EditText edtUsername,edtEnNo,edtAddress;
    TextView edtDOB;

    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private Cipher cipher;
    private FingerprintManager.CryptoObject cryptoObject;

    private FingerprintHandler fingerprintHandler;

    private static final String FINGERPRINT_KEY = "key_name";

    private static final int REQUEST_USE_FINGERPRINT = 300;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_registration);
        edtUsername=findViewById(R.id.edtUsername);
        edtDOB=findViewById(R.id.edtDOB);
        edtEnNo=findViewById(R.id.edtEnNo);
        edtAddress=findViewById(R.id.edtAddress);
        btnSignIn=findViewById(R.id.btnSignIn);
        btnSignUp=findViewById(R.id.btnSignUp);

        fingerprintHandler = new FingerprintHandler(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
        }
        keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

        // check support for android fingerprint on device
        checkDeviceFingerprintSupport();
        //generate fingerprint keystore
        generateFingerprintKeyStore();
        //instantiate Cipher class
        Cipher mCipher = instantiateCipher();
        if(mCipher != null){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cryptoObject = new FingerprintManager.CryptoObject(mCipher);
            }
        }


        edtDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(StudentRegistration.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                edtDOB.setText(year + "-" + (monthOfYear + 1) + "-" +dayOfMonth);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(StudentRegistration.this,StudentLogin.class));
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(edtEnNo.getText().toString())){
                    Toast.makeText(StudentRegistration.this,"Enter Enrollment Number",Toast.LENGTH_LONG).show();
                }else if(edtEnNo.getText().length()<12){
                    Toast.makeText(StudentRegistration.this,"Enter Enrollment number 12 character",Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(edtUsername.getText().toString())){
                    Toast.makeText(StudentRegistration.this,"Enter Username",Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(edtDOB.getText().toString())){
                    Toast.makeText(StudentRegistration.this,"Enter DOB",Toast.LENGTH_LONG).show();
                }else if(TextUtils.isEmpty(edtAddress.getText().toString())){
                    Toast.makeText(StudentRegistration.this,"Enter Address",Toast.LENGTH_LONG).show();
                }else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            fingerprintHandler.completeFingerAuthentication(fingerprintManager, cryptoObject);
                        }
                }
            }
        });

    }

    private void checkDeviceFingerprintSupport() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.USE_FINGERPRINT}, REQUEST_USE_FINGERPRINT);
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!fingerprintManager.isHardwareDetected()) {
                    Toast.makeText(StudentRegistration.this, "Fingerprint is not supported in this device", Toast.LENGTH_LONG).show();
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!fingerprintManager.hasEnrolledFingerprints()) {
                    Toast.makeText(StudentRegistration.this, "Fingerprint not yet configured", Toast.LENGTH_LONG).show();
                }
            }
            if (!keyguardManager.isKeyguardSecure()) {
                Toast.makeText(StudentRegistration.this, "Screen lock is not secure and enable", Toast.LENGTH_LONG).show();
            }
            return;
        }
    }

    private void generateFingerprintKeyStore(){
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
        try {
            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                keyGenerator.init(new KeyGenParameterSpec.Builder(FINGERPRINT_KEY, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setUserAuthenticationRequired(true)
                        .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
                        .build());
            }
            keyGenerator.generateKey();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

    }

    private Cipher instantiateCipher(){
        try {
            cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            keyStore.load(null);
            SecretKey secretKey = (SecretKey)keyStore.getKey(FINGERPRINT_KEY, null);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher;
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | UnrecoverableKeyException |
                CertificateException | IOException | KeyStoreException | InvalidKeyException e) {
            throw new RuntimeException("Failed to instantiate Cipher class");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUEST_USE_FINGERPRINT){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                // check support for android fingerprint on device
                checkDeviceFingerprintSupport();
                //generate fingerprint keystore
                generateFingerprintKeyStore();
                //instantiate Cipher class
                Cipher mCipher = instantiateCipher();
                if(mCipher != null){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        cryptoObject = new FingerprintManager.CryptoObject(mCipher);
                    }
                }
            }
            else{
                Toast.makeText(this, R.string.permission_refused, Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, getString(R.string.Unknown_permission_request), Toast.LENGTH_LONG).show();
        }
    }
    @SuppressLint("NewApi")
    public class FingerprintHandler extends FingerprintManager.AuthenticationCallback{

        private final String TAG = AttendanceActivity.FingerprintHandler.class.getSimpleName();

        private Context context;

        public FingerprintHandler(Context context){
            this.context = context;
        }

        @Override
        public void onAuthenticationError(int errorCode, CharSequence errString) {
            super.onAuthenticationError(errorCode, errString);
            Log.d(TAG, "Error message " + errorCode + ": " + errString);
            Toast.makeText(context, context.getString(R.string.authenticate_fingerprint), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
            super.onAuthenticationHelp(helpCode, helpString);
            Toast.makeText(context, R.string.auth_successful, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
            super.onAuthenticationSucceeded(result);
                Toast.makeText(context, context.getString(R.string.auth_successful), Toast.LENGTH_LONG).show();
                API();
        }

        @Override
        public void onAuthenticationFailed() {
            super.onAuthenticationFailed();
        }

        public void completeFingerAuthentication(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject){
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            try{
                fingerprintManager.authenticate(cryptoObject, new CancellationSignal(), 0, this, null);
            }catch (SecurityException ex) {
                Log.d(TAG, "An error occurred:\n" + ex.getMessage());
            } catch (Exception ex) {
                Log.d(TAG, "An error occurred\n" + ex.getMessage());
            }
        }
    }
    public void API() {
        try {
            final ProgressDialog dialog=new ProgressDialog(this);
            dialog.setMessage("loading...");
            dialog.show();
            StringRequest request = new StringRequest(
                    Request.Method.GET,
                    Constants.URL+"action=registration&enroll_no="+edtEnNo.getText().toString()+"&student_name="+edtUsername.getText().toString().trim()+"&address="+edtAddress.getText().toString().trim()+"&dob="+edtDOB.getText().toString().trim(),
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.dismiss();
                            System.out.println("Response-->"+ response.toString());
                            Gson gson=new Gson();
                            SimpleParsing faculty= gson.fromJson(response, SimpleParsing.class);
                            finish();
                           startActivity(new Intent(StudentRegistration.this, StudentLogin.class));

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
