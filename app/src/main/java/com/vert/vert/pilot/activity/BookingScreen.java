package com.vert.vert.pilot.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.vert.vert.R;
import com.vert.vert.Static.Apilist;
import com.vert.vert.Static.Userdetails;
import com.vert.vert.activity.HomeScreen;
import com.vert.vert.activity.LoginScreen;
import com.vert.vert.fragment.AboutUsFragment;
import com.vert.vert.fragment.HelpFragment;
import com.vert.vert.fragment.InboxFragment;
import com.vert.vert.networkConnectivity.ConnectivityReceiver;
import com.vert.vert.pilot.adapter.DrawerItemCustomAdapter;
import com.vert.vert.pilot.fragment.AboutusPilotFragment;
import com.vert.vert.pilot.fragment.BookingsFragment;
import com.vert.vert.pilot.fragment.ContactVertFragment;
import com.vert.vert.pilot.fragment.HelpPilotFragment;
import com.vert.vert.pilot.fragment.MyTripsFragment;
import com.vert.vert.pilot.fragment.ProfileFragment;
import com.vert.vert.pilot.model.DataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BookingScreen extends AppCompatActivity implements View.OnClickListener {

    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView listview;
    private ImageView profileimage;
    private TextView name,time;
    Toolbar toolbar;
    LinearLayout mDrawerList;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    android.support.v7.app.ActionBarDrawerToggle mDrawerToggle;
    Fragment fragment = null;

    // Storing server url into String variable.
    String HttpUrl = Apilist.ACCEPT;

    // Creating Volley RequestQueue.
    public RequestQueue requestQueue;

    // Creating Progress dialog.
    ProgressDialog progressDialog;

    private StringRequest stringRequest;
    public String todaydate,timev;
    String uri = "@mipmap/ic_vert_launcher";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_screen);

        setupToolbar();

        mTitle = mDrawerTitle = getTitle();
        mNavigationDrawerItemTitles= getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        listview = (ListView) findViewById(R.id.left_list);
        mDrawerList = (LinearLayout) findViewById(R.id.left_drawer);
        profileimage = (ImageView) findViewById(R.id.profileimage);
        name = (TextView) findViewById(R.id.name);
        time = (TextView) findViewById(R.id.time);

        profileimage.setOnClickListener(this);
        name.setOnClickListener(this);

        // Creating Volley newRequestQueue .
        requestQueue = Volley.newRequestQueue(BookingScreen.this);

        //progress daialog
        progressDialog = new ProgressDialog(BookingScreen.this);

        DataModel[] drawerItem = new DataModel[6];

        drawerItem[0] = new DataModel(R.drawable.ic_flight_icon, "Bookings");
        drawerItem[1] = new DataModel(R.drawable.ic_flight_icon, "My Trips");
        drawerItem[2] = new DataModel(R.drawable.ic_flight_icon, "Contact Vert");
        drawerItem[3] = new DataModel(R.drawable.ic_flight_icon, "Help");
        drawerItem[4] = new DataModel(R.drawable.ic_flight_icon, "About Us");
        drawerItem[5] = new DataModel(R.drawable.ic_flight_icon, "Share App");

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.list_pilot_menu_item, drawerItem);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        setupDrawerToggle();

        Date c = Calendar.getInstance().getTime();
//        long c = Calendar.getInstance().getTimeInMillis();
        SimpleDateFormat formatDate = new SimpleDateFormat("hh:mm a");
        timev = formatDate.format(c);
//        time.setText(timev);
        Userdetails.todaytime = timev;

        System.out.println("Current time => " + timev);
//        Toast.makeText(context, timev, Toast.LENGTH_SHORT).show();

        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy");
        todaydate = df.format(c);
        Userdetails.todaydate=todaydate;

        replaceFragment(new BookingsFragment());

        if(Userdetails.username.equals("null")){

            name.setText("Vert");

        }else {

            name.setText(Userdetails.username);

        }



        if(Userdetails.userprofilepic.equals("null")||Userdetails.userprofilepic.equals("")){

            int imageResource = getResources().getIdentifier(uri, null, getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            profileimage.setImageDrawable(res);

        }else {

            Glide.with(getApplicationContext()).load(Userdetails.userprofilepic).into(profileimage);
        }


    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.profileimage:

                replaceFragment(new ProfileFragment());
                setTitle("Profile");
                mDrawerLayout.closeDrawer(mDrawerList);

                break;

                case R.id.name:

                replaceFragment(new ProfileFragment());
                setTitle("Profile");
                mDrawerLayout.closeDrawer(mDrawerList);

                break;

        }

    }

    @Override
    public void onBackPressed() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            new AlertDialog.Builder(this,R.style.DialogeTheme)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            BookingScreen.this.finishAffinity();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



           selectItem(position);
        }

    }

    private void selectItem(int position) {



        switch (position) {

            case 0:

                View view = this.getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }

                replaceFragment(new BookingsFragment());
                listview.setItemChecked(position, true);
                listview.setSelection(position);
                setTitle(mNavigationDrawerItemTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);

                break;

            case 1:

                View view1 = this.getCurrentFocus();
                if (view1 != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view1.getWindowToken(), 0);
                }


                replaceFragment(new MyTripsFragment());
                listview.setItemChecked(position, true);
                listview.setSelection(position);
                setTitle(mNavigationDrawerItemTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);


                break;

            case 2:

                View view2 = this.getCurrentFocus();
                if (view2 != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view2.getWindowToken(), 0);
                }

                replaceFragment(new ContactVertFragment());
                listview.setItemChecked(position, true);
                listview.setSelection(position);
                setTitle(mNavigationDrawerItemTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);


                break;

            case 3:

                View view3 = this.getCurrentFocus();
                if (view3 != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view3.getWindowToken(), 0);
                }


                replaceFragment(new HelpPilotFragment());
                listview.setItemChecked(position, true);
                listview.setSelection(position);
                setTitle(mNavigationDrawerItemTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);


                break;

            case 4:

                View view4 = this.getCurrentFocus();
                if (view4 != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view4.getWindowToken(), 0);
                }


                replaceFragment(new AboutusPilotFragment());
                listview.setItemChecked(position, true);
                listview.setSelection(position);
                setTitle(mNavigationDrawerItemTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);


                break;


            case 5:

                View view6 = this.getCurrentFocus();
                if (view6 != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view6.getWindowToken(), 0);
                }


                boolean isConnected = ConnectivityReceiver.isConnected();
                if (isConnected) {


                    Intent intn = new Intent(Intent.ACTION_SEND);
                    intn.setType("text/plain");
                    intn.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
                    String sAux = "https://play.google.com/store/apps/details?id=com.vert.vert&hl=en\n\n";
                    intn.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(intn, "choose one"));


                } else {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                    // set title
                    alertDialogBuilder.setTitle(R.string.checkinternet);

                    // set dialog message
                    alertDialogBuilder.setMessage(R.string.settingforinternet).setCancelable(false).setIcon(R.mipmap.ic_launcher_round).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, close
                            // current activity
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", getPackageName(), null));
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
//                                    FullImageActivity.this.finish();
                        }
                    }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });

                    // create alert dialog
                    AlertDialog alertDialog = alertDialogBuilder.create();

                    // show it
                    alertDialog.show();



                }

                break;

            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            listview.setItemChecked(position, true);
            listview.setSelection(position);
            setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {


            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    void setupToolbar(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Bookings");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    void setupDrawerToggle(){
        mDrawerToggle = new android.support.v7.app.ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        mDrawerToggle.syncState();
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragMan = getSupportFragmentManager();
        FragmentTransaction fragTrans = fragMan.beginTransaction();
        fragTrans.replace(R.id.content_frame, fragment, "MY_FRAGMENT");
        fragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragTrans.commit();
    }


}
