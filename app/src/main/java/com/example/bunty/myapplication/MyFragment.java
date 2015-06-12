package com.example.bunty.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bunty.myapplication.network.VolleySingleton;

/**
 * Created by bunty on 3/24/2015.
 */
public class MyFragment extends Fragment
{
    public static MyFragment getInstance(int position)
    {
        MyFragment myFragment=new MyFragment();
        return myFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.bus_no,container,false);

        RequestQueue requestQueue= VolleySingleton.getInstance().getRequestQueue();
        StringRequest request=new StringRequest(Request.Method.GET,"http://php.net/",new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getActivity(),response,Toast.LENGTH_SHORT).show();
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(request);
        return layout;
    }
}