package com.example.areekkadan.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.areekkadan.myapplication.Model.model_acceptedService_owner;
import com.example.areekkadan.myapplication.R;

import java.util.ArrayList;

/**
 * Created by ramzan on 20/1/18.
 */

public class Adapter_acceptedService_owner extends ArrayAdapter {
    private Context context;
    private ArrayList<model_acceptedService_owner> status;

    public Adapter_acceptedService_owner(Context context, int textViewResourceId, ArrayList objects) {
        super(context,textViewResourceId, objects);

        this.context= context;
        status=objects;

    }
    private class ViewHolder
    {
        TextView vehicle_name;
        TextView service_type;
        TextView status;
        TextView userid;
        TextView serviceid;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Adapter_acceptedService_owner.ViewHolder holder=null;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.list_row_acceptedservice, null);
            holder = new Adapter_acceptedService_owner.ViewHolder();
            holder.vehicle_name=(TextView)convertView.findViewById(R.id.txt_name);
            holder.service_type=(TextView)convertView.findViewById(R.id.txt_service);
            holder.status=(TextView)convertView.findViewById(R.id.txt_status);
            holder.serviceid=(TextView)convertView.findViewById(R.id.txt_serviceId);
            holder.userid=(TextView)convertView.findViewById(R.id.txt_userId);



            convertView.setTag(holder);
        }


        else {
            holder = (Adapter_acceptedService_owner.ViewHolder) convertView.getTag();
        }
        model_acceptedService_owner each_status=status.get(position);
        holder.vehicle_name.setText(each_status.getVehiclename());
        holder.service_type.setText(each_status.getServicetype());
        holder.serviceid.setText(each_status.getServiceid());
        holder.userid.setText(each_status.getUserid());
        if (each_status.getStatus().equals("2"))
        {
            holder.status.setText("Request Accepted");
        }
        else if (each_status.getStatus().equals("3"))
        {
            holder.status.setText("Going");
        }




        return convertView;
    }

}
