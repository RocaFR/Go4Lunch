package fr.ferrerasroca.go4lunch.data.api.places;


import android.location.Location;

import fr.ferrerasroca.go4lunch.BuildConfig;
import fr.ferrerasroca.go4lunch.data.models.places.responses.NearbyPlacesResponse;
import fr.ferrerasroca.go4lunch.data.models.places.responses.PlaceDetailResponse;
import retrofit2.Call;

public class PlacesServiceImpl implements PlacesService {

    private final PlacesCalls placesCalls;

    public PlacesServiceImpl(PlacesCalls placesCalls) {
        this.placesCalls = placesCalls;
    }

    @Override
    public Call<NearbyPlacesResponse> getNearbyPlaces(Location location) {
        String latitude = Double.toString(location.getLatitude());
        String longitude = Double.toString(location.getLongitude());
        String stringLocation = latitude + "," + longitude;

        return placesCalls.getNearbyPlaces(stringLocation, BuildConfig.MAPS_API_KEY);
    }

    @Override
    public Call<PlaceDetailResponse> getPlaceDetails(String placeID) {
        return placesCalls.getPlaceDetails(placeID, BuildConfig.MAPS_API_KEY);
    }
}
