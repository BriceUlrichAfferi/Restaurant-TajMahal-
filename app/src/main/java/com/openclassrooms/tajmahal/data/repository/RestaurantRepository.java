package com.openclassrooms.tajmahal.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.tajmahal.data.service.RestaurantApi;
import com.openclassrooms.tajmahal.domain.model.Restaurant;
import com.openclassrooms.tajmahal.domain.model.Review;

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

    // The API interface instance that will be used for network requests related to restaurant data.
    private final RestaurantApi restaurantApi;

    /**
     * Constructs a new instance of {@link RestaurantRepository} with the given {@link RestaurantApi}.
     *
     * @param restaurantApi The network API interface for fetching restaurant data.
     */
    @Inject
    public RestaurantRepository(RestaurantApi restaurantApi) {
        this.restaurantApi = restaurantApi;
    }

    /**
     * Fetches the restaurant details.
     *
     * This method will make a network call using the provided {@link RestaurantApi} instance
     * to fetch restaurant data. Note that error handling and any transformations on the data
     * would need to be managed.
     *
     *
     * @return LiveData holding the restaurant details.
     */


    //direct initialization, good If the data is static or doesn't change frequently.
    //of type LiveData, because it gets data from Api
    public LiveData<Restaurant> getRestaurant() {
        return new MutableLiveData<>(restaurantApi.getRestaurant());
    }

    //manual initialization, good If the data may change over time and you need to observe those changes.
    public LiveData<List<Review>> getReviews() {
        // Assuming restaurantApi.getReviews() returns a List<Review>
        List<Review> reviews = restaurantApi.getReviews();
        MutableLiveData<List<Review>> liveData = new MutableLiveData<>();
        liveData.setValue(reviews);
        return liveData;
    }
}
