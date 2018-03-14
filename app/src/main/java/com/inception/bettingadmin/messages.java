package com.inception.bettingadmin;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
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


public class messages extends AppCompatActivity {
    JSONArray jsonArray;
    ProgressDialog progress;
    RecyclerView recyclerView;
    String savedname,savedid;
    TextView add_message;
EditText message_et;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        getSupportActionBar().setTitle("Messages");
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        message_et=findViewById(R.id.msg_et);
        SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
        savedname = sp.getString("username", "");
        savedid = sp.getString("distributor_id", "");
        add_message = findViewById(R.id.add);
        add_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject.put("module", "add_message");
                    jsonObject.put("name" , savedname);
                    jsonObject.put("message" , message_et.getText().toString());
                    jsonObject.put("id" , savedid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println(jsonObject);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url.ipm, jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        System.out.println(response);

                        try {

                            if (response.getString("result").equals("done")) {

                                message_et.setText("");


                                Toast.makeText(messages.this , "message added successfully" , Toast.LENGTH_SHORT).show();


                            } else {

                                Toast.makeText(messages.this , "error try again" , Toast.LENGTH_SHORT).show();
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

                jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(20000 ,2 ,2 ));

                Volley.newRequestQueue(messages.this).add(jsonObjectRequest);

            }
        });

        recyclerView = findViewById(R.id.mesages);
        recyclerView.setLayoutManager(new LinearLayoutManager(messages.this, LinearLayoutManager.VERTICAL, false));
        progress = new ProgressDialog(messages.this);
        progress.setTitle("Loading");
        get_messages();

    }


    private void get_messages() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("module", "show_message");
            jsonObject.put("name", savedname);
            jsonObject.put("id", savedid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(jsonObject);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url.ipm, jsonObject, new Response.Listener<JSONObject>() {
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

        Volley.newRequestQueue(messages.this).add(jsonObjectRequest);
    }
    private class Adapter extends RecyclerView.Adapter<view_holder> {


        @Override
        public view_holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new view_holder(LayoutInflater.from(messages.this).inflate(R.layout.message_cell, parent, false));

        }

        @Override
        public void onBindViewHolder(final view_holder holder, int position) {

            try {
                final JSONObject jsonObject = jsonArray.getJSONObject(position);

                holder.serial_num.setText(jsonObject.getString("id"));
                holder.msgs.setText(jsonObject.getString("message"));

                if (jsonObject.getString("status").equals("1")) {
                    holder.Ative_msg.setImageDrawable(getResources().getDrawable(R.drawable.active_user));

                } else {
                    holder.Ative_msg.setImageDrawable(getResources().getDrawable(R.drawable.block_user));
                }

                holder.active_msg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {

                            progress.show();
                            block(holder.Ative_msg, jsonObject.getString("id"));

                        } catch (Exception e) {
                        }
                    }
                });

                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {

                            progress.show();
                            delete(jsonObject.getString("id"));
                            get_messages();
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

    public void delete(final String msg_id) {
        DialogInterface.OnClickListener dialogClickListener9 = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        JSONObject jsonObject = new JSONObject();

                        try {
                            jsonObject.put("module", "delete_msg");
                            jsonObject.put("id", msg_id);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url.ipm, jsonObject, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                System.out.println(response);

                                try {

                                    if (response.getString("result").equals("done")) {


                                        Toast.makeText(messages.this, "messages deleted successfully", Toast.LENGTH_SHORT).show();
                                        finish();

                                    } else {

                                        Toast.makeText(messages.this, "error try again", Toast.LENGTH_SHORT).show();
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

                        Volley.newRequestQueue(messages.this).add(jsonObjectRequest);

                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }

            }
        };
        AlertDialog.Builder ab9 = new AlertDialog.Builder(this);
        ab9.setMessage("do you want to delete messages?").setPositiveButton("Yes", dialogClickListener9)
                .setNegativeButton("No", dialogClickListener9).show();

    }

    public void block(View view, String id) {
        final ImageView active_dot = (ImageView) view;
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("module", "block_messages");
            jsonObject.put("id", id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        System.out.print(jsonObject);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url.ipm, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);

                try {
                    progress.hide();
                    if (response.getString("result").equals("block_done")) {


                        Toast.makeText(messages.this, "messages blocked", Toast.LENGTH_SHORT).show();
                        active_dot.setImageDrawable(getResources().getDrawable(R.drawable.block_user));
                        progress.hide();
                    }
                    if (response.getString("result").equals("block_not_done")) {

                        Toast.makeText(messages.this, "error try again", Toast.LENGTH_SHORT).show();
                        progress.hide();
                    }
                    if (response.getString("result").equals("unblock_done")) {


                        Toast.makeText(messages.this, "messages unblocked", Toast.LENGTH_SHORT).show();
                        active_dot.setImageDrawable(getResources().getDrawable(R.drawable.active_user));
                        progress.hide();
                    }
                    if (response.getString("result").equals("unblock_not_done")) {

                        Toast.makeText(messages.this, "error try again", Toast.LENGTH_SHORT).show();
                        progress.hide();
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

        Volley.newRequestQueue(messages.this).add(jsonObjectRequest);

    }

    private class view_holder extends RecyclerView.ViewHolder {

        TextView serial_num, msgs, delete;
        LinearLayout active_msg;

        ImageView Ative_msg;

        public view_holder(View itemView) {
            super(itemView);

            serial_num = itemView.findViewById(R.id.serial_num);
            Ative_msg = itemView.findViewById(R.id.Ative_msg);
            msgs = itemView.findViewById(R.id.msgs);
            active_msg = itemView.findViewById(R.id.active_msg);
            delete = itemView.findViewById(R.id.delete_);

        }
    }

    @Override
    public void onResume() {
        super.onResume();

        get_messages();
    }

}
