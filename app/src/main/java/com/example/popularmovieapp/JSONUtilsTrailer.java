package com.example.popularmovieapp;

import com.example.popularmovieapp.database.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtilsTrailer {

    public static ArrayList<Trailer> parseTrailerJson(String json) throws JSONException {

        final String TRAILER_RESULT = "results";
        final String TRAILER_TITLE = "name";
        final String TRAILER_URL_KEY = "key";

        final String YOUTUBE_URL = "https://www.youtube.com/watch?v=";

        ArrayList<Trailer> trailerVMList = new ArrayList<Trailer>();

        JSONObject trailerJson = new JSONObject(json);
        JSONArray trailerArray = trailerJson.getJSONArray(TRAILER_RESULT);

        for(int i = 0; i<trailerArray.length();i++){

            JSONObject trailer = trailerArray.getJSONObject(i);

            String youtubeUrl = YOUTUBE_URL + trailer.getString(TRAILER_URL_KEY);

            Trailer trailerVM = new Trailer(
                    trailer.getString(TRAILER_TITLE),
                    youtubeUrl
            );

            trailerVMList.add(trailerVM);
        }

        return trailerVMList;
    }
}
