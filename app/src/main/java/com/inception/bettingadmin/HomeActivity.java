package com.inception.bettingadmin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.inception.bettingadmin.fragments.AllUsers;
import com.inception.bettingadmin.fragments.ShowFragments;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity {


    private ActionBarDrawerToggle toggle;

    DrawerLayout drawerLayout;
    String savedname, savedid;
    FragmentManager fm;
    TextView message;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    open_home();
                    return true;
                case R.id.navigation_refresh:

                    return true;
                case R.id.navigation_user:

                    open_users();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().setTitle("Betting Admin");

        fm = getSupportFragmentManager();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        SharedPreferences sp = getSharedPreferences("user_info", MODE_PRIVATE);
        savedname = sp.getString("username", "");
        savedid = sp.getString("distributor_id", "");
        message = findViewById(R.id.message_txt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        toggle.setDrawerIndicatorEnabled(true);
        drawerLayout.setDrawerListener(toggle);

        open_home();
        get_message();
    }

    private void get_message() {
        final JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("module", "main_message");
            jsonObject.put("name", savedname);
            jsonObject.put("id", savedid);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(jsonObject);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url.ip, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                System.out.println(response);

                try {
                    JSONObject ob = response.getJSONObject("result");
                    message.setText(ob.getString("message"));
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

        Volley.newRequestQueue(HomeActivity.this).add(jsonObjectRequest);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(Gravity.START)) {
                drawerLayout.closeDrawer(Gravity.START);
            } else {
                drawerLayout.openDrawer(Gravity.START);
            }
        }

        return true;
    }

    private void open_home() {
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.main_frame, new ShowFragments());

        ft.commit();
    }

    private void open_users() {

        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.main_frame, new AllUsers());

        ft.commit();

    }

    public void logout(View view) {

        SharedPreferences.Editor sp = getSharedPreferences("user_info", MODE_PRIVATE).edit();

        sp.clear();
        sp.commit();

        startActivity(new Intent(HomeActivity.this, LoginActivity.class));

        finish();
    }

    public void open_users(View view) {

        drawerLayout.closeDrawer(Gravity.START);

        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.main_frame, new AllUsers());

        ft.commit();

    }

    public void open_home(View view) {

        drawerLayout.closeDrawer(Gravity.START);

        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.main_frame, new ShowFragments());

        ft.commit();

    }

    public void open_markets(View view) {


        drawerLayout.closeDrawer(Gravity.START);

        startActivity(new Intent(HomeActivity.this, Markets.class));


    }

    public void messages(View view) {
        drawerLayout.closeDrawer(Gravity.START);

        startActivity(new Intent(HomeActivity.this, messages.class));
    }

    public void onResume() {
        super.onResume();

        get_message();
    }

    public void open_settings(View view) {

        drawerLayout.closeDrawer(Gravity.START);

        startActivity(new Intent(HomeActivity.this, SettingsActivity.class));

    }
}
