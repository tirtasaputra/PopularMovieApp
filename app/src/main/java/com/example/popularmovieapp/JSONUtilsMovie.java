package com.example.popularmovieapp;

import com.example.popularmovieapp.database.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtilsMovie {



    public static ArrayList<Movie> parseMovieJson(String json) throws JSONException{

        final String MOVIE_ID = "id";

        final String IMAGE_BASE_URL = "https://image.tmdb.org/t/p/";
        final String IMAGE_WIDTH_CONFIGURATION = "w154";

        final String MOVIE_RESULT = "results";
        final String MOVIE_IMAGE = "poster_path";
        final String MOVIE_TITLE = "original_title";
        final String MOVIE_RELEASE = "release_date";
        final String MOVIE_RATING = "vote_average";
        final String MOVIE_DESCRIPTION = "overview";


        ArrayList<Movie> movieVMList = new ArrayList<Movie>();

        JSONObject movieJson = new JSONObject(json);
        JSONArray movieArray = movieJson.getJSONArray(MOVIE_RESULT);

        for(int i = 0; i<movieArray.length();i++){

            JSONObject movie = movieArray.getJSONObject(i);

            String posterPath = IMAGE_BASE_URL + IMAGE_WIDTH_CONFIGURATION + movie.getString(MOVIE_IMAGE);

            Movie movieVM = new Movie(

                    movie.getString(MOVIE_ID),
                    posterPath,
                    movie.getString(MOVIE_TITLE),
                    movie.getString(MOVIE_RELEASE),
                    movie.getString(MOVIE_RATING),
                    movie.getString(MOVIE_DESCRIPTION)
            );
            movieVMList.add(movieVM);
        }
        return movieVMList;
    }
}
