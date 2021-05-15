package fr.ferrerasroca.go4lunch.data.repositories;

import android.view.View;

import com.google.android.gms.maps.MapView;

import fr.ferrerasroca.go4lunch.data.api.GoogleMapsApi;

public class MapRepository {

    private final GoogleMapsApi googleMapsApi;

    public MapRepository() {
        this.googleMapsApi = new GoogleMapsApi();
    }

    public void configureMap(View view) {
        googleMapsApi.configureGoogleMaps(view);
    }

    public MapView getMapView() {
        return googleMapsApi.getMapView();
    }
}
