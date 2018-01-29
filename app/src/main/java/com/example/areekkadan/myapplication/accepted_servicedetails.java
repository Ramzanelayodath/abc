package com.example.areekkadan.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class accepted_servicedetails extends AppCompatActivity {
    String str_serviceId,str_userId,str_status;
    TextView name,service,fuel,vehicletype,year,adinfo,username,phone;
    Button button_going,button_cancel;
    DatabaseHelper myDb;
    ArrayList<String> datas=null;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accepted_servicedetails);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myDb=new DatabaseHelper(this);
        Intent i=getIntent();
        str_serviceId=i.getStringExtra("service_Id");
        str_userId=i.getStringExtra("user_Id");
        str_status=i.getStringExtra("status");
        name=(TextView)findViewById(R.id.txt_name);
        service=(TextView)findViewById(R.id.txt_service);
        fuel=(TextView)findViewById(R.id.txt_fuel);
        vehicletype=(TextView)findViewById(R.id.txt_vehicletype);
        year=(TextView)findViewById(R.id.txt_year);
        adinfo=(TextView)findViewById(R.id.txt_adInfo);
        username=(TextView)findViewById(R.id.txt_userName);
        phone=(TextView)findViewById(R.id.txt_phone);
       // datas=myDb.getservicefullDetail(str_serviceId,str_userId);
        name.setText(datas.get(0).toString());
        service.setText(datas.get(4).toString());
        fuel.append(datas.get(2).toString());
        vehicletype.append(datas.get(1).toString());
        year.append(datas.get(3).toString());
        adinfo.setText(datas.get(5).toString());
        username.append(datas.get(6).toString());
        phone.append(datas.get(7).toString());
        button_going=(Button)findViewById(R.id.btn_going);
        button_cancel=(Button)findViewById(R.id.btn_cancel);

        if (str_status.equals("Request Accepted"))
        {

            button_going.setVisibility(View.VISIBLE);
            button_cancel.setVisibility(View.VISIBLE);

        }
        else
        {
           button_going.setVisibility(View.GONE);
           button_cancel.setVisibility(View.VISIBLE);
        }
        button_going.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
    public void gotoMap(View view)
    {
        try {
            String latitude="8.5241";
            String longitude="76.9366";
            double lat = Double.parseDouble(latitude);
            double longi = Double.parseDouble(longitude);
            String label = "";
            String uriBegin = "geo:" + lat + "," + longi;
            String query = lat + "," + longi + "(" + label + ")";
            String encodedQuery = Uri.encode(query);
            String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
            Uri uri = Uri.parse(uriString);
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
            try{
                startActivity(intent);}catch (ActivityNotFoundException e){}
        }catch (NumberFormatException e){}

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id==android.R.id.home)
        {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
