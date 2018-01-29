package com.example.areekkadan.myapplication;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.ArrayList;

public class nearby_servicedetails extends AppCompatActivity {
    DatabaseHelper myDb;
    ArrayList<String>datas;
    TextView vehicle_name,service,fuel,vehicle_type,mYear,adInfo;
    String id,user_Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nearby_servicedetails);

            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            myDb = new DatabaseHelper(this);
            Intent i=getIntent();
             id=i.getStringExtra("Id");
             user_Id=myDb.getuserId();
            datas= myDb.getservicefullDetail(id);
            System.out.println(user_Id);
            //Toast.makeText(nearby_servicedetails.this, id, Toast.LENGTH_SHORT).show();
            vehicle_name=(TextView)findViewById(R.id.txt_vehiclename);
            vehicle_name.setText("");
            service=(TextView)findViewById(R.id.txt_service);
            service.setText("");
            fuel=(TextView)findViewById(R.id.txt_fuel);
            vehicle_type=(TextView)findViewById(R.id.txt_vType);
            mYear=(TextView)findViewById(R.id.txt_mYear);
            adInfo=(TextView)findViewById(R.id.txt_adInfo);


            vehicle_name.append(datas.get(0));
            service.append(datas.get(4));
            fuel.append(datas.get(2));
           vehicle_type.append(datas.get(1));
            mYear.append(datas.get(3));
            adInfo.append(datas.get(5));



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

    public void gotoDialer(View view)
    {
        String phone="8089178861";
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+phone));
        try {
            startActivity(intent);
        }catch (ActivityNotFoundException e){}
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

    public void Intrested(View v)
    {
       boolean status= myDb.setIntrest(id,user_Id);
       if (status==true)
       {
           MDToast.makeText(nearby_servicedetails.this,"Your Intrest Sented..", Toast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();
       }
       else {
           MDToast.makeText(nearby_servicedetails.this,"Please Try Again..", Toast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
       }
    }
}
