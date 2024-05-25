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

        detailsViewModel = new ViewModelProvider(requireActivity()).get(DetailsViewModel.class);

        reviewAdapter = new ReviewAdapter(new ArrayList<>());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.recyclerView.setAdapter(reviewAdapter);
        binding.recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        Toolbar toolbar = binding.toolbar;
        ((AppCompatActivity) requireActivity()).setSupportActionBar(toolbar);
        ActionBar actionBar = ((AppCompatActivity) requireActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24);
        }

        toolbar.setNavigationOnClickListener(v -> getParentFragmentManager().popBackStack());

        ImageView profileImageView = binding.getRoot().findViewById(R.id.profile);
        ViewGroup.LayoutParams layoutParams = profileImageView.getLayoutParams();
        layoutParams.width = 200;
        layoutParams.height = 200;
        profileImageView.setLayoutParams(layoutParams);

        Chip valider = binding.valider;
        TextInputEditText textField = binding.textField;

        valider.setOnClickListener(v -> {
            //Captures user input and passes it to the ViewModel
            String comment = binding.textField.getText().toString().trim();
            String username = binding.username.getText().toString().trim();
            float rating = binding.rating17.getRating();
            detailsViewModel.validateAndAddReview(username, comment, rating, selectedImageUri);

            // Clear the input fields after adding the review
            binding.textField.setText("");
            binding.rating17.setRating(0);
        });

        detailsViewModel.getRestaurantReviews().observe(getViewLifecycleOwner(), reviews -> {
            reviewAdapter.setReviewList(reviews);
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}