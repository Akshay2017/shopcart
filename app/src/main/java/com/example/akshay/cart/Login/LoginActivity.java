package com.example.akshay.cart.Login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.akshay.cart.DatabaseHelper.DatabaseHelper;
import com.example.akshay.cart.Fragments.Main2Activity;
import com.example.akshay.cart.InputValidation.InputValidation;
import com.example.akshay.cart.AddData.AddProduct;
import com.example.akshay.cart.Activity.ProductDispaly;
import com.example.akshay.cart.Model.ProductModel;
import com.example.akshay.cart.R;
import com.example.akshay.cart.Registration.RegisterActivity;
import com.example.akshay.cart.Session.Session;

import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;

    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonLogin;
    private AppCompatButton appCompatButtonSignup;


    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    Button b;
    Session session;

    private static final String TAG = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        initListeners();
        initObjects();
    }

    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);

        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);
        appCompatButtonSignup = (AppCompatButton) findViewById(R.id.appCompatButtonSignup);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        b = findViewById(R.id.addddd);

        session = new Session(this);

        if (session.loggedin()) {
            Intent intent = new Intent(activity, ProductDispaly.class);
            startActivity(intent);
        }


    }

    private void initListeners() {
        appCompatButtonLogin.setOnClickListener(this);
        appCompatButtonSignup.setOnClickListener(this);
        b.setOnClickListener(this);
    }

    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonLogin:
                verifyFromSQLite();
                break;
            case R.id.appCompatButtonSignup:
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
            case R.id.addddd:
                Intent in = new Intent(getApplicationContext(), AddProduct.class);
                startActivity(in);
                break;

        }
    }

    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_email))) {
            return;
        }

        if (databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim()
                , textInputEditTextPassword.getText().toString().trim())) {


            //get user id from database to further use (cart table) passing user email
            int uid = databaseHelper.getuserid(textInputEditTextEmail.getText().toString().trim());
            Log.d(TAG, "uid " + uid);


            //storing user email in SharedPreferences to passing any activity fro use
            String username = textInputEditTextEmail.getText().toString().trim();
            editor.putString("useremail", username);
            editor.apply();

            //for loggedin
            session.setLoggedin(true);

            addDataStatic();
            //if email and password true go to product page
            Intent intent = new Intent(activity, ProductDispaly.class);
            startActivity(intent);

        } else {
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }

    public void addDataStatic(){
        List<ProductModel> productModelList=new ArrayList<>();
        productModelList.add( new ProductModel("Air conditioner",200,"electronic"));
        productModelList.add( new ProductModel("Hair dryer",100,"electronic"));
        productModelList.add( new ProductModel("Window fan",350,"electronic"));
        productModelList.add( new ProductModel("Oven",150,"electronic"));
        productModelList.add( new ProductModel("Clothes iron",200,"electronic"));

        productModelList.add( new ProductModel("Vegetable Oil",200,"grocery"));
        productModelList.add( new ProductModel("Shampoo",100,"grocery"));
        productModelList.add( new ProductModel("Air Freshner",350,"grocery"));
        productModelList.add( new ProductModel("Toothbrush",150,"grocery"));
        productModelList.add( new ProductModel("Soap",200,"grocery"));

        productModelList.add( new ProductModel("soccer shoes",200,"sports"));
        productModelList.add( new ProductModel("Flying discs",100,"sports"));
        productModelList.add( new ProductModel("bats",350,"sports"));
        productModelList.add( new ProductModel("Sticks",150,"sports"));
        productModelList.add( new ProductModel("tennis shoes",200,"sports"));

        databaseHelper.insertdata(productModelList);

    }

    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }
}
