package com.vert.vert.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.vert.vert.R;

public class InboxScreenDetail extends AppCompatActivity implements View.OnClickListener  {

    String bookid,from,to,flightdate,flighttime,message,bookdate,contactusername;

    private TextView tvbookid,tvfrom,tvto,tvflightdate,tvflighttime,tvmessage,tvbookdate,tvcontactusername;

    private Button close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_screen_detail);


        Intent intent = getIntent();
        bookid = intent.getStringExtra("bookid");
        from = intent.getStringExtra("from");
        to = intent.getStringExtra("to");
        flightdate = intent.getStringExtra("flightdate");
        flighttime = intent.getStringExtra("flighttime");
        message = intent.getStringExtra("message");
        bookdate = intent.getStringExtra("bookdate");
        contactusername = intent.getStringExtra("contactusername");


//        Toast.makeText(this, bookid, Toast.LENGTH_SHORT).show();


        close = (Button)findViewById(R.id.close);

        close.setOnClickListener(this);



        tvbookid = (TextView)findViewById(R.id.bookid);
        tvflightdate = (TextView)findViewById(R.id.flightdate);
        tvflighttime = (TextView)findViewById(R.id.flighttime);
        tvmessage = (TextView)findViewById(R.id.message);
        tvfrom = (TextView)findViewById(R.id.from);
        tvto = (TextView)findViewById(R.id.to);
        tvbookdate = (TextView)findViewById(R.id.bookdate);
        tvcontactusername = (TextView)findViewById(R.id.contactusername);



        tvbookid.setText("Book Id = "+bookid);
        tvflightdate.setText("Flight Date = "+flightdate);
        tvflighttime.setText("Flight Time = "+flighttime);
        tvmessage.setText(message);
        tvfrom.setText("From = "+from);
        tvto.setText("To = "+to);
        tvbookdate.setText("Book Date = "+bookdate);
        tvcontactusername.setText("UserName = "+contactusername);

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
