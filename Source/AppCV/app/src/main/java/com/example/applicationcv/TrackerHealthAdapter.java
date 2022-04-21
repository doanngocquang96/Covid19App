package com.example.applicationcv;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Choreographer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.applicationcv.ShareActivity;
import com.example.applicationcv.R;

import java.util.List;

import com.example.applicationcv.ListItem;

public class TrackerHealthAdapter extends RecyclerView.Adapter<TrackerHealthAdapter.ViewHolder> {

    private Context context;
    private List<TrackerHealthy> listitems; //ListItem from the com.example.dm.Model

    public TrackerHealthAdapter(Context context, List listitem) {
        this.context = context;
        this.listitems = listitem;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TrackerHealthy item = listitems.get(position);
        holder.time.setText(item.getTime());
        holder.relate.setText(item.getRelate());
        holder.name.setText(item.getName());
        holder.desc.setText(item.getDesc());

        int typeTracker = item.getType();
        if(typeTracker == 0){
            holder.imgLogo.setImageResource(R.drawable.ic_person);
        } else {
            holder.imgLogo.setImageResource(R.drawable.ic_relate);
        }

    }

    @Override
    public int getItemCount() {
        return listitems.size();
    }

    //Hold all the items we have in the List Row
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView name, relate, time, desc;
        ImageView imgLogo;

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);

            time = itemView.findViewById(R.id.textTime);
            relate = itemView.findViewById(R.id.textRelate);
            name = itemView.findViewById(R.id.textName);
            desc = itemView.findViewById(R.id.textDesc);
            imgLogo = itemView.findViewById(R.id.imageView);
        }

        @Override
        public void onClick(View v) {
            //Get d position of the row

        }
    }
}
