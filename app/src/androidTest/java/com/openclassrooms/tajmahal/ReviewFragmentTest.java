package com.openclassrooms.tajmahal;

import static junit.framework.TestCase.assertNotNull;

import android.view.View;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.android.material.chip.Chip;
import com.openclassrooms.tajmahal.adapter.ReviewAdapter;
import com.openclassrooms.tajmahal.databinding.FragmentReviewBinding;
import com.openclassrooms.tajmahal.domain.model.Review;
import com.openclassrooms.tajmahal.ui.restaurant.ReviewFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class ReviewFragmentTest {

    @Mock
    private FragmentReviewBinding mockBinding;

    @Mock
    private ReviewAdapter mockAdapter;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock Fragment binding
        when(mockBinding.valider).thenReturn((Chip) new View(null));
    }

    @Test
    public void testValidateAndAddReview_ClickValider() {
        // Launch the fragment
        FragmentScenario<ReviewFragment> scenario = FragmentScenario.launchInContainer(ReviewFragment.class);
        scenario.onFragment(fragment -> {
            // Set up fragment dependencies
            fragment.binding = mockBinding;
            fragment.reviewAdapter = mockAdapter;

            // Simulate user input
            fragment.binding.textField.setText("Test Commentaite");
            fragment.binding.username.setText("Test identifiant nom");
            fragment.binding.rating17.setRating(4f);

            // Ensure that dependencies are not null
            assertNotNull(fragment.binding);
            assertNotNull(fragment.reviewAdapter);

            // Call the method to add review
            fragment.validateAndAddReview();

            // Verify that the review was added to the adapter
            assertNotNull(mockAdapter);
            verify(mockAdapter).addItem(new Review("Test Username", "Picture URL", "Test Comment", 4));
        });
    }

}
