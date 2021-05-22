package fr.ferrerasroca.go4lunch.data.api;

import android.annotation.SuppressLint;

import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlacesApi {

    public interface OnFindCurrentPlacesCompletedListener{
        void onFindCurrentPlacesCompleted(List<PlaceLikelihood> placeLikelihoods);
    }

    private final PlacesClient placesClient;
    private FindCurrentPlaceRequest currentPlaceRequest;

    public PlacesApi(PlacesClient placesClient) {
        this.placesClient = placesClient;
    }

    @SuppressLint("MissingPermission")
    public void findCurrentPlaces(OnFindCurrentPlacesCompletedListener onFindCurrentPlacesCompletedListener) {
        this.configureCurrentPlaceRequest();

        placesClient.findCurrentPlace(this.currentPlaceRequest).addOnCompleteListener(task -> {
            List<PlaceLikelihood> restaurantPlaceLikelihoods = this.filterPlacesByType(task.getResult().getPlaceLikelihoods(), Place.Type.RESTAURANT);
            onFindCurrentPlacesCompletedListener.onFindCurrentPlacesCompleted(restaurantPlaceLikelihoods);
        });
    }

    private void configureCurrentPlaceRequest() {
        List<Place.Field> placeField = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.TYPES);
        this.currentPlaceRequest = FindCurrentPlaceRequest.newInstance(placeField);
    }

    private List<PlaceLikelihood> filterPlacesByType(List<PlaceLikelihood> places, Place.Type type) {
        List<PlaceLikelihood> filteredPlaces = new ArrayList<>();
        for (PlaceLikelihood place : places) {
            if (place.getPlace().getTypes().contains(type)) {
                filteredPlaces.add(place);
            }
        }
        return filteredPlaces;
    }
}