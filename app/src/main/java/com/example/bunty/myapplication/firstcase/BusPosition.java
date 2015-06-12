package com.example.bunty.myapplication.firstcase;

/**
 * Created by bunty on 3/29/2015.
 */
public class BusPosition {

    Double lat,long1;
    int id;

    public BusPosition()
    {

    }

    public BusPosition(Double lat,Double long1,int id)
    {
        this.id=id;
        this.lat=lat;
        this.long1=long1;
    }

    public void setLat(Double lat)
    {
        this.lat=lat;

    }

    public double getlat()
    {
        return lat;
    }

    public void setLong1(Double long1)
    {
        this.long1=long1;
    }

    public double getlong1()
    {
        return long1;
    }

    public void setId(int id)
    {
        this.id=id;
    }

    public int getid()
    {
        return id;
    }
}
