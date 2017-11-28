package com.example.akshay.cart.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.example.akshay.cart.Model.CartModel;
import com.example.akshay.cart.Model.ProductModel;
import com.example.akshay.cart.Model.User;

import java.util.ArrayList;

/**
 * Created by delaroy on 3/27/17.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "database";

    //Database version
    private static final int DATABASE_VERSION = 1;

    // Database name
    private static final String DATABASE_NAME = "Shopping.db";

    // Table names
    private static final String TABLE_USER = "user";
    private static final String TABLE_PRODUCTS = "products";
    private static final String TABLE_CART = "cart";

    // user table column
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";


    // product table column
    public static final String PID = "pid";
    public static final String PNAME = "pname";
    public static final String PQUNTITY = "pquntity";
    public static final String PPRICE = "pprice";


    // card table column
    public static final String CID = "cid";
    public static final String CUID = "cuid";
    public static final String CPID = "cpid";
    public static final String CPQUNTITY = "cpquntity";



    // create user table query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT NOT NULL,"
            + COLUMN_USER_EMAIL + " TEXT NOT NULL UNIQUE," + COLUMN_USER_PASSWORD + " TEXT" + ")";


    // create product table query
    private String CREATE_PRODUCT_TABLE = "CREATE TABLE " + TABLE_PRODUCTS + "("
            + PID + " INTEGER PRIMARY KEY AUTOINCREMENT," + PNAME + " TEXT,"
            + PQUNTITY + " INTEGER," + PPRICE + " INTEGER" + ")";

    // create cart table query
    private String CREATE_CART_TABLE = "CREATE TABLE " + TABLE_CART + "("
            + CID + " INTEGER PRIMARY KEY AUTOINCREMENT," + CUID + " INTEGER," + CPID + " INTEGER,"
            + CPQUNTITY + " INTEGER DEFAULT 0" + ")";


    //drop tables query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;
    private String DROP_PRODUCT_TABLES = "DROP TABLE IF EXISTS " + TABLE_PRODUCTS;
    private String DROP_CART_TABLE = "DROP TABLE IF EXISTS " + TABLE_CART;

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
        db.execSQL(DROP_CART_TABLE);
        onCreate(db);
    }


    // adding user into user database (Registration Activity)
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getUsername());
        values.put(COLUMN_USER_EMAIL, user.getUsername());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        db.insert(TABLE_USER, null, values);
        db.close();
    }


    // checking email if Email Already Exists by passing user name in parameter (Registration time)
    public boolean checkUser(String username) {
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?";
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


    // geting user id in login time from further use passing email in parameter(Login Activity)
    public int getuserid(String useremail) {
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?";
        String[] selectionArgs = {useremail};

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

    // cheking user email and passsword Loging time to login (Login Page)
    public boolean checkUser(String useremail, String password) {
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " =?";
        String[] selectionArgs = {useremail, password};
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

    //Adding product data into product table(AddProduct Activity)
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

    // getting all product to diaply in listview (Productt Activity)
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
            product.setPquentity(cursor.getInt(cursor.getColumnIndex(PQUNTITY)));
            product.setPprice(cursor.getInt(cursor.getColumnIndex(PPRICE)));

            productList.add(product);
        }


        cursor.close();
        db.close();

        return productList;
    }


    // getting all cart data to diaply in listview by joining tabales becuse we only disply the product name , price and cart quntity where product id and cart product id equal  and where user id (loggined user id) (cart Activity)
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
                productModel.setPprice(cursor.getInt(cursor.getColumnIndex(PPRICE)));
                productModel.setPname(cursor.getString(cursor.getColumnIndex(PNAME)));
                productModel.setPquentity(cursor.getInt(cursor.getColumnIndex(PQUNTITY)));
                cart.setProductModel(productModel);

                cartList.add(cart);
            } while (cursor.moveToNext());
        }


        cursor.close();
        db.close();
        return cartList;

    }

    //Adding cart data into cart table when click Add To Cart button (in product adater)
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

   // Upadating product quntity column  when some(only 1) quntity add to cart table
    public int updateData(ProductModel productModel) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        Log.d(TAG, "database :  " + productModel.getPquentity());

        contentValues.put(PQUNTITY, productModel.getPquentity());


        int datas = database.update(TABLE_PRODUCTS, contentValues, "pid = ?", new String[]{String.valueOf(productModel.getPid())});

        return datas;
    }

    // Increment cart quntity data when click to plue button
    public int incrementData(int quntity, int id) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        Log.d(TAG, "database :  " + quntity);

        contentValues.put(CPQUNTITY, quntity);


        int datas = database.update(TABLE_CART, contentValues, "cpid = ?", new String[]{String.valueOf(id)});

        return datas;
    }

    //Inccrement product quntity data when click to minus button
    public int incrementProductData(int quntity, int id) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        Log.d(TAG, "database :  " + quntity);

        contentValues.put(PQUNTITY, quntity);


        int datas = database.update(TABLE_PRODUCTS, contentValues, "pid = ?", new String[]{String.valueOf(id)});

        return datas;
    }


    //Decremnet product quntity data when click to plue button
    public int decrimentProductData(int quntity, int id) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        Log.d(TAG, "database :  " + quntity);

        contentValues.put(PQUNTITY, quntity);


        int datas = database.update(TABLE_PRODUCTS, contentValues, "pid = ?", new String[]{String.valueOf(id)});

        return datas;
    }

    //Decremnet cart quntity data when click to minus button
    public int decrimentData(int quntity, int id) {

        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        Log.d(TAG, "database :  " + quntity);

        contentValues.put(CPQUNTITY, quntity);


        int datas = database.update(TABLE_CART, contentValues, "cpid = ?", new String[]{String.valueOf(id)});

        return datas;
    }


    //get all cart data
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

    //total cart row
    public int getCartCount(int userId) {
        String countQuery = "SELECT  * FROM " + TABLE_CART + " WHERE " + CUID + " = " + userId;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    //Deleting specific row from cart by passing cart id parameter
    public Integer deletcartrow(int id){

        SQLiteDatabase database=this.getWritableDatabase();

        return database.delete(TABLE_CART,"cid = ?",new String[] {String.valueOf(id)});


    }

}
