package com.yello.task.emitter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.util.Consumer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.JsonArrayRequest;

import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class MainActivity<Public> extends AppCompatActivity {
    private static final String TAG = "MyTag";
    private static RequestQueue queue;
    private static String url;
    ExecutorService pool;
    MyAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        // get the ArrayList of users
        getUsers(allUsers -> {
            if (allUsers.size() == 0) {
                // Sending reference and data to Adapter
                adapter = new MyAdapter(MainActivity.this, allUsers);
                // Setting Adapter to RecyclerView
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
            }
        });

        // SetOnRefreshListener on SwipeRefreshLayout
        SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.swipe);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        });

    }

    private void getUsers(Consumer<ArrayList<JSONObject>> usersConsumer) {
        pool = Executors.newFixedThreadPool(2);
        pool.execute(() -> {
            ArrayList<JSONObject> userObjects = new ArrayList<>();
            url = getResources().getString(R.string.Api_url);
            queue = Volley.newRequestQueue(this);
            JsonArrayRequest request = new JsonArrayRequest(url, response -> {
                recyclerView.setVisibility(View.VISIBLE);
                for (int i = 0; i < response.length() - 1; i++) {
                    if (response.length() > 0) {
                        try {
                            JSONObject user = response.getJSONObject(i);
                            userObjects.add(user);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, error -> {

            });
            queue.add(request).setTag(TAG);
            Log.i("request", request.toString());

            usersConsumer.accept(userObjects);
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
//        if (queue != null) queue.cancelAll(TAG);

    }
}

