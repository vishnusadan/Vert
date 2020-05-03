package com.vert.vert.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vert.vert.R;
import com.vert.vert.Static.Userdetails;
import com.vert.vert.services.Utils;
import com.vert.vert.tab.Completed_Booking_Fragment;
import com.vert.vert.tab.Ongoing_Bookings_Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by rajan on 10/4/18.
 */

public class MyBookingFlightFragment extends Fragment {

    private TabLayout tabLayout_BOOKING;
    private ViewPager viewPager_BOOKING;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View mybooking =inflater.inflate(R.layout.fragment_mybooking,container,false);


        viewPager_BOOKING = (ViewPager) mybooking.findViewById(R.id.viewpager_BOOKING);
        tabLayout_BOOKING = (TabLayout) mybooking.findViewById(R.id.tabLayout_BOOKING);
        Utils.setupViewPager(viewPager_BOOKING,
                new Ongoing_Bookings_Fragment(), new Completed_Booking_Fragment(),
                getString(R.string.ongoing), getString(R.string.completed),
                getActivity().getSupportFragmentManager());
        tabLayout_BOOKING.setupWithViewPager(viewPager_BOOKING);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String todaydate = df.format(c);

        Userdetails.todaydate = todaydate;

        //Type face
//        Typeface tf = Typeface.createFromAsset(getResources().getAssets(),
//                "fonts/Titillium.ttf");
        // Custom tab views //
        TextView tab1 = new TextView(getActivity());
        tab1.setText(getString(R.string.ongoing));
        tab1.setTextColor(getResources().getColor(R.color.white));
//        tab1.setTypeface(tf);
        tab1.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView tab2 = new TextView(getActivity());
        tab2.setText(getString(R.string.completed));
        tab2.setTextColor(getResources().getColor(R.color.white));
//        tab2.setTypeface(tf);
        tab2.setGravity(Gravity.CENTER_HORIZONTAL);


        TabLayout tabLayout = (TabLayout)  mybooking.findViewById(R.id.tabLayout_BOOKING);
        tabLayout.setupWithViewPager(viewPager_BOOKING);
        tabLayout.getTabAt(0).setCustomView(tab1);
        tabLayout.getTabAt(1).setCustomView(tab2);



        Utils.setupTabIcons(tabLayout_BOOKING);




        return mybooking;
    }
}
