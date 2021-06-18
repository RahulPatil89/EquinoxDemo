package com.equinox.example.rahul;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public abstract class Methods {


    // TODO Check Device Network Connection ...
    public static boolean isOnline(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }


}
