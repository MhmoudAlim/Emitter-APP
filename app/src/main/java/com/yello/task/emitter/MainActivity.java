package com.yello.task.emitter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonArrayRequest;

import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MyTag";
    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        MyAdapter adapter;

// Start the request queue with the Api url
        final String url = getResources().getString(R.string.Api_url);
        queue = Volley.newRequestQueue(this);

        ArrayList<User> AllUsers = new ArrayList<>();
        ArrayList<JSONObject> userObjects = new ArrayList<>();


        JsonArrayRequest request = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("response", response.length() + "");

                    for (int i = 0; i < response.length() - 1; i++) {
                        if (response.length() > 0) {
                            try {

                                JSONObject user = response.getJSONObject(i);
                                userObjects.add(user);

                                String name = user.getString("name");
                                String phone = user.getString("phone");
                                String email = user.getString("email");
                                int id = user.getInt("id");
                                String username = user.getString("username");
                                String website = user.getString("website");

                                AllUsers.add(new User( id,  name,  username,  email,  phone,  website));




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
        adapter = new MyAdapter(MainActivity.this, AllUsers, userObjects);


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
//        if (queue != null) queue.cancelAll(TAG);

    }
}

