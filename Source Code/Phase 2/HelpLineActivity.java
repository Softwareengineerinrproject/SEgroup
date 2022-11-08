package com.example.covid19brainstromers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
import android.os.AsyncTask;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class HelpLineActivity extends AppCompatActivity {
    ListView listview1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_line);
        listview1=(ListView)findViewById(R.id.listview1);
        new ShowHelpline().execute();
    }


class ShowHelpline extends AsyncTask<String, String, String>
{

    private ProgressDialog pDialog = new ProgressDialog(HelpLineActivity.this);
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
            JSONObject json = new JSONParser().makeHttpRequest(GlobalVariables.baseurl+"HelpLineActivity.php", "GET", params);


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
            //Toast.makeText(HelpLineActivity.this, "Error"+e.toString(), Toast.LENGTH_LONG).show();
        }
        return null;
    }


    protected void onPostExecute(String file_url)
    {

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
                        String str="State : "+s[0]+"\n"+"City : "+s[1]+"\n"+"HelpLine Nos : "+s[2]+"\n"+"Hospitals : "+s[3]+"\n"+"Govt. Contacts : "+s[4];
                        al.add(str);


                    }
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(HelpLineActivity.this,android.R.layout.simple_list_item_1, al);
                listview1.setAdapter(arrayAdapter);

                if(found==false)
                    Toast.makeText(HelpLineActivity.this,"Helpline Not Found",Toast.LENGTH_LONG).show();

            }
            catch(Exception eee)
            {
                Toast.makeText(HelpLineActivity.this,"Error : "+eee,Toast.LENGTH_LONG).show();
            }
       }
        else
        {
            Toast.makeText(HelpLineActivity.this,"Helpline Not Found",Toast.LENGTH_LONG).show();
        }
    }
}

}