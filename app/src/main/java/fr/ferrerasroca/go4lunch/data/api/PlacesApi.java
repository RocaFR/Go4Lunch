package fr.ferrerasroca.go4lunch.data.api;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.PlaceLikelihood;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

import fr.ferrerasroca.go4lunch.R;

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

        placesClient.findCurrentPlace(this.currentPlaceRequest).addOnCompleteListener(new OnCompleteListener<FindCurrentPlaceResponse>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<FindCurrentPlaceResponse> task) {
                onFindCurrentPlacesCompletedListener.onFindCurrentPlacesCompleted(task.getResult().getPlaceLikelihoods());
            }
        });
    }

    private void configureCurrentPlaceRequest() {
        List<Place.Field> placeField = Arrays.asList(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG);
        this.currentPlaceRequest = FindCurrentPlaceRequest.newInstance(placeField);
    }
}