package fr.ferrerasroca.go4lunch.data.api.places;

import android.location.Location;

import fr.ferrerasroca.go4lunch.BuildConfig;
import fr.ferrerasroca.go4lunch.data.models.places.responses.PlaceDetailResponse;
import fr.ferrerasroca.go4lunch.data.models.places.responses.NearbyPlacesResponse;
import retrofit2.Call;

public class PlacesCalls {

    public static Call<NearbyPlacesResponse> getNearbyPlaces(Location location) {
        PlacesService placesService = PlacesService.retrofit.create(PlacesService.class);

        String latitude = Double.toString(location.getLatitude());
        String longitude = Double.toString(location.getLongitude());
        String stringLocation = latitude + "," + longitude;

        return placesService.getNearbyPlaces(stringLocation, BuildConfig.GOOGLE_API_KEY);
    }

    public static Call<PlaceDetailResponse> getPlaceDetails(String placeID) {
        PlacesService placesService = PlacesService.retrofit.create(PlacesService.class);

        return placesService.getPlaceDetails(placeID, BuildConfig.GOOGLE_API_KEY);
    }
}
