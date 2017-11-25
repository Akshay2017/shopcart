package com.example.akshay.cart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class CartDisplay extends AppCompatActivity {
    ListView listView;
    ArrayList<CartModel> arrayList;
    Context mContext;
    DatabaseHelper databaseHelper;
    SharedPreferences sharedPreferences;
    private ImageView back;
    Session session;
    private static final String TAG = "cartdata";
    SharedPreferences.Editor editor;
//    @Override
//    protected void onStart() {
//        super.onStart();
//        registerReceiver(broadcastReceiver,new IntentFilter("is increment"));
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_display);
        listView=findViewById(R.id.itemlist);
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbarcart);
        setSupportActionBar(toolbar);
        arrayList=new ArrayList<>();
        mContext=CartDisplay.this;
        back=findViewById(R.id.back);
        session=new Session(this);
        if (!session.loggedin()){
            logout();
        }
        databaseHelper=new DatabaseHelper(mContext);

        sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", 0);
        String un=sharedPreferences.getString("username","");
        int userid=databaseHelper.getuserid(un);
        Log.d(TAG,"uid " + userid);
        arrayList =databaseHelper.getAllCARTProduct(userid);


        listView.setAdapter(new CartAdapter(mContext,arrayList));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menucart,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout){
            logout();

        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        session.setLoggedin(false);
        editor.clear(); // will delete key name
        editor.commit();
        startActivity(new Intent(CartDisplay.this,LoginActivity.class));
    }
//BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
//    @Override
//    public void onReceive(Context context, Intent intent) {
//
//            String quntity=intent.getStringExtra("quntity");
//            String id=intent.getStringExtra("id");
//
//
//    }
//};


}
