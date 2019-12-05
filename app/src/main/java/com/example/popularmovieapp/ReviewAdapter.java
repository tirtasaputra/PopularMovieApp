package com.example.popularmovieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovieapp.database.Review;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {

    private ArrayList<Review> dataList;
    private Context context;

    final private ListItemClickListenerReview mOnClickListener;

    @NonNull
    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflate = LayoutInflater.from(context);

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.layout_review, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewViewHolder holder, int position) {
        holder.tvReviewAuthor.setText(dataList.get(position).getReviewAuthor());
        holder.tvReviewContent.setText(dataList.get(position).getReviewContent());
    }

    @Override
    public int getItemCount() {
        return (dataList != null) ? dataList.size() : 0;
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvReviewAuthor, tvReviewContent;

        public ReviewViewHolder(View itemView) {
            super(itemView);
            tvReviewAuthor = itemView.findViewById(R.id.review_author);
            tvReviewContent = itemView.findViewById(R.id.review_content);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mOnClickListener.onListItemClickReview(getAdapterPosition());
        }
    }

    public void setDataList(ArrayList<Review> list) {
        this.dataList = list;
        notifyDataSetChanged();
    }

    //interface
    interface ListItemClickListenerReview {
        void onListItemClickReview(int klik);

    }
    ReviewAdapter(ArrayList<Review> dataList, ListItemClickListenerReview mOnClickListener) {
        this.dataList = dataList;
        this.mOnClickListener = mOnClickListener;
    }

}
