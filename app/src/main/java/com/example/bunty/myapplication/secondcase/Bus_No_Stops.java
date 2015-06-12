package com.example.bunty.myapplication.secondcase;

/**
 * Created by bunty on 4/3/2015.
 */
public class Bus_No_Stops {
    String bus_no;
    String stops;
    String dest;


        public Bus_No_Stops() {

        }

        public Bus_No_Stops(String bus_no, String stops,String dest) {
            this.bus_no = bus_no;
            this.stops = stops;
            this.dest=dest;



        }

        public String getBus_no() {
            return bus_no;
        }

        public void setBus_no(String bus_no) {
            this.bus_no = bus_no;
        }

        public String getStops() {
            return stops;
        }

        public void setStops(String stops) {
            this.stops = stops;
        }


    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }






}


