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
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.akshay.cart.Adapter.ProductAdapter;
import com.example.akshay.cart.DatabaseHelper.DatabaseHelper;
import com.example.akshay.cart.Login.LoginActivity;
import com.example.akshay.cart.Model.ProductModel;
import com.example.akshay.cart.R;
import com.example.akshay.cart.Session.Session;
import com.example.akshay.cart.InterfaceListener.UpdateDatabaseListener;

import java.util.ArrayList;

public class ProductDispaly extends AppCompatActivity {
    ListView listView;
    ArrayList<ProductModel> arrayList;
    Context mContext;
    DatabaseHelper databaseHelper;
    ProductAdapter productAdapter;

    Session session;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    ImageView imageView;
    TextView menutextView;

    private static final String TAG = "productdata";
    private int counts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_dispaly);
        listView = findViewById(R.id.list);
        arrayList = new ArrayList<>();
        mContext = ProductDispaly.this;
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        databaseHelper = new DatabaseHelper(mContext);

        //retrive user email using sharedPreferences
        sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = sharedPreferences.edit();

        String un = sharedPreferences.getString("useremail", "");
        Log.d(TAG, "useremail " + un);

        //get user id
        int uid = databaseHelper.getuserid(un);
        Log.d(TAG, "uid " + uid);

        session = new Session(this);
        if (!session.loggedin()) {
            logout();
        }

        // get all data from  product table
        arrayList = databaseHelper.getAllProduct();

        //count cart total row to display
         counts = databaseHelper.getCartCount(uid);



        productAdapter = new ProductAdapter(mContext, arrayList, uid);
        productAdapter.setUpadteProductDBListener(updateDatabaseListener);

        listView.setAdapter(productAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        MenuItem m=menu.findItem(R.id.notification);
        View view=m.getActionView();
        if (view != null){
            imageView=view.findViewById(R.id.carticon);
            menutextView=view.findViewById(R.id.counttv);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductDispaly.this, CartDisplay.class));
            }
        });

     menutextView.setText(""+counts);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logut) {
            logout();

        }
        if (item.getItemId() == R.id.notification) {



        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        session.setLoggedin(false);
        editor.clear(); // will clear or delete all savead SharedPreferences data in our case user email (user email saved for the getting user id in cart activity to store as cart user id (CUID) in table column )
        editor.commit(); // commit changes
        startActivity(new Intent(ProductDispaly.this, LoginActivity.class));
    }

    //this is InterfaceListerner fro upadte Activity view by using anonymous class
    private UpdateDatabaseListener updateDatabaseListener = new UpdateDatabaseListener() {

        @Override
        public void counttotalcartproduct(int count) {
            Log.d(TAG, " inside counttoatoal() -> count : " + count);
            menutextView.setText(""+count);
        }

        @Override
        public void pricecartupadte(int i) {

        }
    };

    //this is InterfaceListerner fro upadte Activity view by using implementation their override methood
   /* @Override
    public void insertcart(long status, int postion) {
        if (status>0){
            Toast.makeText(mContext, "Inserted data into CART succesfully", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext, "Not Inserted data into CART succesfully", Toast.LENGTH_SHORT).show();
        }

    }*/
}
