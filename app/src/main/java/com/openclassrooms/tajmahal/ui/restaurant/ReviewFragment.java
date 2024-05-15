package com.openclassrooms.tajmahal.ui.restaurant;

import android.app.Activity;
import android.content.Intent;
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

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.adapter.ReviewAdapter;
import com.openclassrooms.tajmahal.databinding.FragmentReviewBinding;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.ArrayList;


public class ReviewFragment extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private FragmentReviewBinding binding;
    private ReviewAdapter reviewAdapter;
    private DetailsViewModel detailsViewModel;
    private static final int REQUEST_PICK_IMAGE = 1001;
    private Uri selectedImageUri;


    private Chip valider;
    private TextInputEditText textField;
    private ImageButton imageButton;
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentReviewBinding.inflate(inflater, container, false);


        // Setup toolbar
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

        // Set OnClickListener for the custom button in the toolbar
        imageButton = binding.backButton.findViewById(R.id.backButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the previous fragment
                Log.d("ReviewFragment", "Back button clicked");
                getParentFragmentManager().popBackStack();
            }
        });
        // Observe the LiveData for reviews and update the adapter when data changes
        detailsViewModel.getRestaurantReviews().observe(getViewLifecycleOwner(), reviews -> {
            reviewAdapter.setReviewList(reviews);

            valider = binding.valider.findViewById(R.id.valider);
            //textField = binding.textField.findViewById(R.id.textField);
            recyclerView = binding.recyclerView.findViewById(R.id.recyclerView);


            valider.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    validateAndAddReview();
                }
            });

        });


    }

    private void validateAndAddReview() {
        // Retrieve input from TextInputEditText
        String comment = binding.textField.getText().toString().trim();
        String username = binding.username.getText().toString().trim();

        // Validate username
        if (username.isEmpty()) {
            // Show a Snackbar if the username is empty
            Snackbar.make(
                    requireView(),
                    "Username must not be empty",
                    Snackbar.LENGTH_LONG
            ).setAnchorView(R.id.valider).show();
            return; // Exit the method if the username is empty
        }

        // Validate comment
        if (comment.isEmpty()) {
            // Show a Snackbar if the comment is empty
            Snackbar.make(
                    requireView(),
                    "Comment must not be empty",
                    Snackbar.LENGTH_LONG
            ).setAnchorView(R.id.valider).show();
            return; // Exit the method if the comment is empty
        }

// Retrieve the rating from the RatingBar
        float rating = binding.rating.getRating();

        // Both username and comment are not empty, proceed to create and add the review
        // Create a new Review object
        Review newReview;
        if (selectedImageUri != null) {
            newReview = new Review(username, selectedImageUri.toString(), comment, (int) rating);
        } else {
            newReview = new Review(username, "Picture URL", comment, (int) rating);
        }
        // Add the new Review to your RecyclerView's dataset
        // Assuming reviewAdapter is your RecyclerView adapter
        reviewAdapter.addItem(newReview);

        // Clear the TextInputEditText after adding the review
        binding.textField.setText("");
        binding.username.setText("");
        binding.rating.setRating(0);

        // Load the default profile picture into the profile ImageView
        Glide.with(requireContext())
                .load(R.drawable.baseline_account_circle_24)
                .into(binding.profile);

        binding.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Implement picture selection logic here
                // Open an image picker (e.g., gallery) to select a profile picture
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_PICK_IMAGE);
            }
        });


    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICK_IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            // Retrieve the selected image URI
            selectedImageUri = data.getData();

            // Load the selected image into the profile ImageView
            Glide.with(requireContext())
                    .load(selectedImageUri)
                    .placeholder(R.drawable.baseline_account_circle_24)
                    .error(R.drawable.baseline_error_24)
                    .into(binding.profile);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}