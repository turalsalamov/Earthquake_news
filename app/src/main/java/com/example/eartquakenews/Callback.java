package com.example.eartquakenews;

import androidx.lifecycle.LiveData;

import com.example.eartquakenews.model.Earthquake;

import java.util.ArrayList;

public interface Callback {
    void finishedWork(ArrayList<Earthquake> earthquakes);
}
