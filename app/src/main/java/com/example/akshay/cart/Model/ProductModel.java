package com.example.akshay.cart.Model;

/**
 * Created by Akshay on 11/15/2017.
 */

public class ProductModel {
    int pid;
    String pname;
    int pprice;
    String category;

    public ProductModel(String name, int price, String category) {
        this.pname=name;
        this.pprice=price;
        this.category=category;
    }

    public ProductModel() {

    }

    public int getPprice() {
        return pprice;
    }

    public void setPprice(int pprice) {
        this.pprice = pprice;
    }

    private boolean isAdded;

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
