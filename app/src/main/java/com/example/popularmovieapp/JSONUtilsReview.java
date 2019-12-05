package com.example.popularmovieapp;

import com.example.popularmovieapp.database.Review;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtilsReview {

    public static ArrayList<Review> parseReviewJson(String json) throws JSONException {

        final String REVIEW_RESULT = "results";
        final String REVIEW_AUTHOR = "author";
        final String REVIEW_CONTENT = "content";
        final String REVIEW_URL = "url";

        ArrayList<Review> reviewVMList = new ArrayList<Review>();

        JSONObject reviewJson = new JSONObject(json);
        JSONArray reviewArray = reviewJson.getJSONArray(REVIEW_RESULT);

        for(int i = 0; i<reviewArray.length();i++){

            JSONObject review = reviewArray.getJSONObject(i);

            Review reviewVM = new Review(
                    review.getString(REVIEW_AUTHOR),
                    review.getString(REVIEW_CONTENT),
                    review.getString(REVIEW_URL)
            );

            reviewVMList.add(reviewVM);
        }

        return reviewVMList;
    }
}
