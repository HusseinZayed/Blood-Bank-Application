package com.example.bloodbank.Adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bloodbank.Models.RequestDataModel;
import com.example.bloodbank.R;

import java.util.ArrayList;
import java.util.List;


public class AdapterRequest extends RecyclerView.Adapter<AdapterRequest.MyViewHolder> {

    List<RequestDataModel> modelview;
    Context context;

    public AdapterRequest(List<RequestDataModel> modelview, Context context) {
        this.modelview = modelview;
        this.context = context;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show, null, false);
        MyViewHolder myHolder = new MyViewHolder(v);
        return myHolder;
    }


    // link data to holder
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        RequestDataModel model = modelview.get(position);
        holder.message.setText(model.getMessage());
        Glide.with(context).load(model.getUrl()).into(holder.img);


    }

    @Override
    public int getItemCount() {
        return modelview.size();
    }




    class MyViewHolder extends RecyclerView.ViewHolder {
       TextView message;
       ImageView img , phone ,share;

        public MyViewHolder(@NonNull View itemView) {  // itemView == custom_layout
            super(itemView);
            message = itemView.findViewById(R.id.message);
            img = itemView.findViewById(R.id.img);
            phone = itemView.findViewById(R.id.phone);
            share = itemView.findViewById(R.id.share);

        }
    }
}
