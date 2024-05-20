package com.openclassrooms.tajmahal.adapter;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.ArrayList;
import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {


    public List<Review> reviewList = new ArrayList<>();


    public ReviewAdapter(List<Review> reviewList) {
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item_layout, parent, false);
        return new ReviewViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviewList.get(position);
        holder.usernameTextView.setText(review.getUsername());
        holder.commentTextView.setText(review.getComment());
        holder.ratingBar.setRating(review.getRate());

        // Set the tint color of the RatingBar to yellow
        holder.ratingBar.setProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(holder.itemView.getContext(), R.color.orange)));


        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.garcia);

        Glide.with(holder.itemView.getContext())
                .load(review.getPicture())
                .apply(RequestOptions.circleCropTransform())
                .apply(requestOptions)
                .into(holder.profilePictureImageView);

// Increase the size of profilePictureImageView
        ViewGroup.LayoutParams layoutParams = holder.profilePictureImageView.getLayoutParams();
        layoutParams.width = 200;  // Set your desired width here
        layoutParams.height = 200; // Set your desired height here
        holder.profilePictureImageView.setLayoutParams(layoutParams);


    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }
    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
        notifyDataSetChanged(); // Notify RecyclerView that data has changed
    }

    public void addItem(Review review) {
        if (reviewList == null) {
            reviewList = new ArrayList<>();
        }
        reviewList.add(0, review);
        notifyItemInserted(0);
        //notifyItemInserted(reviewList.size() - 1);
    }


    static class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView usernameTextView;
        TextView commentTextView;
        RatingBar ratingBar;
        ImageView profilePictureImageView;

        ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            commentTextView = itemView.findViewById(R.id.commentTextView);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            profilePictureImageView = itemView.findViewById(R.id.profilePictureImageView);
        }
    }
}
