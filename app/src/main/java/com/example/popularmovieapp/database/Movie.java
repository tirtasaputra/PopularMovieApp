package com.example.popularmovieapp.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "movie")
public class Movie {
    @NonNull
    @PrimaryKey
    private String movieId;
    private String movieImage;
    private String movieName;
    private String movieRelease;
    private String movieRating;
    private String movieDescription;

    public Movie(){

    }

    public Movie(String movieId, String posterPath, String movieName, String movieRelease, String movieRating, String movieDescription) {
        this.movieId = movieId;
        this.movieImage = posterPath;
        this.movieName = movieName;
        this.movieRelease = movieRelease;
        this.movieRating = movieRating;
        this.movieDescription = movieDescription;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }


    public String getMovieImage() {
        return movieImage;
    }

    public void setMovieImage(String movieImage) {
        this.movieImage = movieImage;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieRelease() {
        return movieRelease;
    }

    public void setMovieRelease(String movieRelease) {
        this.movieRelease = movieRelease;
    }

    public String getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(String movieRating) {
        this.movieRating = movieRating;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

}
