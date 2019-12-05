package com.example.popularmovieapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.popularmovieapp.database.AppDatabase;
import com.example.popularmovieapp.database.Movie;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ListItemClickListener{

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private ProgressBar pb;

    private static ArrayList<Movie> movieList;
    final String popularUrl = "https://api.themoviedb.org/3/movie/popular?api_key=9285a39f5cd3614960d26cc9da22b799";
    final String topRatedUrl = "https://api.themoviedb.org/3/movie/top_rated?api_key=9285a39f5cd3614960d26cc9da22b799";

    private List<Movie> favourite = new ArrayList<>();

    private String currentSort = "POP";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieList = new ArrayList<>();
        pb = findViewById(R.id.progress_bar);
        NetworkUtils networkUtils = new NetworkUtils();
        networkUtils.execute(popularUrl);

        recyclerView = findViewById(R.id.movie_list);
        adapter = new MovieAdapter(movieList, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        //setupViewModel();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_by_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NetworkUtils networkUtils = new NetworkUtils();
        switch (item.getItemId()) {
            case R.id.popular_menu:
                currentSort = "POP";
                networkUtils.execute(popularUrl);
                return true;
            case R.id.top_rated_menu:
                currentSort = "TOP";
                networkUtils.execute(topRatedUrl);
                return true;
            case R.id.favourites_menu:
                currentSort = "FAV";
                //adapter.setDataList(favourite);
                setupViewModel();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onListItemClick(int klik) {
        Movie movieKlik = adapter.getDataList().get(klik);
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("id", movieKlik.getMovieId());
        intent.putExtra("title",movieKlik.getMovieName());
        intent.putExtra("image",movieKlik.getMovieImage());
        intent.putExtra("release date",movieKlik.getMovieRelease());
        intent.putExtra("rating",movieKlik.getMovieRating());
        intent.putExtra("description",movieKlik.getMovieDescription());
        startActivity(intent);
    }


    public class NetworkUtils extends AsyncTask<String, Void, ArrayList<Movie>>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Movie> doInBackground(String... strings) {
            URL url = null;
            HttpURLConnection connect = null;
//            ArrayList<Movie> movieList;
            try {
                url = new URL(strings[0]);
                connect = (HttpURLConnection) url.openConnection();

                InputStream inputStream = connect.getInputStream();
                String x = readData(inputStream);

                movieList = JSONUtilsMovie.parseMovieJson(x);

                return movieList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.e("Error m", e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("Error i", e.getMessage());
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("Error j", e.getMessage());
            } finally {
                if(url != null){
                    if (connect != null)
                        connect.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie> movies) {
            super.onPostExecute(movies);
            pb.setVisibility(View.INVISIBLE);
            if (movies.size() != 0){
                adapter.setDataList(movies);
//                Log.d("Ini data si bambang", movies.size()+"");
            }else {}
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

    private void setupViewModel(){
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getMovie().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if (movies.size()>0){
                    favourite.clear();
                    favourite = movies;
                }else {
                    favourite.clear();
                }
                Log.d("data : ", movies.size()+"");
                if (currentSort.equals("FAV")){
                    adapter.setDataList(movies);
                }
            }
        });

    }



}
