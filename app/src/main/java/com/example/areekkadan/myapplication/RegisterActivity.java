package com.example.areekkadan.myapplication;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.areekkadan.myapplication.Config.config;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    // GPSTracker class
    GPSTracker gps;
    Gps gps1;
    Button btn;
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    Context context;
    private AwesomeValidation awesomeValidation;
    EditText edt_name, edt_mobile, edt_email, edt_pincode, edt_password, edt_repeatpassword;
    DatabaseHelper myDb;
    String Url,ret_status,ret_message,ret_userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_register);
        config c = new config();
        Url = c.getUrlServer();
        myDb = new DatabaseHelper(this);
        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_mobile = (EditText) findViewById(R.id.edt_mobile);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_pincode = (EditText) findViewById(R.id.edt_pincode);
        edt_password = (EditText) findViewById(R.id.edt_password);
        edt_repeatpassword = (EditText) findViewById(R.id.edt_repeatpassword);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.edt_name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        awesomeValidation.addValidation(this, R.id.edt_email, Patterns.EMAIL_ADDRESS, R.string.emailerror);


    }

    public void Register(View v) {
        if (awesomeValidation.validate()) {
            if (edt_password.getText().toString().equals(edt_repeatpassword.getText().toString())) {
                String user_name = edt_name.getText().toString();
                String user_mobile = edt_mobile.getText().toString();
                String user_email = edt_email.getText().toString();
                String user_password = edt_password.getText().toString();
                String user_pin=edt_pincode.getText().toString();
                Url=Url+"?operation=2&txtName="+user_name+"&txtEmail="+user_email+"&txtMobile="+user_mobile+"&txtPass="+user_password+"&txtPin="+user_pin;
                final ProgressDialog progressDialog=new ProgressDialog(this);
                progressDialog.setMessage("Loading....");
                RequestQueue requestQueue   = Volley.newRequestQueue(this);
                StringRequest request = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        System.out.println(response.toString());
                        try {
                                JSONObject object=new JSONObject(response);
                                ret_status=object.getString("status");
                                ret_message=object.getString("message");
                                ret_userId=object.getString("userid");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (ret_status.equals("1"))
                        {
                            boolean status=myDb.storeuserId(ret_userId);
                            if (status==true)
                            {
                                System.out.println("userId stored");
                            }
                            else {
                                System.out.println("UserId not stored");
                            }
                            Intent intent=new Intent(RegisterActivity.this,Home.class);
                            startActivity(intent);

                        }
                        else {
                            MDToast.makeText(RegisterActivity.this,ret_message, Toast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();


                        return params;
                    }
                };
                requestQueue.add(request);


            } else {
                edt_repeatpassword.setError("Password and repeat passowrd are mismatch");
            }
        }
    }


}
