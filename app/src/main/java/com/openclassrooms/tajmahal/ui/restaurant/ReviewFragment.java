package com.openclassrooms.tajmahal.ui.restaurant;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.adapter.ReviewAdapter;
import com.openclassrooms.tajmahal.databinding.FragmentReviewBinding;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.ArrayList;

public class ReviewFragment extends Fragment {

    public FragmentReviewBinding binding;
    public ReviewAdapter reviewAdapter;
    public DetailsViewModel detailsViewModel;
    public Uri selectedImageUri;

    public ReviewFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReviewBinding.inflate(inflater, container, false);

        // Setup toolbar
        Toolbar toolbar = binding.toolbar;
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize ViewModel
        detailsViewModel = new ViewModelProvider(requireActivity()).get(DetailsViewModel.class);

        // Initialize RecyclerView adapter and set layout manager
        reviewAdapter = new ReviewAdapter(new ArrayList<>());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(reviewAdapter);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        // Set up the toolbar
        Toolbar toolbar = binding.toolbar;
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        }

        // Handle back button click
        toolbar.setNavigationOnClickListener(v -> getParentFragmentManager().popBackStack());

        // Increase the size of profile Image programmatically
        ImageView profileImageView = binding.getRoot().findViewById(R.id.profile);
        ViewGroup.LayoutParams layoutParams = profileImageView.getLayoutParams();
        layoutParams.width = 200;  // Set your desired width here
        layoutParams.height = 200; // Set your desired height here
        profileImageView.setLayoutParams(layoutParams);

        // Initialize views outside the observer
        Chip valider = binding.valider;
        TextInputEditText textField = binding.textField;

        // Set click listener for valider chip
        valider.setOnClickListener(v -> validateAndAddReview());

        // Observe the LiveData for reviews and update the adapter when data changes
        detailsViewModel.getRestaurantReviews().observe(getViewLifecycleOwner(), reviews -> {
            reviewAdapter.setReviewList(reviews);
        });
    }

    public void validateAndAddReview() {
        String comment = binding.textField.getText().toString().trim();
        String username = binding.username.getText().toString().trim();
        float rating = binding.rating17.getRating();

        // Validate username
        if (username.isEmpty()) {
            Snackbar.make(requireView(), "Username must not be empty", Snackbar.LENGTH_LONG)
                    .setAnchorView(R.id.valider).show();
            return;
        }

        // Validate comment
        if (comment.isEmpty()) {
            Snackbar.make(requireView(), "Comment must not be empty", Snackbar.LENGTH_LONG)
                    .setAnchorView(R.id.valider).show();
            return;
        }

        // Create a new Review object
        Review newReview = new Review(username,
                selectedImageUri != null ? selectedImageUri.toString() : "Picture URL",
                comment,
                (int) rating);

        // Add the new Review to the ViewModel
        detailsViewModel.addReview(newReview);

        // Clear the input fields after adding the review
        binding.textField.setText("");
        binding.username.setText("");
        binding.rating17.setRating(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
