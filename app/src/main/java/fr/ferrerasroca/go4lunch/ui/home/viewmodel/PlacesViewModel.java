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

    private final PlacesRepository placesRepositoryImpl;

    private final MutableLiveData<List<Place>> _placesMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Place> _placeMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Place>> _placesChosenByUsersMutableLiveData = new MutableLiveData<>();

    public PlacesViewModel(PlacesRepository placesRepository) {
        this.placesRepositoryImpl = placesRepository;
    }

    public void retrieveNearbyPlaces(Location location) {
        placesRepositoryImpl.retrieveNearbyPlaces(location, _placesMutableLiveData::postValue);
    }

    public LiveData<List<Place>> getPlaces() { return _placesMutableLiveData; }

    public void retrievePlaceByID(String placeID) {
        placesRepositoryImpl.retrievePlaceDetails(placeID, _placeMutableLiveData::postValue);
    }

    public LiveData<Place> getPlace() { return _placeMutableLiveData; }

    public void retrievePlacesByUsers(List<User> users) {
        placesRepositoryImpl.retrievePlacesByUsers(users, _placesChosenByUsersMutableLiveData::postValue);
    }

    public LiveData<List<Place>> getPlacesChosenByUsers() { return _placesChosenByUsersMutableLiveData; }
}