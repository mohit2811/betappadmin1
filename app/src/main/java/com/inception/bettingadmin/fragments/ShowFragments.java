package com.inception.bettingadmin.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.inception.bettingadmin.CreateUser;
import com.inception.bettingadmin.MatchOddDetails;
import com.inception.bettingadmin.R;
import com.inception.bettingadmin.url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShowFragments extends Fragment {

    private JSONArray jsonArray;
    private static ArrayList<String> blocked_id;
    private RecyclerView recyclerView;


    String saved_id, saved_name;

    public ShowFragments() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_show_fragments, container, false);


        recyclerView = v.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        blocked_id = new ArrayList<>();
        SharedPreferences sp = getActivity().getSharedPreferences("user_info", MODE_PRIVATE);
        saved_id = sp.getString("distributor_id", "");
        saved_name = sp.getString("username", "");
        block_detail();
        get_data();

        return v;
    }


    private void get_data() {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "https://www.lotusbook.com/api/exchange/eventType/4", new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);

                try {
                    jsonArray = response.getJSONArray("result");


                    Log.i("adapter to recycler --", "");
                    Adapter adapter = new Adapter();
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 2, 2));

        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);


    }

    public void block_detail() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("module", "block_detail");
            jsonObject.put("dis_id", saved_id);
            jsonObject.put("dis_name", saved_name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(jsonObject);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url.ip, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);

                try {

                    JSONArray event = response.getJSONArray("result");
                    blocked_id.clear();
                    for (int i = 0; i < event.length(); i++) {
                        blocked_id.add(event.getJSONObject(i).getString("event_id"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println(error);


            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 2, 2));

        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);
    }

    private class Adapter extends RecyclerView.Adapter<view_holder> {


        @Override
        public view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new view_holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.betting_cell, parent, false));
        }

        @Override
        public void onBindViewHolder(final view_holder holder, int position) {

            try {
                final JSONObject jsonObject = jsonArray.getJSONObject(position).getJSONObject("event");

                holder.match_vs.setText(jsonObject.getString("name"));
                holder.date_time.setText(getDate(jsonObject.getString("openDate")));
                if (blocked_id.contains(jsonObject.getString("id"))) {
                    holder.block_status.setText("SHOW");
                    holder.match_vs.setOnClickListener(null);
                    holder.cell_layout.setAlpha((float) 0.7);
                    holder.block_status.setBackgroundColor(Color.GREEN);
                } else {
                    holder.block_status.setText("BLOCK");
                    holder.cell_layout.setAlpha(1);
                    holder.match_vs.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                Intent i = new Intent(getContext(), MatchOddDetails.class);
                                i.putExtra("event_id", jsonObject.getString("id"));
                                i.putExtra("open_date", jsonObject.getString("openDate"));
                                i.putExtra("match_name" , jsonObject.getString("name"));
                                startActivity(i);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    holder.block_status.setBackgroundColor(Color.RED);
                }
                holder.block_status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (holder.block_status.getText().equals("SHOW")) {
                            holder.block_status.setText("BLOCK");
                            holder.cell_layout.setAlpha(1);
                            holder.match_vs.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        Intent i = new Intent(getContext(), MatchOddDetails.class);
                                        i.putExtra("event_id", jsonObject.getString("id"));
                                        i.putExtra("open_date", jsonObject.getString("openDate"));
                                        i.putExtra("match_name" , jsonObject.getString("name"));

                                        startActivity(i);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                            holder.block_status.setBackgroundColor(Color.RED);
                            try {
                                unblock_bet(jsonObject.getString("id"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            ;

                        } else {
                            holder.block_status.setText("SHOW");
                            holder.cell_layout.setAlpha((float) 0.7);
                            holder.block_status.setBackgroundColor(Color.GREEN);
                            holder.match_vs.setOnClickListener(null);

                            try {
                                block_bet(jsonObject.getString("id"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public int getItemCount() {
            return jsonArray.length();
        }
    }

    public void block_bet(String event_id) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("module", "block_bet");
            jsonObject.put("event_id", event_id);
            jsonObject.put("dis_id", saved_id);
            jsonObject.put("dis_name", saved_name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(jsonObject);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url.ip, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);

                try {

                    if (response.getString("result").equals("done")) {


                        Toast.makeText(getActivity(), "bet blocked", Toast.LENGTH_SHORT).show();


                    } else {

                        Toast.makeText(getActivity(), "error try again", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println(error);


            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 2, 2));

        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);
    }

    public void unblock_bet(String event_id) {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("module", "unblock_bet");
            jsonObject.put("event_id", event_id);
            jsonObject.put("dis_id", saved_id);
            jsonObject.put("dis_name", saved_name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(jsonObject);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url.ip, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);

                try {

                    if (response.getString("result").equals("done")) {


                        Toast.makeText(getActivity(), "bet unblocked", Toast.LENGTH_SHORT).show();


                    } else {

                        Toast.makeText(getActivity(), "error try again", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                System.out.println(error);


            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(20000, 2, 2));

        Volley.newRequestQueue(getActivity()).add(jsonObjectRequest);
    }

    private class view_holder extends RecyclerView.ViewHolder {
        TextView match_vs, date_time, open_status, block_status;
        RelativeLayout cell_layout;

        public view_holder(View itemView) {
            super(itemView);
            cell_layout = itemView.findViewById(R.id.cell_layout);
            match_vs = itemView.findViewById(R.id.match_vs);
            block_status = itemView.findViewById(R.id.block_status);
            date_time = itemView.findViewById(R.id.date_time);
            open_status = itemView.findViewById(R.id.open_status);

        }
    }

    private String getDate(String OurDate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.000Z'");
            formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date value = formatter.parse(OurDate);

            SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM, dd, yyyy, hh:mm:ss aa"); //this format changeable
            dateFormatter.setTimeZone(TimeZone.getDefault());
            OurDate = dateFormatter.format(value);

            //Log.d("OurDate", OurDate);
        } catch (Exception e) {
            OurDate = "00-00-0000 00:00";
        }
        return OurDate;
    }

    @Override
    public void onResume() {
        super.onResume();

        block_detail();

        get_data();
    }
}
