package com.example.akshay.cart;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class DetailProduct extends AppCompatActivity {
    ListView listView;
    ArrayList<CartModel> arrayList;
    Context mContext;
    DatabaseHelper databaseHelper;
    private static final String TAG = "productdata";
    ProductAdapter productAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);

        listView=findViewById(R.id.dp);
        arrayList=new ArrayList<>();
        mContext=DetailProduct.this;

        databaseHelper=new DatabaseHelper(mContext);
        arrayList =databaseHelper.getCartDetails();

        listView.setAdapter(new DetailAdapter(mContext,arrayList));

        }
}
