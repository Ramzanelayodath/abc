package com.example.areekkadan.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


public class frag_profileSettings extends Fragment {
    TextView txt_name,txt_email,txt_phone;
    Button logout;
    View v;
    DatabaseHelper myDb;
    ArrayList<String> datas=null;
    String user_Id;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        v= inflater.inflate(R.layout.fragment_profilesettings, container, false);
        myDb = new DatabaseHelper(getActivity());
        user_Id=myDb.getuserId();
        System.out.println("Id:"+user_Id);
        datas= myDb.getuserData(user_Id);
        System.out.print(datas);
        txt_name=(TextView)v.findViewById(R.id.txt_name);
        txt_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_name.setBackgroundResource(R.drawable.label_bg);
                txt_name.setCursorVisible(true);
                txt_name.setFocusableInTouchMode(true);
                txt_name.setInputType(InputType.TYPE_CLASS_TEXT);
                txt_name.requestFocus();
            }
        });
        txt_email=(TextView)v.findViewById(R.id.txt_email);
        txt_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_email.setBackgroundResource(R.drawable.label_bg);
                txt_email.setCursorVisible(true);
                txt_email.setFocusableInTouchMode(true);
                txt_email.setInputType(InputType.TYPE_CLASS_TEXT);
                txt_email.requestFocus();
            }
        });
        txt_phone=(TextView)v.findViewById(R.id.txt_phone);
        txt_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_phone.setBackgroundResource(R.drawable.label_bg);
                txt_phone.setCursorVisible(true);
                txt_phone.setFocusableInTouchMode(true);
                txt_phone.setInputType(InputType.TYPE_CLASS_TEXT);
                txt_phone.requestFocus();
            }
        });
        txt_name.setText(datas.get(0).toString());
        txt_phone.setText(datas.get(1).toString());
        txt_email.setText(datas.get(2).toString());
        logout=(Button)v.findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });

        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Profile Settings");
    }
    public void logOut()
    {
        boolean logout= myDb.deleteuserId();
        if (logout==true)
        {
            Intent i= new Intent(getActivity(),LoginActvity.class);
            startActivity(i);
            getActivity().finish();
        }
    }


}
