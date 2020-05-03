package com.vert.vert.pilot.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.vert.vert.activity.CancelledScreenDetail;
import com.vert.vert.activity.HomeScreen;
import com.vert.vert.adapter.BookingCancelledAdapter;
import com.vert.vert.model.CancelledBooking_model;
import com.vert.vert.pilot.activity.BookingScreen;
import com.vert.vert.pilot.fragment.BookingsFragment;
import com.vert.vert.pilot.model.MyInterface;
import com.vert.vert.pilot.model.Pilot_Booking_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by rajan on 10/24/18.
 */

public class PilotBookingAdapter extends RecyclerView.Adapter<PilotBookingAdapter.PilotbookingHolder>{

    Pilot_Booking_model pilotbookingModel;
    private Context context;
    private List<Pilot_Booking_model> pilotbookingModelList;
    private StringRequest stringRequest;
    // Creating Volley RequestQueue.
    public RequestQueue requestQueue;
    BookingsFragment fragment;


    public PilotBookingAdapter(Context context, List<Pilot_Booking_model> pilotbookingModelList,BookingsFragment fragment) {
        this.pilotbookingModelList = pilotbookingModelList;
        this.context = context;
        this.fragment = fragment;
    }

    @Override
    public PilotBookingAdapter.PilotbookingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bookings_pilot_row_list, parent, false);

        PilotBookingAdapter.PilotbookingHolder viewHolder = new PilotBookingAdapter.PilotbookingHolder(v);

        return viewHolder;

    }
    @Override
    public void onBindViewHolder(PilotBookingAdapter.PilotbookingHolder holder, int position) {
        pilotbookingModel = pilotbookingModelList.get(position);

        Resources resource= context.getResources();
        holder.flighttime.setText("Flight Time :"+pilotbookingModel.getTime());
        holder.date.setText("Flight date :"+pilotbookingModel.getFlightdate());
        holder.from.setText("From :"+pilotbookingModel.getFrom());
        holder.to.setText("To :"+pilotbookingModel.getTo());

    }

    @Override
    public int getItemCount() {
        return pilotbookingModelList.size();
    }

    public class PilotbookingHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView flighttime,from,to,date;
        private Button accept;




        public PilotbookingHolder(View itemView) {
            super(itemView);



            flighttime = (TextView) itemView.findViewById(R.id.flighttime);
            from = (TextView) itemView.findViewById(R.id.from);
            to = (TextView) itemView.findViewById(R.id.to);
            date = (TextView) itemView.findViewById(R.id.date);
            accept = (Button) itemView.findViewById(R.id.accept);



            accept.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {


            if ((v == accept)) {



                if ((v == accept)){

                    // Creating Volley newRequestQueue .
                    requestQueue = Volley.newRequestQueue(context);


                    // Creating string request with post method.
                    stringRequest = new StringRequest(Request.Method.POST, Apilist.ACCEPT,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String ServerResponse) {

                                    // Showing response message coming from server.
//                            Toast.makeText(LoginScreen.this, ServerResponse, Toast.LENGTH_LONG).show();
//
//                            Log.d("Response",ServerResponse);

                                    try {
                                        JSONObject jsondata = new JSONObject(ServerResponse);


                                        JSONArray jsonArray = jsondata.getJSONArray("data");



                                        //Get data
                                        fragment.getData();

//                                        Intent intent = new Intent(context,BookingScreen.class);
//                                        context.startActivity(intent);
                                        Toast.makeText(context, " Booking Accepted", Toast.LENGTH_LONG).show();


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
                                    Toast.makeText(context, "Please Check your Internet", Toast.LENGTH_LONG).show();

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
                            Pilot_Booking_model pilotModel_s = pilotbookingModelList.get(getAdapterPosition());

                            params.put("book_id", pilotModel_s.getBookid());
                            params.put("user_id", pilotModel_s.getUserid());
                            params.put("accept_time", Userdetails.todaytime);
                            params.put("accept_date", Userdetails.todaydate);




                            return params;
                        }

                    };

                    // Creating RequestQueue.
                    RequestQueue requestQueue = Volley.newRequestQueue(context);

//             Adding the StringRequest object into requestQueue.
                    requestQueue.add(stringRequest);


                }   }

        }
    }

}
