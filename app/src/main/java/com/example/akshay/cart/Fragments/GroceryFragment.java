package com.example.akshay.cart.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.akshay.cart.Activity.ProductDispaly;
import com.example.akshay.cart.Adapter.ProductAdapter;
import com.example.akshay.cart.DatabaseHelper.DatabaseHelper;
import com.example.akshay.cart.Model.ProductModel;
import com.example.akshay.cart.R;

import java.util.ArrayList;
import java.util.List;

public class GroceryFragment extends Fragment {
    ListView listView;
    ArrayList<ProductModel> arrayList;
    DatabaseHelper databaseHelper;
    ProductAdapter productAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_item_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        list();
    }

    private void initView() {
        listView = getView().findViewById(R.id.fragmentlist);
        arrayList = new ArrayList<>();
        databaseHelper = new DatabaseHelper(getActivity());
        sharedPreferences = getActivity().getSharedPreferences("MyPref", 0);
        editor = sharedPreferences.edit();
    }

    private void list() {
        String un = sharedPreferences.getString("useremail", "");
        int uid = databaseHelper.getuserid(un);
        String category="grocery";
        arrayList = databaseHelper.getAllProduct(category,uid);
        productAdapter = new ProductAdapter(getActivity(), arrayList, uid);
        listView.setAdapter(productAdapter);

    }

}
