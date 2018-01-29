package com.example.areekkadan.myapplication;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.areekkadan.myapplication.Config.config;
import com.valdesekamdem.library.mdtoast.MDToast;


public class frag_bookService extends Fragment {
    View view;
    DatabaseHelper myDb;
    CardView submit;
    Spinner brand_name,service_type,vehicle_types,fuel_types;
    EditText manufacter_year,ad_information;
    String services[]={"Oil Change","Puncher Works","Piston Works","Engine Works","Other"};
    String Vehicles[]={"Two Wheeler","Three Wheeler","Heavy Vehicles"};
    String Brands[]={"Audi","Bmw","Benz","Suzuki","Maruti","Mahindra","Chevrolet","Ford","Toyota","Honda","Nissan",
            "Hyundai","Mistubshi","Jaguar","Hero","Tvs","Yamaha","Bajaj","Honda Bikes","Royal Enfield",
            "Ktm","vespa","Mahindra Bikes"};
    String fuel_type[]={"Diesel","Petrol"},usr_id,Url;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    // GPSTracker class
    GPSTracker gps;
    Gps gps1;
    Button btn;
    static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    Context context;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments
        view =inflater.inflate(R.layout.fragment_frag_book, container, false);
        /** Code For Local Database **/
        myDb = new DatabaseHelper(getActivity());
        config c=new config();
        Url=c.getUrlServer();

         brand_name=(Spinner)view.findViewById(R.id.sp_brandname);
        ArrayAdapter Array_brand=new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,Brands);
        Array_brand.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brand_name.setAdapter(Array_brand);
         service_type=(Spinner)view.findViewById(R.id.sp_typeofservice);
        ArrayAdapter Array_services=new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,services);
        Array_services.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        service_type.setAdapter(Array_services);
         vehicle_types=(Spinner)view.findViewById(R.id.sp_vehicletype);
        ArrayAdapter Array_vehicles=new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,Vehicles);
        Array_vehicles.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        vehicle_types.setAdapter(Array_vehicles);

         fuel_types=(Spinner)view.findViewById(R.id.sp_fueltype);
        ArrayAdapter Array_fuels=new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,fuel_type);
        Array_fuels.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fuel_types.setAdapter(Array_fuels);
        submit=(CardView)view.findViewById(R.id.crd_submit);
        manufacter_year=(EditText)view.findViewById(R.id.edt_manufactoryear);
        ad_information=(EditText)view.findViewById(R.id.edt_adinformation);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                serviceBooking();
            }
        });
        turnonGps();
        return view;

    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Book Service");
    }

    public void serviceBooking()
    {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP){
            // Do something for lollipop and above versions
            getLocationforaboveaAndroid5();
        } else{
            // do something for phones running an SDK before lollipop
            getLocationbelowAndroid5();
        }
        RequestQueue requestQueue   = Volley.newRequestQueue(getActivity());
            String str_brand_name=brand_name.getSelectedItem().toString();
            String str_fuel_type=fuel_types.getSelectedItem().toString();
            String str_mYear=manufacter_year.getText().toString();
            String str_service=service_type.getSelectedItem().toString();
            String str_vType=vehicle_types.getSelectedItem().toString();
            String str_adinfo=ad_information.getText().toString();
            usr_id=myDb.getuserId();
            Url=Url+"?operation=3"+"&txtUid="+usr_id+"&txtBname="+str_brand_name+"&txtFtype="+str_fuel_type+
                    "&txtMfyears="+str_mYear+"&txtStype="+str_service+"&txtVtype="+str_vType+
                    "&txtAddinfo="+str_adinfo;
        final ProgressDialog pr=new ProgressDialog(getActivity());
        pr.setMessage("Please Wait....");
        pr.show();
        StringRequest request=new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pr.dismiss();
                MDToast.makeText(getActivity(),"Your Service Submitted",Toast.LENGTH_SHORT,MDToast.TYPE_SUCCESS).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pr.dismiss();
                MDToast.makeText(getActivity(),"Error Occured",Toast.LENGTH_SHORT,MDToast.TYPE_ERROR).show();
            }
        });
        requestQueue.add(request);



    }
    public void turnonGps() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {

            builder.setMessage("Enable GPS");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                }
            });
            builder.show();
        }
    }
    void getLocationforaboveaAndroid5() {
        if(ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);

        } else {
            Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            if (location != null){
                double latti = location.getLatitude();
                double longi = location.getLongitude();

                System.out.println("Lattitude"+latti);


            } else {

            }
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_LOCATION:
                getLocationforaboveaAndroid5();
                break;
        }
    }

    void getLocationbelowAndroid5()
    {
        // create class object
        gps1 = new Gps(getActivity());

        // check if GPS enabled
        if(gps1.canGetLocation()){

            double latitude = gps1.getLatitude();
            double longitude = gps1.getLongitude();

            // \n is for new line
            Toast.makeText(getActivity(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps1.showSettingsAlert();
        }

    }
}
