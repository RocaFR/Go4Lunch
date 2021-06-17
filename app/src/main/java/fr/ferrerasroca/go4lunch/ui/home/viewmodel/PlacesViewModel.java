package fr.ferrerasroca.go4lunch.ui.home.viewmodel;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import fr.ferrerasroca.go4lunch.data.api.places.PlacesCalls;
import fr.ferrerasroca.go4lunch.data.models.User;
import fr.ferrerasroca.go4lunch.data.models.places.Place;
import fr.ferrerasroca.go4lunch.data.models.places.responses.PlaceDetailResponse;
import fr.ferrerasroca.go4lunch.data.repositories.PlacesRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlacesViewModel extends ViewModel {

    private final PlacesRepository placesRepository;

    public PlacesViewModel(PlacesRepository placesRepository) {
        this.placesRepository = placesRepository;
    }

    public void retrieveNearbyPlaces(Location location) {
        placesRepository.retrieveNearbyPlaces(location);
    }

    public LiveData<List<Place>> getPlaces() {
        return placesRepository.getPlaces();
    }

    public void retrievePlaceByID(String placeID) {
        placesRepository.retrievePlaceDetails(placeID);
    }

    public LiveData<Place> getPlace() {
        return placesRepository.getPlace();
    }

    public void retrievePlacesByUsers(List<User> users) {
        placesRepository.retrievePlacesByUsers(users);
    }

    public LiveData<List<Place>> getPlacesChosenByUsers() {
        return placesRepository.getPlacesChosenByUsers();
    }
}