package com.example.covid19brainstromers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {
    Button registerbtn;
    EditText name;
    Spinner gender;
    EditText age;
    EditText mobile;
    EditText username;
    EditText password;


    String name_1="";
    String gender_1="";
    String age_1="";
    String mobile_1="";
    String username_1="";
    String password_1="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        name=(EditText)findViewById(R.id.name);
        gender=(Spinner) findViewById(R.id.gender);
        age=(EditText)findViewById(R.id.age);
        mobile=(EditText)findViewById(R.id.mobile);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        gender=(Spinner)findViewById(R.id.gender);
        ArrayList<String> al=new ArrayList<String>();
        al.add("Male");
        al.add("Female");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, al);
        gender.setAdapter(adapter);
        registerbtn=(Button)findViewById(R.id.submit);
        registerbtn.setOnClickListener(new UpdateUser());
        new GetProfile().execute();
    }
    ////////////////////
    class GetProfile extends AsyncTask<String, String, String>
    {

        private ProgressDialog pDialog = new ProgressDialog(UserProfileActivity.this);
        private static final String TAG_SUCCESS = "success";
        int success=0;
        String recs="";

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
                params.add(new BasicNameValuePair("username",GlobalVariables.username));
                JSONObject json = new JSONParser().makeHttpRequest(GlobalVariables.baseurl+"GetUser.php", "GET", params);


                Log.d("Create Response", json.toString());



                success = json.getInt(TAG_SUCCESS);
                if(success==1)
                {
                    recs=json.getString("recs");
                }
            }
            catch (JSONException e)
            {
                //e.printStackTrace();
                //Toast.makeText(UserProfileActivity.this, "Error"+e.toString(), Toast.LENGTH_LONG).show();
            }
            return null;
        }

        protected void onPostExecute(String file_url)
        {
            pDialog.dismiss();


            if (success == 1)
            {

                boolean found=false;
                try
                {
                    ArrayList<String> myArraySpinner =new ArrayList<String>();
                    String s[]=recs.split("~");
                       if(s.length>1)
                        {


                            username.setText(s[0]);
                            name.setText(s[1]);
                            myArraySpinner.add(s[2]);
                            age.setText(s[3]);
                            //gender.setText(s[4]);
                           // myArraySpinner.add(s[4]);

                            mobile.setText(s[4]);
                            password.setText(s[5]);

                        }


                    if(myArraySpinner.contains("Male")){
                        myArraySpinner.add("Female");
                    }
                    else
                    {
                        myArraySpinner.add("Male");
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(UserProfileActivity.this, android.R.layout.simple_dropdown_item_1line, myArraySpinner);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    gender.setAdapter(adapter);


                }
                catch(Exception eee)
                {
                    Toast.makeText(UserProfileActivity.this,"Error : "+eee,Toast.LENGTH_LONG).show();
                }

            }
            else
            {
                Toast.makeText(UserProfileActivity.this,"Profile Not Found",Toast.LENGTH_LONG).show();
            }
        }
    }








    ///////////////

    class UpdateUser implements View.OnClickListener {
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


            name_1 = name.getText().toString();
            if (name_1.length() == 0) {
                Toast.makeText(UserProfileActivity.this, "Fill name", Toast.LENGTH_LONG).show();
                return;
            }
            gender_1 = gender.getSelectedItem().toString();
            if (gender_1.length() == 0) {
                Toast.makeText(UserProfileActivity.this, "Fill gender", Toast.LENGTH_LONG).show();
                return;
            }
            age_1 = age.getText().toString();
            if (age_1.length() == 0) {
                Toast.makeText(UserProfileActivity.this, "Fill age", Toast.LENGTH_LONG).show();
                return;
            }
            mobile_1 = mobile.getText().toString();
            if (mobile_1.length() == 0) {
                Toast.makeText(UserProfileActivity.this, "Fill mobile", Toast.LENGTH_LONG).show();
                return;
            }

            if(mobile_1.length()!=10)
            {
                Toast.makeText(UserProfileActivity.this,"Invalid mobile number",Toast.LENGTH_LONG).show();
                return;
            }

            username_1 = username.getText().toString();
            if (username_1.length() == 0) {
                Toast.makeText(UserProfileActivity.this, "Fill username", Toast.LENGTH_LONG).show();
                return;
            }
            password_1 = password.getText().toString();
            if (password_1.length() == 0) {
                Toast.makeText(UserProfileActivity.this, "Fill password", Toast.LENGTH_LONG).show();
                return;
            }

            if (password_1.length() < 6) {
                Toast.makeText(UserProfileActivity.this, "Password length >=6", Toast.LENGTH_LONG).show();
                return;
            }



            name.setText("");

            age.setText("");
            mobile.setText("");
            username.setText("");
            password.setText("");
            new UpdateUser1().execute();

        }
    }

    class UpdateUser1 extends AsyncTask<String, String, String>
    {

        private ProgressDialog pDialog = new ProgressDialog(UserProfileActivity.this);
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
                JSONObject json = new JSONParser().makeHttpRequest(GlobalVariables.baseurl+"" +
                        ".php", "GET", params);


                Log.d("Create Response", json.toString());



                success = json.getInt(TAG_SUCCESS);
            }
            catch (JSONException e)
            {

                Toast.makeText(UserProfileActivity.this, "Error"+e.toString(), Toast.LENGTH_LONG).show();
            }
            return null;
        }


        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            if (success == 1)
            {
                Toast.makeText(UserProfileActivity.this,"Updation success.!",Toast.LENGTH_LONG).show();
                Intent i=new Intent(UserProfileActivity.this,HomeActivity.class);
                startActivity(i);
                finish();
            }
            else
            {
                Toast.makeText(UserProfileActivity.this,"Updation Failed.  Try Again!",Toast.LENGTH_LONG).show();
            }
        }
    }
}