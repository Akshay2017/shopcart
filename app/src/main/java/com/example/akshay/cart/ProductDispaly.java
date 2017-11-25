package com.example.akshay.cart;

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
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;

public class ProductDispaly extends AppCompatActivity{
    ListView listView;
    ArrayList<ProductModel> arrayList;
    Context mContext;
    DatabaseHelper databaseHelper;
    private static final String TAG = "productdata";
    ProductAdapter productAdapter;
    private TextView textView;
    Session session;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_dispaly);
        listView=findViewById(R.id.list);
        arrayList=new ArrayList<>();
        mContext=ProductDispaly.this;
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        textView=findViewById(R.id.count);
        databaseHelper=new DatabaseHelper(mContext);

//        Intent intent=getIntent();
//        int s=intent.getExtras().getInt("username");


        sharedPreferences = getApplicationContext().getSharedPreferences("MyPref", 0);

        editor=sharedPreferences.edit();
        String un=sharedPreferences.getString("username","");

        Log.d(TAG,"username " + un);

        int i=databaseHelper.getuserid(un);
        Log.d(TAG,"uid " + i);

        session=new Session(this);
        if (!session.loggedin()){
            logout();
        }



        arrayList =databaseHelper.getAllProduct();

        int userId=databaseHelper.getuserid(un);

        int  counts = databaseHelper.getCartCount(userId);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProductDispaly.this,CartDisplay.class);
                startActivity(intent);
            }
        });

        productAdapter=new ProductAdapter(mContext,arrayList,i);
        productAdapter.setUpadteProductDBListener(updateDatabaseListener);

        listView.setAdapter(productAdapter);
        textView.setText(Integer.toString(counts));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logut){
            logout();

        } if (item.getItemId() == R.id.notification){
            startActivity(new Intent(ProductDispaly.this,CartDisplay.class));

        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        session.setLoggedin(false);
        editor.clear(); // will delete key name
        editor.commit(); // commit changes
        startActivity(new Intent(ProductDispaly.this,LoginActivity.class));
    }

    private UpdateDatabaseListener updateDatabaseListener = new UpdateDatabaseListener() {

        @Override
        public void counttotalcartproduct(int count) {
           // textView.setText(Integer.toString(count));
        }
    };


   /* @Override
    public void insertcart(long status, int postion) {
        if (status>0){
            Toast.makeText(mContext, "Inserted data into CART succesfully", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(mContext, "Not Inserted data into CART succesfully", Toast.LENGTH_SHORT).show();
        }

    }*/
}
