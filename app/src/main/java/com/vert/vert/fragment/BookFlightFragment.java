package com.vert.vert.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.vert.vert.R;
import com.vert.vert.Static.Userdetails;
import com.vert.vert.activity.HomeScreen;
import com.vert.vert.activity.LoginScreen;
import com.vert.vert.activity.OnewayScreen;
import com.vert.vert.Static.Apilist;
import com.vert.vert.activity.VerificationScreen;
import com.vert.vert.model.Globals;
import com.vert.vert.pilot.activity.BookingScreen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by rajan on 10/4/18.
 */

public class BookFlightFragment extends Fragment implements
        LocationListener,GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,com.google.android.gms.maps.OnMapReadyCallback,View.OnClickListener {

    private GoogleMap mMap;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    public static final int REQUEST_LOCATION_CODE = 99;

    private ListView lv_search,lv_search1;

    private EditText mSearchEdt,mSearchEdt1;

    private Button onewaybt,roundtripbt;

    ArrayList<String> mStringList;

    // Listview Adapter
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter1;

    // Creating Volley RequestQueue.
    RequestQueue requestQueue;

    // Creating Progress dialog.
    ProgressDialog progressDialog;

    private StringRequest stringRequest;
    private String value;


    LinearLayout firstlinear;
    ProgressDialog progress;


    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2900;


    public static Double currentlatitude,currentlongitude;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View bookflight = inflater.inflate(R.layout.fragment_bookflight, container, false);

        lv_search = (ListView) bookflight.findViewById(R.id.listfrom);
        lv_search1 = (ListView) bookflight.findViewById(R.id.listto);


        mSearchEdt = (EditText) bookflight.findViewById(R.id.ed_from);
        mSearchEdt1 = (EditText) bookflight.findViewById(R.id.ed_to);


        onewaybt = (Button) bookflight.findViewById(R.id.oneway);
        roundtripbt = (Button) bookflight.findViewById(R.id.roundtrip);

        mSearchEdt.setOnClickListener(this);
        mSearchEdt1.setOnClickListener(this);
        onewaybt.setOnClickListener(this);
        roundtripbt.setOnClickListener(this);


        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(getActivity());

        //progress daialog
        progressDialog = new ProgressDialog(getActivity());

        getGPSInfo();

        // Airportlist();
        JSON_GET_ITEM();

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(getActivity());


        mStringList = new ArrayList<String>();


        mSearchEdt.addTextChangedListener(new TextWatcher() {


            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {

                if (BookFlightFragment.this.adapter.getFilter().equals("")) {

                    Toast.makeText(getActivity(),"No data",Toast.LENGTH_LONG).show();

                } else{

                    // When user changed the Text
                    BookFlightFragment.this.adapter.getFilter().filter(cs);
                lv_search.setVisibility(View.VISIBLE);

            }
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

                Userdetails.getlatlng=mSearchEdt.getText().toString();

                getlatlng();


            }
        });



        mSearchEdt1.addTextChangedListener(new TextWatcher() {


            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {

                // When user changed the Text
                BookFlightFragment.this.adapter1.getFilter().filter(cs);
                lv_search1.setVisibility(View.VISIBLE);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub

                Userdetails.getlatlng=mSearchEdt1.getText().toString();
                getlatlng1();

            }
        });

// To find focus of each edit text
        mSearchEdt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                   lv_search.setVisibility(View.INVISIBLE);
                   lv_search1.setVisibility(View.INVISIBLE);
                    value="1";
                }
            }
        });

        mSearchEdt1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    lv_search1.setVisibility(View.VISIBLE);
                    lv_search.setVisibility(View.INVISIBLE);
                    value="2";
                }
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }

        //show map
        setUpMap();


        mSearchEdt.setOnTouchListener(new View.OnTouchListener()
        {
            public boolean onTouch(View arg0, MotionEvent arg1)
            {
                value="1";
                return false;
            }
        });

        mSearchEdt1.setOnTouchListener(new View.OnTouchListener()
        {
            public boolean onTouch(View arg0, MotionEvent arg1)
            {
                value="2";
                return false;
            }
        });

        lv_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String item = adapterView.getItemAtPosition(i).toString();

                mSearchEdt.setText(item);

                lv_search.setVisibility(View.INVISIBLE);
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                Toast.makeText(getActivity(), item, Toast.LENGTH_LONG).show();
            }
        });

        lv_search1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String item = adapterView.getItemAtPosition(i).toString();

                mSearchEdt1.setText(item);

                lv_search1.setVisibility(View.INVISIBLE);

                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//                Toast.makeText(getActivity(), item, Toast.LENGTH_LONG).show();
            }
        });


        lv_search.setVisibility(View.INVISIBLE);
        lv_search1.setVisibility(View.INVISIBLE);


        progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading");
        progress.setMessage("Fetching your Location...");
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();


        // method used to wait time for getting current location
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app

// To dismiss the dialog
                progress.dismiss();


            }
        }, SPLASH_TIME_OUT);




        return bookflight;
    }


    @Override
    public void onStart() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
        super.onStart();
    }


    private void setUpMap() {
        if (mMap == null) {
            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            if (mMap != null) {
//                setUpMap();
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    android.Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker arg0) {
                // TODO Auto-generated method stub

                String title = arg0.getTitle();

                if(value=="1")
                {
                    mSearchEdt.setText(title);
                }else if(value=="2")
                {
                    mSearchEdt1.setText(title);
                }else {

                    mSearchEdt.setText(title);
                }

            }
        });


        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                lv_search.setVisibility(View.INVISIBLE);
                lv_search1.setVisibility(View.INVISIBLE);


            }
        });

    }

    @Override
    public void onResume() {

        buildGoogleApiClient();

        super.onResume();
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(1000);
            mLocationRequest.setFastestInterval(1000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);


        if (ContextCompat.checkSelfPermission(getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            //stop location updates
            if (mGoogleApiClient.isConnected()) {

                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

            }else {

                buildGoogleApiClient();

            }
       }else{

            Toast.makeText(getActivity(),"Please wait to get Your Location",Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        currentlatitude = location.getLatitude();
        currentlongitude = location.getLatitude();

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
//        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(8));

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 8));

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                .zoom(8)                    // Sets the zoom
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        //stop location updates
        if (mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }else {
            buildGoogleApiClient();
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public boolean checkLocationPermission() {

        if (ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_CODE);
            }
            return false;

        } else

            setUpMap();

        return true;
    }


    private void JSON_GET_ITEM() {

        StringRequest jsonArrayRequest = new StringRequest(Apilist.AIRPORTLIST,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSON_GET_DATA(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(getActivity(), "No Airport Found", Toast.LENGTH_SHORT).show();

                    }
                });

        requestQueue = Volley.newRequestQueue(getActivity());

        requestQueue.add(jsonArrayRequest);

    }

Double latitude;
Double longitude;
String airportname;

    // ALl box names details
    public void JSON_GET_DATA(String response){


        try {
            JSONObject jsondata = new JSONObject(response);



            JSONArray jsonArray = jsondata.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject json = jsonArray.getJSONObject(i);

                Log.d("json", String.valueOf(json));
                Log.d("json", String.valueOf(json.getString("airport_name")));

                latitude = Double.parseDouble(json.getString("latitude"));
                longitude = Double.parseDouble(json.getString("longitude"));
                airportname = json.getString("airport_name");


                //  shows data in map

                mMap.addMarker(new MarkerOptions()
                        .title(json.getString("airport_name"))
                        .position(new LatLng(
                                latitude,
                                longitude
                        ))
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_flight_icon))
                );

                mStringList.add(json.getString("airport_name"));

            }

            // Adding items to listview
            adapter = new ArrayAdapter<String>(getActivity(), R.layout.place_list_item, R.id.txt_listitem,  mStringList);
            adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.place_list_item, R.id.txt_listitem,  mStringList);
            lv_search.setAdapter(adapter);
            lv_search1.setAdapter(adapter1);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.ed_from:

                lv_search.setVisibility(View.VISIBLE);
                lv_search1.setVisibility(View.INVISIBLE);

                break;

            case R.id.ed_to:

                lv_search1.setVisibility(View.VISIBLE);
                lv_search.setVisibility(View.INVISIBLE);

                break;

            case R.id.oneway:

                FindTimetravel();


                break;

            case R.id.roundtrip:

                if(mSearchEdt.getText().toString().equals("")||mSearchEdt1.getText().toString().equals("")){

                    Toast.makeText(getActivity(), "Please Enter The Place", Toast.LENGTH_LONG).show();

                }else if(mSearchEdt.getText().toString().trim().equals(mSearchEdt1.getText().toString().trim())){

                    Toast.makeText(getActivity(), "Same Place Not Allowed", Toast.LENGTH_LONG).show();

                }else {

                    startActivity(new Intent(getActivity(), OnewayScreen.class).putExtra("trip", "roundtrip").putExtra("from", mSearchEdt.getText().toString()).putExtra("to", mSearchEdt1.getText().toString()));

                }

                break;


        }
    }

    private void FindTimetravel() {

        progressDialog.setMessage("Please Wait");
        progressDialog.show();


        // Creating string request with post method.
        stringRequest = new StringRequest(Request.Method.POST, Apilist.STATUSCALLLIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {

                        // Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        // Showing response message coming from server.
//                            Toast.makeText(MainActivity.this, ServerResponse, Toast.LENGTH_LONG).show();

                        Log.d("Response",ServerResponse);

                        try {
                            JSONObject jsondata = new JSONObject(ServerResponse);


                            JSONArray jsonArray = jsondata.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject json = jsonArray.getJSONObject(i);

                                String time = json.getString("time");

                                if(time.equals("")){

                                    Userdetails.traveltime = "0";

                                }else {

                                    Userdetails.traveltime = time;

                                }

//                                Log.d("travel time ",Userdetails.traveltime);

                                if(mSearchEdt.getText().toString().equals("")||mSearchEdt1.getText().toString().equals("")){

                                    Toast.makeText(getActivity(), "Please Enter The Place", Toast.LENGTH_LONG).show();

                                }else if(mSearchEdt.getText().toString().trim().equals(mSearchEdt1.getText().toString().trim())){

                                    Toast.makeText(getActivity(), "Same Place Not Allowed", Toast.LENGTH_LONG).show();

                                } else  {

                                    startActivity(new Intent(getActivity(), OnewayScreen.class).putExtra("trip", "oneway").putExtra("from", mSearchEdt.getText().toString()).putExtra("to", mSearchEdt1.getText().toString()));
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

                        Log.d("error",volleyError.toString());
                    }
                }
        )

        {
            @Override
            protected Map<String, String> getParams() {

                // Creating Map String Params.
                Map<String, String> params = new HashMap<String, String>();

                // Adding All values to Params.

                params.put("from_airport", mSearchEdt.getText().toString());
                params.put("to_airport",mSearchEdt1.getText().toString());


                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

//             Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);


    }

    private void getGPSInfo() {
        Criteria criteria = new Criteria();
        String provider;
        Location location;
        LocationManager locationmanager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (locationmanager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            provider = locationmanager.getBestProvider(criteria, false);
            if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            location = locationmanager.getLastKnownLocation(provider);

        } else {
            showGPSDisabledAlertToUser();
        }
    }


    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder
                .setMessage(
                        "GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);

                            }
                        });

        alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent callGPSSettingIntent = new Intent(
                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(callGPSSettingIntent);
            }
        });

        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


    public void getlatlng(){

        // Creating string request with post method.
        stringRequest = new StringRequest(Request.Method.POST, Apilist.GETLATLNG,
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

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject json = jsonArray.getJSONObject(i);

                                String lat = json.getString("latitude");
                                String lng = json.getString("longitude");

                                Userdetails.lat1=lat;
                                Userdetails.lng1=lng;

                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)))      // Sets the center of the map to location user
                                        .zoom(8)                   // Sets the zoom
                                        .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                                        .build();                   // Creates a CameraPosition from the builder
                                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


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

                params.put("airport_name", Userdetails.getlatlng);


                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

//             Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }



    public void getlatlng1(){


        // Creating string request with post method.
        stringRequest = new StringRequest(Request.Method.POST, Apilist.GETLATLNG,
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

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject json = jsonArray.getJSONObject(i);

                                String lat = json.getString("latitude");
                                String lng = json.getString("longitude");

                                Userdetails.lat2=lat;
                                Userdetails.lng2=lng;

                                CameraPosition cameraPosition = new CameraPosition.Builder()
                                        .target(new LatLng(Double.parseDouble(lat), Double.parseDouble(lng)))      // Sets the center of the map to location user
                                        .zoom(8)                   // Sets the zoom
                                        .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                                        .build();                   // Creates a CameraPosition from the builder
                                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


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

                params.put("airport_name", Userdetails.getlatlng);




                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

//             Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);
    }
}
