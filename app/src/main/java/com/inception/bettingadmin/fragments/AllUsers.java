package com.inception.bettingadmin.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.inception.bettingadmin.CreateUser;
import com.inception.bettingadmin.R;
import com.inception.bettingadmin.UserDetails;
import com.inception.bettingadmin.url;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllUsers extends Fragment {

    TextView create_user,bal_txt;

    JSONArray jsonArray;

    ProgressDialog progress;

    RecyclerView recyclerView;
    String saved_id;

    public AllUsers() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_all_users, container, false);

        recyclerView = v.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        create_user = v.findViewById(R.id.create_user);
        bal_txt=v.findViewById(R.id.bal_txt);
        progress = new ProgressDialog(getActivity());
        progress.setTitle("Loading");
        create_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), CreateUser.class));
            }
        });

        get_users();
        get_total();
        return v;
    }


    private void get_users() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("module", "get_user");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url.ip, jsonObject, new Response.Listener<JSONObject>() {
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
    private void get_total() {
        final JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("module", "total_balance");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.print(jsonObject);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url.ip, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);

                try {

                    bal_txt.setText(response.getString("total"));

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
    private class Adapter extends RecyclerView.Adapter<view_holder> {
        String usernamee;

        @Override
        public view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new view_holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_cell, parent, false));

        }

        @Override
        public void onBindViewHolder(final view_holder holder, int position) {

            try {
                final JSONObject jsonObject = jsonArray.getJSONObject(position);

                holder.sr_num.setText(jsonObject.getString("id"));
                holder.username.setText(jsonObject.getString("username"));
                usernamee = holder.username.getText().toString();
                if (jsonObject.getString("status").equals("1")) {
                    holder.isactive.setImageDrawable(getResources().getDrawable(R.drawable.active_user));

                } else {
                    holder.isactive.setImageDrawable(getResources().getDrawable(R.drawable.block_user));

                }

                holder.balance.setText(jsonObject.getString("bal"));

                holder.username.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(getContext(), UserDetails.class);

                        try {
                            i.putExtra("name", jsonObject.getString("username"));
                            i.putExtra("status", jsonObject.getString("status"));
                            i.putExtra("balance", jsonObject.getString("bal"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivity(i);

                    }
                });
                holder.active_status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {

                            progress.show();
                            block(holder.isactive, jsonObject.getString("username"));

                        } catch (Exception e) {
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

    public void block(View view, String user) {
        final ImageView active_dot = (ImageView) view;
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("module", "block_user");
            jsonObject.put("username", user);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.print(jsonObject);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url.ip, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);


                    progress.hide();

                    get_users();



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

        TextView sr_num, username, balance;
        LinearLayout active_status;
        ImageView isactive;

        public view_holder(View itemView) {
            super(itemView);

            sr_num = itemView.findViewById(R.id.sr_num);
            isactive = itemView.findViewById(R.id.isActive);
            username = itemView.findViewById(R.id.username_);
            active_status = itemView.findViewById(R.id.active_status);
            balance = itemView.findViewById(R.id.balance_);

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        get_users();
    }

}
