package com.vert.vert.pilot.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
import com.vert.vert.R;
import com.vert.vert.Static.Apilist;
import com.vert.vert.Static.Userdetails;
import com.vert.vert.pilot.model.Pilot_Booking_model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rajan on 10/27/18.
 */

public class ContactVertFragment extends Fragment implements View.OnClickListener {

    private Button send;
    private EditText message;
    private StringRequest stringRequest;
    // Creating Volley RequestQueue.
    public RequestQueue requestQueue;
    // Creating Progress dialog.
    ProgressDialog progressDialog;

    public ContactVertFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View contactvert =inflater.inflate(R.layout.fragment_contact_vert,container,false);

        send = (Button)contactvert.findViewById(R.id.send);
        message = (EditText)contactvert.findViewById(R.id.ed_message);

        //submit
        send.setOnClickListener(this);
        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(getActivity());

        //progress daialog
        progressDialog = new ProgressDialog(getActivity());


        return contactvert;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.send:
                View view1 = getActivity().getCurrentFocus();
                if (view1 != null) {
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
                }

                if(message.getText().toString().equals("")){

                    Toast.makeText(getActivity(), "Please Enter the Message", Toast.LENGTH_SHORT).show();

                }else {

                    Sendcontact();

                }
                break;

        }
    }

    public void Sendcontact() {

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(getActivity());

        // Creating string request with post method.
        stringRequest = new StringRequest(Request.Method.POST, Apilist.PILOTCONTACT,
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


//                            JSONArray jsonArray = jsondata.getJSONArray("data");

                            Toast.makeText(getActivity(), "Message Send Successfully", Toast.LENGTH_LONG).show();
                            message.setText("");


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


                params.put("pilot_id", Userdetails.userid);
                params.put("message", message.getText().toString());




                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

//             Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);



    }
}
