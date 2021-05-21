package fr.ferrerasroca.go4lunch.data.repositories;

import fr.ferrerasroca.go4lunch.data.api.PlacesApi;

public class PlacesRepository {

    private final PlacesApi placesApi;

    public PlacesRepository(PlacesApi placesApi) {
        this.placesApi = placesApi;
    }

    public void getPlaces(PlacesApi.OnFindCurrentPlacesCompletedListener onFindCurrentPlacesCompletedListener) {
        placesApi.findCurrentPlaces(onFindCurrentPlacesCompletedListener);
    }
}
