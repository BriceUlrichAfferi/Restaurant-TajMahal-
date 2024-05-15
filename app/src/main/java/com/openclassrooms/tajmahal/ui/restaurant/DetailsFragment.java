package com.openclassrooms.tajmahal.ui.restaurant;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.textfield.TextInputEditText;
import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.adapter.ReviewAdapter;
import com.openclassrooms.tajmahal.databinding.FragmentDetailsBinding;
import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DetailsFragment extends Fragment {

    private FragmentDetailsBinding binding;
    private DetailsViewModel detailsViewModel;
    private ProgressBar progressBar1Star;
    private ProgressBar progressBar2Star;
    private ProgressBar progressBar3Star;
    private ProgressBar progressBar4Star;
    private ProgressBar progressBar5Star;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUI(); // Sets up user interface components.
        setupViewModel(); // Prepares the ViewModel for the fragment.

        progressBar1Star = binding.getRoot().findViewById(R.id.progressBar1Star);
        progressBar2Star = binding.getRoot().findViewById(R.id.progressBar2Star);
        progressBar3Star = binding.getRoot().findViewById(R.id.progressBar3Star);
        progressBar4Star = binding.getRoot().findViewById(R.id.progressBar4Star);
        progressBar5Star = binding.getRoot().findViewById(R.id.progressBar5Star);
        TextView totalScore = binding.getRoot().findViewById(R.id.textViewTotalScore); // Initialize totalScore from the binding object

        detailsViewModel.getTajMahalRestaurant().observe(requireActivity(), this::updateUIWithRestaurant); // Observes changes in the restaurant data and updates the UI accordingly.
        detailsViewModel.getRestaurantReviews().observe(requireActivity(), this::updateUIWithReviews);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private void setupUI() {
        Window window = requireActivity().getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        window.setStatusBarColor(Color.TRANSPARENT);
    }

    private void setupViewModel() {
        detailsViewModel = new ViewModelProvider(this).get(DetailsViewModel.class);
    }

    private void updateUIWithRestaurant(Restaurant restaurant) {
        if (restaurant == null) return;

        binding.tvRestaurantName.setText(restaurant.getName());
        binding.tvRestaurantDay.setText(detailsViewModel.getCurrentDay(requireContext()));
        binding.tvRestaurantType.setText(String.format("%s %s", getString(R.string.restaurant), restaurant.getType()));
        binding.tvRestaurantHours.setText(restaurant.getHours());
        binding.tvRestaurantAddress.setText(restaurant.getAddress());
        binding.tvRestaurantWebsite.setText(restaurant.getWebsite());
        binding.tvRestaurantPhoneNumber.setText(restaurant.getPhoneNumber());
        binding.chipOnPremise.setVisibility(restaurant.isDineIn() ? View.VISIBLE : View.GONE);
        binding.chipTakeAway.setVisibility(restaurant.isTakeAway() ? View.VISIBLE : View.GONE);
        binding.buttonAdress.setOnClickListener(v -> openMap(restaurant.getAddress()));
        binding.buttonPhone.setOnClickListener(v -> dialPhoneNumber(restaurant.getPhoneNumber()));
        binding.buttonWebsite.setOnClickListener(v -> openBrowser(restaurant.getWebsite()));
        binding.laisserUnAvis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Replace the current fragment with the ReviewFragment
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.container, new ReviewFragment());
                transaction.addToBackStack(null);  // Optional: allows the user to navigate back
                transaction.commit();
            }
        });

    }

    private double calculateTotalScore(int count1Star, int count2Star, int count3Star,
                                       int count4Star, int count5Star, int totalReviews) {
        double totalScore = 0.0;

        // Calculate the weighted sum of ratings
        totalScore += count1Star * 1.0 / totalReviews;
        totalScore += count2Star * 2.0 / totalReviews;
        totalScore += count3Star * 3.0 / totalReviews;
        totalScore += count4Star * 4.0 / totalReviews;
        totalScore += count5Star * 5.0 / totalReviews;


        return totalScore;
    }
    private void updateUIWithReviews(List<Review> reviews) {
        if (reviews == null || reviews.isEmpty()) return;

        // Calculate the total number of reviews
        int totalReviews = reviews.size();

        binding.textViewReviewCount.setText("(" + totalReviews + ")");

        // Initialize variables to store the count of each rating
        int count1Star = 0;
        int count2Star = 0;
        int count3Star = 0;
        int count4Star = 0;
        int count5Star = 0;

        // Count the occurrences of each rating
        for (Review review : reviews) {
            int rating = review.getRate();
            switch (rating) {
                case 1:
                    count1Star++;
                    break;
                case 2:
                    count2Star++;
                    break;
                case 3:
                    count3Star++;
                    break;
                case 4:
                    count4Star++;
                    break;
                case 5:
                    count5Star++;
                    break;
            }
        }

        double totalScore = calculateTotalScore(count1Star, count2Star, count3Star, count4Star, count5Star, totalReviews);

        // Update the UI with the total score
        updateTotalScoreUI(totalScore);

        // Set the rating of the RatingBar to match the total score
        binding.ratingBar.setRating((float) totalScore);



        // Calculate the percentage of reviews for each rating
        double percentage1Star = (double) count1Star / totalReviews * 100;
        double percentage2Star = (double) count2Star / totalReviews * 100;
        double percentage3Star = (double) count3Star / totalReviews * 100;
        double percentage4Star = (double) count4Star / totalReviews * 100;
        double percentage5Star = (double) count5Star / totalReviews * 100;

    /*
    5-star (2): 40%
    4-star (2): 40%
    3-star (0): 0%
    2-star (1): 20%
    1-star (0): 0%
     */

        // Set progress for each progress bar
        progressBar1Star.setProgress((int) percentage1Star);
        progressBar2Star.setProgress((int) percentage2Star);
        progressBar3Star.setProgress((int) percentage3Star);
        progressBar4Star.setProgress((int) percentage4Star);
        progressBar5Star.setProgress((int) percentage5Star);
    }


    private void updateTotalScoreUI(double totalScore) {
        // Set the total score in the textView_totalScore TextView if the binding is not null

        binding.textViewTotalScore.setText(String.format("%.1f", totalScore));
    }



  /*  // Helper method to check if reviews contain a specific rating
    private boolean containsRating(List<Review> reviews, int rating) {
        for (Review review : reviews) {
            if (review.getRate() == rating) {
                return true;
            }
        }
        return false;
    }


    private double calculateAverageRating(List<Review> reviews) {
        if (reviews == null || reviews.isEmpty()) {
            return 0.0;
        }

        double totalRating = 0.0;
        for (Review review : reviews) {
            totalRating += review.getRate();
        }

        return totalRating / reviews.size();
    }

    private void updateProgressBar(ProgressBar progressBar, double rating) {
        int progress = (int) (rating * 20);
        progressBar.setProgress(progress);
    }
*/
    private void openMap(String address) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(requireActivity(), R.string.maps_not_installed, Toast.LENGTH_SHORT).show();
        }
    }

    private void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(requireActivity(), R.string.phone_not_found, Toast.LENGTH_SHORT).show();
        }
    }

    private void openBrowser(String websiteUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl));
        if (intent.resolveActivity(requireActivity().getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Toast.makeText(requireActivity(), R.string.no_browser_found, Toast.LENGTH_SHORT).show();
        }
    }

    public static DetailsFragment newInstance() {
        return new DetailsFragment();
    }


}
