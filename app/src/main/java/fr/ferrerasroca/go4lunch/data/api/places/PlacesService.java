package fr.ferrerasroca.go4lunch.data.api.places;

import android.location.Location;

import fr.ferrerasroca.go4lunch.data.models.places.responses.NearbyPlacesResponse;
import fr.ferrerasroca.go4lunch.data.models.places.responses.PlaceDetailResponse;
import retrofit2.Call;

public interface PlacesService {

    Call<NearbyPlacesResponse> getNearbyPlaces(Location location);

    Call<PlaceDetailResponse> getPlaceDetails(String placeID);

}
