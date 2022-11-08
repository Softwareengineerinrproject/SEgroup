package com.example.covid19brainstromers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DashBoardActivity extends AppCompatActivity {
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
    }
    class Profileclick implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {


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

        }
    }

    class CovidCertificateclick implements View.OnClickListener
    {

        @Override
        public void onClick(View v)
        {

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