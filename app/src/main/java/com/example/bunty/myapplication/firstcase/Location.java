package com.example.bunty.myapplication.firstcase;

import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bunty.myapplication.network.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by bunty on 3/27/2015.
 */
/*public class Location{

    String bus_no,srcstop,deststop;

    public Location(String no,String src,String dest)

    {
        this.bus_no=no;
        this.srcstop=src;
        this.deststop=dest;

    }

    public ArrayList<String> getdata()
    {
        RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();
        String url="http://whereisthebus.in/bus_position1.php?bus_no="+bus_no+"&source_stop="+srcstop+"&dest="+deststop;
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    if(response.equals("null"))
                    {
                       // Toast.makeText(getActivity(), "No busses found", Toast.LENGTH_SHORT).show();
                       // progressDialog.dismiss();
                    }
                    else {
                        listData2 = parseJSON(response);

                        //  finaldata = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, listData2);
                        finaldata.addAll(listData2);
                        l1.setAdapter(finaldata);
                        //progressDialog.dismiss();
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


    }

}
*/