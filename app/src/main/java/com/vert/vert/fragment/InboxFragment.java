package com.vert.vert.fragment;

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
import com.vert.vert.adapter.BookingCancelledAdapter;
import com.vert.vert.adapter.InboxAdapter;
import com.vert.vert.model.CancelledBooking_model;
import com.vert.vert.model.Inbox_model;

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

public class InboxFragment extends Fragment {


    private RecyclerView recyclerViewinbox;

    //adapter object
    private InboxAdapter adapterGifts;

    // Creating Volley RequestQueue.
    RequestQueue requestQueue;

    // Creating Progress dialog.
    ProgressDialog progressDialog;

    private StringRequest stringRequest;

    //list to hold all the uploaded images
    private List<Inbox_model> inboxModelList;

    public String todaydate,noevents;


    public InboxFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View inbox =inflater.inflate(R.layout.fragment_inbox,container,false);


        recyclerViewinbox = (RecyclerView) inbox.findViewById(R.id.recyclerView_inbox);
        recyclerViewinbox.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewinbox.setLayoutManager(llm);

        progressDialog = new ProgressDialog(getActivity());

        inboxModelList = new ArrayList<Inbox_model>();

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(getActivity());

        //progress daialog
        progressDialog = new ProgressDialog(getActivity());


        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        todaydate = df.format(c);
        Userdetails.todaydate=todaydate;

//        Toast.makeText(getActivity(), Userdetails.userid, Toast.LENGTH_SHORT).show();


        getData();


        return inbox;
    }

    public void getData()

    {
//        Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();


        // Creating string request with post method.
        stringRequest = new StringRequest(Request.Method.POST, Apilist.INBOXLIST,
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
                                String from = json.getString("source");
                                String to = json.getString("destination");
                                String flightdate = json.getString("flight_date");
                                String flighttime = json.getString("flight_time");
                                String message = json.getString("message");
                                String contactusername = json.getString("username");
                                String bookdate = json.getString("book_date");

//                                Toast.makeText(getActivity(),bookid+flightname+flightnumber,Toast.LENGTH_LONG).show();

                                inboxModelList.add(new Inbox_model(bookid,from,to,flightdate,flighttime,message,contactusername,bookdate));


                                //creating adapter
                                adapterGifts = new InboxAdapter(getActivity(), inboxModelList);

                                //adding adapter to recyclerview
                                recyclerViewinbox.setAdapter(adapterGifts);
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

//                Toast.makeText(getActivity(), Userdetails.userid, Toast.LENGTH_SHORT).show();


                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

//             Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);


    }

}
