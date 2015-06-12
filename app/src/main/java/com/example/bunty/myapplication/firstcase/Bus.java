package com.example.bunty.myapplication.firstcase;

/**
 * Created by bunty on 3/28/2015.
 */
public class Bus {
    String location;
    String time;
    String distance;


    public Bus() {

    }

    public Bus(String time, String distance, String location) {
        this.time = time;
        this.distance = distance;
        this.location = location;


    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

}
