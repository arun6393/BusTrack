package com.example.bunty.myapplication.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.bunty.myapplication.firstcase.Bus;
import com.example.bunty.myapplication.firstcase.BusPosition;
import com.example.bunty.myapplication.R;
import com.example.bunty.myapplication.adapters.AdapterBusInfo;
import com.example.bunty.myapplication.firstcase.Bus_Stops;
import com.example.bunty.myapplication.network.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragmentbusno extends Fragment implements View.OnClickListener, TextWatcher {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private AdapterBusInfo adapterBusInfo;
    private ArrayList<Bus> list = new ArrayList<>();
    private ArrayList<BusPosition> list1 = new ArrayList<>();
    private RecyclerView businfo;
    private String get_bus_stops = "http://whereisthebus.in/trial1.php?bus_no=";
    private VolleySingleton volleySingleton;
    private RequestQueue requestQueue;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String input_bus_no, srcstop, destination, x, y;
    EditText bus_no;
    Button getsrcstop, swap1, locate;
    TextView to, from;
    String srcdest[];
    String append = "%20";
    String textkms, texttime;
    String distancetime[];
    Double sourcelatlong[];
    int id;

    int z = 0;
    int j;
    double data1[];
    double data4[];
    double data6[];
    ArrayAdapter<String> finaldata;
    Double lat, long1;
    private ArrayList<String> listData2 = new ArrayList<>();
    MenuItem fav;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int RESULT_CANCELED = 0;
    ProgressDialog progressDialog;


    // TODO: Rename and change types and number of parameters
    public static Fragmentbusno newInstance(String param1, String param2) {
        Fragmentbusno fragment = new Fragmentbusno();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragmentbusno() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
        //  fav = menu.add("Refresh");
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle item selection
        switch (item.getItemId()) {
            case R.id.refresh:
                // do s.th.
               // Toast.makeText(getActivity(), "called", Toast.LENGTH_SHORT).show();
                getallthedata();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_bus_no, container, false);
        bus_no = (EditText) v.findViewById(R.id.bus_no);
        getsrcstop = (Button) v.findViewById(R.id.b1);
        to = (TextView) v.findViewById(R.id.to);
        from = (TextView) v.findViewById(R.id.from);
        swap1 = (Button) v.findViewById(R.id.switch1);
        locate = (Button) v.findViewById(R.id.locate);
        to.setVisibility(View.GONE);
        from.setVisibility(View.GONE);
        getsrcstop.setOnClickListener(this);
        swap1.setOnClickListener(this);
        locate.setOnClickListener(this);

        businfo = (RecyclerView) v.findViewById(R.id.businfo);
        businfo.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterBusInfo = new AdapterBusInfo(getActivity());
        businfo.setAdapter(adapterBusInfo);
        bus_no.addTextChangedListener(this);


        return v;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.b1:
//get the source stop,pass the bus no
                if (!list.isEmpty()) {

                    list.clear();
                    adapterBusInfo.setData(list);

                }
                if (!bus_no.getText().toString().equals("")) {
                    input_bus_no = bus_no.getText().toString();
                    Intent i = new Intent(getActivity(), Bus_Stops.class);
                    Bundle b = new Bundle();

                    b.putString("busno", input_bus_no);

                    i.putExtras(b);

                    startActivityForResult(i, 1);
                } else {
                    Toast.makeText(getActivity(), "please enter the bus no", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.locate:

                getallthedata();

                break;

            case R.id.switch1:
//to change the from n to
                if (!list.isEmpty()) {


                    list.clear();
                    adapterBusInfo.setData(list);
                    //list.notify();
                }
                String temp = from.getText().toString();
                from.setText(to.getText().toString());
                to.setText(temp);

                break;


        }
    }

    private void getallthedata() {
        if (!list.isEmpty()) {
            list.clear();
            adapterBusInfo.setData(list);

        }


        //get the location,distance and time of the busses
        if (bus_no.getText().toString().equals("") || getsrcstop.getText().toString().equals("")) {
            Toast.makeText(getActivity(), "enter the details", Toast.LENGTH_SHORT).show();
        } else {
            input_bus_no = bus_no.getText().toString();
            srcstop = getsrcstop.getText().toString();
            destination = to.getText().toString();
            //append %20 if there is a space between the letters
            String v1[] = checkx();
//called to get the information of busses
            getdata(input_bus_no, v1[0], v1[1]);


        }
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
                        Toast.makeText(getActivity(), "No busses found", Toast.LENGTH_SHORT).show();
                        list.clear();

                    } else {
                        Log.i("res frm gting bus info", response.toString());

                        //called to done json parsing
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setMessage("Fetching The File....");
                        progressDialog.show();
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

        list1.clear();


        for (int i = 0; i < (data.length() - 1); i++) {
            JSONObject object = data.getJSONObject(i);
            lat = Double.parseDouble(object.getString("lat"));
            long1 = Double.parseDouble(object.getString("long"));
            id=object.getInt("id");

            Log.i("lat n long is", "" + lat + "" + long1);
            BusPosition busPosition = new BusPosition();
            busPosition.setLat(lat);
            busPosition.setLong1(long1);
            busPosition.setId(id);
            list1.add(busPosition);
            adapterBusInfo.setData1(list1);
            Log.i("data aded to the arraylist is", lat + "" + long1 + "to the position" + i);


            //get the time and distance from the source stop
            String url1 = "http://maps.googleapis.com/maps/api/distancematrix/json?" + "origins=" + lat + "," + long1 + "&destinations=" + x[0] + "," + x[1] + "&mode=bus";


            Log.i("url", url1);

            StringRequest request1 = new StringRequest(Request.Method.GET, url1, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
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
                                    progressDialog.cancel();

                                    distancetime[z] = textkms;
                                    distancetime[z + 1] = texttime;


                                }
                                z++;


                            }

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

            requestQueue.add(request1);

        }

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

    //append %20 to the string
    private String[] checkx() {
        String a[] = new String[2];
        int b1 = 0;
        x = null;
        y = null;
        // TODO Auto-generated method stub
        for (int i = 0; i < srcstop.length(); i++) {
            if (Character.isWhitespace(srcstop.charAt(i))) {
                b1 = 1;
                break;
            }
        }
        if (b1 == 1) {
            x = srcstop.replace(" ", append);
            a[0] = x;

        } else {
            a[0] = srcstop;

        }
        for (int i11 = 0; i11 < destination.length(); i11++) {
            if (Character.isWhitespace(destination.charAt(i11))) {
                b1 = 2;
                break;
            }
        }
        if (b1 == 2) {

            y = destination.replace(" ", append);
            a[1] = y;

        } else {
            a[1] = destination;

        }
        return a;

    }


    //bus stop selected as source stop
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            String result = data.getStringExtra("result");

            getsrcstop.setText(result);
//set visibility of to and from textview
            callback();
        }
        if (resultCode == RESULT_CANCELED) {    //Write your code if there's no result
            Toast.makeText(getActivity(), "please enter the correct bus no", Toast.LENGTH_SHORT).show();
            getsrcstop.setText("select the source stop");
        }


    }

    private void callback() {

        to.setVisibility(View.VISIBLE);
        from.setVisibility(View.VISIBLE);
        //get the source and destination of the entered bus no
        loadsrcdest();

    }

    private void loadsrcdest() {

        volleySingleton = VolleySingleton.getInstance();
        requestQueue = volleySingleton.getRequestQueue();
        String get_source_destination = "http://whereisthebus.in/srcdest.php?bus=" + input_bus_no;
        JsonObjectRequest b = new JsonObjectRequest(Request.Method.GET, get_source_destination, "null", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray items = null;
                try {

                    items = response.getJSONArray("row");
                    srcdest = new String[2];
                    int ji = 0;

                    srcdest[0] = items.getJSONObject(0).getString("Source");
                    srcdest[1] = items.getJSONObject(0).getString("dest");
                    assigndata(srcdest);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(b);
    }

    private void assigndata(String[] srcdest1) {

        to.setText(srcdest1[0]);
        from.setText(srcdest1[1]);

    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (!list.isEmpty()) {

            getsrcstop.setText("SELECT SOURCE STOP");
            to.setText("");
            from.setText("");
            list.clear();
            adapterBusInfo.setData(list);

        }

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}

