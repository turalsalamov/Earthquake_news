package com.example.eartquakenews.controller;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.eartquakenews.Callback;
import com.example.eartquakenews.model.Earthquake;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AppController extends Application {
    private static RequestQueue requestQueue;
    private String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&&starttime=2020-05-28&&endtime=2020-06-06&&minmag=4.5";
    private static AppController appController;
    private ArrayList<Earthquake> earthquakes;
    public  Context context;
    public AppController(){}

    public static AppController getAppController() {
        if (appController == null){
            appController = new AppController();
        }
        return appController;
    }

    public void getData(final Callback callback,Context context){
        this.context = context;
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    earthquakes = new ArrayList<>();
                    JSONArray jsonArray = response.getJSONArray("features");
                    for (int i = 0;i < jsonArray.length(); i++){
                        JSONObject arrayElement = (JSONObject) jsonArray.get(i);
                        JSONObject properties = arrayElement.getJSONObject("properties");
                        String place = properties.getString("place");
                        if (place.contains(",")){
                            String[] places = place.split(",");
                            place = places[1];
                        }
                        String url = properties.getString("url");
                        JSONObject geometry = arrayElement.getJSONObject("geometry");
                        JSONArray coordinates = geometry.getJSONArray("coordinates");

                        double latitude = (double)coordinates.get(0);
                        double longitude = (double)coordinates.get(1);
//                        System.out.println(place);
                        Earthquake earthquake = new Earthquake(latitude,longitude,place,url);
                        earthquakes.add(earthquake);
                    }
                    if (callback != null && earthquakes.size() > 0){
                        callback.finishedWork(earthquakes);
                    }else {
                        System.out.println("bosh");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Tag","ishlemir");
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Tag","ishlemir");
            }
        });
        AppController.getAppController().addRequest(jsonObjectRequest);
    }

    private  RequestQueue requestQueue(){
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(context);
        }
        return requestQueue;
    }
    private   <T> RequestQueue addRequest(Request<T> jsonRequest){
        requestQueue().add(jsonRequest);
        return null;
    }
}
