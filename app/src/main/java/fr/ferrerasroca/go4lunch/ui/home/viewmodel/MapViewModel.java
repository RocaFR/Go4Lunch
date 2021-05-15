package fr.ferrerasroca.go4lunch.ui.home.viewmodel;

import android.view.View;

import androidx.lifecycle.ViewModel;

import com.google.android.gms.maps.MapView;

import fr.ferrerasroca.go4lunch.data.repositories.MapRepository;

public class MapViewModel extends ViewModel {

    private final MapRepository mapRepository;

    public MapViewModel(MapRepository mapRepository) {
        this.mapRepository = mapRepository;
    }

    public void configureMap(View view) {
        this.mapRepository.configureMap(view);
    }

    public MapView getMapView() {
        return mapRepository.getMapView();
    }
}
