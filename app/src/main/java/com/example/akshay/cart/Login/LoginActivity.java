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
import com.example.akshay.cart.InputValidation.InputValidation;
import com.example.akshay.cart.AddData.AddProduct;
import com.example.akshay.cart.Activity.ProductDispaly;
import com.example.akshay.cart.R;
import com.example.akshay.cart.Registration.RegisterActivity;
import com.example.akshay.cart.Session.Session;


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

            //if email and password true go to product page
            Intent intent = new Intent(activity, ProductDispaly.class);
            startActivity(intent);

        } else {
            Snackbar.make(nestedScrollView, getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }


    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }
}