package com.example.akshay.cart.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.akshay.cart.Activity.ProductDispaly;
import com.example.akshay.cart.DatabaseHelper.DatabaseHelper;
import com.example.akshay.cart.InterfaceListener.UpdateDatabaseListener;
import com.example.akshay.cart.Model.ProductModel;

import java.util.ArrayList;

public class MyReceiver extends BroadcastReceiver {

    DatabaseHelper databaseHelper;
    ArrayList<Integer> mCartPrize;
    private UpdateDatabaseListener mUpdateDatabaseListener;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DatabaseHelper pdb;

    @Override
    public void onReceive(Context context, Intent intent) {
        mCartPrize = new ArrayList<>();
        databaseHelper = new DatabaseHelper(context);
        sharedPreferences = context.getSharedPreferences("MyPref", 0);
        editor = sharedPreferences.edit();
        pdb = new DatabaseHelper(context);
        if (context instanceof ProductDispaly) {
            mUpdateDatabaseListener = (UpdateDatabaseListener) context;
        }
        String action = intent.getAction();

        if (action.equals("is_increment")) {

            //Increment
            int quntity = intent.getExtras().getInt("quntity");
            int id = intent.getExtras().getInt("id");

            int status = databaseHelper.incrementData(quntity, id);
            if (mUpdateDatabaseListener != null) {

//                String un = sharedPreferences.getString("useremail", "");

                //get user id
//                int uid = pdb.getuserid(un);
//                int  count = pdb.getCartCount(uid);
//                mUpdateDatabaseListener.counttotalcartproduct(count);
            }
            Toast.makeText(context, "Scessfull", Toast.LENGTH_SHORT).show();


        } else if (action.equals("is_decrement")) {

            int quntity = intent.getExtras().getInt("dquntity");
            int id = intent.getExtras().getInt("did");

            databaseHelper.decrimentData(quntity, id);

            Toast.makeText(context, "Scessfull", Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(context, "nothing", Toast.LENGTH_SHORT).show();
        }


    }
}
