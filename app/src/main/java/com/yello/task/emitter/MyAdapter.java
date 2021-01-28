package com.yello.task.emitter;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.flatdialoglibrary.dialog.FlatDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<JSONObject> AllUsers;
    Context context;

    // Constructor Adapter initialization
    public MyAdapter(Context myContext, ArrayList<JSONObject> userObjects) {
        AllUsers = userObjects;
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
        TextView userName, userMail, userPhone, companyName, address;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName_tv);
            userMail = itemView.findViewById(R.id.useMail_tv);
            userPhone = itemView.findViewById(R.id.userPhone_tv);
            companyName = itemView.findViewById(R.id.companyName_tv);
            address = itemView.findViewById(R.id.address_tv);

        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            holder.userName.setText(context.getResources().getString(R.string.name) +
                    AllUsers.get(position).getString("name"));
            holder.userMail.setText(context.getResources().getString(R.string.email) +
                    AllUsers.get(position).getString("email"));
            holder.userPhone.setText(context.getResources().getString(R.string.phone) +
                    AllUsers.get(position).getString("phone"));
            holder.companyName.setText(context.getResources().getString(R.string.company) +
                    AllUsers.get(position).getJSONObject("company").getString("name"));
            holder.address.setText(context.getResources().getString(R.string.address) +
                    AllUsers.get(position).getJSONObject("address").getString("street") +
                    ", " + AllUsers.get(position).getJSONObject("address").getString("city"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        FlatDialog flatDialog = new FlatDialog(context);

        holder.itemView.setOnClickListener(v -> {
            try {
                String name = AllUsers.get(position).getString("name");
                flatDialog.setTitle("send User (" +  name + ") to MiddleMan App")
                        .setFirstButtonText("Yes")
                    .withFirstButtonListner(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setAction("com.yello.task.middleman");
                            intent.putExtra("com.yello.task.middleman" , AllUsers.get(position).toString());
                            intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
//                            intent.setComponent(new ComponentName("com.yello.task.middleman","com.yello.task.middleman.UserReceiver"));
                            context.sendBroadcast(intent);
                            flatDialog.dismiss();
                        }
                    })
                        .setSecondButtonText("CANCEL")
                        .withSecondButtonListner(view -> flatDialog.dismiss())
                        .isCancelable(true)
                        .show();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public int getItemCount() {
        return AllUsers.size();
    }



}
