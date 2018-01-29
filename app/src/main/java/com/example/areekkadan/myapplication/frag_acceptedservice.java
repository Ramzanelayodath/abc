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

import com.example.areekkadan.myapplication.Adapter.Adapter_acceptedService_owner;
import com.example.areekkadan.myapplication.Model.model_acceptedService_owner;

import java.util.ArrayList;


public class frag_acceptedservice extends Fragment {
    ArrayList<model_acceptedService_owner> arrayList=null;
    ListView listView=null;
    DatabaseHelper myDb;
    Adapter_acceptedService_owner adapter=null;
    String user_id;
    View v;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       v=inflater.inflate(R.layout.fragment_frag_acceptedservice, container, false);
        myDb = new DatabaseHelper(getActivity());
        user_id= myDb.getuserId();
        arrayList=myDb.getAcceptedservices(user_id);
        System.out.println(arrayList);
        adapter=new Adapter_acceptedService_owner(getActivity(),R.layout.list_row_acceptedservice,arrayList);
        listView=(ListView)v.findViewById(R.id.list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView serviceId=(TextView)view.findViewById(R.id.txt_serviceId);
                TextView userId=(TextView)view.findViewById(R.id.txt_userId);
                String str_serviceId=serviceId.getText().toString();
                String str_userId=userId.getText().toString();
                TextView status=(TextView)view.findViewById(R.id.txt_status);
                String str_status=status.getText().toString();
                gotoacceptedsrviceDetails(str_serviceId,str_userId,str_status);
            }
        });


       return  v;
    }

    private void gotoacceptedsrviceDetails(String serviceId,String userId,String status) {
        Intent i= new Intent(getActivity(),accepted_servicedetails.class);
        i.putExtra("service_Id",serviceId);
        i.putExtra("user_Id",userId);
        i.putExtra("status",status);
        startActivity(i);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Accepted Service");
    }
}
