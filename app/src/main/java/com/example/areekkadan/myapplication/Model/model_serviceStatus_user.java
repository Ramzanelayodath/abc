package com.example.areekkadan.myapplication.Model;

/**
 * Created by ramzan on 18/1/18.
 */

public class model_serviceStatus_user {

    String Vehiclename;
    String Servicetype;
    String Status;
    String Ownerid;
    String Serviceid;

    public String getServiceid() {
        return Serviceid;
    }

    public void setServiceid(String serviceid) {
        Serviceid = serviceid;
    }



    public String getOwnerid() {
        return Ownerid;
    }

    public void setOwnerid(String ownerid) {
        Ownerid = ownerid;
    }



    public model_serviceStatus_user(String vehiclename, String servicetype, String status,String ownerid,String serviceid) {
        Vehiclename = vehiclename;
        Servicetype = servicetype;
        Status = status;
        Ownerid=ownerid;
        Serviceid=serviceid;


    }

    public String getVehiclename() {
        return Vehiclename;
    }

    public void setVehiclename(String vehiclename) {
        Vehiclename = vehiclename;
    }

    public String getServicetype() {
        return Servicetype;
    }

    public void setServicetype(String servicetype) {
        Servicetype = servicetype;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }






}
