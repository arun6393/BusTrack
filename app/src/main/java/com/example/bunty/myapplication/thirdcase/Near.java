package com.example.bunty.myapplication.thirdcase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bunty.myapplication.R;
import com.example.bunty.myapplication.adapters.AdapterBusNo;
import com.example.bunty.myapplication.network.VolleySingleton;
import com.example.bunty.myapplication.secondcase.Bus_No_Stops;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;

import java.util.ArrayList;

/**
 * Created by bunty on 4/12/2015.
 */

public class Near extends Activity {


    GoogleMap map;
    LocationManager lm;
    Location l;
    String destination, near_stop;
    Double lat, lng;
    private AdapterBusNo adapterBusNo;
    private ArrayList<Bus_No_Stops> list = new ArrayList<>();
    private RecyclerView busdata;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thirdmodule);
        busdata = (RecyclerView) findViewById(R.id.thirdmodule_busdata);
        busdata.setLayoutManager(new LinearLayoutManager(this));
        adapterBusNo = new AdapterBusNo(this);
        busdata.setAdapter(adapterBusNo);

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.thirdmodule_map1))
                .getMap();
        // API 11 REQUIRED
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        map.setMyLocationEnabled(true);
        map.setTrafficEnabled(true);
        map.setIndoorEnabled(true);
        map.setBuildingsEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);
        Intent i = getIntent();

        destination = i.getStringExtra("destination");
        Toast.makeText(this, destination + "is", Toast.LENGTH_SHORT).show();
        getneareststop(destination);


    }

    private void getneareststop(String destination) {
        RequestQueue requestQueue1 = VolleySingleton.getInstance().getRequestQueue();
        Toast.makeText(getApplicationContext(), "method called", Toast.LENGTH_SHORT).show();


        String nearstop = "http://whereisthebus.in/thirdmodulecopy.php?destination=" + destination;
        Log.i("data sent is",nearstop);
        Toast.makeText(getApplicationContext(), "method called 1", Toast.LENGTH_SHORT).show();

        StringRequest request1 = new StringRequest(Request.Method.GET, nearstop, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("data is",response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue1.add(request1);

    }

}