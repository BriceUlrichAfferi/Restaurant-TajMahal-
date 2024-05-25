package com.openclassrooms.tajmahal.ui.restaurant;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.tajmahal.R;
import com.openclassrooms.tajmahal.data.repository.RestaurantRepository;
import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.Review;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * MainViewModel is responsible for preparing and managing the data for the {@link DetailsFragment}.
 * It communicates with the {@link RestaurantRepository} to fetch restaurant details and provides
 * utility methods related to the restaurant UI.
 *
 * This ViewModel is integrated with Hilt for dependency injection.
 */
@HiltViewModel
public class DetailsViewModel extends ViewModel {

    public RestaurantRepository restaurantRepository;

    private MutableLiveData<String> validationMessage = new MutableLiveData<>();

    @Inject
    public DetailsViewModel(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public LiveData<Restaurant> getTajMahalRestaurant() {
        return restaurantRepository.getRestaurant();
    }

    public LiveData<List<Review>> getRestaurantReviews() {
        return restaurantRepository.getReviews();
    }

    public LiveData<String> getValidationMessage() {
        return validationMessage;
    }

    public void validateAndAddReview(String username, String comment, float rating, Uri selectedImageUri) {
        if (username.isEmpty()) {
            validationMessage.setValue("Username must not be empty");
            return;
        }

        if (comment.isEmpty()) {
            validationMessage.setValue("Comment must not be empty");
            return;
        }

        Review newReview = new Review(username,
                selectedImageUri != null ? selectedImageUri.toString() : "Picture URL",
                comment,
                (int) rating);

        addReview(newReview);

        validationMessage.setValue(""); // Clear the validation message
    }

    private void addReview(Review review) {
        restaurantRepository.addReview(review);
    }

    public String getCurrentDay(Context context) {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        String dayString;

        switch (dayOfWeek) {
            case Calendar.MONDAY:
                dayString = context.getString(R.string.monday);
                break;
            case Calendar.TUESDAY:
                dayString = context.getString(R.string.tuesday);
                break;
            case Calendar.WEDNESDAY:
                dayString = context.getString(R.string.wednesday);
                break;
            case Calendar.THURSDAY:
                dayString = context.getString(R.string.thursday);
                break;
            case Calendar.FRIDAY:
                dayString = context.getString(R.string.friday);
                break;
            case Calendar.SATURDAY:
                dayString = context.getString(R.string.saturday);
                break;
            case Calendar.SUNDAY:
                dayString = context.getString(R.string.sunday);
                break;
            default:
                dayString = "";
        }
        return dayString;
    }
}