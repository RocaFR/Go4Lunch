package fr.ferrerasroca.go4lunch.data.repositories;

import android.location.Location;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import fr.ferrerasroca.go4lunch.BuildConfig;
import fr.ferrerasroca.go4lunch.data.api.places.PlacesCalls;
import fr.ferrerasroca.go4lunch.data.models.places.Place;
import fr.ferrerasroca.go4lunch.data.models.places.responses.NearbyPlacesResponse;
import fr.ferrerasroca.go4lunch.data.models.places.responses.PlaceDetailResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlacesRepository {

    private final MutableLiveData<List<Place>> _placesMutableLiveData = new MutableLiveData<>();
    private final LiveData<List<Place>> placesLiveData = _placesMutableLiveData;
    private final MutableLiveData<Place> _placeMutableLiveData = new MutableLiveData<>();
    private final LiveData<Place> placeLiveData = _placeMutableLiveData;

    public PlacesRepository() {}

    public void retrieveNearbyPlaces(Location location) {
        PlacesCalls.getNearbyPlaces(location).enqueue(new Callback<NearbyPlacesResponse>() {
            @Override
            public void onResponse(@NotNull Call<NearbyPlacesResponse> call, @NotNull Response<NearbyPlacesResponse> response) {
                NearbyPlacesResponse nearbyPlacesResponse = response.body();
                if (response.isSuccessful() && nearbyPlacesResponse != null) {
                    List<Place> places = nearbyPlacesResponse.getPlaces();
                    if (!places.isEmpty()) {
                        for (Place place : places) {
                            if (!place.getPhotos().isEmpty()) {
                                String photoUrl = place.getPhotos().get(0).getPhotoReference();
                                place.setPhotoUrl("https://maps.googleapis.com/maps/api/place/photo?key=" + BuildConfig.GOOGLE_API_KEY + "&photoreference=" + photoUrl + "&maxwidth=500");
                            }
                            String distance = calculDistanceBetweenUserAndRestaurant(place, location);
                            place.setDistanceFromUser(distance);
                        }
                        _placesMutableLiveData.postValue(places);
                    }
                }
            }
            @Override
            public void onFailure(@NotNull Call<NearbyPlacesResponse> call, @NotNull Throwable t) {
                Log.e("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    private String calculDistanceBetweenUserAndRestaurant(Place place, Location userLocation) {
        double startLatitude = userLocation.getLatitude();
        double startLongitude = userLocation.getLongitude();
        double endLatitude = place.getGeometry().getLocation().getLat();
        double endLongitude = place.getGeometry().getLocation().getLng();
        float[] results = new float[1];

        Location.distanceBetween(startLatitude, startLongitude, endLatitude, endLongitude, results);
        float f = results[0];
        int intDistance = Math.round(f);
        return Integer.toString(intDistance);
    }

    public LiveData<List<Place>> getPlaces() {
        return placesLiveData;
    }

    public void retrievePlaceDetails(String placeID) {
        PlacesCalls.getPlaceDetails(placeID).enqueue(new Callback<PlaceDetailResponse>() {
            @Override
            public void onResponse(@NotNull Call<PlaceDetailResponse> call, @NotNull Response<PlaceDetailResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Place place = response.body().getPlace();
                    if (!place.getPhotos().isEmpty()) {
                        String photoReference = place.getPhotos().get(0).getPhotoReference();
                        place.setPhotoUrl("https://maps.googleapis.com/maps/api/place/photo?key=" + BuildConfig.GOOGLE_API_KEY + "&photoreference=" + photoReference + "&maxwidth=500");
                    }
                    _placeMutableLiveData.postValue(place);
                }
            }

            @Override
            public void onFailure(@NotNull Call<PlaceDetailResponse> call, @NotNull Throwable t) {
                Log.e("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    public LiveData<Place> getPlace() {
        return this.placeLiveData;
    }
}
