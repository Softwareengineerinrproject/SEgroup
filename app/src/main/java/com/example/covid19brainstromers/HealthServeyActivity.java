package com.example.covid19brainstromers;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HealthServeyActivity extends AppCompatActivity {
    EditText age,temperature,blood_pressure,oxygen_levels,critinine_levels;
    Spinner gender,cough,headache;
    Button predictbtn;
    TextView resulttv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_servey);

        resulttv=(TextView) findViewById(R.id.result);
        age=(EditText)findViewById(R.id.userage);
        cough=(Spinner)findViewById(R.id.user_cough);
        headache=(Spinner)findViewById(R.id.user_headache);
        oxygen_levels=(EditText)findViewById(R.id.user_oxygen_levels);

        gender=(Spinner)findViewById(R.id.usergender);
        temperature=(EditText)findViewById(R.id.usertemperature);
        blood_pressure=(EditText)findViewById(R.id.user_blood_pressure);
        critinine_levels=(EditText)findViewById(R.id.user_critinine_levels);

        predictbtn=(Button)findViewById(R.id.predictbtn);
        predictbtn.setOnClickListener(new PredictBtnClick());


    }
    class PredictBtnClick implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {

            if(age.getText().length()==0)
            {
                Toast.makeText(HealthServeyActivity.this,"Enter age",Toast.LENGTH_LONG).show();
                return;
            }

            if(temperature.getText().length()==0)
            {
                Toast.makeText(HealthServeyActivity.this,"Enter temperature",Toast.LENGTH_LONG).show();
                return;
            }

            if(blood_pressure.getText().length()==0)
            {
                Toast.makeText(HealthServeyActivity.this,"Enter blood pressure",Toast.LENGTH_LONG).show();
                return;
            }

            if(oxygen_levels.getText().length()==0)
            {
                Toast.makeText(HealthServeyActivity.this,"Enter oxygen levels",Toast.LENGTH_LONG).show();
                return;
            }

            if(critinine_levels.getText().length()==0)
            {
                Toast.makeText(HealthServeyActivity.this,"Enter critinine levels",Toast.LENGTH_LONG).show();
                return;
            }

            new CovidPredict().execute();

        }
    }

    class CovidPredict extends AsyncTask<String, String, String>
    {

        private ProgressDialog pDialog = new ProgressDialog(HealthServeyActivity.this);

        int success=0;
        String result="";

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
                params.add(new BasicNameValuePair("age",age.getText().toString()));
                params.add(new BasicNameValuePair("temperature",temperature.getText().toString()));
                params.add(new BasicNameValuePair("blood_pressure",blood_pressure.getText().toString()));
                params.add(new BasicNameValuePair("oxygen_levels",oxygen_levels.getText().toString()));
                params.add(new BasicNameValuePair("critinine_levels",critinine_levels.getText().toString()));
                params.add(new BasicNameValuePair("gender",gender.getSelectedItem().toString()));
                params.add(new BasicNameValuePair("cough",cough.getSelectedItem().toString()));
                params.add(new BasicNameValuePair("headache",headache.getSelectedItem().toString()));
                params.add(new BasicNameValuePair("latitude",GlobalVariables.latitude));
                params.add(new BasicNameValuePair("longitude",GlobalVariables.longitude));

                JSONObject json = new JSONParser().makeHttpRequest(GlobalVariables.baseurl+"HealthSurveyActivity.php", "GET", params);
                Log.d("Create Response", json.toString());
               success = json.getInt("success");
                result=json.getString("result");
            }
            catch (JSONException e)
            {
                //e.printStackTrace();
                //Toast.makeText(HealthServeyActivity.this, "Error"+e.toString(), Toast.LENGTH_LONG).show();
            }
            return null;
        }

        protected void onPostExecute(String file_url) {
            pDialog.dismiss();

            resulttv.setText("Result: "+ result);
            if (success == 1)
            {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(HealthServeyActivity.this);
                alertDialog.setPositiveButton("Close", null);

                alertDialog.setMessage("Covid result:  "+ result);
                alertDialog.setTitle("Survey");
                alertDialog.show();

                Intent i=new Intent(HealthServeyActivity.this, PrecautionsActivity.class);
                startActivity(i);


          }
            else
            {
                Toast.makeText(HealthServeyActivity.this,"Survey Failed.",Toast.LENGTH_LONG).show();
            }
        }
    }


}