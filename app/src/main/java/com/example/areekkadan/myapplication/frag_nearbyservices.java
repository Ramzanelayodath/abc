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

import com.example.areekkadan.myapplication.Adapter.Adapter_nearby;
import com.example.areekkadan.myapplication.Model.model_nearby;

import java.util.ArrayList;


public class frag_nearbyservices extends Fragment {


    View view;
    ArrayList<model_nearby> adapter_nearby=null;
    ListView listView=null;
    ArrayList<model_nearby> services=null;
    DatabaseHelper myDb;
    Adapter_nearby adapter=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_frag_nearbyservices, container, false);
        myDb = new DatabaseHelper(getActivity());
        adapter=new Adapter_nearby(getActivity(),R.layout.list_row_nearby,adapter_nearby);
        listView=(ListView)view.findViewById(R.id.listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView id=(TextView)view.findViewById(R.id.txt_id);
                String service_id=id.getText().toString();
                //Toast.makeText(getActivity(), service_id, Toast.LENGTH_SHORT).show();
                gotoServicedetils(service_id);
            }
        });


        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Nearby Services");
    }
    public void gotoServicedetils(String id)
    {
        Intent i=new Intent(getActivity(),nearby_servicedetails.class);
        i.putExtra("Id",id);
        startActivity(i);
    }





}
