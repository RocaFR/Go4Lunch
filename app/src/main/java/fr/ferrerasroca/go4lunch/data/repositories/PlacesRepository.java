package fr.ferrerasroca.go4lunch.data.repositories;

import android.content.Context;

import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;

import fr.ferrerasroca.go4lunch.ui.home.view.GoogleMapsComponent;
import fr.ferrerasroca.go4lunch.data.api.PlacesApi;

public class PlacesRepository {

    private final GoogleMapsComponent googleMapsComponent;
    private final PlacesApi placesApi;

    public PlacesRepository(GoogleMapsComponent googleMapsComponent, PlacesApi placesApi) {
        this.googleMapsComponent = googleMapsComponent;
        this.placesApi = placesApi;
    }

    // ###########
    // GOOGLE MAPS
    // ###########

    public MapView getMapView() {
        return googleMapsComponent.getMapView();
    }

    public void addGoogleMapMarker(String title, LatLng latLng) {
        googleMapsComponent.addGoogleMapMarker(title, latLng);
    }

    public void moveGoogleMapCamera(LatLng latLng) {
        googleMapsComponent.moveGoogleMapCamera(latLng);
    }

    public void getLastLocation(Context context) {
        googleMapsComponent.getLastLocation(context);
    }

    // #############
    // GOOGLE PLACES
    // #############

    public void getPlaces(PlacesApi.OnFindCurrentPlacesCompletedListener onFindCurrentPlacesCompletedListener) {
        placesApi.findCurrentPlaces(onFindCurrentPlacesCompletedListener);
    }
}
