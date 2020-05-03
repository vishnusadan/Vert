package com.vert.vert.app;

import android.app.Application;

import com.vert.vert.networkConnectivity.ConnectivityReceiver;


/**
 * Created by rajan on 1/30/18.
 */

public class MyApplication extends Application {


    private static MyApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
