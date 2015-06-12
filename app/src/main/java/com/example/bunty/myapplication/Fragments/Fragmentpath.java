package com.example.bunty.myapplication.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.bunty.myapplication.R;
import com.example.bunty.myapplication.secondcase.Get_all_stops;
import com.example.bunty.myapplication.thirdcase.Nearest_stop_bus_no;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragmentpath#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragmentpath extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Button dest_stop,find_near_stop;
    String result;
    private int RESULT_CANCELED=0;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragmentpath.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragmentpath newInstance(String param1, String param2) {
        Fragmentpath fragment = new Fragmentpath();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Fragmentpath() {
        // Required empty public constructor
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
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_path, container, false);
        dest_stop= (Button) v.findViewById(R.id.destinationstop);
        find_near_stop= (Button) v.findViewById(R.id.nearstop);
        dest_stop.setOnClickListener(this);
        find_near_stop.setOnClickListener(this);
        return v ;
    }


    @Override
    public void onClick(View v) {
       switch(v.getId())
       {
           case R.id.destinationstop:

               Intent i = new Intent(getActivity(), Get_all_stops.class);
               startActivityForResult(i, 1);
               break;


           case R.id.nearstop:
               if(dest_stop.equals("destination stop"))
               {
                   Toast.makeText(getActivity(),"select destination stop",Toast.LENGTH_SHORT).show();
               }
               else {

    //               Intent i1 = new Intent(getActivity(), Nearest_stop_bus_no.class);
                   Intent i1 = new Intent(getActivity(),Nearest_stop_bus_no.class);
                   i1.putExtra("destination",dest_stop.getText().toString());
                   startActivity(i1);
               }

               break;
       }

    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            result = data.getStringExtra("result");

            dest_stop.setText(result);
            //Toast.makeText(getActivity(), src.getText().toString(), Toast.LENGTH_SHORT).show();

        }
        if (resultCode == RESULT_CANCELED) {    //Write your code if there's no result
            if (!dest_stop.getText().toString().equals("")) {

            } else {
                result = "destination stop";
                dest_stop.setText(result);
                //Toast.makeText(getActivity(), src.getText().toString(), Toast.LENGTH_SHORT).show();
            }
            //src.setText("");

        }
    }
}
