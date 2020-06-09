package com.example.eartquakenews.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eartquakenews.MapsActivity;
import com.example.eartquakenews.R;
import com.example.eartquakenews.model.Earthquake;

import java.text.MessageFormat;
import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private Context myContext;
    private ArrayList<Earthquake> earthquakes;

    public RecyclerAdapter(Context context, ArrayList<Earthquake> earthquakeArrayList) {
        this.myContext = context;
        this.earthquakes = earthquakeArrayList;
    }

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(myContext).inflate(R.layout.recycler_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, final int position) {
        holder.lat.setText(String.valueOf(earthquakes.get(position).getLatitude()));
        holder.longitude.setText(String.valueOf(earthquakes.get(position).getLongitude()));
        holder.place.setText(earthquakes.get(position).getPlaceName());
        holder.readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(earthquakes.get(position).getUrl()));
                myContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return earthquakes.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView longitude;
        public TextView lat;
        public TextView place;
        public TextView readMore;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            longitude = itemView.findViewById(R.id.lang);
            lat = itemView.findViewById(R.id.lat);
            place = itemView.findViewById(R.id.place);
            readMore = itemView.findViewById(R.id.read_more);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        MapsActivity.update(lat.getText().toString(),longitude.getText().toString());
//                        Toast.makeText(myContext,strings1[1] + string2[1],Toast.LENGTH_LONG).show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
