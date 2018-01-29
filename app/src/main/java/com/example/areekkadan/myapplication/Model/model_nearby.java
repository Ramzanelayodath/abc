package com.example.areekkadan.myapplication.Model;

/**
 * Created by ramzan on 17/1/18.
 */

public class model_nearby {

    String Vehicle_name;
    String Service_type;
    String Ad_info;
    String id;

    public String getVehicle_name() {
        return Vehicle_name;
    }

    public void setVehicle_name(String vehicle_name) {
        Vehicle_name = vehicle_name;
    }

    public String getService_type() {
        return Service_type;
    }

    public void setService_type(String service_type) {
        Service_type = service_type;
    }

    public String getAd_info() {
        return Ad_info;
    }

    public void setAd_info(String ad_info) {
        Ad_info = ad_info;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public model_nearby(String vehicle_name, String service_type, String ad_info, String id) {
        Vehicle_name = vehicle_name;
        Service_type = service_type;
        Ad_info = ad_info;
        this.id = id;
    }






}
