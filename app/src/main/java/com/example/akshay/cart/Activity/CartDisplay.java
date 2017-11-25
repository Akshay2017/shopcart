package com.example.akshay.cart.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.akshay.cart.Adapter.CartAdapter;
import com.example.akshay.cart.DatabaseHelper.DatabaseHelper;
import com.example.akshay.cart.Login.LoginActivity;
import com.example.akshay.cart.Model.CartModel;
import com.example.akshay.cart.R;
import com.example.akshay.cart.Session.Session;

import java.util.ArrayList;

public class CartDisplay extends AppCompatActivity {
    ListView listView;
    ArrayList<CartModel> arrayList;
    Context mContext;
    DatabaseHelper databaseHelper;
    SharedPreferences sharedPreferences;
    Session session;
    SharedPreferences.Editor editor;

    private static final String TAG = "cartdata";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_display);
        listView = findViewById(R.id.itemlist);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbarcart);
        setSupportActionBar(toolbar);
        arrayList = new ArrayList<>();
        mContext = CartDisplay.this;
        session = new Session(this);

        if (!session.loggedin()) {
            logout();
        }

        databaseHelper = new DatabaseHelper(mContext);

        //retrive user email using sharedPreferences
        sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", 0);
        String un = sharedPreferences.getString("useremail", "");

        //get user id
        int userid = databaseHelper.getuserid(un);
        Log.d(TAG, "uid " + userid);

        //get all joined table data to diaplay
        arrayList = databaseHelper.getAllCARTProduct(userid);

        listView.setAdapter(new CartAdapter(mContext, arrayList));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menucart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            logout();

        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        session.setLoggedin(false);
        editor.clear(); // will delete key name
        editor.commit();
        startActivity(new Intent(CartDisplay.this, LoginActivity.class));
    }

}
