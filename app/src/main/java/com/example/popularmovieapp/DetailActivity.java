package com.example.popularmovieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.popularmovieapp.database.AppDatabase;
import com.example.popularmovieapp.database.Movie;
import com.example.popularmovieapp.database.Review;
import com.example.popularmovieapp.database.Trailer;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class DetailActivity extends AppCompatActivity implements TrailerAdapter.ListItemClickListenerTrailer, ReviewAdapter.ListItemClickListenerReview {

    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;
//    private ProgressBar pb;
    private static ArrayList<Trailer> trailerList;
    private static ArrayList<Review> reviewList;
    private RecyclerView trailerRecyclerView, reviewRecyclerView;

    private ImageButton ibLove;
    private Boolean isLove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        trailerList = new ArrayList<>();
        reviewList = new ArrayList<>();
//        pb = findViewById(R.id.progress_bar);
        DetailActivity.TrailerNetworkUtils trailerNetworkUtils = new DetailActivity.TrailerNetworkUtils();
        DetailActivity.ReviewNetworkUtils reviewNetworkUtils = new DetailActivity.ReviewNetworkUtils();


        TextView tvTitle = findViewById(R.id.movie_title_detail);
        ImageView ivImage = findViewById(R.id.movie_image_detail);
        TextView tvRelease = findViewById(R.id.movie_release_date);
        TextView tvRating   = findViewById(R.id.movie_rating);
        TextView tvDescription = findViewById(R.id.movie_description);

        Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        final String title = intent.getStringExtra("title");
        final String image = intent.getStringExtra("image");
        final String releaseDate = intent.getStringExtra("release date");
        final String rating = intent.getStringExtra("rating");
        final String description = intent.getStringExtra("description");


        final String trailerUrl = "https://api.themoviedb.org/3/movie/" + id + "/videos?api_key=9285a39f5cd3614960d26cc9da22b799";
        final String reviewUrl = "https://api.themoviedb.org/3/movie/" +  id + "/reviews?api_key=363a304bf3803692ae784f270971be88";
        trailerNetworkUtils.execute(trailerUrl);
        reviewNetworkUtils.execute(reviewUrl);

        trailerRecyclerView = findViewById(R.id.trailer_list);
        trailerAdapter = new TrailerAdapter(trailerList, (TrailerAdapter.ListItemClickListenerTrailer) this);
        RecyclerView.LayoutManager trailerLayoutManager = new LinearLayoutManager(DetailActivity.this);
        trailerRecyclerView.setLayoutManager(trailerLayoutManager);
        trailerRecyclerView.setAdapter(trailerAdapter);

        reviewRecyclerView = findViewById(R.id.review_list);
        reviewAdapter = new ReviewAdapter(reviewList,(ReviewAdapter.ListItemClickListenerReview) this);
        RecyclerView.LayoutManager reviewLayoutManager = new LinearLayoutManager(DetailActivity.this);
        reviewRecyclerView.setLayoutManager(reviewLayoutManager);
        reviewRecyclerView.setAdapter(reviewAdapter);

        tvTitle.setText(title);
        Picasso.with(this).load(image).into(ivImage);
        tvRelease.setText(releaseDate);
        tvRating.setText(rating);
        tvDescription.setText(description);

        ibLove = findViewById(R.id.love_button);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {

            @Override
            public void run() {
                Movie insideDetail = AppDatabase.getInstance(getApplicationContext()).movieDao().selectById(id);
                if (insideDetail != null){
                    Log.d("nama", insideDetail.getMovieName());
                    isLove = true;
                    ibLove.setBackgroundResource(R.drawable.pink_heart);
                }else{
                    isLove = false;
                    ibLove.setBackgroundResource(R.drawable.grey_heart);
                }
                Log.d("isLove", isLove+"");

            }
        });




        ibLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLove == true){
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            AppDatabase.getInstance(getApplicationContext()).movieDao().deleteMovie(new Movie(id, image, title, releaseDate, rating, description));
                        }
                    });
                    isLove = false;
                    ibLove.setBackgroundResource(R.drawable.grey_heart);

                } else if (isLove == false){
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            AppDatabase.getInstance(getApplicationContext()).movieDao().insertMovie(new Movie(id, image, title, releaseDate, rating, description));
                        }
                    });
                    isLove = true;
                    ibLove.setBackgroundResource(R.drawable.pink_heart);
                }

            }
        });
    }

    @Override
    public void onListItemClickTrailer(int klik) {
        Trailer trailerKlik = trailerList.get(klik);
        Intent trailerIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerKlik.getTrailerUrl()));
        startActivity(trailerIntent);
    }

    @Override
    public void onListItemClickReview(int klik) {
        Review reviewKlik = reviewList.get(klik);
        Intent reviewIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(reviewKlik.getReviewUrl()));
        startActivity(reviewIntent);
    }


    public class TrailerNetworkUtils extends AsyncTask<String, Void, ArrayList<Trailer>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Trailer> doInBackground(String... strings) {
            URL url = null;
            HttpURLConnection connect = null;
            try {
                url = new URL(strings[0]);
                connect = (HttpURLConnection) url.openConnection();

                InputStream inputStream = connect.getInputStream();
                String x = readData(inputStream);

                trailerList = JSONUtilsTrailer.parseTrailerJson(x);

                return trailerList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e("Error m-d", e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Error i-d", e.getMessage());
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("Error j-d", e.getMessage());
            } finally {
                if(url != null){
                    if (connect != null)
                        connect.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Trailer> trailer) {
            super.onPostExecute(trailer);
//            pb.setVisibility(View.INVISIBLE);
            if (trailer.size() != 0){
                trailerAdapter.setDataList(trailer);
//                Log.d("Ini data si bambang", trailer.size()+"");
            }else{}
//                Log.d("Ini null bambang",trailer.size()+"");
        }

        private String readData(InputStream input){

            Scanner scanner = new Scanner(input);
            scanner.useDelimiter("\\A");

            if (scanner.hasNext())
                return scanner.next();
            else
                return null;

        }
    }

    public class ReviewNetworkUtils extends AsyncTask<String, Void, ArrayList<Review>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Review> doInBackground(String... strings) {
            URL url = null;
            HttpURLConnection connect = null;
            try {
                url = new URL(strings[0]);
                connect = (HttpURLConnection) url.openConnection();

                InputStream inputStream = connect.getInputStream();
                String x = readData(inputStream);

                reviewList = JSONUtilsReview.parseReviewJson(x);

                return reviewList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e("Error m-d", e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Error i-d", e.getMessage());
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("Error j-d", e.getMessage());
            } finally {
                if(url != null){
                    if (connect != null)
                        connect.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Review> review) {
            super.onPostExecute(review);
//            pb.setVisibility(View.INVISIBLE);
            if (review.size() != 0){
                reviewAdapter.setDataList(review);
//                Log.d("Ini data si bambang", review.size()+"");
            }else{}
//                Log.d("Ini null bambang",review.size()+"");
        }

        private String readData(InputStream input){

            Scanner scanner = new Scanner(input);
            scanner.useDelimiter("\\A");

            if (scanner.hasNext())
                return scanner.next();
            else
                return null;

        }
    }

}
