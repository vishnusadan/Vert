package com.vert.vert.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.vert.vert.networkConnectivity.ConnectivityReceiver;
import com.vert.vert.R;
import com.vert.vert.Static.Apilist;
import com.vert.vert.Static.Userdetails;
import com.vert.vert.pilot.activity.BookingScreen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener,
        GoogleApiClient.OnConnectionFailedListener {


    private static final String TAG = LoginScreen.class.getSimpleName();

    private static final int RC_SIGN_IN = 007;

    public  GoogleApiClient mGoogleApiClient;

    String personNamesocial,personPhotoUrlsocial,emailsocial;

    // Storing server url into String variable.
    String HttpUrl = Apilist.LOGIN;

    // Creating Volley RequestQueue.
    RequestQueue requestQueue;

    // Creating Progress dialog.
    ProgressDialog progressDialog;

    private StringRequest stringRequest;


    EditText mobilenumber;
    TextView gmail;
    Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginscreen);

        checkConnection();


        mobilenumber = (EditText)findViewById(R.id.ed_mobileno);
        gmail = (TextView) findViewById(R.id.gmail);
        next = (Button) findViewById(R.id.next);

        gmail.setOnClickListener(this);
        next.setOnClickListener(this);


        //oreo not supporting number format
        this.mobilenumber.setInputType(InputType.TYPE_CLASS_PHONE);
        this.mobilenumber.setTypeface(Typeface.DEFAULT);


        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(LoginScreen.this);

        //progress daialog
        progressDialog = new ProgressDialog(LoginScreen.this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            ActivityCompat.requestPermissions(LoginScreen.this,
                    new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.READ_SMS,android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION},1 );

        }


        //gmail login
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        //Gps on
        getGPSInfo();


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.next:

                //verify the mobile is correct

                if(mobilenumber.getText().toString().equals("") || mobilenumber.getText().toString().length()<8){

                    mobilenumber.setError("Please check your Number");

                }else {

                    // register the user
                    CheckalreadyUser();
                }


                break;

            case R.id.gmail:

                 signIn();

                break;
        }

    }

    // on back button click
    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this,R.style.DialogeTheme)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        LoginScreen.this.finishAffinity();
                    }
                })
                .setNegativeButton("No", null)
                .show();


    }

//permission if above marshmellow
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(LoginScreen.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    private void CheckalreadyUser() {

//             Showing progress dialog at user registration time.
        progressDialog.setMessage("Please Wait");
        progressDialog.show();


        // Creating string request with post method.
        stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String ServerResponse) {



                        // Showing response message coming from server.
//                            Toast.makeText(MainActivity.this, ServerResponse, Toast.LENGTH_LONG).show();

//                        Log.d("Response",ServerResponse);

                        try {
                            JSONObject jsondata = new JSONObject(ServerResponse);


                            JSONArray jsonArray = jsondata.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject json = jsonArray.getJSONObject(i);

                                String id = json.getString("user_id");
                                String verify = json.getString("verify");
                                String emailid = json.getString("email_id");
                                String profilepic = json.getString("profile_picture");
                                String username = json.getString("username");


//                                Log.d("id", id);

//                                    Log.d("email", email);

                                Userdetails.usermailid=emailid;
                                Userdetails.userprofilepic=profilepic;
                                Userdetails.username=username;
                                Userdetails.userid=id;
                                Userdetails.logintype="phonenumber";


                                // Hiding the progress dialog after all task complete.
                                progressDialog.dismiss();


                                if(verify.equals("no")){

                                    startActivity(new Intent(LoginScreen.this, VerificationScreen.class).putExtra("number",mobilenumber.getText().toString()));

                                }else {

                                    Userdetails.userPhonenumber=mobilenumber.getText().toString();

                                    startActivity(new Intent(LoginScreen.this, HomeScreen.class));
                                    finish();

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
                        Toast.makeText(LoginScreen.this, "Please Check your Internet", Toast.LENGTH_LONG).show();

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

                params.put("phone_number", mobilenumber.getText().toString());
                params.put("login_type","phonenumber");



                return params;
            }

        };

        // Creating RequestQueue.
        RequestQueue requestQueue = Volley.newRequestQueue(LoginScreen.this);

//             Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }


    //gmail  login coding
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    public void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Log.d("social","logout");
                    }
                });
    }

    private void revokeAccess() {
        Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

                    }
                });
    }



    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

//            Log.e(TAG, "display name: " + acct.getDisplayName());

            if(!acct.getDisplayName().equals("")){
                personNamesocial = acct.getDisplayName();
            }else {
                personNamesocial = "";
            }

            if(acct.getPhotoUrl()!=null){
                personPhotoUrlsocial = acct.getPhotoUrl().toString();
            }else {
                personPhotoUrlsocial = "";
            }

            if(!acct.getEmail().equals("")){
                emailsocial = acct.getEmail();
            }else {
                emailsocial = "";
            }


//            Log.e(TAG, "Name: " + personNamesocial + ", email: " + emailsocial + ", Image: " + personPhotoUrlsocial);


//          Showing progress dialog at user registration time.
            progressDialog.setMessage("Please Wait");
            progressDialog.show();


            // Creating string request with post method.
            stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
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


                                JSONArray jsonArray = jsondata.getJSONArray("data");

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject json = jsonArray.getJSONObject(i);

                                    String id = json.getString("user_id");
                                    String emailid = json.getString("email_id");
                                    String profilepic = json.getString("profile_picture");
                                    String username = json.getString("username");
                                    String phonenumber = json.getString("phone_number");
                                    String usertype = json.getString("user_type");

                                    Log.d("id", id);

//                                    Log.d("email", email);


                                    Userdetails.userid=id;
                                    Userdetails.usermailid=emailid;
                                    Userdetails.userprofilepic=profilepic;
                                    Userdetails.username=username;
                                    Userdetails.userPhonenumber=phonenumber;
                                    Userdetails.logintype="gmail";

                                    if(usertype.equals("user")){
//user
                                        startActivity(new Intent(LoginScreen.this,HomeScreen.class));
                                        finish();

                                    }else {
//pilot
                                        startActivity(new Intent(LoginScreen.this, BookingScreen.class));
                                        finish();

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
                            Toast.makeText(LoginScreen.this, "Please Check your Internet", Toast.LENGTH_LONG).show();

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

                    params.put("email_id", emailsocial);
                    params.put("username", personNamesocial);
                    params.put("profile_picture", personPhotoUrlsocial);
                    params.put("login_type","gmail");


                    return params;
                }

            };

            // Creating RequestQueue.
            RequestQueue requestQueue = Volley.newRequestQueue(LoginScreen.this);

//             Adding the StringRequest object into requestQueue.
            requestQueue.add(stringRequest);


        } else {
            // Signed out, show unauthenticated UI.

        }



}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.

            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {

                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }



    // Method to manually check connection status
    private void checkConnection() {
        boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {


        } else {
            Toast.makeText(getApplicationContext(),"Please Connect the Internet Connection",Toast.LENGTH_LONG).show();


        }
    }


    private void getGPSInfo() {
        Criteria criteria = new Criteria();
        String provider;
        Location location;
        LocationManager locationmanager = (LocationManager) LoginScreen.this.getSystemService(Context.LOCATION_SERVICE);

        if (locationmanager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            provider = locationmanager.getBestProvider(criteria, false);
            if (ActivityCompat.checkSelfPermission(LoginScreen.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LoginScreen.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginScreen.this);
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

}
