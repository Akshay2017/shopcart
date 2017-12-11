package com.example.akshay.cart.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.akshay.cart.AsyncTask.MyAsyncTask;
import com.example.akshay.cart.DatabaseHelper.DatabaseHelper;
import com.example.akshay.cart.Model.CartModel;
import com.example.akshay.cart.Model.ProductModel;
import com.example.akshay.cart.Activity.ProductDispaly;
import com.example.akshay.cart.R;
import com.example.akshay.cart.InterfaceListener.UpdateDatabaseListener;

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
    int uid;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int count;

    private static final String TAG = "Adapter";

    public ProductAdapter(Context context, ArrayList<ProductModel> arrayList, int uid) {
        this.mContext = context;
        if (mContext instanceof Activity) {
            mUpadteProductDBListener = (UpdateDatabaseListener) mContext;
        }
        this.mArrayList = arrayList;
        this.pdb = new DatabaseHelper(context);
        this.productDispaly = new ProductDispaly();
        this.cartModel = new CartModel();
        this.uid = uid;
        this.sharedPreferences = context.getSharedPreferences("MyPref", 0);
        this.editor = sharedPreferences.edit();

//        if (mContext instanceof Activity)
//        {
//               mUpadteProductDBListener = (UpdateDatabaseListener) mContext;
//        }

        if (mContext instanceof Activity){

            productDispaly= (ProductDispaly) mContext;
        }

    }

    public ProductAdapter() {

    }

    @Override
    public int getCount() {

        Log.d(TAG, "count :  " + mArrayList.size());
        return mArrayList.size();
    }

    @Override
    public Object getItem(int i) {


        Log.d(TAG, "items :  " + mArrayList.get(i));
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
            viewHolder.cost = (TextView) view.findViewById(R.id.textView3);
            viewHolder.addtocart = (Button) view.findViewById(R.id.addtocarts);

            view.setTag(viewHolder);

        } else {

            viewHolder = (ViewHolder) view.getTag();
        }


        ProductModel ma = (ProductModel) getItem(i);

        if (ma != null) {


            viewHolder.name.setText(ma.getPname().toString());
            viewHolder.cost.setText(String.valueOf(ma.getPprice()));

            if (ma.isAdded()) {
                //if add to cart button is pressed once in ListView then add to cart button is disabled
                viewHolder.addtocart.setEnabled(false);
                viewHolder.addtocart.setText("Added");
            } else {
                viewHolder.addtocart.setEnabled(true);
            }


            viewHolder.addtocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ProductModel productModel = (ProductModel) getItem(i);
                    int pid = Integer.parseInt(String.valueOf(productModel.getPid()));
//                    int pquntity = productModel.getPquentity();

                    int cpq = 1;
//                    int updateinproduct = pquntity - cpq;

                    //Inserting data in cart table
                    cartModel.setUid(uid);
                    cartModel.setpId(pid);
                    cartModel.setQunatity(cpq);

                    MyAsyncTask myAsyncTask=new MyAsyncTask(cartModel,mContext);
                    myAsyncTask.execute(String.valueOf(count));
                    myAsyncTask.setContext(productDispaly);
                   // pdb.insertcart(cartModel);

                    //Upadte data in product to disply live
//                    productModel.setPquentity(updateinproduct);
//                    pdb.updateData(productModel);
//                    notifyDataSetChanged();


                    String un = sharedPreferences.getString("useremail", "");

                    //get user id
                    uid = pdb.getuserid(un);
                    //get total count added cart product and pass to interface listener
<<<<<<< HEAD
                    count = pdb.getCartCount(uid);
                    Log.d(TAG, "akshays : " + count);
//                    mUpadteProductDBListener.counttotalcartproduct(count);
=======
                    int  count = pdb.getCartCount(uid);
                    Log.d("TAGA", "akshay : " + count);
                    mUpadteProductDBListener.counttotalcartproduct(count);
>>>>>>> 423a543f2739d3a3e5a879d30f5a49690f4db523



                    viewHolder.addtocart.setEnabled(false);
                    viewHolder.addtocart.setFocusable(false);
<<<<<<< HEAD
=======
                    /*viewHolder.addtocart.setText(null);*/
>>>>>>> 423a543f2739d3a3e5a879d30f5a49690f4db523

                }
            });


        }


        notifyDataSetChanged();
        return view;
    }

    class ViewHolder {

        private TextView name;
        private TextView cost;
        private Button addtocart;

    }

    public void setUpadteProductDBListener(UpdateDatabaseListener upadteProductDB) {

        this.mUpadteProductDBListener = upadteProductDB;
    }

}
