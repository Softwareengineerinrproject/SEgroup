package com.example.covid19brainstromers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

import java.util.List;


import org.apache.http.NameValuePair;

import org.apache.http.message.BasicNameValuePair;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;

import android.app.ProgressDialog;

import android.content.Intent;

import android.util.Log;

import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.EditText;

import android.widget.Toast;

import android.widget.*;
public class UserRegistrationActivity extends AppCompatActivity {
    Button registerbtn;
    EditText name;
    Spinner gender;
    EditText age;
   EditText mobile;
    EditText username;
    EditText password;
    EditText confirmpassword;

    String name_1="";
    String gender_1="";
    String age_1="";
    String mobile_1="";
    String username_1="";
    String password_1="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        name=(EditText)findViewById(R.id.name);
        gender=(Spinner) findViewById(R.id.gender);
        age=(EditText)findViewById(R.id.age);
        mobile=(EditText)findViewById(R.id.mobile);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        confirmpassword=(EditText)findViewById(R.id.confirmpassword);
        ArrayList<String> al=new ArrayList<String>();
        al.add("Male");
        al.add("Female");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, al);
        gender.setAdapter(adapter);
        registerbtn=(Button)findViewById(R.id.submit);
        registerbtn.setOnClickListener(new NewRegistration());
    }

    class NewRegistration implements View.OnClickListener {
        @Override
        public void onClick(View v) {


            if (name.getText().toString().length() == 0) {
                Toast.makeText(getApplicationContext(), "Fill The Field name", Toast.LENGTH_LONG).show();
                return;
            }



            if (age.getText().toString().length() == 0) {
                Toast.makeText(getApplicationContext(), "Fill The Field age", Toast.LENGTH_LONG).show();
                return;
            }

            if (mobile.getText().toString().length() == 0) {
                Toast.makeText(getApplicationContext(), "Fill The Field mobile", Toast.LENGTH_LONG).show();
                return;
            }

            if (username.getText().toString().length() == 0) {
                Toast.makeText(getApplicationContext(), "Fill The Field username", Toast.LENGTH_LONG).show();
                return;
            }

            if (password.getText().toString().length() == 0) {
                Toast.makeText(getApplicationContext(), "Fill The Field password", Toast.LENGTH_LONG).show();
                return;
            }

            if (!password.getText().toString().equals(confirmpassword.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Passwords not match", Toast.LENGTH_LONG).show();
                return;
            }

            name_1 = name.getText().toString();
            if (name_1.length() == 0) {
                Toast.makeText(UserRegistrationActivity.this, "Fill name", Toast.LENGTH_LONG).show();
                return;
            }
            gender_1 = gender.getSelectedItem().toString();
            if (gender_1.length() == 0) {
                Toast.makeText(UserRegistrationActivity.this, "Fill gender", Toast.LENGTH_LONG).show();
                return;
            }
            age_1 = age.getText().toString();
            if (age_1.length() == 0) {
                Toast.makeText(UserRegistrationActivity.this, "Fill age", Toast.LENGTH_LONG).show();
                return;
            }
            mobile_1 = mobile.getText().toString();
            if (mobile_1.length() == 0) {
                Toast.makeText(UserRegistrationActivity.this, "Fill mobile", Toast.LENGTH_LONG).show();
                return;
            }

            if(mobile_1.length()!=10)
            {
                Toast.makeText(UserRegistrationActivity.this,"Invalid mobile number",Toast.LENGTH_LONG).show();
                return;
            }

            username_1 = username.getText().toString();
            if (username_1.length() == 0) {
                Toast.makeText(UserRegistrationActivity.this, "Fill username", Toast.LENGTH_LONG).show();
                return;
            }
            password_1 = password.getText().toString();
            if (password_1.length() == 0) {
                Toast.makeText(UserRegistrationActivity.this, "Fill password", Toast.LENGTH_LONG).show();
                return;
            }

            if (password_1.length() < 6) {
                Toast.makeText(UserRegistrationActivity.this, "Password length >=6", Toast.LENGTH_LONG).show();
                return;
            }


            if(!password_1.equals(confirmpassword.getText().toString()))
            {
                Toast.makeText(UserRegistrationActivity.this,"Passwords not match",Toast.LENGTH_LONG).show();
                return;
            }

            name.setText("");

            age.setText("");
            mobile.setText("");
            username.setText("");
            password.setText("");
            new Registration().execute();

        }
    }

        class Registration extends AsyncTask<String, String, String>
        {

            private ProgressDialog pDialog = new ProgressDialog(UserRegistrationActivity.this);
            private static final String TAG_SUCCESS = "success";
            int success=0;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pDialog.setMessage("wait...");
                pDialog.setIndeterminate(false);
                pDialog.setCancelable(true);
                pDialog.show();
            }

            protected String doInBackground(String... args) {
                try {
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    params.add(new BasicNameValuePair("name",name_1));
                    params.add(new BasicNameValuePair("gender",gender_1));
                    params.add(new BasicNameValuePair("age",age_1));
                    params.add(new BasicNameValuePair("mobile",mobile_1));
                    params.add(new BasicNameValuePair("username",username_1));
                    params.add(new BasicNameValuePair("password",password_1));
                    JSONObject json = new JSONParser().makeHttpRequest(GlobalVariables.baseurl+"UserRegistrationActivity.php", "GET", params);


                    Log.d("Create Response", json.toString());



                    success = json.getInt(TAG_SUCCESS);
                }
                catch (JSONException e)
                {

                    Toast.makeText(UserRegistrationActivity.this, "Error"+e.toString(), Toast.LENGTH_LONG).show();
                }
                return null;
            }


            protected void onPostExecute(String file_url) {
               pDialog.dismiss();
              if (success == 1)
                {
                    Toast.makeText(UserRegistrationActivity.this,"Registration success.!",Toast.LENGTH_LONG).show();
                    Intent i=new Intent(UserRegistrationActivity.this,HomeActivity.class);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Toast.makeText(UserRegistrationActivity.this,"Registration Failed.  Try Again!",Toast.LENGTH_LONG).show();
                }
            }
        }
    }