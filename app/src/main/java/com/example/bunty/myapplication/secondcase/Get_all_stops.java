package com.example.bunty.myapplication.secondcase;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bunty.myapplication.R;
import com.example.bunty.myapplication.network.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by bunty on 3/26/2015.
 */
public class Get_all_stops extends Activity implements AdapterView.OnItemClickListener {

    ListView l;
    private ArrayList<String> listData1;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_all_stops);
        l = (ListView) findViewById(R.id.listView2);
        l.setOnItemClickListener(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching The File....");
        progressDialog.show();

        loaddata();


    }

    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String src = listData1.get(position);
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result", src);
        setResult(RESULT_OK, returnIntent);
      //  setResult();
        finish();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
             //Log.d(this.getClass().getName(), "back button pressed");
            Intent returnIntent1 = new Intent();
            setResult(RESULT_CANCELED, returnIntent1);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    private void loaddata() {
//        L.t(this,busId);
        // Toast.makeText(this,"entered",Toast.LENGTH_SHORT).show();
        RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();

        String requesturl = "http://whereisthebus.in/stops.php";
        StringRequest request = new StringRequest(Request.Method.GET, requesturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //          L.t(MainActivity.this, "onResponse " + response);
                // if (response != null && response.length() > 0) {

                try {
                    listData1 = parseJSON(response);
                    //finaldata=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listData);
                    ArrayAdapter<String> finaldata = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, listData1);
                    l.setAdapter(finaldata);


                } catch (JSONException e) {

                }
                progressDialog.dismiss();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                L.t(MainActivity.this, "onError "+error.networkResponse.toString());
                //L.m("onError " + error.networkResponse + "");
                //buttonStart.setEnabled(false);
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
            listItems.add(object.getString("Stops"));

        }
        return listItems;
    }
}
