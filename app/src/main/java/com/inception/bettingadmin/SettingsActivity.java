package com.inception.bettingadmin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SettingsActivity extends AppCompatActivity {

    private EditText bet_place_interval_et , bet_start_time_et ;

    private RequestQueue requestQueue ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setTitle("Settings");

        requestQueue = Volley.newRequestQueue(SettingsActivity.this);


        bet_place_interval_et = findViewById(R.id.bet_place_interval_et);

        bet_start_time_et = findViewById(R.id.bet_start_et);

        get_initial_times();


    }

    public void bet_place_interval(View view) {

        JSONObject jsonObject = new JSONObject();

        String time = bet_place_interval_et.getText().toString().equals("") ? "0" : bet_place_interval_et.getText().toString();



        try {
            jsonObject.put("module" , "set bet interval");
            jsonObject.put("time_" , time);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url.ip, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.getString("result").equals("done"))
                    {
                        Toast.makeText(SettingsActivity.this , "updated" , Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);


    }

    public void bet_start_time(View view) {

        JSONObject jsonObject = new JSONObject();

        String time = bet_start_time_et.getText().toString().equals("") ? "0" : bet_start_time_et.getText().toString();



        try {
            jsonObject.put("module" , "set bet start time");
            jsonObject.put("time_" , time);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url.ip, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.getString("result").equals("done"))
                    {
                        Toast.makeText(SettingsActivity.this , "updated" , Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);

    }

    public void get_initial_times()
    {
        JSONObject jsonObject = new JSONObject();


        try {
            jsonObject.put("module" , "get bet times");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url.ip, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                   bet_place_interval_et.setText(response.getString("betting_time"));
                   bet_start_time_et.setText(response.getString("betting_start_time"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }

        return true;
    }
}
