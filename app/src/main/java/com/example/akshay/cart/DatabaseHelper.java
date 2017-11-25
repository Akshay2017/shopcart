package com.example.akshay.cart;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;

/**
 * Created by delaroy on 3/27/17.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "database";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "Shopping.db";


    private static final String TABLE_PRODUCTS = "products";
    private static final String TABLE_CART = "cart";


    public static final String PID = "pid";
    public static final String PNAME = "pname";
    public static final String PQUNTITY = "pquntity";
    public static final String PPRICE = "pprice";


    public static final String CID = "cid";
    public static final String CUID = "cuid";
    public static final String CPID = "cpid";
    public static final String CPQUNTITY = "cpquntity";

    private static final String TABLE_USER = "user";

    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_FULL_NAME = "user_full_name";
    private static final String COLUMN_USER_USERNAME = "user_name";
    private static final String COLUMN_USER_PASSWORD = "user_password";

    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_FULL_NAME + " TEXT NOT NULL,"
            + COLUMN_USER_USERNAME + " TEXT NOT NULL UNIQUE," + COLUMN_USER_PASSWORD + " TEXT" + ")";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;


    private String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
            + PID + " INTEGER PRIMARY KEY AUTOINCREMENT," + PNAME + " TEXT,"
            + PQUNTITY + " INTEGER," + PPRICE + " INTEGER" + ")";

    private String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART + "("
            + CID + " INTEGER PRIMARY KEY AUTOINCREMENT," + CUID + " INTEGER," + CPID + " INTEGER,"
            + CPQUNTITY + " INTEGER DEFAULT 0" + ")";


    private String DROP_PRODUCT_TABLES = "DROP TABLE IF EXISTS " + TABLE_PRODUCTS;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_PRODUCT_TABLE);
        db.execSQL(CREATE_CART_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_PRODUCT_TABLES);
        db.execSQL(CREATE_CART_TABLE);
        onCreate(db);
    }

    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_FULL_NAME, user.getFullname());
        values.put(COLUMN_USER_USERNAME, user.getUsername());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        db.insert(TABLE_USER, null, values);
        db.close();
    }

    public boolean checkUser(String username) {
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USER_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;
    }


    public int getuserid(String username) {
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USER_USERNAME + " = ?";
        String[] selectionArgs = {username};

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null,
                null);
        if (cursor != null)
            cursor.moveToFirst();

        int cursorCount = cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID));
        cursor.close();
        return cursorCount;

    }

    public boolean checkUser(String username, String password) {
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USER_USERNAME + " = ?" + " AND " + COLUMN_USER_PASSWORD + " =?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public boolean insertdata(ProductModel productModel) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(PNAME, productModel.getPname());
        contentValues.put(PQUNTITY, productModel.getPquentity());
        contentValues.put(PPRICE, productModel.getPprice());

        long result = database.insert(TABLE_PRODUCTS, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }


    public ArrayList<ProductModel> getAllProduct() {
        ArrayList<ProductModel> productList = new ArrayList<>();
        //Select all Query
        String selectQuery = "SELECT * FROM " + TABLE_PRODUCTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        while (cursor != null && cursor.moveToNext()) {
            ProductModel product = new ProductModel();
            product.setPid(cursor.getString(cursor.getColumnIndex(PID)));
            product.setPname(cursor.getString(cursor.getColumnIndex(PNAME)));
            product.setPquentity(cursor.getString(cursor.getColumnIndex(PQUNTITY)));
            product.setPprice(cursor.getString(cursor.getColumnIndex(PPRICE)));

            productList.add(product);
        }


        cursor.close();
        db.close();

        return productList;
    }


    public ArrayList<CartModel> getAllCARTProduct(int userId) {
        ArrayList<CartModel> cartList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_CART
                + " JOIN " + TABLE_PRODUCTS
                + " ON " + CPID + " = " + PID + " JOIN " + TABLE_USER
                + " ON " + COLUMN_USER_ID + " = " + CUID + " WHERE " + CUID + " = " + userId;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor != null && cursor.moveToNext()) {
            do {
                CartModel cart = new CartModel();

                cart.setcId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(CID))));
                cart.setpId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(CPID))));
                cart.setQunatity(cursor.getInt(cursor.getColumnIndex(CPQUNTITY)));
                ProductModel productModel = new ProductModel();
                productModel.setPprice(cursor.getString(cursor.getColumnIndex(PPRICE)));
                productModel.setPname(cursor.getString(cursor.getColumnIndex(PNAME)));
                productModel.setPquentity(cursor.getString(cursor.getColumnIndex(PQUNTITY)));
                cart.setProductModel(productModel);

                cartList.add(cart);
            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();
        return cartList;

    }


    public boolean insertcart(CartModel cartModel) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(CUID, cartModel.getUid());
        contentValues.put(CPID, cartModel.getpId());
        contentValues.put(CPQUNTITY, cartModel.getQunatity());


        long result = database.insert(TABLE_CART, null, contentValues);

        if (result == -1)
            return false;
        else
            return true;
    }


    public int updateData(ProductModel productModel) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        Log.d(TAG, "database :  " + productModel.getPquentity());

        contentValues.put(PQUNTITY, productModel.getPquentity());


        int datas = database.update(TABLE_PRODUCTS, contentValues, "pid = ?", new String[]{String.valueOf(productModel.getPid())});

        return datas;
    }

    public int incrementData(int quntity, int id) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        Log.d(TAG, "database :  " + quntity);

        contentValues.put(CPQUNTITY, quntity);


        int datas = database.update(TABLE_CART, contentValues, "cpid = ?", new String[]{String.valueOf(id)});

        return datas;
    }

    public int incrementProductData(int quntity, int id) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        Log.d(TAG, "database :  " + quntity);

        contentValues.put(PQUNTITY, quntity);


        int datas = database.update(TABLE_PRODUCTS, contentValues, "pid = ?", new String[]{String.valueOf(id)});

        return datas;
    }


    public int decrimentProductData(int quntity, int id) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        Log.d(TAG, "database :  " + quntity);

        contentValues.put(PQUNTITY, quntity);


        int datas = database.update(TABLE_PRODUCTS, contentValues, "pid = ?", new String[]{String.valueOf(id)});

        return datas;
    }

    public int decrimentData(int quntity, int id) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        Log.d(TAG, "database :  " + quntity);

        contentValues.put(CPQUNTITY, quntity);


        int datas = database.update(TABLE_CART, contentValues, "cpid = ?", new String[]{String.valueOf(id)});

        return datas;
    }


    public ArrayList<CartModel> getCartDetails() {
        ArrayList<CartModel> cartList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_CART;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToNext()) {
            do {
                CartModel cart = new CartModel();
                cart.setpId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(CPID))));
                cart.setQunatity(cursor.getInt(cursor.getColumnIndex(CPQUNTITY)));
                cartList.add(cart);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return cartList;

    }

    public int getCartCount(int userId) {
        String countQuery = "SELECT  * FROM " + TABLE_CART + " WHERE " + CUID + " = " + userId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    public Integer deletcartrow(int id){

        SQLiteDatabase database=this.getWritableDatabase();

        return database.delete(TABLE_CART,"cid = ?",new String[] {String.valueOf(id)});


    }


}
