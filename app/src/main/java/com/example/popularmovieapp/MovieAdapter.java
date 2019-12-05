package com.example.popularmovieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovieapp.database.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {


    private List<Movie> dataList;
    private Context context;

    final private ListItemClickListener mOnClickListener;

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();
        LayoutInflater inflate = LayoutInflater.from(context);

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Picasso.with(context).load(dataList.get(position).getMovieImage()).into(holder.imgMovie);
        holder.tvMovie.setText(dataList.get(position).getMovieName());

    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imgMovie;
        private TextView tvMovie;

        public MovieViewHolder(View itemView) {
            super(itemView);
            imgMovie = itemView.findViewById(R.id.movie_image);
            tvMovie = itemView.findViewById(R.id.movie_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            mOnClickListener.onListItemClick(getAdapterPosition());

        }
    }

    public void setDataList(List<Movie> list) {
        this.dataList = list;
        notifyDataSetChanged();
    }

    public List<Movie> getDataList(){
        return dataList;
    }

    //interface
    interface ListItemClickListener {
        void onListItemClick(int klik);
    }

    public MovieAdapter(ArrayList<Movie> dataList, ListItemClickListener mOnClickListener) {
        this.dataList = dataList;
        this.mOnClickListener = mOnClickListener;
    }
}