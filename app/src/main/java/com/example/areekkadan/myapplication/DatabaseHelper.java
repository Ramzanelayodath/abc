package com.example.areekkadan.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.areekkadan.myapplication.Model.model_acceptedService_owner;
import com.example.areekkadan.myapplication.Model.model_nearby;
import com.example.areekkadan.myapplication.Model.model_serviceStatus_user;

import java.util.ArrayList;

/**
 * Created by ramzan on 16/1/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    String dbName;
    public DatabaseHelper(Context context) {
        super(context,"db_neartek", null, 10);
        dbName=context.getDatabasePath("db_neartek").toString();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tbl_user (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,EMAIL TEXT, PHONE TEXT,PASSWORD TEXT )");
        db.execSQL("create table tbl_userId (id INTEGER PRIMARY KEY AUTOINCREMENT,user_id TEXT)");
        db.execSQL("insert into tbl_userId(user_id)values(0)");
        db.execSQL("create table tbl_servicedetails(ID INTEGER PRIMARY KEY AUTOINCREMENT,VEHICLENAME TEXT,VEHICLETYPE TEXT," +
                "FUELTYPE TEXT,MANUFACTUERYEAR TEXT ,SERVICETYPE TEXT,ADDITIONALINFORMATION TEXT,USERID  TEXT,SERVICESTATUS TEXT,SERVICERID TEXT) ");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS tbl_user");
        db.execSQL("DROP TABLE IF EXISTS tbl_userId");
        db.execSQL("DROP TABLE IF EXISTS tbl_servicedetails");
        onCreate(db);
    }
    public boolean registerUser(String name,String email,String phone,String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("NAME",name);
        contentValues.put("EMAIL",email);
        contentValues.put("PHONE",phone);
        contentValues.put("PASSWORD",password);
        long result = db.insert("tbl_user",null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

    public Cursor loginUser(String mobile)
    {
        String user_id="0";
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select password,id from tbl_user where phone="+mobile;
        Cursor c = db.rawQuery(sql, null);
        System.out.println(c);
        return c;
    }
    public boolean storeuserId(String User_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_id",User_id);
        long result = db.update("tbl_userId",contentValues,"ID=?",new String[]{"1"});
        if(result == -1)
            return false;
        else
            return true;
    }
    public boolean insertserviceDetails(String vehicle_name,String vechicle_type,String fuel_type,String manufactuer_year,
                                        String service_type,String additional_information,String user_id,String service_status ,String servicer_id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("VEHICLENAME",vehicle_name);
        contentValues.put("VEHICLETYPE",vechicle_type);
        contentValues.put("FUELTYPE",fuel_type);
        contentValues.put("MANUFACTUERYEAR",manufactuer_year);
        contentValues.put("SERVICETYPE",service_type);
        contentValues.put("ADDITIONALINFORMATION",additional_information);
        contentValues.put("USERID",user_id);
        contentValues.put("SERVICESTATUS ",service_status);
        contentValues.put("SERVICERID",servicer_id);
        long result = db.insert("tbl_servicedetails",null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;

    }
    public String getuserId()
    {
        String ret_id="";
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select user_id from tbl_userId where id=1";
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext())
        {
            ret_id=c.getString(0);
        }
        return ret_id;

    }

    public ArrayList<model_nearby> getnearbyServices()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<model_nearby> services= new ArrayList<model_nearby>();
        Cursor result=db.rawQuery("SELECT ID,VEHICLENAME,SERVICETYPE,ADDITIONALINFORMATION FROM tbl_servicedetails Where SERVICESTATUS=0",null);
        while (result.moveToNext())
        {
            services.add(new model_nearby(result.getString(result.getColumnIndex("VEHICLENAME")),result.getString(result.getColumnIndex("SERVICETYPE")),
                    result.getString(result.getColumnIndex("ADDITIONALINFORMATION")),result.getString(result.getColumnIndex("ID"))));

        }
        return services;
    }

    public ArrayList<String> getservicefullDetail(String id)
    {
        ArrayList<String> details=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql="select * from tbl_servicedetails where id="+id;
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext())
        {
            details.add(c.getString(1));
            details.add(c.getString(2));
            details.add(c.getString(3));
            details.add(c.getString(4));
            details.add(c.getString(5));
            details.add(c.getString(6));

        }
        System.out.println(details);
        return details;

    }

    public ArrayList<String> getservicefullDetail(String service_id,String user_id  )
    {
        ArrayList<String> details=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql="select * from tbl_servicedetails where id="+service_id+" and USERID="+user_id;
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext())
        {
            details.add(c.getString(1));
            details.add(c.getString(2));
            details.add(c.getString(3));
            details.add(c.getString(4));
            details.add(c.getString(5));
            details.add(c.getString(6));

        }
        String query="select NAME,PHONE from tbl_user where ID="+user_id;
        Cursor cursor=db.rawQuery(query,null);
        while (cursor.moveToNext())
        {
            details.add(cursor.getString(0));
            details.add(cursor.getString(1));

        }
        System.out.println(details);
        return details;

    }
    public boolean setIntrest(String serviceId,String servicer_Id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("SERVICESTATUS" ,"1");
        contentValues.put("SERVICERID",servicer_Id);
        long result=db.update("tbl_servicedetails",contentValues,"ID=?",new String[] {serviceId});
        if (result==-1)
        {
            return false;
        }
        else {
            return true;
        }

    }

    public ArrayList<model_serviceStatus_user> getServicestatusofuser(String userId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<model_serviceStatus_user> status= new ArrayList<model_serviceStatus_user>();
        Cursor result=db.rawQuery("SELECT ID,VEHICLENAME,SERVICETYPE ,SERVICESTATUS, SERVICERID FROM tbl_servicedetails WHERE USERID = "+userId+"  " ,null);
        while (result.moveToNext())
        {
            status.add(new model_serviceStatus_user(result.getString(result.getColumnIndex("VEHICLENAME")),result.getString(result.getColumnIndex("SERVICETYPE")),result.getString(result.getColumnIndex("SERVICESTATUS")),result.getString(result.getColumnIndex("SERVICERID")),result.getString(result.getColumnIndex("ID"))));

        }
        return status;
    }

    public ArrayList<String>getuserData(String id)
    {
        ArrayList<String> details=new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String sql="select * from tbl_user where id="+id;
        Cursor c = db.rawQuery(sql, null);
        while (c.moveToNext())
        {
            details.add(c.getString(1));
            details.add(c.getString(2));
            details.add(c.getString(3));

        }
        System.out.println("getuserData"+details);
        return details;
    }

    public boolean deleteuserId()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("user_id","0");
        long result=db.update("tbl_userId",contentValues,"ID=?",new String[]{"1"});
        if (result==-1)
        {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean acceptRequest(String serviceId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("SERVICESTATUS","2");
        long result=db.update("tbl_servicedetails",contentValues,"ID=?",new String[]{serviceId});
        if (result==-1)
        {
            return false;
        }
        else {
            return true;
        }
    }

    public ArrayList<model_acceptedService_owner> getAcceptedservices(String servicerId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<model_acceptedService_owner> status= new ArrayList<model_acceptedService_owner>();
        Cursor result=db.rawQuery("SELECT ID,VEHICLENAME,SERVICETYPE ,SERVICESTATUS, USERID  FROM tbl_servicedetails WHERE SERVICERID = "+servicerId+" and SERVICESTATUS < 4",null);
        while (result.moveToNext())
        {
            status.add(new model_acceptedService_owner(result.getString(result.getColumnIndex("VEHICLENAME")),result.getString(result.getColumnIndex("SERVICETYPE")),result.getString(result.getColumnIndex("SERVICESTATUS")),result.getString(result.getColumnIndex("USERID")),result.getString(result.getColumnIndex("ID"))));

        }
        return status;
    }

    public boolean declineRequest(String serviceId)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("SERVICESTATUS","0");
        contentValues.put("SERVICERID","0");
        long result=db.update("tbl_servicedetails",contentValues,"ID=?",new String[]{serviceId});
        if (result==-1)
        {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean going(String serviceId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("SERVICESTATUS","3");
        long result=db.update("tbl_servicedetails",contentValues,"ID=?",new String[]{serviceId});
        if (result==-1)
        {
            return false;
        }
        else {
            return true;
        }
    }

    public boolean cancelRequest(String serviceId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("SERVICESTATUS","0");
        long result=db.update("tbl_servicedetails",contentValues,"ID=?",new String[]{serviceId});
        if (result==-1)
        {
            return false;
        }
        else {
            return true;
        }
    }
    public void getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select SERVICESTATUS from tbl_servicedetails",null);
        while (res.moveToNext())
        {
            System.out.println("Dummy Function"+res.getString(0));
        }
    }





}

