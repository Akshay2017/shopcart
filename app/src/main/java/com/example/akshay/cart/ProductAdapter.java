package com.example.akshay.cart;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Akshay on 11/15/2017.
 */

public class ProductAdapter extends BaseAdapter {

    private ArrayList<ProductModel> mArrayList;
    private Context mContext;
    DatabaseHelper pdb;
    ProductDispaly productDispaly;
    UpdateDatabaseListener mUpadteProductDBListener;
    CartModel cartModel;
    SharedPreferences pref ;
    int uid;





    private static final String TAG = "Adapter";
    public ProductAdapter(Context context, ArrayList<ProductModel> arrayList, int uid) {
        this.mContext=context;
        this.mArrayList=arrayList;
        this.pdb =new DatabaseHelper(context);
        this.productDispaly=new ProductDispaly();
        this.cartModel=new CartModel();
        this.pref=context.getSharedPreferences("MyPref", 0);
        this.uid=uid;

    }

    @Override
    public int getCount() {

        Log.d(TAG, "count :  " + mArrayList.size() );
        return mArrayList.size();
    }

    @Override
    public Object getItem(int i) {


        Log.d(TAG, "items :  " + mArrayList.get(i) );
         return mArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder viewHolder;
        if (view == null) {

            view = LayoutInflater.from(mContext).inflate(R.layout.list, viewGroup, false);

            viewHolder = new ViewHolder();

            viewHolder.name = (TextView) view.findViewById(R.id.textView2);
            viewHolder.quentity = (TextView) view.findViewById(R.id.textView3);
            viewHolder.addtocart = (Button) view.findViewById(R.id.addtocarts);

            view.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) view.getTag();
        }



         ProductModel ma = (ProductModel) getItem(i);

        if (ma != null){


            viewHolder.name.setText(ma.getPname().toString());
            viewHolder.quentity.setText(String.valueOf(ma.getPquentity()));


            viewHolder.addtocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ProductModel productModel = (ProductModel) getItem(i);
                    int pid= Integer.parseInt(String.valueOf(productModel.getPid()));
                    int pquntity= Integer.parseInt(productModel.getPquentity());

                    int cpq=1;
                    int updateinproduct = pquntity - cpq;

                    //Insert data in cart


                    cartModel.setUid(uid);
                    cartModel.setpId(pid);
                    cartModel.setQunatity(cpq);
                    pdb.insertcart(cartModel);

                    //Upadte data in product to disply live
                    productModel.setPquentity(String.valueOf(updateinproduct));
                    pdb.updateData(productModel);
                    notifyDataSetChanged();


//                    int  count = pdb.getCartCount();
//                    Log.d(TAG, "onClick: " + count);
//                    mUpadteProductDBListener.counttotalcartproduct(count);




                    viewHolder.addtocart.setEnabled(false);
                    viewHolder.addtocart.setFocusable(false);
                    viewHolder.addtocart.setText(null);

                }
            });


        }


        notifyDataSetChanged();
        return view;
    }

    class ViewHolder {

        private TextView name;
        private TextView quentity;
        private Button addtocart;

    }

    public void setUpadteProductDBListener(UpdateDatabaseListener upadteProductDB){

       this.mUpadteProductDBListener=upadteProductDB;
    }

}
