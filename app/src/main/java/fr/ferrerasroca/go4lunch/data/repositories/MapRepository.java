package fr.ferrerasroca.go4lunch.data.repositories;

import android.content.Context;
import android.view.View;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;

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

    public void addGoogleMapMarker(String title, LatLng latLng) {
        googleMapsApi.addGoogleMapMarker(title, latLng);
    }

    public void moveGoogleMapCamera(LatLng latLng) {
        googleMapsApi.moveGoogleMapCamera(latLng);
    }

    public void getLastLocation(Context context) {
        googleMapsApi.getLastLocation(context);
    }
}
