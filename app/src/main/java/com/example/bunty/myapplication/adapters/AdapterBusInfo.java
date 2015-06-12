package com.example.bunty.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bunty.myapplication.firstcase.Bus;
import com.example.bunty.myapplication.firstcase.BusPosition;
import com.example.bunty.myapplication.R;
import com.example.bunty.myapplication.firstcase.DisplayBusOnTheMap;

import java.util.ArrayList;

/**
 * Created by bunty on 3/28/2015.
 */
public class AdapterBusInfo extends RecyclerView.Adapter<AdapterBusInfo.ViewHolderBusInfo> {

    Context context;
    double lat, long1;
    int id;
    private LayoutInflater layoutInflater;
    //    OnItemClickListener mItemClickListener;
    // private String data[];
    private ArrayList<Bus> list = new ArrayList<>();
    private ArrayList<BusPosition> list1 = new ArrayList<>();

    public AdapterBusInfo(Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;

    }

    @Override
    public ViewHolderBusInfo onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.each_bus_info, parent, false);
        ViewHolderBusInfo viewHolder = new ViewHolderBusInfo(view);
        return viewHolder;
    }

    //    public void setData(String [] d)
//    {
//        this.data=d;
//    }
    public void setData(ArrayList<Bus> list) {
        this.list = list;
        notifyItemRangeChanged(0, list.size());
    }

    public void setData1(ArrayList<BusPosition> list1) {
        this.list1 = list1;
        notifyItemRangeChanged(0, list1.size());
    }

    @Override
    public void onBindViewHolder(ViewHolderBusInfo holder, final int position) {
        //holder.distance.setText(data[position]);
        Bus b1 = list.get(position);
        holder.distance.setText(b1.getDistance());
        holder.time.setText(b1.getTime());
        holder.location.setText(b1.getLocation());
//        BusPosition b2=list1.get(position);
//            lat=b2.getlat();
//            long1=b2.getlong1();
        // Toast.makeText(context,"onbind called for position"+position,Toast.LENGTH_SHORT).show();
        Log.i("onbind", "position is" + position);
//        holder.distance.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "position is" + position, Toast.LENGTH_SHORT).show();
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolderBusInfo extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView location, time, distance;
        Button track;

        public ViewHolderBusInfo(View itemView) {

            super(itemView);
            location = (TextView) itemView.findViewById(R.id.buslocation1);
            time = (TextView) itemView.findViewById(R.id.time1);
            distance = (TextView) itemView.findViewById(R.id.distance1);
            track = (Button) itemView.findViewById(R.id.track);
            // distance.setOnClickListener(this);
            track.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            Log.i("on click", "called");
//            Log.i("size of list 1 in adapter is",list1.size()+"");
//            Log.i("data at 0 is","lat is"+list1.get(0).getlat()+"and long is"+list1.get(0).getlong1());
//            Log.i("data at 1 is","lat is"+list1.get(1).getlat()+"and long is"+list1.get(1).getlong1());
//            Log.i("data at 2 is","lat is"+list1.get(2).getlat()+"and long is"+list1.get(2).getlong1());
//            Log.i("data at 3 is","lat is"+list1.get(3).getlat()+"and long is"+list1.get(3).getlong1());

            Toast.makeText(context, "the size of the list 1 is" + list1.size(), Toast.LENGTH_SHORT).show();

//            Data d=new Data(getPosition());
            // int x=list1.size();
            BusPosition b2 = list1.get(getPosition());
            lat = b2.getlat();
//
            long1 = b2.getlong1();
            id=b2.getid();
//            Toast.makeText(context,"lat is"+lat+"and long is"+long1,Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "lat" + lat + "and long is" + long1, Toast.LENGTH_SHORT).show();

            Toast.makeText(context, "position is" + getPosition(), Toast.LENGTH_SHORT).show();

            Intent i = new Intent(context, DisplayBusOnTheMap.class);
            //Bundle b=new Bundle();
//            b.putString("lat",lat);
//            b.putString("long",long1);
            i.putExtra("lat", lat);
            i.putExtra("long", long1);
            i.putExtra("id",id);
//            b.putDouble("lat",lat);
//            b.putDouble("long",long1);
            // i.putExtras(b);

            context.startActivity(i);

        }

        //@Override
//        public void onClick(View v) {
//            if (mItemClickListener != null) {
//                mItemClickListener.onItemClick(v, getPosition()); //OnItemClickListener mItemClickListener;
//            }
//        }

    }
//    public interface OnItemClickListener {
//        public void onItemClick(View view , int position);
//
//    }
//
//    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener)
//    {
//        this.mItemClickListener=mItemClickListener;
//    }

    public class Data {
        int position;

        public Data(int position) {
            this.position = position;
        }

        BusPosition b2 = list1.get(position);
        double lat = b2.getlat();
        double long1 = b2.getlong1();
    }
}

