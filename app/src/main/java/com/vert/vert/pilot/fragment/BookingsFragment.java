package com.vert.vert.pilot.fragment;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.vert.vert.adapter.BookingCancelledAdapter;
import com.vert.vert.model.CancelledBooking_model;
import com.vert.vert.pilot.adapter.PilotBookingAdapter;
import com.vert.vert.pilot.model.MyInterface;
import com.vert.vert.pilot.model.Pilot_Booking_model;

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
 * Created by rajan on 10/4/18.
 */

public class BookingsFragment extends Fragment implements MyInterface {


    private RecyclerView recyclerViewbooking;

    //adapter object
    private PilotBookingAdapter adapterGifts;

    // Creating Volley RequestQueue.
    RequestQueue requestQueue;

    // Creating Progress dialog.
    ProgressDialog progressDialog;

    private StringRequest stringRequest;

    //list to hold all the uploaded images
    private List<Pilot_Booking_model> pilotbookingModelList;

    public String todaydate,noevents;


    public BookingsFragment() {
    }



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View pilotbooking =inflater.inflate(R.layout.fragment_pilot_booking,container,false);



        recyclerViewbooking = (RecyclerView) pilotbooking.findViewById(R.id.recyclerView_booking);
        recyclerViewbooking.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewbooking.setLayoutManager(llm);

        progressDialog = new ProgressDialog(getActivity());

        pilotbookingModelList = new ArrayList<Pilot_Booking_model>();

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(getActivity());

        //progress daialog
        progressDialog = new ProgressDialog(getActivity());


//        Toast.makeText(getActivity(), Userdetails.userid, Toast.LENGTH_SHORT).show();


        getData();


        return pilotbooking;
    }

    public void getData() {

//        Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        clear();


        // Creating string request with post method.
        stringRequest = new StringRequest(Request.Method.POST, Apilist.PILOTBOOKING,
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
                                String status = json.getString("status");


                                if(status.equals("failure")){

                                    Toast.makeText(getActivity(), "No Bookings", Toast.LENGTH_SHORT).show();

                                }else {

                                    String bookid = json.getString("book_id");
                                    String flightdate = json.getString("flight_date");
                                    String flighttime = json.getString("flight_time");
                                    String from = json.getString("source");
                                    String to = json.getString("destination");
                                    String userid = json.getString("user_id");


//                                Toast.makeText(getActivity(),bookid+flightname+flightnumber,Toast.LENGTH_LONG).show();

                                    pilotbookingModelList.add(new Pilot_Booking_model(bookid,from, to, flightdate,userid,flighttime));


                                    //creating adapter
                                    adapterGifts = new PilotBookingAdapter(getActivity(), pilotbookingModelList,BookingsFragment.this);

                                    //adding adapter to recyclerview
                                    recyclerViewbooking.setAdapter(adapterGifts);
                                    adapterGifts.notifyDataSetChanged();
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

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                100,
                                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                        // Showing error message if something goes wrong.
                        Toast.makeText(getActivity(), "Please Check your Internet", Toast.LENGTH_LONG).show();

//                     Log.d("error",volleyError.toString());

                    }
                }
        )

        {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.

                params.put("pilot_email_id", Userdetails.usermailid);

//                Toast.makeText(getActivity(), Userdetails.userid, Toast.LENGTH_SHORT).show();


                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

//             Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);


    }


    public void clear() {
        final int size = pilotbookingModelList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                pilotbookingModelList.remove(0);
            }

            adapterGifts.notifyItemRangeRemoved(0,size);
        }
    }

}
