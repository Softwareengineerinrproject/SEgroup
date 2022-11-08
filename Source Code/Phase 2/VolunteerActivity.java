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
import android.content.Intent;
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
public class VolunteerActivity extends AppCompatActivity {
    ListView listview1;
    ArrayList<String> destlst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer);
        listview1=(ListView)findViewById(R.id.listview1);
        listview1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                GlobalVariables.link = destlst.get(position);
                Intent i=new Intent(VolunteerActivity.this, WebViewActivity.class);
                startActivity(i);


            }

        });

        new ShowVolunteers().execute();
    }
    class ShowVolunteers extends AsyncTask<String, String, String>
    {

        private ProgressDialog pDialog = new ProgressDialog(VolunteerActivity.this);
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
                JSONObject json = new JSONParser().makeHttpRequest(GlobalVariables.baseurl+"VolunteerActivity.php", "GET", params);


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
                //Toast.makeText(VolunteerActivity.this, "Error"+e.toString(), Toast.LENGTH_LONG).show();
            }
            return null;
        }


        protected void onPostExecute(String file_url)
        {

            pDialog.dismiss();


            if (success == 1)
            {
                ArrayList<String> al=new ArrayList<String>();
                destlst=new ArrayList<String>();

                boolean found=false;
                try
                {
                    String rows[]=recs.split("@@");
                    for(String row: rows)
                    {
                        if(row.length()>1)
                        {
                            String s[]=row.split("~");
                            String str="description : "+s[0]+"\n"+"links : "+s[1];
                            al.add(str);
                            destlst.add(s[1]);

                        }
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(VolunteerActivity.this,android.R.layout.simple_list_item_1, al);
                    listview1.setAdapter(arrayAdapter);

                    if(found==false)
                        Toast.makeText(VolunteerActivity.this,"Volunteers Not Found",Toast.LENGTH_LONG).show();

                }
                catch(Exception eee)
                {
                    Toast.makeText(VolunteerActivity.this,"Error : "+eee,Toast.LENGTH_LONG).show();
                }

            }
            else
            {
                Toast.makeText(VolunteerActivity.this,"Volunteers Not Found",Toast.LENGTH_LONG).show();
            }
        }
    }

}