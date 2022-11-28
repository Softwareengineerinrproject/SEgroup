package com.example.covid19brainstromers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DashBoardActivity extends AppCompatActivity implements LocationListener {
    Button Profile;
    Button TestResult;
    Button NearbyPositivecases;
    Button Precautions;
    Button Helpline;
    Button donate;
    Button CovidCases;
    Button VaccinationCentres;
    Button CovidCertificate;
    Button Logout;

    String provider;
    String latitude = "0.0", longitude = "0.0";
    LocationManager locationManager;


    Location location = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Profile=(Button)findViewById(R.id.Profile);
        Profile.setOnClickListener(new Profileclick ());
        TestResult=(Button)findViewById(R.id.TestResult);
        TestResult.setOnClickListener(new TestResultclick ());
        NearbyPositivecases=(Button)findViewById(R.id.NearbyPositivecases);
        NearbyPositivecases.setOnClickListener(new NearbyPositivecasesclick ());
        Precautions=(Button)findViewById(R.id.Precautions);
        Precautions.setOnClickListener(new Precautionsclick ());
        Helpline=(Button)findViewById(R.id.Helpline);
        Helpline.setOnClickListener(new Helplineclick ());
        donate=(Button)findViewById(R.id.Donate);
        donate.setOnClickListener(new DonateClick());
        CovidCases=(Button)findViewById(R.id.CovidCases);
        CovidCases.setOnClickListener(new CovidCasesclick ());
        VaccinationCentres=(Button)findViewById(R.id.VaccinationCentres);
        VaccinationCentres.setOnClickListener(new VaccinationCentresclick ());
        CovidCertificate=(Button)findViewById(R.id.CovidCertificate);
        CovidCertificate.setOnClickListener(new CovidCertificateclick ());
        Logout=(Button)findViewById(R.id.Logout);
        Logout.setOnClickListener(new Logoutclick ());


        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        Criteria criteria = new Criteria();


        provider = locationManager.getBestProvider(criteria, false);
        try {
            if (provider != null && !provider.equals("")) {
                location = locationManager.getLastKnownLocation(provider);
                locationManager.requestLocationUpdates(provider, 3000, 1, DashBoardActivity.this);

                if(location!=null)
                    onLocationChanged(location);
                else
                    Toast.makeText(getBaseContext(), "Location not found", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(getBaseContext(), "Provider not found", Toast.LENGTH_SHORT).show();
            }

        }catch(Exception eee)
        {

        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("Latitude", "changing location");

        latitude=""+location.getLatitude();
        longitude=""+location.getLongitude();

        GlobalVariables.latitude=latitude;
        GlobalVariables.longitude=longitude;

    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude", "disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude", "enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude", "status");
    }




    class Profileclick implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {

            Intent i=new Intent(DashBoardActivity.this, UserProfileActivity.class);
            startActivity(i);
        }
    }

    class TestResultclick implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            Intent i=new Intent(DashBoardActivity.this, HealthServeyActivity.class);
            startActivity(i);

        }
    }

    class NearbyPositivecasesclick implements View.OnClickListener
    {
      @Override
        public void onClick(View v)
        {
            Intent i=new Intent(DashBoardActivity.this, PostiveCasesNearByActivity.class);
            startActivity(i);
       }
    }

    class Precautionsclick implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            Intent i=new Intent(DashBoardActivity.this, PrecautionsActivity.class);
            startActivity(i);
        }
    }

    class Helplineclick implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            Intent i=new Intent(DashBoardActivity.this, HelpLineActivity.class);
            startActivity(i);
        }
    }

    class CovidCasesclick implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            Intent i=new Intent(DashBoardActivity.this, CovidCasesRealTimeActivity.class);
            startActivity(i);
        }
    }

    class DonateClick implements View.OnClickListener
    {
        @Override
        public void onClick(View v)
        {
            Intent i=new Intent(DashBoardActivity.this, VolunteerActivity.class);
            startActivity(i);
        }
    }

    class VaccinationCentresclick implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            Intent i=new Intent(DashBoardActivity.this, VaccinationCentresActivity.class);
            startActivity(i);

        }
    }

    class CovidCertificateclick implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {
            Intent i=new Intent(DashBoardActivity.this,VaccineCertificateActivity.class);
            startActivity(i);
        }
    }

    class Logoutclick implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {

            Intent i=new Intent(DashBoardActivity.this,HomeActivity.class);
            startActivity(i);
        }
    }
}