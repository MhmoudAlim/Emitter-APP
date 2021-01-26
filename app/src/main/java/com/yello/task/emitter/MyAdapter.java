package com.yello.task.emitter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    String[] userNames, userMails, userPhones;
    Context context;

    // Constructor for initialization
    public MyAdapter(Context myContext, String[] names, String[] mails, String[] phones) {
        userNames = names;
        userMails = mails;
        userPhones = phones;
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
        holder.userName.setText(userNames[position]);
        holder.userMail.setText(userMails[position]);
        holder.userPhone.setText(userPhones[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("s", "item  : " + userNames[position]  + " clicked");
            }
        });
    }

    @Override
    public int getItemCount() {
        return userNames.length;
    }

}
