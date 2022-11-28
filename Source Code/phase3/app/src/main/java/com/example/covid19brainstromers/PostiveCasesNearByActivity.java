package com.example.covid19brainstromers;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PostiveCasesNearByActivity extends AppCompatActivity {
    ListView listview1;

    LocationManager locationManager ;
    String location="" ;
    Geocoder geocoder;
    String provider;
    Location location1=null;

    ArrayList<String> al=null,destal=null,dupal=null;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postive_cases_near_by);

        listview1=(ListView)findViewById(R.id.listview1);
        listview1.setOnItemClickListener(new Listclick());
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());

        new ShowLocations().execute();
    }
    class Listclick implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> arg0, View arg1, int position,
                                long arg3) {

            String s[]=destal.get(position).split("#");
            String add="http://maps.google.com/?saddr="+GlobalVariables.latitude+","+GlobalVariables.longitude+"&daddr="+s[0]+","+s[1];

            Intent ii=new Intent(Intent.ACTION_VIEW, Uri.parse(add));
            startActivity(ii);
        }
    }
    class ShowLocations extends AsyncTask<String, String, String>
    {

        private ProgressDialog pDialog = new ProgressDialog(PostiveCasesNearByActivity.this);
        String recs="";
        private static final String TAG_SUCCESS = "success";
        int success;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog.setMessage("loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            try {

                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("username",GlobalVariables.username));
                JSONObject json = new JSONParser().makeHttpRequest(GlobalVariables.baseurl+"GetCovidPatientsLocations.php", "GET", params);
                Log.d("Create Response", json.toString());

                success = json.getInt(TAG_SUCCESS);
                if (success == 1)
                {
                    recs=json.getString("recs");
                }

            } catch (JSONException e) {
                //e.printStackTrace();
                //Toast.makeText(PositiveCasesNearByActivity.this, "Error"+e.toString(), Toast.LENGTH_LONG).show();
            }

            return null;
        }


        protected void onPostExecute(String file_url) {
            pDialog.dismiss();
            Intent i=null;
            if (success == 1)
            {
                al=new ArrayList<String>();
                destal=new ArrayList<String>();
                dupal = new ArrayList<String>();

                String s[]=recs.split("@@");
                for(String row : s)
                {
                    if(row.length()>1)
                    {
                        String ss[]=row.split("#");
                        double dist=getDistance(ss[1], ss[2]);
                        String address="Location: ";

                        try {
                            List<Address> listAddresses = geocoder.getFromLocation(Double.parseDouble(ss[1]), Double.parseDouble(ss[2]), 1);
                            if(null!=listAddresses&&listAddresses.size()>0){
                                location = listAddresses.get(0).getAddressLine(0);
                                address+=location.toString();
                            }
                        } catch (IOException e) {
                             Toast.makeText(PostiveCasesNearByActivity.this, "Location not found" , Toast.LENGTH_LONG).show();
                        }


                        al.add("\n USERNAME : "+ss[0]+"\nAddress :"+address+"\nDISTANCE :"+df2.format(dist)+" mts\nLATITUDE: "+ss[1]+"\nLONGITUDE: "+ss[2]);
                        destal.add(ss[1]+"#"+ss[2]);


                    }
                }

               ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(PostiveCasesNearByActivity.this, android.R.layout.simple_list_item_1, al);
                listview1.setAdapter(arrayAdapter);




            }
            else
            {

                Toast.makeText(PostiveCasesNearByActivity.this, "No data found",Toast.LENGTH_LONG).show();
            }
        }
    }

    public double getDistance(String latitude,String longitude)
    {

        Location startPoint=new Location("locationA");
        startPoint.setLatitude(Double.parseDouble(latitude));
        startPoint.setLongitude(Double.parseDouble(longitude));

        Location endPoint=new Location("locationA");
        endPoint.setLatitude(Double.parseDouble(GlobalVariables.latitude));
        endPoint.setLongitude(Double.parseDouble(GlobalVariables.longitude));

        double distance=startPoint.distanceTo(endPoint);
        return distance;
    }
}