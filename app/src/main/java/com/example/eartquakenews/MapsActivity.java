package com.example.eartquakenews;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


import com.example.eartquakenews.adapters.RecyclerAdapter;
import com.example.eartquakenews.controller.AppController;
import com.example.eartquakenews.model.Earthquake;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback{
    private static LatLng places;
    private static GoogleMap mMap;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    private ArrayList<Earthquake> arrayList;
    private static Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        arrayList = new ArrayList<>();
        mapFragment.getMapAsync(this);
        recyclerView = findViewById(R.id.recycler_view);


        AppController.getAppController().getData(new Callback() {
            @Override
            public void finishedWork(ArrayList<Earthquake> earthquakes) {
                arrayList = earthquakes;
                recyclerAdapter = new RecyclerAdapter(MapsActivity.this, arrayList);
                recyclerView.setHasFixedSize(false);
                recyclerView.setLayoutManager(new LinearLayoutManager(MapsActivity.this));
                recyclerView.setAdapter(recyclerAdapter);
//                recyclerAdapter.notifyDataSetChanged();
            }
        } , MapsActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    public static void update(String latitude, String longitude){
        if (marker != null) {
            marker.remove();
        }
        places = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        marker = mMap.addMarker(new MarkerOptions().position(places).title("This is the place you marked!"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(places,3));
    }
}
