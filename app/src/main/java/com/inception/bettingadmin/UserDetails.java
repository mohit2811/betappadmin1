package com.inception.bettingadmin;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class UserDetails extends AppCompatActivity implements View.OnClickListener {
    Button block,add50, add100, add500, add1000, add5000, add10000, add50000, minus1000, minus5000, minus10000;
    TextView balance,name;
    EditText change_password;
    String username1,block_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        getSupportActionBar().setTitle("User Details");

        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
change_password=findViewById(R.id.change_password);
        name = findViewById(R.id.name_txt);
        add50 = findViewById(R.id.add50);
        add100 = findViewById(R.id.add100);
        add500 = findViewById(R.id.add500);
        add1000 = findViewById(R.id.add1000);
        add5000 = findViewById(R.id.add5000);
        add10000 = findViewById(R.id.add10000);
        add50000 = findViewById(R.id.add50000);
        minus1000 = findViewById(R.id.minus1000);
        minus5000 = findViewById(R.id.minus5000);
        minus10000 = findViewById(R.id.minus10000);
        add50.setOnClickListener(this);
        add100.setOnClickListener(this);
        add500.setOnClickListener(this);
        add1000.setOnClickListener(this);
        add5000.setOnClickListener(this);
        add10000.setOnClickListener(this);
        add50000.setOnClickListener(this);
        minus1000.setOnClickListener(this);
        minus5000.setOnClickListener(this);
        minus10000.setOnClickListener(this);
        block=findViewById(R.id.block_button);
        username1 = getIntent().getStringExtra("name");
        block_status =getIntent().getStringExtra("status");
        name.setText(username1);
        if (block_status.equals("1"))
        {
            block.setText("Block");
        }
        else
            block.setText("Unblock");

        balance= findViewById(R.id.balance);
        balance.setText(getIntent().getStringExtra("balance"));

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return true;
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.add50:

                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                int bal = Integer.parseInt(balance.getText().toString());
                                bal+=50;
                                balance.setText(String.valueOf(bal));
                                JSONObject jsonObject = new JSONObject();

                                try {
                                    jsonObject.put("module", "add_balance");
                                    jsonObject.put("username" , name.getText().toString());
                                    jsonObject.put("balance" , balance.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url.ip, jsonObject, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {

                                        System.out.println(response);

                                        try {

                                            if (response.getString("result").equals("done")) {



                                                Toast.makeText(UserDetails.this , "Balance added successfully" , Toast.LENGTH_SHORT).show();


                                            } else {

                                                Toast.makeText(UserDetails.this , "error try again" , Toast.LENGTH_SHORT).show();
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

                                Volley.newRequestQueue(UserDetails.this).add(jsonObjectRequest);

                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();
                                break;
                        }

                    }
                };
                AlertDialog.Builder ab = new AlertDialog.Builder(this);
                ab.setMessage("do you want to add 50 balance?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();

            break;
            case R.id.add100:
                DialogInterface.OnClickListener dialogClickListener1 = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                int bal = Integer.parseInt(balance.getText().toString());
                                bal+=100;
                                balance.setText(String.valueOf(bal));
                                JSONObject jsonObject = new JSONObject();

                                try {
                                    jsonObject.put("module", "add_balance");
                                    jsonObject.put("username" , name.getText().toString());
                                    jsonObject.put("balance" , balance.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url.ip, jsonObject, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {

                                        System.out.println(response);

                                        try {

                                            if (response.getString("result").equals("done")) {



                                                Toast.makeText(UserDetails.this , "Balance added successfully" , Toast.LENGTH_SHORT).show();


                                            } else {

                                                Toast.makeText(UserDetails.this , "error try again" , Toast.LENGTH_SHORT).show();
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

                                Volley.newRequestQueue(UserDetails.this).add(jsonObjectRequest);

                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();
                                break;
                        }

                    }
                };
                AlertDialog.Builder ab1 = new AlertDialog.Builder(this);
                ab1.setMessage("Do you want to add 100?").setPositiveButton("Yes", dialogClickListener1)
                        .setNegativeButton("No", dialogClickListener1).show();


                break;
            case R.id.add500:
                DialogInterface.OnClickListener dialogClickListener2 = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                int bal = Integer.parseInt(balance.getText().toString());
                                bal+=500;
                                balance.setText(String.valueOf(bal));
                                JSONObject jsonObject = new JSONObject();

                                try {
                                    jsonObject.put("module", "add_balance");
                                    jsonObject.put("username" , name.getText().toString());
                                    jsonObject.put("balance" , balance.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url.ip, jsonObject, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {

                                        System.out.println(response);

                                        try {

                                            if (response.getString("result").equals("done")) {



                                                Toast.makeText(UserDetails.this , "Balance added successfully" , Toast.LENGTH_SHORT).show();


                                            } else {

                                                Toast.makeText(UserDetails.this , "error try again" , Toast.LENGTH_SHORT).show();
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

                                Volley.newRequestQueue(UserDetails.this).add(jsonObjectRequest);

                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();
                                break;
                        }

                    }
                };
                AlertDialog.Builder ab2 = new AlertDialog.Builder(this);
                ab2.setMessage("Do you want to add 500?").setPositiveButton("Yes", dialogClickListener2)
                        .setNegativeButton("No", dialogClickListener2).show();

                break;
            case R.id.add1000:
                DialogInterface.OnClickListener dialogClickListener3 = new DialogInterface.OnClickListener() {
                   @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                int bal = Integer.parseInt(balance.getText().toString());
                                bal+=1000;
                                balance.setText(String.valueOf(bal));
                                JSONObject jsonObject = new JSONObject();

                                try {
                                    jsonObject.put("module", "add_balance");
                                    jsonObject.put("username" , name.getText().toString());
                                    jsonObject.put("balance" , balance.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url.ip, jsonObject, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {

                                        System.out.println(response);

                                        try {

                                            if (response.getString("result").equals("done")) {



                                                Toast.makeText(UserDetails.this , "Balance added successfully" , Toast.LENGTH_SHORT).show();


                                            } else {

                                                Toast.makeText(UserDetails.this , "error try again" , Toast.LENGTH_SHORT).show();
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

                                Volley.newRequestQueue(UserDetails.this).add(jsonObjectRequest);

                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();
                                break;
                        }

                    }
                };
                AlertDialog.Builder ab3 = new AlertDialog.Builder(this);
                ab3.setMessage("Do you want to add 1000?").setPositiveButton("Yes", dialogClickListener3)
                        .setNegativeButton("No", dialogClickListener3).show();

                break;
            case R.id.add5000:
                DialogInterface.OnClickListener dialogClickListener4 = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                int bal = Integer.parseInt(balance.getText().toString());
                                bal+=5000;
                                balance.setText(String.valueOf(bal));
                                JSONObject jsonObject = new JSONObject();

                                try {
                                    jsonObject.put("module", "add_balance");
                                    jsonObject.put("username" , name.getText().toString());
                                    jsonObject.put("balance" , balance.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url.ip, jsonObject, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {

                                        System.out.println(response);

                                        try {

                                            if (response.getString("result").equals("done")) {



                                                Toast.makeText(UserDetails.this , "Balance added successfully" , Toast.LENGTH_SHORT).show();


                                            } else {

                                                Toast.makeText(UserDetails.this , "error try again" , Toast.LENGTH_SHORT).show();
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

                                Volley.newRequestQueue(UserDetails.this).add(jsonObjectRequest);

                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();
                                break;
                        }

                    }
                };
                AlertDialog.Builder ab4 = new AlertDialog.Builder(this);
                ab4.setMessage("Do you want to add 5000?").setPositiveButton("Yes", dialogClickListener4)
                        .setNegativeButton("No", dialogClickListener4).show();

                break;
            case R.id.add10000:
                DialogInterface.OnClickListener dialogClickListener5 = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                int bal = Integer.parseInt(balance.getText().toString());
                                bal+=10000;
                                balance.setText(String.valueOf(bal));
                                JSONObject jsonObject = new JSONObject();

                                try {
                                    jsonObject.put("module", "add_balance");
                                    jsonObject.put("username" , name.getText().toString());
                                    jsonObject.put("balance" , balance.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url.ip, jsonObject, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {

                                        System.out.println(response);

                                        try {

                                            if (response.getString("result").equals("done")) {



                                                Toast.makeText(UserDetails.this , "Balance added successfully" , Toast.LENGTH_SHORT).show();


                                            } else {

                                                Toast.makeText(UserDetails.this , "error try again" , Toast.LENGTH_SHORT).show();
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

                                Volley.newRequestQueue(UserDetails.this).add(jsonObjectRequest);


                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();
                                break;
                        }

                    }
                };
                AlertDialog.Builder ab5= new AlertDialog.Builder(this);
                ab5.setMessage("Do you want to add 10000?").setPositiveButton("Yes", dialogClickListener5)
                        .setNegativeButton("No", dialogClickListener5).show();

                break;
            case R.id.add50000:
                DialogInterface.OnClickListener dialogClickListener6 = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                int bal = Integer.parseInt(balance.getText().toString());
                                bal+=50000;
                                balance.setText(String.valueOf(bal));
                                JSONObject jsonObject = new JSONObject();

                                try {
                                    jsonObject.put("module", "add_balance");
                                    jsonObject.put("username" , name.getText().toString());
                                    jsonObject.put("balance" , balance.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url.ip, jsonObject, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {

                                        System.out.println(response);

                                        try {

                                            if (response.getString("result").equals("done")) {



                                                Toast.makeText(UserDetails.this , "Balance added successfully" , Toast.LENGTH_SHORT).show();


                                            } else {

                                                Toast.makeText(UserDetails.this , "error try again" , Toast.LENGTH_SHORT).show();
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

                                Volley.newRequestQueue(UserDetails.this).add(jsonObjectRequest);

                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();
                                break;
                        }

                    }
                };
                AlertDialog.Builder ab6 = new AlertDialog.Builder(this);
                ab6.setMessage("Do you want to add 50000?").setPositiveButton("Yes", dialogClickListener6)
                        .setNegativeButton("No", dialogClickListener6).show();

                break;
            case R.id.minus1000:
                DialogInterface.OnClickListener dialogClickListener7 = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                int bal = Integer.parseInt(balance.getText().toString());
                                bal-=1000;
                                balance.setText(String.valueOf(bal));
                                JSONObject jsonObject = new JSONObject();

                                try {
                                    jsonObject.put("module", "add_balance");
                                    jsonObject.put("username" , name.getText().toString());
                                    jsonObject.put("balance" , balance.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url.ip, jsonObject, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {

                                        System.out.println(response);

                                        try {

                                            if (response.getString("result").equals("done")) {



                                                Toast.makeText(UserDetails.this , "Balance added successfully" , Toast.LENGTH_SHORT).show();


                                            } else {

                                                Toast.makeText(UserDetails.this , "error try again" , Toast.LENGTH_SHORT).show();
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

                                Volley.newRequestQueue(UserDetails.this).add(jsonObjectRequest);

                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();
                                break;
                        }

                    }
                };
                AlertDialog.Builder ab7= new AlertDialog.Builder(this);
                ab7.setMessage("Do you want to withdraw 1000?").setPositiveButton("Yes", dialogClickListener7)
                        .setNegativeButton("No", dialogClickListener7).show();

                break;
            case R.id.minus5000:
                DialogInterface.OnClickListener dialogClickListener8 = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                int bal = Integer.parseInt(balance.getText().toString());
                                bal-=5000;
                                balance.setText(String.valueOf(bal));
                                JSONObject jsonObject = new JSONObject();

                                try {
                                    jsonObject.put("module", "add_balance");
                                    jsonObject.put("username" , name.getText().toString());
                                    jsonObject.put("balance" , balance.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url.ip, jsonObject, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {

                                        System.out.println(response);

                                        try {

                                            if (response.getString("result").equals("done")) {



                                                Toast.makeText(UserDetails.this , "Balance added successfully" , Toast.LENGTH_SHORT).show();


                                            } else {

                                                Toast.makeText(UserDetails.this , "error try again" , Toast.LENGTH_SHORT).show();
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

                                Volley.newRequestQueue(UserDetails.this).add(jsonObjectRequest);


                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();
                                break;
                        }

                    }
                };
                AlertDialog.Builder ab8 = new AlertDialog.Builder(this);
                ab8.setMessage("Do you want to withdraw 5000?").setPositiveButton("Yes", dialogClickListener8)
                        .setNegativeButton("No", dialogClickListener8).show();

                break;
            case R.id.minus10000:
                DialogInterface.OnClickListener dialogClickListener9 = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                int bal = Integer.parseInt(balance.getText().toString());
                                bal-=10000;
                                balance.setText(String.valueOf(bal));
                                JSONObject jsonObject = new JSONObject();

                                try {
                                    jsonObject.put("module", "add_balance");
                                    jsonObject.put("username" , name.getText().toString());
                                    jsonObject.put("balance" , balance.getText().toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url.ip, jsonObject, new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {

                                        System.out.println(response);

                                        try {

                                            if (response.getString("result").equals("done")) {



                                                Toast.makeText(UserDetails.this , "Balance added successfully" , Toast.LENGTH_SHORT).show();


                                            } else {

                                                Toast.makeText(UserDetails.this , "error try again" , Toast.LENGTH_SHORT).show();
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

                                Volley.newRequestQueue(UserDetails.this).add(jsonObjectRequest);

                                break;
                            case DialogInterface.BUTTON_NEGATIVE:
                                dialog.dismiss();
                                break;
                        }

                    }
                };
                AlertDialog.Builder ab9 = new AlertDialog.Builder(this);
                ab9.setMessage("do you want to withdraw 10000?").setPositiveButton("Yes", dialogClickListener9)
                        .setNegativeButton("No", dialogClickListener9).show();

                break;
        }
    }

    public void delete(View view) {
        DialogInterface.OnClickListener dialogClickListener9 = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("module", "delete_user");
            jsonObject.put("username" , name.getText().toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url.ip, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);

                try {

                    if (response.getString("result").equals("done")) {



                        Toast.makeText(UserDetails.this , "user deleted successfully" , Toast.LENGTH_SHORT).show();
finish();

                    } else {

                        Toast.makeText(UserDetails.this , "error try again" , Toast.LENGTH_SHORT).show();
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

        Volley.newRequestQueue(UserDetails.this).add(jsonObjectRequest);

        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }

            }
        };
        AlertDialog.Builder ab9 = new AlertDialog.Builder(this);
        ab9.setMessage("do you want to delete user?").setPositiveButton("Yes", dialogClickListener9)
                .setNegativeButton("No", dialogClickListener9).show();

    }
    public void block(View view) {
        DialogInterface.OnClickListener dialogClickListener9 = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        JSONObject jsonObject = new JSONObject();

                        try {
                            jsonObject.put("module", "block_user");
                            jsonObject.put("username" , name.getText().toString());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url.ip, jsonObject, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                System.out.println(response);

                                try {

                                    if (response.getString("result").equals("block_done")) {


                                        Toast.makeText(UserDetails.this, "user blocked", Toast.LENGTH_SHORT).show();
                                        block.setText("Unblock");


                                    }
                                    if(response.getString("result").equals("block_not_done"))
                                    {

                                        Toast.makeText(UserDetails.this, "error try again", Toast.LENGTH_SHORT).show();
                                    }
                                    if (response.getString("result").equals("unblock_done")) {


                                        Toast.makeText(UserDetails.this, "user unblocked", Toast.LENGTH_SHORT).show();
                                        block.setText("Block");


                                    }
                                    if(response.getString("result").equals("unblock_not_done"))
                                    {

                                        Toast.makeText(UserDetails.this, "error try again", Toast.LENGTH_SHORT).show();
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

                        Volley.newRequestQueue(UserDetails.this).add(jsonObjectRequest);
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }

            }
        };
        AlertDialog.Builder ab9 = new AlertDialog.Builder(this);
        ab9.setMessage("Are You sure?").setPositiveButton("Yes", dialogClickListener9)
                .setNegativeButton("No", dialogClickListener9).show();

    }

    public void change_pass(View view) {
        final String pass = change_password.getText().toString();
        if (pass.equals(""))
        {
            Toast.makeText(UserDetails.this, "enter password", Toast.LENGTH_SHORT).show();
        }
        else
        {
            DialogInterface.OnClickListener dialogClickListener9 = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            JSONObject jsonObject = new JSONObject();

                            try {
                                jsonObject.put("module", "change_password");
                                jsonObject.put("username", name.getText().toString());
                                jsonObject.put("password", pass);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url.ip, jsonObject, new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    System.out.println(response);

                                    try {

                                        if (response.getString("result").equals("done")) {


                                            Toast.makeText(UserDetails.this, "password changed successfully", Toast.LENGTH_SHORT).show();
                                            finish();

                                        } else {

                                            Toast.makeText(UserDetails.this, "error try again", Toast.LENGTH_SHORT).show();
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

                            Volley.newRequestQueue(UserDetails.this).add(jsonObjectRequest);

                            break;
                        case DialogInterface.BUTTON_NEGATIVE:
                            dialog.dismiss();
                            break;
                    }

                }
            };
            AlertDialog.Builder ab9 = new AlertDialog.Builder(this);
            ab9.setMessage("Do you want to change password?").setPositiveButton("Yes", dialogClickListener9)
                    .setNegativeButton("No", dialogClickListener9).show();
        }
    }
}
