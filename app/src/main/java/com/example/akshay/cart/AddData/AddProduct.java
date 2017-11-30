package com.example.akshay.cart.AddData;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.akshay.cart.DatabaseHelper.DatabaseHelper;
import com.example.akshay.cart.Model.ProductModel;
import com.example.akshay.cart.R;

public class AddProduct extends AppCompatActivity {
    private TextView pname, pquntity, pprice;
    private Button padd;
    Context context;
    DatabaseHelper pdb;
    ProductModel productModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pname = (TextView) findViewById(R.id.pname);
        pquntity = (TextView) findViewById(R.id.pquntity);
        pprice = (TextView) findViewById(R.id.pprice);
      //  padd = (Button) findViewById(R.id.padd);

        context = AddProduct.this;
        pdb = new DatabaseHelper(context);

        productModel = new ProductModel();

        padd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//                productModel.setPname(pname.getText().toString().trim());
//                productModel.setPquentity(Integer.parseInt(pquntity.getText().toString().trim()));
//                productModel.setPprice(Integer.parseInt(pprice.getText().toString().trim()));
//
//                boolean insertdt = pdb.insertdata(productModel);

//                if (insertdt == true) {
//                    Toast.makeText(AddProduct.this, "succesfull Addded product to product table", Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(AddProduct.this, "Unsuccesfull", Toast.LENGTH_SHORT).show();
//                }

            }
        });


    }
}
