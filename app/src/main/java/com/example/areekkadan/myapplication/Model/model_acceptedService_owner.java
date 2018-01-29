package com.example.areekkadan.myapplication.Model;

/**
 * Created by ramzan on 20/1/18.
 */

public class model_acceptedService_owner {
    String Vehiclename;
    String Servicetype;
    String Status;
    String Userid;
    String Serviceid;

    public String getServiceid() {
        return Serviceid;
    }

    public void setServiceid(String serviceid) {
        Serviceid = serviceid;
    }



    public String getUserid() {
        return Userid;
    }

    public void setUserid(String userid) {
        Userid = userid;
    }



    public model_acceptedService_owner(String vehiclename, String servicetype, String status,String userid,String serviceid) {
        Vehiclename = vehiclename;
        Servicetype = servicetype;
        Status = status;
        Userid=userid;
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
