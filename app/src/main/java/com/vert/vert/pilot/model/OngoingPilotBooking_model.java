package com.vert.vert.pilot.model;

/**
 * Created by rajan on 10/15/18.
 */

public class OngoingPilotBooking_model {

    private String bookid;
    private String flightnumber;
    private String from;
    private String to;
    private String date;
    private String time;



    public OngoingPilotBooking_model(){

    }

    public OngoingPilotBooking_model(String bookid, String flightnumber, String from,String to,String date , String time) {
        this.bookid = bookid;
        this.flightnumber = flightnumber;
        this.from = from;
        this.to = to;
        this.date = date;
        this.time = time;


    }




    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getFlightnumber() {
        return flightnumber;
    }

    public void setFlightnumber(String flightnumber) {
        this.flightnumber = flightnumber;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}
