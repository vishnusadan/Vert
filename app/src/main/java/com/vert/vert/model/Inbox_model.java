package com.vert.vert.model;

/**
 * Created by rajan on 10/15/18.
 */

public class Inbox_model {

    private String bookid;
    private String from;
    private String to;
    private String date;
    private String time;
    private String message;
    private String bookdate;
    private String contactusername;



    public Inbox_model(){

    }

    public Inbox_model(String bookid, String from, String to, String date, String time, String message, String contactusername,String bookdate) {
        this.bookid = bookid;
        this.from = from;
        this.to = to;
        this.date = date;
        this.time = time;
        this.message = message;
        this.contactusername = contactusername;
        this.bookdate = bookdate;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



    public String getBookdate() {
        return bookdate;
    }

    public void setBookdate(String bookdate) {
        this.bookdate = bookdate;
    }

    public String getContactusername() {
        return contactusername;
    }

    public void setContactusername(String contactusername) {
        this.contactusername = contactusername;
    }



}
