package com.example.akshay.cart.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.akshay.cart.Adapter.ProductAdapter;
import com.example.akshay.cart.DatabaseHelper.DatabaseHelper;
import com.example.akshay.cart.Model.ProductModel;
import com.example.akshay.cart.R;

import java.util.ArrayList;


public class SportsFragment extends Fragment {

    ListView listView;
    ArrayList<ProductModel> arrayList;
    DatabaseHelper databaseHelper;
    ProductAdapter productAdapter;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank_fragment2, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        list();
    }

    private void initView() {
        listView = getView().findViewById(R.id.fragmentlist2);
        arrayList = new ArrayList<>();
        databaseHelper = new DatabaseHelper(getActivity());
        sharedPreferences = getActivity().getSharedPreferences("MyPref", 0);
        editor = sharedPreferences.edit();
    }

    private void list() {
        String un = sharedPreferences.getString("useremail", "");
        int uid = databaseHelper.getuserid(un);
        String category="sports";
        arrayList = databaseHelper.getAllProduct(category,uid);
        productAdapter = new ProductAdapter(getActivity(), arrayList, uid);
        listView.setAdapter(productAdapter);

    }

}
