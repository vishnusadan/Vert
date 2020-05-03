package com.vert.vert.activity;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.GoogleApiClient;
import com.vert.vert.R;
import com.vert.vert.Static.Apilist;
import com.vert.vert.Static.Userdetails;
import com.vert.vert.pilot.activity.BookingScreen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashScreen extends AppCompatActivity {

    public GoogleApiClient mGoogleApiClient;
    String personNamesocial,personPhotoUrlsocial,emailsocial;
    // Storing server url into String variable.
    String HttpUrl = Apilist.LOGIN;

    // Creating Volley RequestQueue.
    RequestQueue requestQueue;

    private StringRequest stringRequest;

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
    /** Called when the activity is first created. */
    Thread splashTread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        StartAnimations();

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(SplashScreen.this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();
        mGoogleApiClient.connect();

    }

    private void StartAnimations() {


        Animation anim = AnimationUtils.loadAnimation(this, R.anim.move);
        anim.reset();
        RelativeLayout l = (RelativeLayout) findViewById(R.id.imagelayout);
        l.clearAnimation();
        l.startAnimation(anim);


        splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 1000) {
                        sleep(30);
                        waited += 30;
                    }

                    gmailcheck();

                } catch (InterruptedException e) {
                    // do nothing
                } finally {
//                    SplashScreen.this.finish();
                }

            }
        };
        splashTread.start();
    }


    public void gmailcheck()

    {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(SplashScreen.this);
        if (acct != null) {


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


//            Log.e("splash", "Name: " + personNamesocial + ", email: " + emailsocial
//                    + ", Image: " + personPhotoUrlsocial);


            // Creating string request with post method.
            stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String ServerResponse) {


                            // Showing response message coming from server.
//                            Toast.makeText(SplashScreen.this, ServerResponse, Toast.LENGTH_LONG).show();

//                            Log.d("Response", ServerResponse);

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

                                    Log.d("usertype", usertype);

//                                    Log.d("email", email);

                                    Userdetails.userid=id;
                                    Userdetails.usermailid=emailid;
                                    Userdetails.userprofilepic=profilepic;
                                    Userdetails.username=username;
                                    Userdetails.userPhonenumber=phonenumber;
                                    Userdetails.logintype="gmail";

                                    if(usertype.equals("user")){
//user
                                        startActivity(new Intent(SplashScreen.this, HomeScreen.class));
                                        finish();

                                    }else {
//pilot
                                        startActivity(new Intent(SplashScreen.this, BookingScreen.class));
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

                            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                                    100,
                                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

                            // Showing error message if something goes wrong.
                            Toast.makeText(SplashScreen.this, "Please Check your Internet", Toast.LENGTH_LONG).show();

                            Log.d("error", volleyError.toString());
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
                    params.put("login_type", "gmail");


                    return params;
                }

            };

            // Creating RequestQueue.
            RequestQueue requestQueue = Volley.newRequestQueue(SplashScreen.this);

//             Adding the StringRequest object into requestQueue.
            requestQueue.add(stringRequest);


        } else {
            Intent intent = new Intent(SplashScreen.this,
                    LoginScreen.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            SplashScreen.this.finish();

        }

    }


}
