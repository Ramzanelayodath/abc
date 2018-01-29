package com.example.areekkadan.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;

public class view_servicerProfile extends AppCompatActivity {
    String servicer_Id,service_Id;
    DatabaseHelper db;
    TextView name,email,phone;
    Button accept,decline;
    ArrayList<String> datas=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_servicer_profile);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db=new DatabaseHelper(this);
        db.getAllData();
        Intent i=getIntent();
        servicer_Id=i.getStringExtra("Servcier_Id");
        service_Id=i.getStringExtra("Service_Id");
        datas= db.getuserData(servicer_Id);
        name=(TextView)findViewById(R.id.txt_name);
        email=(TextView)findViewById(R.id.txt_email);
        phone=(TextView)findViewById(R.id.txt_phone);
        accept=(Button)findViewById(R.id.btn_accept);
        name.setText("");
        email.setText("");
        phone.setText("");
        name.append(datas.get(0).toString());
        email.append(datas.get(1).toString());
        phone.append(datas.get(2).toString());
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accept();


            }
        });
        decline=(Button)findViewById(R.id.btn_decline);
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decline();
            }
        });

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
    public void accept()
    {
        boolean status=db.acceptRequest(service_Id);
        if (status==true)
        {
            MDToast.makeText(view_servicerProfile.this,"Thank You.. Servicer will reach shortly", Toast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();
        }
        else {
            MDToast.makeText(view_servicerProfile.this,"Sorry..",Toast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
        }
    }

    public void decline()
    {
        boolean status=db.declineRequest(service_Id);
        if (status==true)
        {
            MDToast.makeText(view_servicerProfile.this,"Request Ignored", Toast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();
        }
        else {
            MDToast.makeText(view_servicerProfile.this,"Sorry..",Toast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
        }
    }
}
