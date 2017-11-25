package com.example.akshay.cart.Model;

import com.example.akshay.cart.Model.ProductModel;

/**
 * Created by Akshay on 11/15/2017.
 */

public class CartModel {
    int cId;
    int uid;
    int pId;
    int qunatity;
    ProductModel productModel;

    public CartModel() {

    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getcId() {
        return cId;
    }

    public void setcId(int cId) {
        this.cId = cId;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public int getQunatity() {
        return qunatity;
    }

    public void setQunatity(int qunatity) {
        this.qunatity = qunatity;
    }

    public ProductModel getProductModel() {
        return productModel;
    }

    public void setProductModel(ProductModel productModel) {
        this.productModel = productModel;
    }
}
