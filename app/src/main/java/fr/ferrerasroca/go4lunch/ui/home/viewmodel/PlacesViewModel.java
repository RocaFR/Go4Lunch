package fr.ferrerasroca.go4lunch.ui.home.viewmodel;

import android.location.Location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import fr.ferrerasroca.go4lunch.data.models.User;
import fr.ferrerasroca.go4lunch.data.models.places.Place;
import fr.ferrerasroca.go4lunch.data.repositories.PlacesRepository;

public class PlacesViewModel extends ViewModel {

    private final PlacesRepository placesRepository;

    private final MutableLiveData<List<Place>> _placesMutableLiveData = new MutableLiveData<>();
    private final LiveData<List<Place>> places = _placesMutableLiveData;
    private final MutableLiveData<Place> _placeMutableLiveData = new MutableLiveData<>();
    private final LiveData<Place> place = _placeMutableLiveData;
    private final MutableLiveData<List<Place>> _placesChosenByUsersMutableLiveData = new MutableLiveData<>();
    private final LiveData<List<Place>> placesChosenByUsers = _placesChosenByUsersMutableLiveData;

    public PlacesViewModel(PlacesRepository placesRepository) {
        this.placesRepository = placesRepository;
    }

    public void retrieveNearbyPlaces(Location location) {
        placesRepository.retrieveNearbyPlaces(location, new PlacesRepository.NearbyPlacesRetrievedListener() {
            @Override
            public void onNearbyPlacesRetrieved(List<Place> places) {
                _placesMutableLiveData.postValue(places);
            }
        });
    }

    public LiveData<List<Place>> getPlaces() { return places; }

    public void retrievePlaceByID(String placeID) {
        placesRepository.retrievePlaceDetails(placeID, new PlacesRepository.PlaceDetailsRetrievedListener() {
            @Override
            public void onPlaceDetailsRetrieved(Place place) {
                _placeMutableLiveData.postValue(place);
            }
        });
    }

    public LiveData<Place> getPlace() { return place; }

    public void retrievePlacesByUsers(List<User> users) {
        placesRepository.retrievePlacesByUsers(users, new PlacesRepository.PlacesChosenByUsersRetrievedListener() {
            @Override
            public void onPlacesChosenByUsersRetrieved(List<Place> places) {
                _placesChosenByUsersMutableLiveData.postValue(places);
            }
        });
    }

    public LiveData<List<Place>> getPlacesChosenByUsers() { return placesChosenByUsers; }
}