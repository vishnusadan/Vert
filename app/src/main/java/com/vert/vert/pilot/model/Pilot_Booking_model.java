package com.vert.vert.pilot.model;

/**
 * Created by rajan on 10/24/18.
 */

public class Pilot_Booking_model {

    private String bookid;
    private String from;
    private String to;
    private String userid;
    private String time;
    private String flightdate;




    public Pilot_Booking_model(){

    }

    public Pilot_Booking_model(String bookid, String from, String to,String flightdate,String userid, String time) {

        this.bookid = bookid;
        this.from = from;
        this.to = to;
        this.userid = userid;
        this.time = time;
        this.flightdate = flightdate;

    }




    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }



    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFlightdate() {
        return flightdate;
    }

    public void setFlightdate(String flightdate) {
        this.flightdate = flightdate;
    }
}

