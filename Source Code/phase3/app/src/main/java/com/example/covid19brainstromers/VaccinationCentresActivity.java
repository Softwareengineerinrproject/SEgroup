package com.example.covid19brainstromers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.*;
import android.view.ViewGroup;
import android.view.LayoutInflater;
public class VaccinationCentresActivity extends AppCompatActivity {
    ListView listview1;
    ArrayList<String> destlst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination_centres);
        listview1=(ListView)findViewById(R.id.listview1);

        new ShowVaccinationCentres().execute();

    }

    class ShowVaccinationCentres extends AsyncTask<String, String, String>
    {

        private ProgressDialog pDialog = new ProgressDialog(VaccinationCentresActivity.this);
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
                JSONObject json = new JSONParser().makeHttpRequest(GlobalVariables.baseurl+"GetVaccinatonCentres.php", "GET", params);


                Log.d("Create Response", json.toString());


                success = json.getInt(TAG_SUCCESS);
                if(success==1)
                {
                    recs=json.getString("recs");
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
                Toast.makeText(VaccinationCentresActivity.this, "Error"+e.toString(), Toast.LENGTH_LONG).show();
            }
            return null;
        }

        protected void onPostExecute(String file_url)
        {
            //dismiss the dialog once done
            pDialog.dismiss();


            if (success == 1)
            {
                ArrayList<String> al=new ArrayList<String>();

                boolean found=false;
                try
                {
                    String rows[]=recs.split("@@");
                    for(String row: rows)
                    {
                        if(row.length()>1)
                        {
                            String s[]=row.split("~");
                            String str="State : "+s[0]+"\n"+"City : "+s[1]+"\n"+"Vaccinationcenter : "+s[2]+"\n"+"Mobiles : "+s[3];
                            al.add(str);

                        }
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(VaccinationCentresActivity.this,android.R.layout.simple_list_item_1, al);
                    listview1.setAdapter(arrayAdapter);

                    if(found==false)
                        Toast.makeText(VaccinationCentresActivity.this,"Data Not Found",Toast.LENGTH_LONG).show();

                }
                catch(Exception eee)
                {
                    Toast.makeText(VaccinationCentresActivity.this,"Error : "+eee,Toast.LENGTH_LONG).show();
                }

            }
            else
            {
                Toast.makeText(VaccinationCentresActivity.this,"Data Not Found",Toast.LENGTH_LONG).show();
            }
        }
    }

}