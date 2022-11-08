package com.example.covid19brainstromers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.*;

public class HomeActivity extends AppCompatActivity {

    Button submit;
    TextView newregtv;
    EditText username;
    EditText password;
    String username_1="";
    String password_1="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        newregtv=(TextView)findViewById(R.id.newregtv);
        newregtv.setOnClickListener(new UserRegistration());


        submit=(Button)findViewById(R.id.submit);
        submit.setOnClickListener(new UserLogin());
    }
    class UserRegistration implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {

            Intent i=new Intent(HomeActivity.this,UserRegistrationActivity.class);
            startActivity(i);

        }

    }
    class UserLogin implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {



            username_1=username.getText().toString();
            if(username_1.length()==0)
            {
                Toast.makeText(HomeActivity.this,"Fill username",Toast.LENGTH_LONG).show();
                return;
            }
            password_1=password.getText().toString();
            if(password_1.length()==0)
            {
                Toast.makeText(HomeActivity.this,"Fill password",Toast.LENGTH_LONG).show();
                return;
            }
            new Login().execute();
        }
    }
    class Login extends AsyncTask<String, String, String>
    {

        private ProgressDialog pDialog = new ProgressDialog(HomeActivity.this);
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
                params.add(new BasicNameValuePair("username",username_1));
                params.add(new BasicNameValuePair("password",password_1));
                JSONObject json = new JSONParser().makeHttpRequest(GlobalVariables.baseurl+"LoginActivity.php", "GET", params);


                Log.d("Create Response", json.toString());

                success = json.getInt("success");
            }
            catch (JSONException e)
            {

                //Toast.makeText(HomeActivity.this, "Error"+e.toString(), Toast.LENGTH_LONG).show();
            }
            return null;
        }
        protected void onPostExecute(String file_url) {
            pDialog.dismiss();


            if (success == 1)
            {
                Intent i=new Intent(HomeActivity.this,DashBoardActivity.class);
                GlobalVariables.username = username_1;
                startActivity(i);
            }
            else
            {
                Toast.makeText(HomeActivity.this,"Login Failed.  Try Again!",Toast.LENGTH_LONG).show();
            }
        }
    }
}