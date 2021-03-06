package com.vert.vert.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.vert.vert.R;
import com.vert.vert.Static.Userdetails;
import com.vert.vert.model.Globals;

import java.io.Console;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import android.app.TimePickerDialog;
import android.app.Fragment;
import android.app.DialogFragment;
import java.util.Calendar;
import android.widget.TimePicker;


public class OnewayScreen extends AppCompatActivity implements View.OnClickListener {

    private String keyv,fromv,tov,spinnerv,formattedDate,formattedminute,formattedtimezone;
    private TextView from,to,time,timecontent;
    private Spinner spinner;
    private EditText date,name,name1,name2,name3,namepound,namepound1,namepound2,namepound3;
    private Button book,backbt;
    private LinearLayout mainlayer,namelayer,name1layer,name2layer,name3layer;
    Calendar myCalendar = Calendar.getInstance();
    private int mYear, mMonth, mDay, mHour, mMinute, merdian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oneway_screen);

        Intent intent = getIntent();
        keyv = intent.getStringExtra("trip");
        fromv = intent.getStringExtra("from");
        tov = intent.getStringExtra("to");


        spinner=(Spinner)findViewById(R.id.sp_seat);
        from = (TextView)findViewById(R.id.from);
        to = (TextView)findViewById(R.id.to);
        time = (TextView)findViewById(R.id.time);
        timecontent = (TextView)findViewById(R.id.timecontent);
        date = (EditText) findViewById(R.id.ed_date);
        name = (EditText) findViewById(R.id.name);
        name1 = (EditText) findViewById(R.id.name1);
        name2 = (EditText) findViewById(R.id.name2);
        name3 = (EditText) findViewById(R.id.name3);
        namepound = (EditText) findViewById(R.id.namepound);
        namepound1 = (EditText) findViewById(R.id.namepound1);
        namepound2 = (EditText) findViewById(R.id.namepound2);
        namepound3 = (EditText) findViewById(R.id.namepound3);
        book = (Button) findViewById(R.id.submit);
        backbt = (Button) findViewById(R.id.backbt);
        mainlayer = (LinearLayout) findViewById(R.id.mainlenear);
        namelayer = (LinearLayout) findViewById(R.id.name_ln);
        name1layer = (LinearLayout) findViewById(R.id.name_ln1);
        name2layer = (LinearLayout) findViewById(R.id.name_ln2);
        name3layer = (LinearLayout) findViewById(R.id.name_ln3);



        // Get current time
        DateFormat hourFormat = new SimpleDateFormat("hh");
        DateFormat minuteFormat = new SimpleDateFormat("mm");
        DateFormat timezoneFormat = new SimpleDateFormat("a");


         formattedDate = hourFormat.format(new Date()).toString();;
         formattedminute = minuteFormat.format(new Date()).toString();
         formattedtimezone = timezoneFormat.format(new Date()).toString();
        System.out.println(formattedDate);
        System.out.println(formattedminute);
        System.out.println(formattedtimezone);

        //change the button name according to screen direct
        if(keyv.equals("oneway")){

            book.setText(R.string.btbook);

        }else {

            book.setText(R.string.btroundtrip);

        }

        //hide the main layer
        mainlayer.setVisibility(View.INVISIBLE);


        from.setText(fromv);
        to.setText(tov);

        date.setOnClickListener(this);
        time.setOnClickListener(this);
        book.setOnClickListener(this);
        backbt.setOnClickListener(this);


        // Initializing a String Array
        String[] count = new String[]{
                "Seat",
                "1",
                "2",
                "3",
                "4"
        };

        final List<String> countList = new ArrayList<>(Arrays.asList(count));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_seat,R.id.number,countList);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_seat);

        spinner.setAdapter(spinnerArrayAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent,
                                       View view, int pos, long id) {
                spinnerv = parent.getItemAtPosition(pos).toString();

//                Toast.makeText(OnewayScreen.this, spinnerv, Toast.LENGTH_SHORT).show();

                switch (spinnerv) {

                    case "1":

                        mainlayer.setVisibility(View.VISIBLE);
                        namelayer.setVisibility(View.VISIBLE);
                        name1layer.setVisibility(View.INVISIBLE);
                        name2layer.setVisibility(View.INVISIBLE);
                        name3layer.setVisibility(View.INVISIBLE);

                        break;

                    case "2":

                        mainlayer.setVisibility(View.VISIBLE);
                        namelayer.setVisibility(View.VISIBLE);
                        name1layer.setVisibility(View.VISIBLE);
                        name2layer.setVisibility(View.INVISIBLE);
                        name3layer.setVisibility(View.INVISIBLE);

                        break;

                    case "3":

                        mainlayer.setVisibility(View.VISIBLE);
                        namelayer.setVisibility(View.VISIBLE);
                        name1layer.setVisibility(View.VISIBLE);
                        name2layer.setVisibility(View.VISIBLE);
                        name3layer.setVisibility(View.INVISIBLE);

                        break;

                    case "4":

                        mainlayer.setVisibility(View.VISIBLE);
                        namelayer.setVisibility(View.VISIBLE);
                        name1layer.setVisibility(View.VISIBLE);
                        name2layer.setVisibility(View.VISIBLE);
                        name3layer.setVisibility(View.VISIBLE);


                        break;


                    case "Seat":

                        mainlayer.setVisibility(View.INVISIBLE);
                        namelayer.setVisibility(View.INVISIBLE);
                        name1layer.setVisibility(View.INVISIBLE);
                        name2layer.setVisibility(View.INVISIBLE);
                        name3layer.setVisibility(View.INVISIBLE);

//                        Toast.makeText(OnewayScreen.this, "Please Select The Seat Count", Toast.LENGTH_SHORT).show();

                        break;

                    default:

                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.time:

                //Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                merdian = c.get(Calendar.AM_PM);

//                 Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

//                                time.setText(hourOfDay + ":" + minute);

                                String hour = String.valueOf(hourOfDay);

                                updateTime(hourOfDay,minute);
                            }
                        }, mHour, mMinute, false);

                timePickerDialog.show();


                break;


            case R.id.ed_date:

                // Get Current Date
                final Calendar c1 = Calendar.getInstance();
                mYear = c1.get(Calendar.YEAR);
                mMonth = c1.get(Calendar.MONTH);
                mDay = c1.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                String dayString = String.valueOf(dayOfMonth);
                                if (dayString.length() == 1) {
                                    dayString = "0" + dayString;
                                }
                                int month = monthOfYear + 1;
                                String monthString = String.valueOf(month);
                                if (monthString.length() == 1) {
                                    monthString = "0" + monthString;
                                }
                                date.setText(monthString + "-" + dayString + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();


                break;

            case R.id.submit:


                if (date.getText().toString().equals("") || time.getText().toString().equals("Time") || spinnerv.equals("Seat") || spinnerv.equals("")){

                    Toast.makeText(this, "Please Fill All Details", Toast.LENGTH_SHORT).show();

                }else  {

                    switch (spinnerv){

                        case "1":

                            if (name.getText().toString().equals("") || namepound.getText().toString().equals("")){

                                Toast.makeText(this, "Please Fill All Details", Toast.LENGTH_SHORT).show();

                            }else {

                                poundsCheck( "1");

                            }


                            break;


                        case "2":


                            if (name.getText().toString().equals("")|| name1.getText().toString().equals("") || namepound.getText().toString().equals("") || namepound1.getText().toString().equals("")){

                                Toast.makeText(this, "Please Fill All Details", Toast.LENGTH_SHORT).show();

                            }else {

                                poundsCheck("2");

                            }


                            break;

                        case  "3":


                            if (name.getText().toString().equals("")|| name1.getText().toString().equals("")|| name2.getText().toString().equals("") || namepound.getText().toString().equals("")|| namepound1.getText().toString().equals("")|| namepound2.getText().toString().equals("")){

                                Toast.makeText(this, "Please Fill All Details", Toast.LENGTH_SHORT).show();

                            }else {

                                poundsCheck("3");

                            }



                            break;


                        case  "4":


                            if (name.getText().toString().equals("")|| name1.getText().toString().equals("")|| name2.getText().toString().equals("")|| name3.getText().toString().equals("")|| namepound.getText().toString().equals("")|| namepound1.getText().toString().equals("")|| namepound2.getText().toString().equals("")|| namepound3.getText().toString().equals("")){

                                Toast.makeText(this, "Please Fill All Details", Toast.LENGTH_SHORT).show();

                            }else {

                                poundsCheck("4");

                            }



                            break;

                    }




                }


                break;


            case R.id.backbt:

                startActivity(new Intent(OnewayScreen.this,HomeScreen.class));
                finish();

                break;

        }
    }


    public void poundsCheck(String value){


       switch (value){

           case "1":

               int pounds = Integer.parseInt(namepound.getText().toString());

               if (pounds > 500){

                   Toast.makeText(this, "More Than 500 Pounds Not Allowed", Toast.LENGTH_SHORT).show();

               }else {

                   screenDirect();

               }


               break;

           case "2":

               int pounds1 = Integer.parseInt(namepound.getText().toString()) + Integer.parseInt(namepound1.getText().toString());

               if (pounds1 > 500){

                   Toast.makeText(this, "More Than 500 Pounds Not Allowed", Toast.LENGTH_SHORT).show();

               }else {

                   screenDirect();

               }


               break;

           case "3":

               int pounds2 = Integer.parseInt(namepound.getText().toString()) + Integer.parseInt(namepound1.getText().toString()) + Integer.parseInt(namepound2.getText().toString());

               if (pounds2 > 500){

                   Toast.makeText(this, "More Than 500 Pounds Not Allowed", Toast.LENGTH_SHORT).show();

               }else {

                   screenDirect();

               }


               break;

           case "4":




               int pounds3 = Integer.parseInt(namepound.getText().toString()) + Integer.parseInt(namepound1.getText().toString()) + Integer.parseInt(namepound2.getText().toString()) + Integer.parseInt(namepound3.getText().toString());

               if (pounds3 > 500){

                   Toast.makeText(this, "More Than 500 Pounds Not Allowed", Toast.LENGTH_SHORT).show();

               }else {

                   screenDirect();

               }

               break;

       }



    }


    public void screenDirect(){



        Globals sharedData = Globals.getInstance();
        sharedData.setOnewayfrom(from.getText().toString());
        sharedData.setOnewayto(to.getText().toString());
        sharedData.setOnewaybookdate(date.getText().toString());
        sharedData.setOnewaybooktime(time.getText().toString());
        sharedData.setOnewaybookseat(spinnerv);
        sharedData.setOnewaybookname(name.getText().toString());
        sharedData.setOnewaybookname1(name1.getText().toString());
        sharedData.setOnewaybookname2(name2.getText().toString());
        sharedData.setOnewaybookname3(name3.getText().toString());
        sharedData.setOnewaypound(namepound.getText().toString());
        sharedData.setOnewaypound1(namepound1.getText().toString());
        sharedData.setOnewaypound2(namepound2.getText().toString());
        sharedData.setOnewaypound3(namepound3.getText().toString());
        Userdetails.BOOKTYPE = "oneway";


//        Userdetails.ONEWAYFROM = from.getText().toString();
//        Userdetails.ONEWAYTO = to.getText().toString();
//        Userdetails.ONEWAYBOOKDATE = date.getText().toString();
//        Userdetails.ONEWAYBOOKTIME = time.getText().toString();
//        Userdetails.ONEWAYBOOKSEAT = spinnerv;
//        Userdetails.BOOKTYPE = "oneway";
//        Userdetails.ONEWAYBOOKNAME = name.getText().toString();
//        Userdetails.ONEWAYBOOKNAME1 = name1.getText().toString();
//        Userdetails.ONEWAYBOOKNAME2 = name2.getText().toString();
//        Userdetails.ONEWAYBOOKNAME3 = name3.getText().toString();
//        Userdetails.ONEWAYPOUND = namepound.getText().toString();
//        Userdetails.ONEWAYPOUND1 = namepound1.getText().toString();
//        Userdetails.ONEWAYPOUND2 = namepound2.getText().toString();
//        Userdetails.ONEWAYPOUND3 = namepound3.getText().toString();


        //change the button name according to screen direct
        if(keyv.equals("oneway")){

            startActivity(new Intent(OnewayScreen.this,ContactUserScreen.class));

        }else {

            startActivity(new Intent(OnewayScreen.this,RoundTripScreen.class));


        }

    }

    // Used to convert 24hr format to 12hr format with AM/PM values
    private void updateTime(int hours, int mins) {


        String timeSet = "";
        if (hours > 12) {
            hours -= 12;
            timeSet = "PM";
        } else if (hours == 0) {
            hours += 12;
            timeSet = "AM";
        } else if (hours == 12)
            timeSet = "PM";
        else
            timeSet = "AM";

        String minutes = "";

        if (mins < 10)

            minutes = "0" + mins;

        else

            minutes = String.valueOf(mins);

        String hoursday = "";

        if (hours < 10)

            hoursday = "0" + hours;


        else

            hoursday = String.valueOf(hours);


        if (formattedDate.equals("") || hoursday.equals("")) {

            Toast.makeText(getApplicationContext(),"please select again",Toast.LENGTH_LONG).show();

        } else {

            // Time check whther its past or new
            if (timeSet.equals(formattedtimezone)) {

                if (Integer.parseInt(hoursday) < Integer.parseInt(formattedDate)) {

                    if (Integer.parseInt(hoursday) == Integer.parseInt(formattedDate)) {

                        if (Integer.parseInt(minutes) == Integer.parseInt(formattedminute) || Integer.parseInt(minutes) < Integer.parseInt(formattedminute)) {

                            timecontent.setText("You Selected Invalid Time");
                            time.setText("Time");

                        } else {

                            // Append in a StringBuilder
                            String aTime = new StringBuilder().append(hoursday).append(':')
                                    .append(minutes).append(" ").append(timeSet).toString();

                            time.setText(aTime);
                            timecontent.setText("Flight Is available For this Time");

                        }
                    } else {

                        if (Integer.parseInt(minutes) == Integer.parseInt(formattedminute) || Integer.parseInt(minutes) < Integer.parseInt(formattedminute)) {

                            timecontent.setText("You Selected Invalid Time");
                            time.setText("Time");

                        } else {

                            // Append in a StringBuilder
                            String aTime = new StringBuilder().append(hoursday).append(':')
                                    .append(minutes).append(" ").append(timeSet).toString();

                            time.setText(aTime);
                            timecontent.setText("Flight Is available For this Time");

                        }

                    }
                } else {

                    if (Integer.parseInt(hoursday) == Integer.parseInt(formattedDate)) {

                        if (Integer.parseInt(minutes) == Integer.parseInt(formattedminute) || Integer.parseInt(minutes) < Integer.parseInt(formattedminute)) {

                            timecontent.setText("You Selected Invalid Time");
                            time.setText("Time");

                        } else {

                            // Append in a StringBuilder
                            String aTime = new StringBuilder().append(hoursday).append(':')
                                    .append(minutes).append(" ").append(timeSet).toString();

                            time.setText(aTime);
                            timecontent.setText("Flight Is available For this Time");

                        }
                    } else {


                        if(Integer.parseInt(hoursday)==12){

                            timecontent.setText("You Selected Invalid Time");
                            time.setText("Time");

                        }else {
                            // Append in a StringBuilder
                            String aTime = new StringBuilder().append(hoursday).append(':')
                                    .append(minutes).append(" ").append(timeSet).toString();

                            time.setText(aTime);
                            timecontent.setText("Flight Is available For this Time");

                        }
                    }
                }

            } else {

                if (formattedtimezone.equals("PM")) {

                    if (timeSet.equals("AM")) {

                        timecontent.setText("You Selected Invalid Time");
                        time.setText("Time");
                    } else {

                        timecontent.setText("Please Select The Time Correctly");
                        time.setText("Time");
                    }

                } else {

                    if (timeSet.equals("PM")) {

                        // Append in a StringBuilder
                        String aTime = new StringBuilder().append(hoursday).append(':')
                                .append(minutes).append(" ").append(timeSet).toString();

                        time.setText(aTime);
                        timecontent.setText("Flight Is available For this Time");


                    } else {

                        timecontent.setText("You Selected Invalid Time");
                        time.setText("Time");

                    }

                }


            }


        }
    }


}
