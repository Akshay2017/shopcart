package com.example.akshay.cart.AsyncTask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.example.akshay.cart.Activity.ProductDispaly;
import com.example.akshay.cart.DatabaseHelper.DatabaseHelper;
import com.example.akshay.cart.Model.CartModel;
import com.example.akshay.cart.Model.ProductModel;
import com.example.akshay.cart.R;

import java.lang.reflect.Parameter;
import java.net.URISyntaxException;

/**
 * Created by Akshay on 12/8/2017.
 */

public class MyAsyncTask extends AsyncTask<String,Void,String> {

    DatabaseHelper databaseHelper;
    CartModel cartModel;
    Context context;

    String TAG="asynctask";
    SharedPreferences sharedPreferences;

    ProductDispaly productDispaly;

    public void setContext(ProductDispaly activity){
        this.productDispaly = activity;
    }

    public MyAsyncTask(CartModel cartModel, Context context) {
        this.databaseHelper = new DatabaseHelper(context);
        this.cartModel = cartModel;
        this.context = context;
       this.sharedPreferences = context.getSharedPreferences("MyPref", 0);
       this.productDispaly=new ProductDispaly();

    }


    @Override
    protected String doInBackground(String... params) {

    boolean status=databaseHelper.insertcart(cartModel);
        Log.d(TAG, "doInBackground: "+ status);

        String un = sharedPreferences.getString("useremail", "");
        int uid = databaseHelper.getuserid(un);
        int c=databaseHelper.getCartCount(uid);
    return String.valueOf(c);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        productDispaly.menutextView.setText(s);
//        Intent intent = new Intent("async");
//        intent.putExtra("count",s);
//        Log.d(TAG, "doInBackground string: "+ s);
//        context.sendBroadcast(intent);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
