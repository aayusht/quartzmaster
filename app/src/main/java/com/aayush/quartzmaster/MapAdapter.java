package com.aayush.quartzmaster;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by aayush on 11/5/17.
 */

public class MapAdapter extends RecyclerView.Adapter<MapAdapter.CustomViewHolder> {
    Context context;

    public MapAdapter(Context context) {
        this.context = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.map_row_view, parent, false));
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final String s = locations[position];
        holder.title.setText(s);
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MapActivity.class);
                intent.putExtra("location", s);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return locations.length;
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        View container;

        CustomViewHolder (View view) {
            super(view);
            container = view.findViewById(R.id.map_row);
            title = view.findViewById(R.id.location_name);
        }
    }

    static String locations[] = {
            "Bayonne, NJ",
            "Dallas, TX",
            "Canton, MA",
            "Los Angeles, CA",
            "Palm Beach, FL",
            "Mountain View, CA",
            "Nashville, TN",
            "Chicago, IL",
            "Denver, CO",
            "Sterling, VA",
            "Roseville, MN",
            "Tucker, GA",
            "Toronto"
    };
}
