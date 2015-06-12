package com.example.bunty.myapplication.Fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.bunty.myapplication.secondcase.Get_all_stops;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragmentsrcdest#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragmentsrcdest extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button src, dest, find;
    private AdapterBusNo adapterBusNo;
    private ArrayList<Bus_No_Stops> list=new ArrayList<>();
    private RecyclerView busdata;
   // ListView l1;
    String result, result1;
    String busdest[];
    String x, in1, in2, y;
    String append = "%20";
    String x1 = "source stop";
    String x2="destination stop";
    //private ArrayList<String> listData2 = new ArrayList<>();
    //    ArrayAdapter<String> finaldata= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData2);
   // ArrayAdapter<String> finaldata;
    ProgressDialog progressDialog;
    private int RESULT_CANCELED = 0;
    //private String RESULT_CANCELED="RESULT_CANCELED";


    public static Fragmentsrcdest newInstance(String param1, String param2) {
        Fragmentsrcdest fragment = new Fragmentsrcdest();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragmentsrcdest() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_src_dest, container, false);
        src = (Button) v.findViewById(R.id.srcstop);
        find = (Button) v.findViewById(R.id.find);
        dest = (Button) v.findViewById(R.id.desstop);
        //l1 = (ListView) v.findViewById(R.id.listView3);
        src.setOnClickListener(this);
        dest.setOnClickListener(this);
        find.setOnClickListener(this);

        busdata= (RecyclerView) v.findViewById(R.id.busdata);
        busdata.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterBusNo=new AdapterBusNo(getActivity());
        busdata.setAdapter(adapterBusNo);
        //finaldata = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, listData2);
        return v;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.srcstop:
                list.clear();
                adapterBusNo.setData(list);
                Intent i = new Intent(getActivity(), Get_all_stops.class);
                startActivityForResult(i, 1);
                //  listData2=null;
    //            finaldata.clear();
                //  finaldata.addAll(listData2);



                break;

            case R.id.desstop:
                list.clear();
                adapterBusNo.setData(list);
                Intent i1 = new Intent(getActivity(), Get_all_stops.class);
                startActivityForResult(i1, 2);
      //          finaldata.clear();
                //list.clear();

                break;

            case R.id.find:
                list.clear();
                adapterBusNo.setData(list);
                //              Toast.makeText(getActivity(),"button clicked",Toast.LENGTH_SHORT).show();
                if (src.getText().toString().equals(x1) || dest.getText().toString().equals(x2)) {
//                    Toast.makeText(getActivity(),"if part executed",Toast.LENGTH_SHORT).show();

                    Toast.makeText(getActivity(), "please enter the complete details ", Toast.LENGTH_SHORT).show();
                }
//                if (dest.toString().equals("")) {
//Toast.makeText(getActivity(),"please select the destination ",Toast.LENGTH_SHORT).show();
//
//                }
                else {

                    if(src.getText().toString().equals(dest.getText().toString()))
                    {
                        Toast.makeText(getActivity(), "u r already there ", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        //                Toast.makeText(getActivity(),"else part executed",Toast.LENGTH_SHORT).show();
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setMessage("Fetching The File....");
                        progressDialog.show();

                        //pass the source and destination to get the bus no
                        getbuses(result, result1);
                    }
                }

        }


    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            result = data.getStringExtra("result");

            src.setText(result);
            //Toast.makeText(getActivity(), src.getText().toString(), Toast.LENGTH_SHORT).show();

        }
        if (resultCode == RESULT_CANCELED) {    //Write your code if there's no result
            if (!src.getText().toString().equals("")) {

            } else {
                result = "source stop";
                src.setText(result);
            //    Toast.makeText(getActivity(), src.getText().toString(), Toast.LENGTH_SHORT).show();
            }
            //src.setText("");

        }

        if (requestCode == 2) {

            result1 = data.getStringExtra("result");

            dest.setText(result1);
          //  Toast.makeText(getActivity(), dest.getText().toString(), Toast.LENGTH_SHORT).show();

        }
        if (resultCode == RESULT_CANCELED) {    //Write your code if there's no result
            if (!dest.getText().toString().equals("")) {

            } else {
                result1 = "destination stop";
                dest.setText(result1);
              //  Toast.makeText(getActivity(), dest.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        }


    }

    private void getbuses(String src, String dest) {
        in1 = src;
        in2 = dest;
        String x[] = new String[2];

        //to append %20 to the source and destination
        x = checkx();
        adapterBusNo.getSource(x[0]);

        //get the final bus no
        getbusno(x);


    }

    private void getbusno(String[] x) {


        RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();

        final String busno = "http://whereisthebus.in/path.php?source="
                + x[0] + "&destination=" + x[1];
        StringRequest request = new StringRequest(Request.Method.GET, busno, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if(response.equals("null"))
                    {
                     Toast.makeText(getActivity(),"No busses found",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                    else {
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



                        }


                        //  finaldata = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listData2);
        //                finaldata.addAll(listData2);
                        //l1.setAdapter(finaldata);
                        progressDialog.dismiss();
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

    private ArrayList<String> parseJSON(String output) throws JSONException {
        JSONObject json = new JSONObject(output);
        JSONArray data = json.getJSONArray("row");
        ArrayList<String> listItems = new ArrayList<>();
        for (int i = 0; i < data.length(); i++) {
            JSONObject object = data.getJSONObject(i);
            listItems.add(object.getString("bus_no"));

        }
        return listItems;
    }

    //append %20
    private String[] checkx() {
        String a[] = new String[2];
        int b1 = 0;
        x = null;
        y = null;
        // TODO Auto-generated method stub
        for (int i = 0; i < in1.length(); i++) {
            if (Character.isWhitespace(in1.charAt(i))) {
                b1 = 1;
                break;
            }
        }
        if (b1 == 1) {
            x = in1.replace(" ", append);
            a[0] = x;

        } else {
            a[0] = in1;

        }
        for (int i11 = 0; i11 < in2.length(); i11++) {
            if (Character.isWhitespace(in2.charAt(i11))) {
                b1 = 2;
                break;
            }
        }
        if (b1 == 2) {

            y = in2.replace(" ", append);
            a[1] = y;

        } else {
            a[1] = in2;

        }
        return a;

    }

}
