package com.inception.bettingadmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateUser extends AppCompatActivity {

    private EditText username_et , password_et , limit_et ;
    String saved_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        getSupportActionBar().setTitle("Create User");

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sp = getSharedPreferences("user_info" , MODE_PRIVATE);
        saved_id = sp.getString("distributor_id","");
        username_et = findViewById(R.id.username_et);
        password_et = findViewById(R.id.password_et);
        limit_et = findViewById(R.id.limit_et);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }

        return true;
    }

    private Boolean validate_inputs()
    {
        if(username_et.getText().toString().equals(""))
        {
            return false;
        }

        else if(password_et.getText().toString().equals(""))
        {
            return false;
        }

        else if(limit_et.getText().toString().equals("")) {

            return false;
        }

        else {

            return true;
        }
    }

    private void create_user()
    {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("module", "create_user");
            jsonObject.put("username" , username_et.getText().toString());
            jsonObject.put("password" , password_et.getText().toString());
            jsonObject.put("limit" , limit_et.getText().toString());
            jsonObject.put("dis_id" , saved_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url.ip, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);

                try {

                    if (response.getString("result").equals("done")) {

                        password_et.setText("");
                        username_et.setText("");
                        limit_et.setText("");

                        Toast.makeText(CreateUser.this , "user created successfully" , Toast.LENGTH_SHORT).show();


                    } else {

                        Toast.makeText(CreateUser.this , "error try again" , Toast.LENGTH_SHORT).show();
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

        Volley.newRequestQueue(CreateUser.this).add(jsonObjectRequest);
    }

    public void create_(View view)
    {
        if(validate_inputs())
        {
            create_user();
        }
        else {
            Toast.makeText(CreateUser.this , "invalid data" , Toast.LENGTH_SHORT).show();

        }


    }
}
