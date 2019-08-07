package com.example.priyanka_dash.ecomapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.priyanka_dash.ecomapp.helpers.InputValidation;
import com.example.priyanka_dash.ecomapp.sql.DatabaseHelper;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView changeSignupModeTextView;
    Boolean signupModeActive = true;
    final EditText username = (EditText) findViewById(R.id.editTextEmail);
    final EditText password = (EditText) findViewById(R.id.editTextPassword);
    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeSignupModeTextView = (TextView) findViewById(R.id.changeSigupModeTextView);
        changeSignupModeTextView.setText((CharSequence) this);
        initObjects();
    }

    private void initObjects() {
        //databaseHelper = new DatabaseHelper(activity);
        //inputValidation = new InputValidation(activity);
    }

    @Override
    public void onClick(View v) {
        Button loginBtn= (Button) findViewById(R.id.buttonLogin);
        if(v.getId() == R.id.changeSigupModeTextView){
            if(signupModeActive){
                signupModeActive=false;
                loginBtn.setText("or, Login");
            } else {
                signupModeActive = true;
                changeSignupModeTextView.setText("or, SignUp");
            }
        }
    }
}
