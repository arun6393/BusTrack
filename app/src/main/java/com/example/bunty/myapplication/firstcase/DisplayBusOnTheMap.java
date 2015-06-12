package com.example.bunty.myapplication.firstcase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bunty.myapplication.R;
import com.example.bunty.myapplication.network.VolleySingleton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bunty on 3/29/2015.
 */
public class DisplayBusOnTheMap extends Activity {

    GoogleMap map;
    LocationManager lm;
    Location l;
    Marker marker;
    int id;
    Double newlatlng[]= new Double[2];
    LatLng myloc;
    Double returneddata[]=new Double[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_on_the_map);
        Intent in = getIntent();
        double lat=in.getDoubleExtra("lat",0);
        double long1=in.getDoubleExtra("long",0);
        id=in.getIntExtra("id",0);
        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                .getMap();
        // API 11 REQUIRED
        lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        map.setMyLocationEnabled(true);
        map.setTrafficEnabled(true);
        map.setIndoorEnabled(true);
        map.setBuildingsEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);

        try {
            Log.i("Lat", "" +lat);
            Log.i("Long", "" + long1);
            Log.i("id is", "" + id);

            Toast.makeText(this,"lat is"+lat+"and long is"+long1+"id is"+id,Toast.LENGTH_SHORT).show();
            l = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            myloc = new LatLng(lat,long1);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myloc, 10));
           marker= map.addMarker(new MarkerOptions().title("Tracking You")
                    .snippet("Right you are in this circle").position(myloc));
        } catch (Exception e) {

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);//Menu Resource, Menu
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
               // Toast.makeText(getApplicationContext(), "Item 1 Selected", Toast.LENGTH_LONG).show();

//                if (marker != null) {
//                    marker.remove();
//                }

                 getnewlatlong();
//                Log.i("data retunred to menu is",returneddata[0]+""+returneddata[1]);
//                myloc = new LatLng(returneddata[0],returneddata[1]);
//                map.moveCamera(CameraUpdateFactory.newLatLngZoom(myloc, 10));
//                marker= map.addMarker(new MarkerOptions().title("Tracking You")
//                        .snippet("Right you are in this circle").position(myloc));
//


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getnewlatlong() {

        RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();

        final String newlatlong = "http://whereisthebus.in/updated_lat_long.php?id="+id;
        StringRequest request = new StringRequest(Request.Method.GET, newlatlong, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if(response.equals("null"))
                    {
                        Log.i("data is","null");
                                               // Toast.makeText(getActivity(),"No busses found",Toast.LENGTH_SHORT).show();
                        //progressDialog.dismiss();
                    }
                    else {
                        // listData2 = parseJSON(response);
                        JSONObject json = new JSONObject(response);
                        JSONArray data = json.getJSONArray("row");
                        //busdest=new String[data.length()];
                        // ArrayList<String> listItems = new ArrayList<>();


                        Log.i("data returned is", response);

                        for (int i = 0; i < data.length(); i++) {

                            JSONObject object = data.getJSONObject(i);
                           newlatlng[0]=Double.parseDouble(object.getString("bus_lat"));
                            newlatlng[1]=Double.parseDouble(object.getString("bus_long"));



                        }

                        if (marker != null) {
                            marker.remove();
                        }
                        Log.i("data retunred to menu is",newlatlng[0]+""+newlatlng[1]);
                        myloc = new LatLng(newlatlng[0],newlatlng[1]);
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myloc, 10));
                        marker= map.addMarker(new MarkerOptions().title("Tracking You")
                                .snippet("Right you are in this circle").position(myloc));




                        Log.i("data in newlatlng is",newlatlng[0]+""+newlatlng[1]);
                        //  finaldata = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listData2);
                        //                finaldata.addAll(listData2);
                        //l1.setAdapter(finaldata);
                    //    progressDialog.dismiss();
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
        Log.i("data in newlatlng is",newlatlng[0]+""+newlatlng[1]);


    }
    }

