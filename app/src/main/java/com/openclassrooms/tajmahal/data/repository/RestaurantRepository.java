package com.openclassrooms.tajmahal.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.tajmahal.data.service.RestaurantApi;
import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.Review;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;


/**
 * Ici nous avons la logique pour la recuperation des donnees depuis lApi selon les modeles Restauant et Review
 *
 *
 * This is the repository class for managing restaurant data. Repositories are responsible
 * for coordinating data operations from data sources such as network APIs, databases, etc.
 *
 * Typically in an Android app built with architecture components, the repository will handle
 * the logic for deciding whether to fetch data from a network source or use data from a local cache.
 *
 *
 * @see Restaurant
 * @see RestaurantApi
 */
@Singleton
public class RestaurantRepository {

    private final RestaurantApi restaurantApi;
    private final MutableLiveData<List<Review>> reviewsLiveData;

    @Inject
    public RestaurantRepository(RestaurantApi restaurantApi) {
        this.restaurantApi = restaurantApi;
        // Initialize with fake API data
        List<Review> initialReviews = restaurantApi.getReviews();
        reviewsLiveData = new MutableLiveData<>(new ArrayList<>(initialReviews));
    }

    public LiveData<Restaurant> getRestaurant() {
        return new MutableLiveData<>(restaurantApi.getRestaurant());
    }

    public LiveData<List<Review>> getReviews() {
        return reviewsLiveData;
    }

    public void addReview(Review review) {
        List<Review> currentReviews = reviewsLiveData.getValue();
        if (currentReviews != null) {
            currentReviews.add(0, review); // Add new review at the top of the list
            reviewsLiveData.setValue(currentReviews);
        }
    }
}
