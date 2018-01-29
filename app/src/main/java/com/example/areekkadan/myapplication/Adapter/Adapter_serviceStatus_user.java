package com.example.areekkadan.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.areekkadan.myapplication.Model.model_serviceStatus_user;
import com.example.areekkadan.myapplication.R;

import java.util.ArrayList;

/**
 * Created by ramzan on 18/1/18.
 */

public class Adapter_serviceStatus_user extends ArrayAdapter {

    private Context context;
    private ArrayList<model_serviceStatus_user> status;

    public Adapter_serviceStatus_user(Context context, int textViewResourceId, ArrayList objects) {
        super(context,textViewResourceId, objects);

        this.context= context;
        status=objects;

    }
    private class ViewHolder
    {
        TextView vehicle_name;
        TextView service_type;
        TextView status;
        TextView ownerid;
        TextView serviceid;

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Adapter_serviceStatus_user.ViewHolder holder=null;
        if (convertView == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.list_row_service_status, null);

            holder = new Adapter_serviceStatus_user.ViewHolder();
            holder.vehicle_name=(TextView)convertView.findViewById(R.id.txt_name);
            holder.service_type=(TextView)convertView.findViewById(R.id.txt_serviceType);
            holder.status=(TextView)convertView.findViewById(R.id.txt_status);
            holder.ownerid=(TextView)convertView.findViewById(R.id.txt_ownerId);
            holder.serviceid=(TextView)convertView.findViewById(R.id.txt_serviceId);


            convertView.setTag(holder);
        }

        else {
            holder = (Adapter_serviceStatus_user.ViewHolder) convertView.getTag();
        }
        model_serviceStatus_user each_status=status.get(position);
        holder.vehicle_name.setText(each_status.getVehiclename());
       holder.service_type.setText(each_status.getServicetype());
       holder.ownerid.setText(each_status.getOwnerid());
       holder.serviceid.setText(each_status.getServiceid());
        String ret_status=each_status.getStatus();
       if (ret_status.equals("0"))
        {
            holder.status.setText("Looking for service");
        }
        else if (ret_status.equals("1"))
        {
            holder.status.setText("You have one service request");
        }
        else if (ret_status.equals("2"))
        {
            holder.status.setText("Request accepted");
        }
        else if (ret_status.equals("3"))
        {
            holder.status.setText("Waiting for service");
        }
        else if (ret_status.equals("4"))
        {
            holder.status.setText("Service completed");
        }

        return convertView;
    }
}
