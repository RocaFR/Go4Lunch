package fr.ferrerasroca.go4lunch.ui.home.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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

    public void getPlaces() {
        placesRepository.getPlaces(placeLikelihoods -> _listMutableLiveData.postValue(placeLikelihoods));
    }
}