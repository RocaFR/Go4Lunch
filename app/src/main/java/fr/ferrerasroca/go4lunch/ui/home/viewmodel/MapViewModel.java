package fr.ferrerasroca.go4lunch.ui.home.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.PlaceLikelihood;

import java.util.List;

import fr.ferrerasroca.go4lunch.data.repositories.PlacesRepository;

public class MapViewModel extends ViewModel {

    private final PlacesRepository placesRepository;
    private MutableLiveData<List<PlaceLikelihood>> _listMutableLiveData = new MutableLiveData<>();
    public LiveData<List<PlaceLikelihood>> placeLikelihoodLiveData = _listMutableLiveData;

    public MapViewModel(PlacesRepository placesRepository) {
        this.placesRepository = placesRepository;
    }

    // ###########
    // GOOGLE MAPS
    // ###########

    public MapView getMapView() {
        return placesRepository.getMapView();
    }

    public void addGoogleMapMarker(String title, LatLng latLng) {
        placesRepository.addGoogleMapMarker(title, latLng);
    }

    public void moveGoogleMapCamera(LatLng latLng) {
        placesRepository.moveGoogleMapCamera(latLng);
    }

    public void getLastLocation(Context context) {
        placesRepository.getLastLocation(context);
    }

    // #############
    // GOOGLE PLACES
    // #############

    public void getPlaces() {
        placesRepository.getPlaces(placeLikelihoods -> {
            _listMutableLiveData.postValue(placeLikelihoods);
        });
    }
}