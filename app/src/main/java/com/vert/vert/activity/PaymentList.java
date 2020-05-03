package com.vert.vert.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.vert.vert.model.Globals;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PaymentList extends AppCompatActivity implements View.OnClickListener {

    private Button sumbit;
    private EditText cardname,cardnumber,month,year,cvv;

    // Storing server url into String variable.
    String HttpUrl = Apilist.BOOKTICKET;

    // Creating Volley RequestQueue.
    RequestQueue requestQueue;

    // Creating Progress dialog.
    ProgressDialog progressDialog;

    private StringRequest stringRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_list);

        sumbit = (Button)findViewById(R.id.submit);
        sumbit.setOnClickListener(this);

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(PaymentList.this);

        //progress daialog
        progressDialog = new ProgressDialog(PaymentList.this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.submit:

                Book();

                break;

        }
    }

    public void Book(){


//          Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait On Booking");
        progressDialog.show();


        // Creating string request with post method.
        stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing response message coming from server.
//                            Toast.makeText(PaymentList.this, ServerResponse, Toast.LENGTH_LONG).show();
//
//                            Log.d("Response",ServerResponse);

                        Toast.makeText(PaymentList.this, "Successfully Booked", Toast.LENGTH_LONG).show();


                                startActivity(new Intent(PaymentList.this,HomeScreen.class));
                                finish();




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
                        Toast.makeText(PaymentList.this, "Please Check your Internet", Toast.LENGTH_LONG).show();

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
                Globals sharedData = Globals.getInstance();

                Log.d("username",sharedData.getBookusername());
//                Toast.makeText(getApplicationContext(),sharedData.getBookusername(),Toast.LENGTH_LONG).show();

                if(Userdetails.BOOKTYPE.equals("oneway")){

                    params.put("book_type", Userdetails.BOOKTYPE);
                    params.put("user_id", Userdetails.userid);
                    params.put("source", sharedData.getOnewayfrom());
                    params.put("destination", sharedData.getOnewayto());
                    params.put("seat",sharedData.getOnewaybookseat());
                    params.put("flight_date",sharedData.getOnewaybookdate());
                    params.put("price",sharedData.getOnewayprice());
                    params.put("contact_mail",sharedData.getBookemailid());
                    params.put("contact_username",sharedData.getBookusername());
                    params.put("phone_number",sharedData.getBookmobileno());
                    params.put("card_username",sharedData.getBookusername());
                    params.put("payment_status","yes");
                    params.put("payment_type","card");
                    params.put("flight_time",sharedData.getOnewaybooktime());
                    params.put("name",sharedData.getOnewaybookname());
                    params.put("nametwo",sharedData.getOnewaybookname1());
                    params.put("namethree",sharedData.getOnewaybookname2());
                    params.put("namefour",sharedData.getOnewaybookname3());
                    params.put("pounds",sharedData.getOnewaypound());
                    params.put("poundstwo",sharedData.getOnewaypound1());
                    params.put("poundsthree",sharedData.getOnewaypound2());
                    params.put("poundsfour",sharedData.getOnewaypound3());
                    params.put("nautical_mile_charges",sharedData.getNauticalprice());

                }else {


                    params.put("source", sharedData.getOnewayfrom());
                    params.put("destination", sharedData.getOnewayto());
                    params.put("seat",sharedData.getOnewaybookseat());
                    params.put("flight_date",sharedData.getOnewaybookdate());
                    params.put("price",sharedData.getOnewayprice());
                    params.put("flight_time",sharedData.getOnewaybooktime());
                    params.put("name",sharedData.getOnewaybookname());
                    params.put("nametwo",sharedData.getOnewaybookname1());
                    params.put("namethree",sharedData.getOnewaybookname2());
                    params.put("namefour",sharedData.getOnewaybookname3());
                    params.put("pounds",sharedData.getOnewaypound());
                    params.put("poundstwo",sharedData.getOnewaypound1());
                    params.put("poundsthree",sharedData.getOnewaypound2());
                    params.put("poundsfour",sharedData.getOnewaypound3());



                    params.put("book_type", Userdetails.BOOKTYPE);
                    params.put("user_id", Userdetails.userid);
                    params.put("sourcetwo", sharedData.getRoundtripfrom());
                    params.put("destinationtwo", sharedData.getRoundtripto());
                    params.put("seattwo", sharedData.getRoundtripbookseat());
                    params.put("return_date", sharedData.getRoundtripbookdate());
                    params.put("pricetwo", sharedData.getRoundtripprice());

                    params.put("contact_mail", sharedData.getBookemailid());
                    params.put("contact_username", sharedData.getBookusername());
                    params.put("phone_number", sharedData.getBookmobileno());
                    params.put("card_username", sharedData.getBookusername());
                    params.put("payment_status", "yes");
                    params.put("payment_type", "card");

                    params.put("flight_timetwo", sharedData.getRoundtripbooktime());

                    params.put("returnname", sharedData.getRoundtripbookname());
                    params.put("returnnametwo", sharedData.getRoundtripbookname1());
                    params.put("returnnamethree", sharedData.getRoundtripbookname2());
                    params.put("returnnamefour", sharedData.getRoundtripbookname3());

                    params.put("return_pounds", sharedData.getRoundtrippound());
                    params.put("return_poundstwo", sharedData.getRoundtrippound1());
                    params.put("return_poundsthree", sharedData.getRoundtrippound2());
                    params.put("return_poundsfour", sharedData.getRoundtrippound3());
                    params.put("nautical_mile_charges",sharedData.getNauticalprice());

                    Log.d("responseup", sharedData.getOnewayfrom());

                }

                return params;

            }
        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(PaymentList.this);

//             Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);


    }

}
