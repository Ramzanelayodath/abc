package com.example.areekkadan.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.areekkadan.myapplication.Model.model_nearby;
import com.example.areekkadan.myapplication.R;

import java.util.ArrayList;

/**
 * Created by ramzan on 17/1/18.
 */

public class Adapter_nearby extends ArrayAdapter {
    private Context context;
    private ArrayList<model_nearby> services;

    public Adapter_nearby(Context context, int textViewResourceId, ArrayList objects) {
        super(context,textViewResourceId, objects);

        this.context= context;
        services=objects;

    }

    private class ViewHolder
    {
        TextView vehicle_name;
        TextView service_type;
        TextView ad_info;
        TextView id;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder=null;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.list_row_nearby, null);

            holder = new ViewHolder();
            holder.vehicle_name=(TextView)convertView.findViewById(R.id.txt_vehcilename);
            holder.service_type=(TextView)convertView.findViewById(R.id.txt_service);
            holder.ad_info=(TextView)convertView.findViewById(R.id.txt_adinfo);
            holder.id=(TextView)convertView.findViewById(R.id.txt_id);
            convertView.setTag(holder);
        }

        else {
                 holder = (ViewHolder) convertView.getTag();
             }
             model_nearby each_services=services.get(position);
            holder.vehicle_name.setText(each_services.getVehicle_name());
            holder.service_type.setText(each_services.getService_type());
            holder.ad_info.setText(each_services.getAd_info());
            holder.id.setText(each_services.getId());
            return convertView;
    }

}
