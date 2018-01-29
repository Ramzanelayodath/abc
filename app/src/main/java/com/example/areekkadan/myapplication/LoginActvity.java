package com.example.areekkadan.myapplication;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.areekkadan.myapplication.Config.config;
import com.valdesekamdem.library.mdtoast.MDToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActvity extends AppCompatActivity {
    EditText number,password;
    DatabaseHelper myDb;
    String ret_password,ret_userId,ret_status,ret_message,ret_userType;
    String Url;
    String ret_Status,ret_Message,ret_userid,ret_Usertype;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        setContentView(R.layout.activity_login_actvity);
        myDb = new DatabaseHelper(this);
        number=(EditText)findViewById(R.id.edt_mobile);
        password=(EditText)findViewById(R.id.edt_password);
        config  c=new config();
       //Url=c.getUrlServer()+"?operation=1&txtMob=8089178861&txtPass=asd123";
            Url=c.getUrlServer();
        System.out.println(Url);



    }
    public void gotoRegister(View view)
    {

        Intent i=new Intent(LoginActvity.this,RegisterActivity.class);
        startActivity(i);
    }

    public void gotoforgotPassword(View view)
    {
        Intent i=new Intent(LoginActvity.this,forgotpassword.class);
        startActivity(i);
    }

    public void showNotification()
    {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                        .setContentTitle("Audi")
                        .setContentText("Puncher Works")
                        .setSubText("Calicut");

        Intent notificationIntent = new Intent(this, ownerHome.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());


    }

    public void Login(View view)
    {
        final String str_mob=number.getText().toString();
        final String str_pass=password.getText().toString();
        Url=Url+"?operation=1&txtMob="+str_mob+"&txtPass="+str_pass;
        final ProgressDialog dialog=new ProgressDialog(this);
        dialog.setMessage("Loading....");
        dialog.show();
        RequestQueue requestQueue   = Volley.newRequestQueue(this);
        StringRequest stringRequest =   new StringRequest(Request.Method.GET, Url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();

                        System.out.println(response.toString());
                        try {
                                JSONObject object=new JSONObject(response);
                                ret_status=object.getString("status");
                                ret_message=object.getString("message");
                                ret_userId=object.getString("userid");
                                ret_userType=object.getString("usertype");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (ret_status.equals("1"))
                        {
                            boolean status =myDb.storeuserId(ret_userId);
                            if (status==true)
                            {
                                System.out.println("userId stored");
                            }
                            else {
                                System.out.println("UserId notstored");
                            }
                            if (ret_userType.equals("customer")) {
                                Intent i = new Intent(LoginActvity.this, Home.class);
                                startActivity(i);
                            }
                            else {
                                Intent i = new Intent(LoginActvity.this, ownerHome.class);
                                startActivity(i);
                            }

                        }
                        else {
                            MDToast.makeText(LoginActvity.this,ret_message, Toast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
                        }

                      /* */


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.dismiss();
                        //error.printStackTrace();

                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
               params.put("operation","1");
               params.put("txtMob",str_mob);
               params.put("txtPass",str_pass);

                return params;
            }
        };
        requestQueue.add(stringRequest);

    }
}
