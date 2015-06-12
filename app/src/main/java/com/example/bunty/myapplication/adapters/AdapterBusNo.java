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

import com.example.bunty.myapplication.R;
import com.example.bunty.myapplication.secondcase.BusData3;
import com.example.bunty.myapplication.secondcase.Bus_No_Stops;

import java.util.ArrayList;

/**
 * Created by bunty on 4/3/2015.
 */
public class AdapterBusNo extends RecyclerView.Adapter<AdapterBusNo.ViewHolderBusNo>{


    Context context;
    String source;

    private LayoutInflater layoutInflater;
    //    OnItemClickListener mItemClickListener;
    // private String data[];
    private ArrayList<Bus_No_Stops> list = new ArrayList<>();


    public AdapterBusNo(Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;

    }

    @Override
    public ViewHolderBusNo onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.each_bus_info2, parent, false);
        ViewHolderBusNo viewHolder = new ViewHolderBusNo(view);
        return viewHolder;
    }

    public void setData(ArrayList<Bus_No_Stops> list) {
        this.list = list;
        notifyItemRangeChanged(0, list.size());
    }

    public void getSource(String source)
    {
        this.source=source;


    }


    @Override
    public void onBindViewHolder(ViewHolderBusNo holder, final int position) {
        //holder.distance.setText(data[position]);
        Bus_No_Stops b1 = list.get(position);
        holder.bus_no.setText(b1.getBus_no());
        holder.stops.setText(b1.getStops());

        Log.i("onbind", "position is" + position);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolderBusNo extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView bus_no,stops;
        Button whereisit;

        public ViewHolderBusNo(View itemView) {

            super(itemView);
            bus_no = (TextView) itemView.findViewById(R.id.busno2);
            stops = (TextView) itemView.findViewById(R.id.noofstops);

            whereisit = (Button) itemView.findViewById(R.id.whereisit);
            // distance.setOnClickListener(this);
            whereisit.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {
            Log.i("on click", "called");
            Bus_No_Stops bns=list.get(getPosition());
            String no=bns.getBus_no();
            String stop=bns.getStops();
            String dest=bns.getDest();
            String src=source;

            Log.i("data is",no+""+stop+""+dest+""+src);

            Intent i=new Intent(context,BusData3.class);
            i.putExtra("dest",dest);
            i.putExtra("busno",no);
            i.putExtra("source",src);
            context.startActivity(i);



        }

    }
}