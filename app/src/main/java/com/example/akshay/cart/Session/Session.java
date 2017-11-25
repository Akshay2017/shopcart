package com.example.akshay.cart.Session;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Akshay on 11/24/2017.
 */

public class Session {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Context context;

    public Session(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences("login", context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void setLoggedin(boolean loggedin) {
        editor.putBoolean("logginmmode", loggedin);
        editor.commit();
    }

    public boolean loggedin() {
        return sharedPreferences.getBoolean("logginmmode", false);
    }

}
