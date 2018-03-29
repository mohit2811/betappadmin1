package com.inception.bettingadmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Distributor1Market extends AppCompatActivity {


    private TextView  team_1_m , team_2_m , all_total_team_1 , all_total_team_2 , market_status_heading;


    private JSONArray market_status_array ;

    private RecyclerView market_status_recycler ;

    private RequestQueue requestQueue ;

    private int total_team_1 , total_team_2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distributor1_market);

        requestQueue = Volley.newRequestQueue(Distributor1Market.this);

        market_status_heading = findViewById(R.id.market_status);

        market_status_heading.setText("Market status of level 2 distributors under "+getIntent().getStringExtra("username"));


        getSupportActionBar().setTitle(getIntent().getStringExtra("match_name"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        team_1_m = findViewById(R.id.team_1_m);
        team_2_m = findViewById(R.id.team_2_m);

        market_status_recycler = findViewById(R.id.match_market_recycler);
        market_status_recycler.setLayoutManager(new LinearLayoutManager(Distributor1Market.this , LinearLayoutManager.VERTICAL , false));

        market_status_recycler.setNestedScrollingEnabled(false);

        all_total_team_1 = findViewById(R.id.all_total_team_1);

        all_total_team_2 = findViewById(R.id.all_total_team_2);

        team_1_m.setText(getIntent().getStringExtra("team1"));
        team_2_m.setText(getIntent().getStringExtra("team2"));

        get_market_status();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }

        return true;
    }


    private void get_market_status()
    {
        JSONObject jsonObject = new JSONObject();


        try {
            jsonObject.put("event_id" , getIntent().getStringExtra("event_id"));
            jsonObject.put("dis_id" , getIntent().getIntExtra("dis_id" , 0));
            jsonObject.put("module" , "market status of distributor1");
            jsonObject.put("team1" , getIntent().getStringExtra("team1"));
            jsonObject.put("team2" , getIntent().getStringExtra("team2"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.println(jsonObject);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url.ip, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {



                System.out.println(response);

                try {
                    market_status_array = response.getJSONArray("result");

                    total_team_1 = response.getInt("total_team_1");

                    total_team_2 = response.getInt("total_team_2");

                    Adapter adapter = new Adapter();




                    market_status_recycler.setAdapter(adapter);

                    all_total_team_1.setText(String.valueOf(total_team_1));
                    all_total_team_2.setText(String.valueOf(total_team_2));

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

    private class view_holder extends RecyclerView.ViewHolder
    {

        private TextView username , team_1_total , team_2_total;

        public view_holder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.username);

            team_1_total = itemView.findViewById(R.id.team_1_total);

            team_2_total = itemView.findViewById(R.id.team_2_total);

        }
    }


    private  class Adapter extends RecyclerView.Adapter<view_holder>
    {

        @Override
        public view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new view_holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.market_status_cell , parent , false));
        }

        @Override
        public void onBindViewHolder(view_holder holder, int position) {

            try {
                final JSONObject jsonObject = market_status_array.getJSONObject(position);

                holder.username.setText(jsonObject.getString("username"));
                holder.team_1_total.setText(jsonObject.getString(getIntent().getStringExtra("team1")));
                holder.team_2_total.setText(jsonObject.getString(getIntent().getStringExtra("team2")));

                holder.username.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(Distributor1Market.this , Distributor2Market.class);

                        i.putExtra("event_id" , getIntent().getStringExtra("event_id"));
                        i.putExtra("team1" , team_1_m.getText().toString());
                        i.putExtra("team2" , team_2_m.getText().toString());
                        i.putExtra("match_name" , getIntent().getStringExtra("match_name"));
                        try {
                            i.putExtra("username" , jsonObject.getString("username"));
                            i.putExtra("dis_id" , jsonObject.getInt("id"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        startActivity(i);

                    }
                });

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public int getItemCount() {
            return market_status_array.length();
        }
    }


}
