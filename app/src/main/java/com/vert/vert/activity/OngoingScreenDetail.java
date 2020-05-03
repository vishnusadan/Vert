package com.vert.vert.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.vert.vert.R;
import com.vert.vert.Static.Apilist;
import com.vert.vert.Static.Userdetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OngoingScreenDetail extends AppCompatActivity implements View.OnClickListener{

    String bookid,flightnumber,flightname,from,to,flightdate,flighttime,flightstatus,name,name2,name3,name4,
            pound,pound2,pound3,pound4, price,paymentstatus,seat,bookdate,contactusername,contactemail,contactphonenumber,bookstatus;

    private TextView tvbookid,tvflightnumber,tvflightname,tvfrom,tvto,tvflightdate,tvflighttime,tvflightstatus,tvname,tvname2,tvname3,tvname4,
            tvpound,tvpound2,tvpound3,tvpound4, tvprice,tvpaymentstatus,tvseat,tvbookdate,tvcontactusername,tvcontactemail,tvcontactphonenumber,tvbookstatus;

    private Button cancel,close;

    // Creating Volley RequestQueue.
    RequestQueue requestQueue;

    // Creating Progress dialog.
    ProgressDialog progressDialog;

    private StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ongoing_screen_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Book Detail");
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        bookid = intent.getStringExtra("bookid");
        flightnumber = intent.getStringExtra("flightnumber");
        flightname = intent.getStringExtra("flightname");
        from = intent.getStringExtra("from");
        to = intent.getStringExtra("to");
        flightdate = intent.getStringExtra("flightdate");
        flighttime = intent.getStringExtra("flighttime");
        flightstatus = intent.getStringExtra("flightstatus");
        name = intent.getStringExtra("name");
        name2 = intent.getStringExtra("name2");
        name3 = intent.getStringExtra("name3");
        name4 = intent.getStringExtra("name4");
        pound = intent.getStringExtra("pound");
        pound2 = intent.getStringExtra("pound2");
        pound3 = intent.getStringExtra("pound3");
        pound4 = intent.getStringExtra("pound4");
        price = intent.getStringExtra("price");
        paymentstatus = intent.getStringExtra("paymentstatus");
        seat = intent.getStringExtra("seat");
        bookdate = intent.getStringExtra("bookdate");
        contactusername = intent.getStringExtra("contactusername");
        contactemail = intent.getStringExtra("contactemailid");
        contactphonenumber = intent.getStringExtra("contactphonenumber");
        bookstatus = intent.getStringExtra("bookstatus");

//        Toast.makeText(this, bookid, Toast.LENGTH_SHORT).show();


        cancel = (Button)findViewById(R.id.cancel);
        close = (Button)findViewById(R.id.close);

        close.setOnClickListener(this);
        cancel.setOnClickListener(this);


        tvbookid = (TextView)findViewById(R.id.bookid);
        tvflightnumber = (TextView)findViewById(R.id.flightnumber);
        tvflightname = (TextView)findViewById(R.id.flightname);
        tvflightdate = (TextView)findViewById(R.id.flightdate);
        tvflighttime = (TextView)findViewById(R.id.flighttime);
        tvflightstatus = (TextView)findViewById(R.id.flightstatus);
        tvfrom = (TextView)findViewById(R.id.from);
        tvto = (TextView)findViewById(R.id.to);
        tvseat = (TextView)findViewById(R.id.seat);
        tvpaymentstatus = (TextView)findViewById(R.id.paymentstatus);
        tvname = (TextView)findViewById(R.id.name);
        tvname2 = (TextView)findViewById(R.id.name2);
        tvname3 = (TextView)findViewById(R.id.name3);
        tvname4 = (TextView)findViewById(R.id.name4);
        tvpound = (TextView)findViewById(R.id.pound);
        tvpound2 = (TextView)findViewById(R.id.pound2);
        tvpound3 = (TextView)findViewById(R.id.pound3);
        tvpound4 = (TextView)findViewById(R.id.pound4);
        tvbookdate = (TextView)findViewById(R.id.bookdate);
        tvbookstatus = (TextView)findViewById(R.id.bookstatus);
        tvcontactusername = (TextView)findViewById(R.id.contactusername);
        tvcontactemail = (TextView)findViewById(R.id.contactemail);
        tvcontactphonenumber = (TextView)findViewById(R.id.contactphonenumber);
        tvprice = (TextView)findViewById(R.id.price);


        // make all invisible
        tvname.setVisibility(View.GONE);
        tvpound.setVisibility(View.GONE);
        tvname2.setVisibility(View.GONE);
        tvpound2.setVisibility(View.GONE);
        tvname3.setVisibility(View.GONE);
        tvpound3.setVisibility(View.GONE);
        tvname4.setVisibility(View.GONE);
        tvpound4.setVisibility(View.GONE);


        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(OngoingScreenDetail.this);

        //progress daialog
        progressDialog = new ProgressDialog(OngoingScreenDetail.this);





        switch (seat){

            case "1":

                tvname.setVisibility(View.VISIBLE);
                tvpound.setVisibility(View.VISIBLE);

                break;


            case "2":

                tvname.setVisibility(View.VISIBLE);
                tvpound.setVisibility(View.VISIBLE);
                tvname2.setVisibility(View.VISIBLE);
                tvpound2.setVisibility(View.VISIBLE);


                break;

            case  "3":


                tvname.setVisibility(View.VISIBLE);
                tvpound.setVisibility(View.VISIBLE);
                tvname2.setVisibility(View.VISIBLE);
                tvpound2.setVisibility(View.VISIBLE);
                tvname3.setVisibility(View.VISIBLE);
                tvpound3.setVisibility(View.VISIBLE);

                break;


            case  "4":



                tvname.setVisibility(View.VISIBLE);
                tvpound.setVisibility(View.VISIBLE);
                tvname2.setVisibility(View.VISIBLE);
                tvpound2.setVisibility(View.VISIBLE);
                tvname3.setVisibility(View.VISIBLE);
                tvpound3.setVisibility(View.VISIBLE);
                tvname4.setVisibility(View.VISIBLE);
                tvpound4.setVisibility(View.VISIBLE);


                break;

                default:

                    Toast.makeText(this, "Something went Wrong", Toast.LENGTH_SHORT).show();

                    break;
        }


        tvbookid.setText("Book Id = "+bookid);
        tvfrom.setText("From = "+from);
        tvto.setText("To = "+to);
        tvflightdate.setText("Flight Date = "+flightdate);
        tvflighttime.setText("Flight Time = "+flighttime);
        tvname.setText("First Member Name = "+name);
        tvname2.setText("Second Member Name = "+name2);
        tvname3.setText("Thirf Member Name = "+name3);
        tvname4.setText("Fourth Member Name = "+name4);
        tvpound.setText("Pounds for First Member = "+pound);
        tvpound2.setText("Pounds for Second Member = "+pound2);
        tvpound3.setText("Pounds for Third Member = "+pound3);
        tvpound4.setText("Pounds for Fourth Member = "+pound4);
        tvprice.setText("Price = "+price);
        tvseat.setText("Seat = "+seat);
        tvbookdate.setText("Book Date = "+bookdate);
        tvcontactusername.setText("Contact Username = "+contactusername);
        tvcontactemail.setText("Contact Email Id = "+contactemail);
        tvcontactphonenumber.setText("Contact Phonenumber = "+contactphonenumber);
        tvbookstatus.setText("Book Status = "+bookstatus);

        if(flightnumber.equals("null")){

            tvflightnumber.setText("Flight Number = Still Not Assigned");

        }else {

            tvflightnumber.setText("Flight Number = "+flightnumber);
        }


        if(flightname.equals("null")){

            tvflightname.setText("Flight Name = Still Not Assigned");


        }else {

            tvflightname.setText("Flight Name = "+flightname);

        }

        if(flightstatus.equals("null")){

            tvflightstatus.setText("Flight Status = On Service");


        }else {

            tvflightstatus.setText("Flight Status = "+flightstatus);

        }

        if(paymentstatus.equals("yes")){

            tvpaymentstatus.setText("Payment Status = Success");


        }else {

            tvpaymentstatus.setText("Payment Status = Failure");

        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){


            case R.id.cancel:

                new AlertDialog.Builder(this,R.style.DialogeTheme)
                        .setMessage("Are you sure you want to Cancel?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                // cancel the ticket
                              cancelticket();

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();


                break;

            case R.id.close:

                finish();

                break;
        }
    }


    public void cancelticket(){



//          Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();


        // Creating string request with post method.
        stringRequest = new StringRequest(Request.Method.POST, Apilist.CANCELTICKET,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing response message coming from server.
//                            Toast.makeText(LoginScreen.this, ServerResponse, Toast.LENGTH_LONG).show();
//
//                            Log.d("Response",ServerResponse);

                        try {
                            JSONObject jsondata = new JSONObject(ServerResponse);


                            JSONArray jsonArray = jsondata.getJSONArray("data");

                            Toast.makeText(OngoingScreenDetail.this, "Cancelled The booking", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(OngoingScreenDetail.this,HomeScreen.class));
                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }




                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                100,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        // Showing error message if something goes wrong.
                        Toast.makeText(OngoingScreenDetail.this, "Please Check your Internet", Toast.LENGTH_LONG).show();

//                            Log.d("error",volleyError.toString());
                    }
                }
        )

        {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.

                params.put("user_id", Userdetails.userid);
                params.put("book_id", bookid);

                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(OngoingScreenDetail.this);

//             Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);



    }
}
