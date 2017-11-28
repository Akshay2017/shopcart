package com.example.akshay.cart.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.akshay.cart.DatabaseHelper.DatabaseHelper;
import com.example.akshay.cart.InterfaceListener.UpdateDatabaseListener;
import com.example.akshay.cart.Model.CartModel;
import com.example.akshay.cart.Model.ProductModel;
import com.example.akshay.cart.R;

import java.util.ArrayList;

import at.markushi.ui.CircleButton;

/**
 * Created by Akshay on 11/16/2017.
 */

public class CartAdapter extends BaseAdapter {
    private ArrayList<CartModel> mArrayList;
    private Context mContext;
    DatabaseHelper pdb;
    UpdateDatabaseListener mUpadteProductDBListener;
    private static final String TAG = "CartAdapter";


    public CartAdapter(Context mContext, ArrayList<CartModel> mArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
        this.pdb = new DatabaseHelper(mContext);

    }

    @Override
    public int getCount() {
        Log.d(TAG, "count :  " + mArrayList.size());
        return mArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return mArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final CartAdapter.ViewHolder viewHolder;
        if (view == null) {

            view = LayoutInflater.from(mContext).inflate(R.layout.item, viewGroup, false);

            viewHolder = new CartAdapter.ViewHolder();

            viewHolder.name = (TextView) view.findViewById(R.id.textView5);
            viewHolder.quentity = (TextView) view.findViewById(R.id.textView6);
            viewHolder.price = (TextView) view.findViewById(R.id.textView11);
            viewHolder.increment = view.findViewById(R.id.plue);
            viewHolder.decriment = view.findViewById(R.id.minus);
            viewHolder.detet = view.findViewById(R.id.delete);


            view.setTag(viewHolder);

        } else {

            viewHolder = (CartAdapter.ViewHolder) view.getTag();
        }

        final CartModel ma = (CartModel) getItem(i);

        viewHolder.quentity.setText(String.valueOf(ma.getQunatity()));
        viewHolder.name.setText(ma.getProductModel().getPname());
        viewHolder.price.setText(String.valueOf(ma.getProductModel().getPprice()));


        int cpq = 1;

        //Get product id and product quntity
        final int pid = ma.getpId();
        final int pquntity = ma.getQunatity();

        //Increment in cart product(Cart table)
        final int updateinproduct = pquntity + cpq;

        //Decrement in product (Product table)
        final int pqd = ma.getProductModel().getPquentity();
        final int upd = pqd - cpq;


        //Decrement in cart product (Cart table)
        final int updatedeproduct = pquntity - cpq;

        //Increment in product (Product table)
        final int pqi = ma.getProductModel().getPquentity();
        final int upi = pqi + cpq;


        viewHolder.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent1 = new Intent("is_increment");
                CartModel cartModel= (CartModel) getItem(i);
                intent1.putExtra("postion", i);
                Log.d(TAG, "onClick: postion" + i);

                //Increment
                intent1.putExtra("quntity", updateinproduct);
                intent1.putExtra("id", pid);

                //change dyanamiclly in  listview upadted quntity
                cartModel.setQunatity(cartModel.getQunatity() + 1);

                //send intent values to broadcast
                mContext.sendBroadcast(intent1);

               mUpadteProductDBListener.pricecartupadte(i);

                notifyDataSetChanged();
            }
        });

        viewHolder.decriment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent2 = new Intent("is_decrement");

                intent2.putExtra("postion",i);

                //Decrement
                intent2.putExtra("dquntity", updatedeproduct);
                intent2.putExtra("did", pid);

                //change dyanamicly in  listview upadted quntity
                CartModel cartModel = (CartModel) getItem(i);
                cartModel.setQunatity(cartModel.getQunatity() - 1);



                //send intent values to broadcast
                mContext.sendBroadcast(intent2);

                notifyDataSetChanged();
            }
        });

        viewHolder.detet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CartModel de = (CartModel) getItem(i);
                int dcid = de.getcId();
                pdb.deletcartrow(dcid);
                mArrayList.remove(i);// to remove row dynamically
                notifyDataSetChanged();
            }
        });


        notifyDataSetChanged();
        return view;
    }

    class ViewHolder {

        private TextView name;
        private TextView quentity;
        private TextView price;
        private Button increment, decriment, detet;
    }

    public void setUpadteProductDBListener(UpdateDatabaseListener upadteProductDB) {

        this.mUpadteProductDBListener = upadteProductDB;
    }
}
