package com.vert.vert.tab;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.vert.vert.adapter.BookingCompletedAdapter;
import com.vert.vert.adapter.BookingOngoingAdapter;
import com.vert.vert.model.CompletedBooking_model;
import com.vert.vert.model.OngoingBooking_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rajan on 10/15/18.
 */

public class Completed_Booking_Fragment extends Fragment {

    private RecyclerView recyclerViewcomplete;

    //adapter object
    private BookingCompletedAdapter adapterGifts;

    // Creating Volley RequestQueue.
    RequestQueue requestQueue;

    // Creating Progress dialog.
    ProgressDialog progressDialog;

    private StringRequest stringRequest;

    //list to hold all the uploaded images
    private List<CompletedBooking_model> completedModelList;

    public String todaydate;


    public Completed_Booking_Fragment() {
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View completed =inflater.inflate(R.layout.fragment_completedbook,container,false);

        recyclerViewcomplete = (RecyclerView) completed.findViewById(R.id.recyclerView_complete);
        recyclerViewcomplete.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewcomplete.setLayoutManager(llm);

        progressDialog = new ProgressDialog(getActivity());

        completedModelList = new ArrayList<CompletedBooking_model>();

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(getActivity());

        //progress daialog
        progressDialog = new ProgressDialog(getActivity());


        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        todaydate = df.format(c);
        Userdetails.todaydate=todaydate;

        getData();


        return completed;
    }

    public void getData()

    {
//        Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();


        // Creating string request with post method.
        stringRequest = new StringRequest(Request.Method.POST, Apilist.COMPLETEDGBOOKING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing response message coming from server.
//                            Toast.makeText(LoginScreen.this, ServerResponse, Toast.LENGTH_LONG).show();
//
                        Log.d("Response",ServerResponse);


                        try {

                            JSONObject jsondata = new JSONObject(ServerResponse);


                            JSONArray jsonArray = jsondata.getJSONArray("data");

                            for (int i = jsonArray.length()-1; i>=0; i--) {

                                JSONObject json = jsonArray.getJSONObject(i);

                                String bookid = json.getString("book_id");
                                String flightnumber = json.getString("flight_number");
                                String flightname = json.getString("flight_name");
                                String from = json.getString("source");
                                String to = json.getString("destination");
                                String flightdate = json.getString("flight_date");
                                String flighttime = json.getString("flight_time");
                                String flightstatus = json.getString("flight_status");
                                String name = json.getString("name");
                                String name2 = json.getString("nametwo");
                                String name3 = json.getString("namethree");
                                String name4 = json.getString("namefour");
                                String pound = json.getString("pounds");
                                String pound2 = json.getString("poundstwo");
                                String pound3 = json.getString("poundsthree");
                                String pound4 = json.getString("poundsfour");
                                String price = json.getString("price");
                                String paymentstatus = json.getString("payment_status");
                                String paymenttype = json.getString("payment_type");
                                String seat = json.getString("seat");
                                String bookdate = json.getString("book_date");
                                String contactusername = json.getString("contact_username");
                                String contactemail = json.getString("contact_email");
                                String phonenumber = json.getString("phone_number");
                                String bookstatus = json.getString("book_status");

//                                Toast.makeText(getActivity(),seat,Toast.LENGTH_LONG).show();

                                completedModelList.add(new CompletedBooking_model(bookid, flightnumber, flightname, from,to,flightdate,flighttime,flightstatus,name,name2,name3,name4,pound,pound2,pound3,pound4,
                                        price,paymentstatus,paymenttype,seat,bookdate,contactusername,contactemail,phonenumber,bookstatus));


//                                Toast.makeText(getActivity(),bookid+flightname+flightnumber,Toast.LENGTH_LONG).show();


                                //creating adapter
                                adapterGifts = new BookingCompletedAdapter(getActivity(), completedModelList);

                                //adding adapter to recyclerview
                                recyclerViewcomplete.setAdapter(adapterGifts);
                                adapterGifts.notifyDataSetChanged();

                            }

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
                        Toast.makeText(getActivity(), "Please Check your Internet", Toast.LENGTH_LONG).show();

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
                params.put("date", Userdetails.todaydate);


                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

//             Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);


    }

}
