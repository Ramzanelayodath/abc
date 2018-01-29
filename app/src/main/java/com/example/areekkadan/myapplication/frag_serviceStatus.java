package com.example.areekkadan.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.areekkadan.myapplication.Adapter.Adapter_serviceStatus_user;
import com.example.areekkadan.myapplication.Model.model_serviceStatus_user;

import java.util.ArrayList;



public class frag_serviceStatus extends Fragment {
    View v;
    ArrayList<model_serviceStatus_user> arrayList=null;
    ListView listView=null;
    DatabaseHelper myDb;
    Adapter_serviceStatus_user adapter=null;
    String user_id,service_id;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        v=inflater.inflate(R.layout.fragment_service_status, container, false);
        myDb = new DatabaseHelper(getActivity());
        user_id= myDb.getuserId();
        arrayList=myDb.getServicestatusofuser(user_id);
        System.out.println(arrayList);
        adapter=new Adapter_serviceStatus_user(getActivity(),R.layout.list_row_service_status,arrayList);
        listView=(ListView)v.findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView servicer_Id=(TextView)view.findViewById(R.id.txt_ownerId);
                TextView service_Id=(TextView)view.findViewById(R.id.txt_serviceId);
                String str_serviceId=service_Id.getText().toString();
                String str_servicerId=servicer_Id.getText().toString();
                if (Integer.parseInt(str_servicerId)>0)
                {
                    Intent in=new Intent(getActivity(),view_servicerProfile.class);
                    in.putExtra("Servcier_Id",str_servicerId);
                    in.putExtra("Service_Id",str_serviceId);
                    startActivity(in);


                }
            }
        });
        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Service Status");
    }
}
