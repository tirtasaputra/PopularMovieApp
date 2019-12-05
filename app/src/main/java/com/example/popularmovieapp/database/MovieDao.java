package com.example.popularmovieapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface MovieDao {

    @Query("SELECT * FROM movie")
    LiveData<List<Movie>> loadAllMovie();

    @Insert
    void insertMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

//    @Query("SELECT * FROM movie where movieId= :idErased")
//    void deleteById(int idErased);

    @Query("SELECT * FROM movie where movieId= :idAdded")
    Movie selectById(String idAdded);

}
