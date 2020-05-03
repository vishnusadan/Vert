package com.vert.vert.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.vert.vert.R;
import com.vert.vert.Static.Apilist;
import com.vert.vert.Static.Userdetails;
import com.vert.vert.model.Globals;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ContactUserScreen extends AppCompatActivity {

    EditText username,emailid,mobileno;
    private Button addpayment;
    private Double onewayprice,roundtripprice;

    private TextView total,totalprice;

    // Storing server url into String variable.
    String HttpUrl = Apilist.VIEWPROFILE;

    StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_user_screen);

        username = (EditText)findViewById(R.id.ed_username);
        emailid = (EditText)findViewById(R.id.ed_useremail);
        mobileno = (EditText)findViewById(R.id.ed_mobileno);
        addpayment = (Button) findViewById(R.id.submit);
        total = (TextView) findViewById(R.id.price);
        totalprice = (TextView) findViewById(R.id.totalprice);


        Globals sharedData = Globals.getInstance();
        String from = sharedData.getOnewaybookseat();


// miles calculating
        Double miles = distance(Double.parseDouble(Userdetails.lat1),Double.parseDouble(Userdetails.lng1),Double.parseDouble(Userdetails.lat2),Double.parseDouble(Userdetails.lng2));

        // calculation for price
        Double Nautical = miles*.87;
        String nauticalprice = String.valueOf(4*Nautical);
        Double originalprice = 4*Nautical+150;
        String dollar = String.valueOf(originalprice);

        String mainChapterNum = dollar.substring(0, dollar.indexOf("."));



        Globals seat = Globals.getInstance();
        seat.setNauticalprice(nauticalprice);



        if(Userdetails.BOOKTYPE.equals("oneway")){

            onewayprice = Double.parseDouble(mainChapterNum.trim())*Double.parseDouble(seat.getOnewaybookseat());

            roundtripprice=0.0;

            totalprice.setText("Total Price : $"+onewayprice);

        }else {

            onewayprice = Double.parseDouble(mainChapterNum.trim())*Double.parseDouble(seat.getOnewaybookseat());
            roundtripprice = Double.parseDouble(mainChapterNum.trim())*Double.parseDouble(seat.getRoundtripbookseat());

            totalprice.setText("Total Price : $"+(onewayprice+roundtripprice));
        }



        total.setText("OneWay Price : $"+onewayprice+"  Round Trip : $"+roundtripprice );


        // to get the user details
        getprofile();

        if(!Userdetails.username.equals("")){
              username.setText(Userdetails.username);
        }

        if(!Userdetails.usermailid.equals("")){
            emailid.setText(Userdetails.usermailid);
        }

        if(!Userdetails.userPhonenumber.equals("")){
            mobileno.setText(Userdetails.userPhonenumber);
        }


        addpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(username.getText().toString().equals("")){
                    username.setError("Please Enter Contact UserName");
                }else if (emailid.getText().toString().equals("")){
                    emailid.setError("Please Enter Mail Id");
                }else if(mobileno.getText().toString().equals("")){
                    mobileno.setError("Please Enter Mobile Number");
                }else {

//                    Toast.makeText(getApplicationContext(),username.getText().toString(),Toast.LENGTH_LONG).show();
                    Globals sharedData = Globals.getInstance();
                    sharedData.setBookusername(username.getText().toString());
                    sharedData.setBookemailid(emailid.getText().toString());
                    sharedData.setBookmobileno(mobileno.getText().toString());
                    sharedData.setOnewayprice(String.valueOf(onewayprice));
                    sharedData.setRoundtripprice(String.valueOf(roundtripprice));

                    Userdetails.lat1 = "";
                    Userdetails.lat2 = "";
                    Userdetails.lng1 = "";
                    Userdetails.lng2 = "";


//                    Userdetails.BOOKUSERNAME = username.getText().toString();
//                    Userdetails.BOOKEMAILID = emailid.getText().toString();
//                    Userdetails.BOOKMOBILENO = mobileno.getText().toString();
//                    Userdetails.ONEWAYPRICE = String.valueOf(price);
//                    Userdetails.ROUNDTRIPPRICE = String.valueOf(price1);

//                    Toast.makeText(getApplicationContext(),"Successfully Booked",Toast.LENGTH_LONG).show();

                    startActivity(new Intent(ContactUserScreen.this,PaymentList.class));
                }
            }
        });
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }





    public void getprofile(){



        // Creating string request with post method.
        stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {



                        // Showing response message coming from server.
//                                        Toast.makeText(VerificationActivity.this, ServerResponse, Toast.LENGTH_LONG).show();

                        try {

                            JSONObject jsondata = new JSONObject(ServerResponse);


                            JSONArray jsonArray = jsondata.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject json = jsonArray.getJSONObject(i);


                                String namev = json.getString("username");
                                String emailv = json.getString("email_id");
                                String phonenumberv = json.getString("phone_number");



//                                Toast.makeText(ContactUserScreen.this, namev, Toast.LENGTH_SHORT).show();

                                if(namev.equals("null")){

                                    String usernm = "";
                                    username.setText(usernm);

                                }else {

                                    username.setText(namev);
                                    Userdetails.username = namev;

                                }

                                if(emailv.equals("null")){

                                    String emailidv = "";
                                    emailid.setText(emailidv);

                                }else {

                                    emailid.setText(emailv);
                                    Userdetails.usermailid = emailv;
                                }


                                if(phonenumberv.equals("null")){

                                    String phoneno = "";
                                    mobileno.setText(phoneno);

                                }else {

                                    mobileno.setText(phonenumberv);
                                    Userdetails.userPhonenumber = phonenumberv;

                                }



                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }

                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {



                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                100,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        // Showing error message if something goes wrong.
                        Toast.makeText(ContactUserScreen.this, "Please Check Your Internet", Toast.LENGTH_LONG).show();

                        Log.d("error", volleyError.toString());
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


                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(ContactUserScreen.this);

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);




    }




}
