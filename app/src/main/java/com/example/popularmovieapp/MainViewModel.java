package com.example.popularmovieapp;


import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.example.popularmovieapp.database.AppDatabase;
import com.example.popularmovieapp.database.Movie;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {


    private static final String TAG = MainViewModel.class.getSimpleName();

    private LiveData<List<Movie>> movie;

    public MainViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the DataBase");
        movie = database.movieDao().loadAllMovie();
    }

    // (3) Create a getter for the tasks variable
    public LiveData<List<Movie>> getMovie() {
        return movie;
    }
}

