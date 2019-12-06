package com.example.popularmovieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovieapp.database.Trailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private ArrayList<Trailer> dataList;
    private Context context;

    final private ListItemClickListenerTrailer mOnClickListener;

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflate = LayoutInflater.from(context);

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_trailer, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder( TrailerViewHolder holder, int position) {
        Picasso.with(context).load(dataList.get(position).getTrailerImage()).into(holder.ivTrailer);
        holder.tvTrailerTitle.setText(dataList.get(position).getTrailerTittle());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvTrailerTitle;
        private ImageView ivTrailer;

        public TrailerViewHolder(View itemView) {
            super(itemView);
            tvTrailerTitle = itemView.findViewById(R.id.trailer_title);
            ivTrailer = itemView.findViewById(R.id.trailer_image);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClickTrailer(getAdapterPosition());
        }
    }

    public void setDataList(ArrayList<Trailer> list) {
        this.dataList = list;
        notifyDataSetChanged();
    }

    //interface
    interface ListItemClickListenerTrailer {
        void onListItemClickTrailer(int klik);

    }
    TrailerAdapter(ArrayList<Trailer> dataList, ListItemClickListenerTrailer mOnClickListener) {
        this.dataList = dataList;
        this.mOnClickListener = mOnClickListener;
    }


}
