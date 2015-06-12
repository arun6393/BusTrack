package com.example.bunty.myapplication.secondcase;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.bunty.myapplication.adapters.AdapterBusInfo;
import com.example.bunty.myapplication.firstcase.Bus;
import com.example.bunty.myapplication.firstcase.BusPosition;
import com.example.bunty.myapplication.network.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by bunty on 4/3/2015.
 */
public class BusData3 extends Activity {
    private AdapterBusInfo adapterBusInfo;
    private ArrayList<Bus> list = new ArrayList<>();
    private ArrayList<BusPosition> list1 = new ArrayList<>();
    private RecyclerView busdata2;
    //private String get_bus_stops = "http://whereisthebus.in/trial1.php?bus_no=";
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    String source1,destination1,busno1;
    String x;
    Double lat, long1;
    String textkms, texttime;
    String distancetime[];
    Double sourcelatlong[];
    int id;


    String c;
    int z = 0;
    int j;
    double data1[];
    double data4[];
    double data6[];
    String append = "%20";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.busdata2);
        busdata2 = (RecyclerView) findViewById(R.id.busdata2);
        busdata2.setLayoutManager(new LinearLayoutManager(this));


        setdata();

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
                if(!list.isEmpty() || !list1.isEmpty())
                {
                    list.clear();
                    list1.clear();
                }
                setdata();


            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void setdata() {
        adapterBusInfo = new AdapterBusInfo(this);
        busdata2.setAdapter(adapterBusInfo);
        Intent i=getIntent();

        source1=i.getStringExtra("source");
        destination1=i.getStringExtra("dest");
        busno1=i.getStringExtra("busno");

        Log.i("data got is", source1 + "" + destination1 + "" + busno1);

        String v1 = checkx();
        getdata(busno1, source1, v1);
    }

    private void getdata(String bus_no, String s, String s1) {

        RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();
        String url = "http://whereisthebus.in/bus_position1.php?bus_no=" + bus_no + "&source_stop=" + s + "&dest=" + s1;

        Log.i("url fr getting bus info", url);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if (response.equals("null")) {
                        Toast.makeText(getApplication(), "No busses found", Toast.LENGTH_SHORT).show();
                        list.clear();

                    } else {
                        Log.i("response frm  bus info", response.toString());

                        //called to done json parsing
//                        progressDialog = new ProgressDialog(getApplicationContext());
//                        progressDialog.setMessage("Fetching The File....");
//                        progressDialog.show();
                        parseJSON(response);
                    }

                } catch (Exception e) {

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


    //parse the data
    private void parseJSON(String output) throws JSONException {
//called to get the lat n long of the source stop
        Double x[] = getsourcelatlong(output);


        JSONObject json = new JSONObject(output);


        JSONArray data = json.getJSONArray("row");
        distancetime = new String[data.length()];
        data1 = new double[data.length() - 1];
        Log.i("array", data + "");

  //      list1.clear();
        RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();


        for (int i = 0; i < (data.length() - 1); i++) {
            JSONObject object = data.getJSONObject(i);
            lat = Double.parseDouble(object.getString("lat"));
            long1 = Double.parseDouble(object.getString("long"));
            id=Integer.parseInt(object.getString("id"));

            Log.i("lat n long is", "" + lat + "" + long1);
            BusPosition busPosition = new BusPosition();
            busPosition.setLat(lat);
            busPosition.setLong1(long1);
            busPosition.setId(id);
            list1.add(busPosition);
            adapterBusInfo.setData1(list1);
           // Log.i("data aded to the arraylist is", lat + "" + long1 + "to the position" + i);


            //get the time and distance from the source stop
            //String url2 = "http://maps.googleapis.com/maps/api/distancematrix/json?" + "origins=" + lat + "," + long1 + "&destinations=" + x[0] + "," + x[1] + "&mode=bus";
            //Log.i("url", url2);
            Log.i("hi", "hello");
            c="http://maps.googleapis.com/maps/api/distancematrix/json?" + "origins=" + lat + "," + long1 + "&destinations=" + x[0] + "," + x[1] + "&mode=bus";

//            StringRequest request5 = new StringRequest(Request.Method.GET, c, new Response.Listener<String>() {
//                @Override
//                public void onResponse(String response) {
//                    Log.i("hi", "hello");
//                    try {
////                        if (response.equals("null")) {
////                            Log.i("no","response");
////                        } else {
////                            //parse the google distance api
////
//                        Log.i("the response from google api is", response);
////
////                            JSONObject ob = new JSONObject(response);
////
////                            JSONArray originadd = ob.getJSONArray("origin_addresses");
////                            String x1 = originadd.getString(0);
////                            JSONArray rows = ob.getJSONArray("rows");
////                            for (int k = 0; k < rows.length(); k++) {
////
////                                JSONObject ob1 = rows.getJSONObject(k);
////                                JSONArray elements = ob1.getJSONArray("elements");
////                                for (int k1 = 0; k1 < elements.length(); k1++) {
////                                    JSONObject dis = elements.getJSONObject(k1);
////                                    JSONObject distance1 = dis.getJSONObject("distance");
////                                    textkms = distance1.getString("text");
////                                    JSONObject duration = dis.getJSONObject("duration");
////                                    texttime = duration.getString("text");
////                                    Bus b = new Bus();
////                                    b.setLocation(x1);
////                                    b.setDistance(textkms);
////                                    b.setTime(texttime);
////                                    list.add(b);
////                                    adapterBusInfo.setData(list);
//////                                    progressDialog.cancel();
////
////                                    distancetime[z] = textkms;
////                                    distancetime[z + 1] = texttime;
////
////
////                                }
////                                z++;
////
////
////                            }
////
//                        // }
//
//                    } catch (Exception e) {
//                        Log.i("exception is", e.toString());
//
//                    }
//                }
//
//            }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    Log.i("error", error.toString());
//
//                }
//            });
//
//
//            requestQueue.add(request5);
//
            StringRequest requeststring=new StringRequest(Request.Method.GET,c,new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                   // Toast.makeText(getApplication(),response,Toast.LENGTH_SHORT).show();
                    try{
                        Log.i("url is",c);
                    Log.i("data is",response);
                    if (response.equals("null")) {
                    } else {
                        //parse the google distance api

                        Log.i("the response from google api is", response.toString());

                        JSONObject ob = new JSONObject(response);

                        JSONArray originadd = ob.getJSONArray("origin_addresses");
                        String x1 = originadd.getString(0);
                        JSONArray rows = ob.getJSONArray("rows");
                        for (int k = 0; k < rows.length(); k++) {

                            JSONObject ob1 = rows.getJSONObject(k);
                            JSONArray elements = ob1.getJSONArray("elements");
                            for (int k1 = 0; k1 < elements.length(); k1++) {
                                JSONObject dis = elements.getJSONObject(k1);
                                JSONObject distance1 = dis.getJSONObject("distance");
                                textkms = distance1.getString("text");
                                JSONObject duration = dis.getJSONObject("duration");
                                texttime = duration.getString("text");
                                Bus b = new Bus();
                                b.setLocation(x1);
                                b.setDistance(textkms);
                                b.setTime(texttime);
                                list.add(b);
                                adapterBusInfo.setData(list);
//                                progressDialog.cancel();

                                distancetime[z] = textkms;
                                distancetime[z + 1] = texttime;


                            }
                            z++;


                        }

                    }

                } catch (Exception e) {

                }

                }
            },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            requestQueue.add(requeststring);

            Log.i("end", "of loop");
        }


    }



    //append %20 to the string
    private String checkx() {
        String a;
        int b1 = 0;
        x = null;
        //y = null;
        // TODO Auto-generated method stub
        for (int i = 0; i < destination1.length(); i++) {
            if (Character.isWhitespace(destination1.charAt(i))) {
                b1 = 1;
                break;
            }
        }
        if (b1 == 1) {
            x = destination1.replace(" ", append);
            a = x;

        } else {
            a = destination1;

        }

        return a;

    }



    //get source stop lat n long
    private Double[] getsourcelatlong(String output) {

        try {
            sourcelatlong = new Double[2];
            JSONObject json2 = new JSONObject(output);
            JSONArray data2 = json2.getJSONArray("row");
            int le = data2.length();
            JSONObject data3 = data2.getJSONObject(le - 1);
            sourcelatlong[0] = Double.parseDouble(data3.getString("lat"));
            sourcelatlong[1] = Double.parseDouble(data3.getString("long"));
            //          Toast.makeText(getActivity(), sourcelatlong[0] + "" + sourcelatlong[1], Toast.LENGTH_SHORT).show();


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sourcelatlong;

    }
}
