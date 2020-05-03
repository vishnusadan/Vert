package com.vert.vert.pilot.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
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
import com.vert.vert.activity.HomeScreen;
import com.vert.vert.activity.OngoingScreenDetail;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PilotOngoingScreenDetail extends AppCompatActivity implements View.OnClickListener {

    String bookid, flightnumber, from, to, flightdate, flighttime;

    private TextView tvbookid, tvflightnumber, tvfrom, tvto, tvflightdate, tvflighttime, tvbookstatus;

    private Button close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilot_ongoing_screen_detail);


        Intent intent = getIntent();
        bookid = intent.getStringExtra("bookid");
        flightnumber = intent.getStringExtra("flightnumber");
        from = intent.getStringExtra("from");
        to = intent.getStringExtra("to");
        flightdate = intent.getStringExtra("flightdate");
        flighttime = intent.getStringExtra("flighttime");

//        Toast.makeText(this, bookid, Toast.LENGTH_SHORT).show();


        tvbookid = (TextView) findViewById(R.id.bookid);
        tvflightnumber = (TextView) findViewById(R.id.flightnumber);
        tvflightdate = (TextView) findViewById(R.id.flightdate);
        tvflighttime = (TextView) findViewById(R.id.flighttime);
        tvfrom = (TextView) findViewById(R.id.from);
        tvto = (TextView) findViewById(R.id.to);
        tvbookstatus = (TextView) findViewById(R.id.bookstatus);

        close = (Button)findViewById(R.id.close);
        close.setOnClickListener(this);

        tvbookid.setText("Book Id = "+bookid);
        tvfrom.setText("From = "+from);
        tvto.setText("To = "+to);
        tvflightdate.setText("Flight Date = "+flightdate);
        tvflighttime.setText("Flight Time = "+flighttime);
        tvbookstatus.setText("Flight Status = Ready");

        if (flightnumber.equals("")){

            tvflightnumber.setText("Flight Number = Still Flight Not Assigned");

        }else {
            tvflightnumber.setText("Flight Number = "+flightnumber);
        }

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){



            case R.id.close:

                finish();

                break;
        }
    }

}
