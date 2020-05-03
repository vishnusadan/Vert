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
import com.vert.vert.adapter.HelpAdapter;
import com.vert.vert.adapter.InboxAdapter;
import com.vert.vert.model.Help_model;
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

public class HelpFragment extends Fragment {


    private RecyclerView recyclerViewreply;

    //adapter object
    private HelpAdapter adapterGifts;

    // Creating Volley RequestQueue.
    RequestQueue requestQueue;

    // Creating Progress dialog.
    ProgressDialog progressDialog;

    private StringRequest stringRequest;

    //list to hold all the uploaded images
    private List<Help_model> helpModelList;


    public HelpFragment() {
    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View help =inflater.inflate(R.layout.fragment_help,container,false);


        recyclerViewreply = (RecyclerView) help.findViewById(R.id.recyclerView_inbox);
        recyclerViewreply.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewreply.setLayoutManager(llm);

        progressDialog = new ProgressDialog(getActivity());

        helpModelList = new ArrayList<Help_model>();

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(getActivity());

        //progress daialog
        progressDialog = new ProgressDialog(getActivity());


//        Toast.makeText(getActivity(), Userdetails.userid, Toast.LENGTH_SHORT).show();


        getData();


        return help;
    }

    public void getData()

    {
//        Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();


        // Creating string request with post method.
        stringRequest = new StringRequest(Request.Method.GET, Apilist.HELPLIST,
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

                                String help = json.getString("vert_help");
                                String answer = json.getString("vert_answer");

//                                Toast.makeText(getActivity(),bookid+flightname+flightnumber,Toast.LENGTH_LONG).show();

                                helpModelList.add(new Help_model(help,answer));


                                //creating adapter
                                adapterGifts = new HelpAdapter(getActivity(), helpModelList);

                                //adding adapter to recyclerview
                                recyclerViewreply.setAdapter(adapterGifts);
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



                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

//             Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);


    }
}