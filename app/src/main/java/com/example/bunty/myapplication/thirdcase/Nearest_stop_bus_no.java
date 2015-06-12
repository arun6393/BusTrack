package com.example.bunty.myapplication.thirdcase;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bunty.myapplication.R;
import com.example.bunty.myapplication.adapters.AdapterBusNo;
import com.example.bunty.myapplication.network.VolleySingleton;
import com.example.bunty.myapplication.secondcase.Bus_No_Stops;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by bunty on 4/12/2015.
 */

public class Nearest_stop_bus_no extends Activity {


    GoogleMap map;
    LocationManager lm;
    Location l;
    String destination, near_stop,destination1,source1;
    Double lat, lng;
    private AdapterBusNo adapterBusNo;
    private ArrayList<Bus_No_Stops> list = new ArrayList<>();
    private RecyclerView busdata;
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    String busdest[];
    Marker marker;
    LatLng myloc,myloc1;
    GPSTracker gps;
    double lat1,long1;
    String append = "%20";
    ProgressDialog progressDialog;


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
//        Toast.makeText(this, destination + "is", Toast.LENGTH_SHORT).show();

        l = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);



            getneareststop();
        //getbusno();


    }


    private void getneareststop() {
  //      Toast.makeText(this,"destination is"+destination,Toast.LENGTH_SHORT).show();

            int b1 = 0;
            for (int i = 0; i < destination.length(); i++) {
                if (Character.isWhitespace(destination.charAt(i))) {
                    b1 = 1;
                    break;
                }
            }
            if (b1 == 1) {
                destination1 = destination.replace(" ", append);


            } else {
                destination1=destination;

            }




        gps = new GPSTracker(this);

        // check if GPS enabled
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            myloc = new LatLng(latitude,longitude);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myloc, 10));
            marker= map.addMarker(new MarkerOptions().title("Tracking You")
                    .snippet("Right you are in this circle").position(myloc));



            // \n is for new line
//            Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();



            requestQueue = volleySingleton.getInstance().getRequestQueue();
  //          Toast.makeText(getApplicationContext(), "destination one is"+destination1, Toast.LENGTH_SHORT).show();

            progressDialog=new ProgressDialog(Nearest_stop_bus_no.this);
            progressDialog.show();
            progressDialog.setMessage("getting the nearest stop and bus no");

            String nearstop = "http://whereisthebus.in/thirdmodulecopy.php?destination="+destination1+"&lat="+latitude+"&long="+longitude;

            Log.i("data to third module is",nearstop);
    //        Toast.makeText(getApplicationContext(), "third mofule data is"+nearstop, Toast.LENGTH_SHORT).show();

            StringRequest request1 = new StringRequest(Request.Method.GET, nearstop, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
//                    Toast.makeText(getApplication(), response + "", Toast.LENGTH_SHORT).show();
                    Log.i("data is", response);
                    try {
                        JSONObject ob = new JSONObject(response);
                        JSONArray row1=ob.getJSONArray("row");
                        near_stop=row1.getJSONObject(0).getString("near_stop");
                        lat1=Double.parseDouble(row1.getJSONObject(0).getString("lat"));
                        long1=Double.parseDouble(row1.getJSONObject(0).getString("long"));

                        Log.i("near stop is",near_stop);
                        Log.i("lat1 is",lat1+"");
                        Log.i("long  is", long1 + "");
                        myloc1 = new LatLng(lat1,long1);
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myloc1, 10));
                        marker= map.addMarker(new MarkerOptions().title("Tracking You")
                                .snippet("TAKE THE BUS FROM HERE").position(myloc1));
                        Polygon polygon = map.addPolygon(new PolygonOptions()
                                .add(myloc, myloc1
                                )
                                .strokeWidth(5)
                                .strokeColor(Color.BLUE));


                        getbusno();



                    }catch(Exception e)
                    {
Toast.makeText(getApplication(),e.toString(),Toast.LENGTH_SHORT).show();
                    }







                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("error is", error.toString());

                }
            });
            int socketTimeout = 60000;//60 seconds - change to what you want
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

            request1.setRetryPolicy(policy);
            requestQueue.add(request1);




        }else{
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gps.showSettingsAlert();
        }


    }

    private void getbusno() {

        int b2 = 0;
        for (int i = 0; i < near_stop.length(); i++) {
            if (Character.isWhitespace(near_stop.charAt(i))) {
                b2 = 1;
                break;
            }
        }
        if (b2 == 1) {
            source1 = near_stop.replace(" ", append);


        } else {
            source1=near_stop;

        }



        RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();

  //      Toast.makeText(this,"the source and destinaton is"+source1+"and"+destination1,Toast.LENGTH_SHORT).show();

        final String busno = "http://whereisthebus.in/path.php?source="+source1+"&destination="+destination1;
        Log.i("data to path is",busno);
        StringRequest request = new StringRequest(Request.Method.GET, busno, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if(response.equals("null"))
                    {
                        Toast.makeText(getApplicationContext(),"No busses found",Toast.LENGTH_SHORT).show();
                        //progressDialog.dismiss();
                    }
                    else {
                        Log.i("data received id",response);
                        // listData2 = parseJSON(response);
                        JSONObject json = new JSONObject(response);
                        JSONArray data = json.getJSONArray("row");
                        busdest=new String[data.length()];
                        // ArrayList<String> listItems = new ArrayList<>();

                        Log.i("data", response);

                        for (int i = 0; i < data.length(); i++) {

                            JSONObject object = data.getJSONObject(i);
                            String busno=object.getString("bus_no");
                            String busDestination=object.getString("bus_destination");
                            String no_of_stops=object.getString("no_of_stops");
                            Log.i("data in string",busno+""+busDestination);
                            Bus_No_Stops b=new Bus_No_Stops();
                            b.setBus_no(busno);
                            b.setStops(no_of_stops);
                            b.setDest(busDestination);
                            busdest[i]=busDestination;
                            list.add(b);
                            Log.i("data in arraylist",list.get(0)+"");
                            adapterBusNo.setData(list);
                            progressDialog.cancel();



                        }


                        //  finaldata = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listData2);
                        //                finaldata.addAll(listData2);
                        //l1.setAdapter(finaldata);
                     //   progressDialog.dismiss();
                    }

                } catch (JSONException e) {

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//
            }
        });
        requestQueue.add(request);


    }
}