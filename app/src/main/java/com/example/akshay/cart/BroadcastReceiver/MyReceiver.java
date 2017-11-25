package com.example.akshay.cart.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.akshay.cart.DatabaseHelper.DatabaseHelper;

public class MyReceiver extends BroadcastReceiver {

    DatabaseHelper databaseHelper;

    @Override
    public void onReceive(Context context, Intent intent) {

        databaseHelper = new DatabaseHelper(context);
        String action = intent.getAction();

        if (action.equals("is_increment")) {

            //Increment
            int quntity = intent.getExtras().getInt("quntity");
            int id = intent.getExtras().getInt("id");

            databaseHelper.incrementData(quntity, id);


            //Decrement
            int pq = intent.getExtras().getInt("pqi");
            int pid = intent.getExtras().getInt("pidi");

            databaseHelper.incrementProductData(pq, pid);

            Toast.makeText(context, "Scessfull", Toast.LENGTH_SHORT).show();


        } else if (action.equals("is_decrement")) {

            //Decrement
            int quntity = intent.getExtras().getInt("dquntity");
            int id = intent.getExtras().getInt("did");

            databaseHelper.decrimentData(quntity, id);


            //Increment
            int pq = intent.getExtras().getInt("pqd");
            int pid = intent.getExtras().getInt("pidd");

            databaseHelper.decrimentProductData(pq, pid);
            Toast.makeText(context, "Scessfull", Toast.LENGTH_SHORT).show();


        } else {
            Toast.makeText(context, "nothing", Toast.LENGTH_SHORT).show();
        }


    }
}
