package com.yello.task.emitter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ArrayAdapter;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MyTag";
    RequestQueue queue;

    String[] allUsers_names = new String[10];
    String[] allUsers_phones= new String[10];
    String[] allUsers_mails= new String[10];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        MyAdapter adapter;

// Start the request queue with the Api url
        final String url = getResources().getString(R.string.Api_url);
        queue = Volley.newRequestQueue(this);

        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("response", response.length() + "");

//                allUsers_names = new String[response.length()];
//                allUsers_phones = new String[response.length()];
//                allUsers_mails = new String[response.length()];

                    for (int i = 0; i < response.length() - 1; i++) {
                        if (response.length() > 0) {
                            try {
                                String name = response.getJSONObject(i).getString("name");
                                allUsers_names[i] = name;
                                Log.i("response name", name);

                                String phone = response.getJSONObject(i).getString("phone");
                                allUsers_phones[i] = phone;
                                String email = response.getJSONObject(i).getString("email");
                                allUsers_mails[i] = email;

                            } catch (JSONException e) {
                                e.printStackTrace();
//                            allUsers_names[i] = "Not Available";
//                            allUsers_phones[i] = "Not Available";
//                            allUsers_mails[i] = "Not Available";
                            }
                        }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(request).setTag(TAG);
        Log.i("request", request.toString());


        // Sending reference and data to Adapter
        adapter = new MyAdapter(MainActivity.this, allUsers_names, allUsers_mails, allUsers_phones);

        Log.i("names list" , Arrays.toString(allUsers_names) +"");

        // Setting Adapter to RecyclerView
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // SetOnRefreshListener on SwipeRefreshLayout
        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (queue != null) queue.cancelAll(TAG);

    }
}

