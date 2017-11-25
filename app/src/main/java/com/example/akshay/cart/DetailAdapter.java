package com.example.akshay.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Akshay on 11/17/2017.
 */

public class DetailAdapter extends BaseAdapter {

    private ArrayList<CartModel> mArrayList;
    private Context mContext;

    public DetailAdapter( Context mContext,ArrayList<CartModel> mArrayList) {
        this.mContext = mContext;
        this.mArrayList = mArrayList;
    }

    @Override
    public int getCount() {
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        DetailAdapter.ViewHolder viewHolder;
        if (view == null) {

            view = LayoutInflater.from(mContext).inflate(R.layout.detailp, viewGroup, false);

            viewHolder = new DetailAdapter.ViewHolder();

            viewHolder.pid = (TextView) view.findViewById(R.id.textView7);
            viewHolder.name = (TextView) view.findViewById(R.id.textView8);
            viewHolder.quentity = (TextView) view.findViewById(R.id.textView9);
            viewHolder.price = (TextView) view.findViewById(R.id.textView10);


            view.setTag(viewHolder);

        } else {

            viewHolder = (DetailAdapter.ViewHolder) view.getTag();
        }

        final CartModel ma = (CartModel) getItem(i);

        viewHolder.pid.setText(String.valueOf(ma.getpId()));
       // viewHolder.name.setText(ma.getProductModel().getPname());
        viewHolder.quentity.setText(String.valueOf(ma.getQunatity()));
      //  viewHolder.price.setText(String.valueOf(ma.getProductModel().getPprice()));
        return view;
    }

    class ViewHolder {

        private TextView pid;
        private TextView name;
        private TextView quentity;
        private TextView price;
    }
}
