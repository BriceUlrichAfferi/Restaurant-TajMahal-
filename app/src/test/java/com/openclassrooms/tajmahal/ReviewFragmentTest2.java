package com.openclassrooms.tajmahal;



import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.google.android.material.textfield.TextInputEditText;
import com.openclassrooms.tajmahal.adapter.ReviewAdapter;
import com.openclassrooms.tajmahal.databinding.FragmentReviewBinding;
import com.openclassrooms.tajmahal.domain.model.Review;
import com.openclassrooms.tajmahal.ui.restaurant.ReviewFragment;

import org.junit.Before;
import org.junit.Test;

public class ReviewFragmentTest2 {

    private ReviewFragment reviewFragment;
    private Review review;
    private FragmentReviewBinding mockBinding;
    private TextInputEditText mockTextField;
    // You may need to mock other dependencies used in the method

    @Before
    public void setUp() {
        reviewFragment = new ReviewFragment();
        mockBinding = mock(FragmentReviewBinding.class);
        mockTextField = mock(TextInputEditText.class);
        review = mock(Review.class); // or create a real Review object
        reviewFragment.binding = mockBinding;
        reviewFragment.reviewAdapter = mock(ReviewAdapter.class); // Initialize your reviewAdapter mock or use a real instance
    }

    @Test
    public void validateAndAddReview_withValidInput_addsReviewToDataset() {
        // Set up input values or mock behavior if needed

        // Call the method to be tested
        reviewFragment.validateAndAddReview();

        // Verify the expected behavior or outcome
        // For example, verify that the review is added to the dataset

        verify(reviewFragment.reviewAdapter).addItem(review);
    }

    @Test
    public void validateAndAddReview_withEmptyComment_showsSnackbar() {
        // Set up input values or mock behavior if needed
        // For example, mock behavior to return an empty comment

        // Call the method to be tested
        reviewFragment.validateAndAddReview();

        // Verify the expected behavior or outcome
        // For example, verify that a Snackbar is shown for an empty comment
        //verify(mockBinding.getRoot().getContext()).showSnackbar(/*expected message*/);
    }

    // Add more test cases to cover different scenarios


}


