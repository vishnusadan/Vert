package com.vert.vert.activity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.vert.vert.Static.Userdetails;
import com.vert.vert.fragment.AboutUsFragment;
import com.vert.vert.fragment.BookFlightFragment;
import com.vert.vert.fragment.CancelledBookingFragment;
import com.vert.vert.fragment.HelpFragment;
import com.vert.vert.fragment.InboxFragment;
import com.vert.vert.fragment.MyBookingFlightFragment;
import com.vert.vert.R;
import com.vert.vert.networkConnectivity.ConnectivityReceiver;

public class HomeScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int RC_SIGN_IN = 007;
    Toolbar toolbar;
    final Context context = this;
    String uri = "@mipmap/ic_vert_launcher";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Book Flight");
        setSupportActionBar(toolbar);

        //set bookflight fragment
        replaceFragment(new BookFlightFragment());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new MyBookingFlightFragment());
                toolbar.setTitle("My Booking");
//               logout();
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
         drawer.setClickable(false);

        //drawer close
//        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View headerView = navigationView.getHeaderView(0);
        TextView textView = (TextView) headerView.findViewById(R.id.username);

        if(Userdetails.username.equals("null")){

            textView.setText("Vert");

        }else {

            textView.setText(Userdetails.username);

        }

        ImageView profilepic = (ImageView) headerView.findViewById(R.id.profileicon);



        if(Userdetails.userprofilepic.equals("null")||Userdetails.userprofilepic.equals("")){

            int imageResource = getResources().getIdentifier(uri, null, getPackageName());
            Drawable res = getResources().getDrawable(imageResource);
            profilepic.setImageDrawable(res);

        }else {

            Glide.with(getApplicationContext()).load(Userdetails.userprofilepic).into(profilepic);
        }

        profilepic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeScreen.this,ProfileScreen.class));

            }
        });



    }



    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);

        } else {

            new AlertDialog.Builder(this,R.style.DialogeTheme)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            HomeScreen.this.finishAffinity();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        if(item.getItemId() == android.R.id.home){ // use android.R.id
//
//            Toast.makeText(getApplicationContext(), "hi", Toast.LENGTH_SHORT).show();
//        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_bookflight) {

            toolbar.setTitle("Book Flight");
            replaceFragment(new BookFlightFragment());

            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }


        } else if (id == R.id.nav_mybooking) {

            toolbar.setTitle("My Booking");
            replaceFragment(new MyBookingFlightFragment());

            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

        } else if (id == R.id.nav_cancelledbook) {

            toolbar.setTitle("Cancelled Booking");
            replaceFragment(new CancelledBookingFragment());

            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

        } else if (id == R.id.nav_inbox) {

            toolbar.setTitle("Inbox");
            replaceFragment(new InboxFragment());

            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

        } else if (id == R.id.nav_help) {

            toolbar.setTitle("Help");
            replaceFragment(new HelpFragment());

            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

        } else if (id == R.id.nav_shareapp) {

            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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


        }else if (id == R.id.nav_aboutus) {

            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }

            toolbar.setTitle("About Us");
            replaceFragment(new AboutUsFragment());

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    // on back button click

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragMan = getSupportFragmentManager();
        FragmentTransaction fragTrans = fragMan.beginTransaction();
        fragTrans.replace(R.id.content, fragment, "MY_FRAGMENT");
        fragTrans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragTrans.commit();
    }

}
