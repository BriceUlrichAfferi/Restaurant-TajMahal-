package com.openclassrooms.tajmahal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import android.net.Uri;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;

import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.domain.model.Review;
import com.openclassrooms.tajmahal.ui.restaurant.DetailsViewModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DetailsViewModelTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private Observer<String> validationMessageObserver;

    @InjectMocks
    private DetailsViewModel detailsViewModel;

    @Captor
    private ArgumentCaptor<String> validationMessageCaptor;

    // Mock instance of Uri
    private Uri mockedUri;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        detailsViewModel = new DetailsViewModel(restaurantRepository);
        detailsViewModel.getValidationMessage().observeForever(validationMessageObserver);

        // Initialize the mocked Uri instance
        mockedUri = mock(Uri.class);
    }

    @Test
    public void validateAndAddReview_EmptyUsername_ShowsErrorMessage() {
        // Arrange
        String username = "";
        String comment = "Great place!";
        float rating = 4.5f;
        Uri selectedImageUri = null;

        // Act
        detailsViewModel.validateAndAddReview(username, comment, rating, selectedImageUri);

        // Assert
        verify(validationMessageObserver).onChanged(validationMessageCaptor.capture());
        assertEquals("Username must not be empty", validationMessageCaptor.getValue());
    }

    @Test
    public void validateAndAddReview_EmptyComment_ShowsErrorMessage() {
        // Arrange
        String username = "JohnDoe";
        String comment = "";
        float rating = 4.5f;
        Uri selectedImageUri = null;

        // Act
        detailsViewModel.validateAndAddReview(username, comment, rating, selectedImageUri);

        // Assert
        verify(validationMessageObserver).onChanged(validationMessageCaptor.capture());
        assertEquals("Comment must not be empty", validationMessageCaptor.getValue());
    }

    @Test
    public void validateAndAddReview_ValidInputs_AddsReview() {
        // Arrange
        String username = "JohnDoe";
        String comment = "Great place!";
        float rating = 4.5f;

        // Act
        detailsViewModel.validateAndAddReview(username, comment, rating, mockedUri);

        // Assert
        verify(restaurantRepository, times(1)).addReview(new Review(
                username, mockedUri.toString(), comment, (int) rating
        ));
    }
}
