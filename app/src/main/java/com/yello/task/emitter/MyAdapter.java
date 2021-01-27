package com.yello.task.emitter;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<JSONObject> AlluserObjects;
    Context context;

    // Constructor for initialization
    public MyAdapter(Context myContext,  ArrayList<JSONObject> userObjects) {
        AlluserObjects = userObjects;
        context = myContext;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(view);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView userName, userMail, userPhone;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName_tv);
            userMail = itemView.findViewById(R.id.useMail_tv);
            userPhone = itemView.findViewById(R.id.userPhone_tv);

        }
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        try {
            holder.userName.setText(AlluserObjects.get(position).getString("name"));
            holder.userPhone.setText(AlluserObjects.get(position).getString("email"));
            holder.userMail.setText(AlluserObjects.get(position).getString("phone"));

        } catch (JSONException e) {
            e.printStackTrace();
        }



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("s", "item  : " + AlluserObjects.get(position)  + " clicked");

            }
        });
    }

    @Override
    public int getItemCount() {
        return AlluserObjects.size();
    }

}
