package com.example.akshay.cart.Model;

/**
 * Created by Akshay on 11/15/2017.
 */

public class ProductModel {
    String pid;
    String pname;
    String pquentity;
    String pprice;

    public String getPprice() {
        return pprice;
    }

    public void setPprice(String pprice) {
        this.pprice = pprice;
    }

    private boolean isAdded;

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPquentity() {
        return pquentity;
    }

    public void setPquentity(String pquentity) {
        this.pquentity = pquentity;
    }
}
