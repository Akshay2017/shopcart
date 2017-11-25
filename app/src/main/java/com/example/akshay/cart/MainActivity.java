package com.example.akshay.cart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private TextView pname,pquntity,pprice;
    private Button padd,d,c,dpb;
    Context context;
    DatabaseHelper pdb;
    ProductModel productModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pname= (TextView) findViewById(R.id.pname);
        pquntity= (TextView) findViewById(R.id.pquntity);
        pprice= (TextView) findViewById(R.id.pprice);
        padd= (Button) findViewById(R.id.padd);
        d= (Button) findViewById(R.id.button2);
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,ProductDispaly.class);
                startActivity(i);
            }
        });
       c = (Button) findViewById(R.id.cart);
       c.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent i=new Intent(MainActivity.this,CartDisplay.class);
               startActivity(i);
           }
       });

        dpb = (Button) findViewById(R.id.dpb);
        dpb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,DetailProduct.class);
                startActivity(i);
            }
        });
        context=MainActivity.this;
        pdb=new DatabaseHelper(context);

        productModel=new ProductModel();

        padd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                productModel.setPname(pname.getText().toString().trim());
                productModel.setPquentity(pquntity.getText().toString().trim());
                productModel.setPprice(pprice.getText().toString().trim());

                boolean insertdt=   pdb.insertdata(productModel);

                if (insertdt==true){
                    Toast.makeText(MainActivity.this, "succesfull", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this, "Unsuccesfull", Toast.LENGTH_SHORT).show();
                }

            }
        });




    }
}
