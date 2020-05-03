package com.vert.vert.pilot.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rajan on 10/27/18.
 */

public class AboutusPilotFragment extends Fragment {

    TextView content;

    private ProgressDialog progress;

    StringRequest stringRequest;

    public AboutusPilotFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View help =inflater.inflate(R.layout.fragment_pilot_aboutus,container,false);



        return help;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles


        content = (TextView)view.findViewById(R.id.aboutus);


        progress=new ProgressDialog(getActivity());
        progress.setMessage("Please Wait..");
        progress.show();

        getAboutus();


    }


    public void getAboutus(){




        // Creating string request with post method.
        stringRequest = new StringRequest(Request.Method.GET, Apilist.ABOUTUS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {


                        // Hiding the progress dialog after all task complete.
                        progress.dismiss();

                        // Showing response message coming from server.
//                        Toast.makeText(getActivity(), ServerResponse, Toast.LENGTH_LONG).show();

//                        Log.d("Response",ServerResponse);

                        try {
                            JSONObject jsondata = new JSONObject(ServerResponse);


                            JSONArray jsonArray = jsondata.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject json = jsonArray.getJSONObject(i);

                                String contentv = json.getString("about_vert");

                                content.setText(contentv);



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
                        progress.dismiss();
                        // Showing error message if something goes wrong.
                        Toast.makeText(getActivity(), "PLease Try Again", Toast.LENGTH_LONG).show();

                        Log.d("error",volleyError.toString());
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

        // Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }

}

