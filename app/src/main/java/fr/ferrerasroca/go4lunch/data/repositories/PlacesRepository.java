package fr.ferrerasroca.go4lunch.data.repositories;

import android.location.Location;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import fr.ferrerasroca.go4lunch.BuildConfig;
import fr.ferrerasroca.go4lunch.data.api.places.PlacesCalls;
import fr.ferrerasroca.go4lunch.data.models.User;
import fr.ferrerasroca.go4lunch.data.models.places.Place;
import fr.ferrerasroca.go4lunch.data.models.places.responses.NearbyPlacesResponse;
import fr.ferrerasroca.go4lunch.data.models.places.responses.PlaceDetailResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlacesRepository {

    public interface NearbyPlacesRetrievedListener {
        void onNearbyPlacesRetrieved(List<Place> places);
    }

    public interface PlaceDetailsRetrievedListener {
        void onPlaceDetailsRetrieved(Place place);
    }

    public interface PlacesChosenByUsersRetrievedListener {
        void onPlacesChosenByUsersRetrieved(List<Place> places);
    }

    public PlacesRepository() {}

    public void retrieveNearbyPlaces(Location location, NearbyPlacesRetrievedListener listener) { //todo THIS ONE
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
                                place.setPhotoUrl("https://maps.googleapis.com/maps/api/place/photo?key=" + BuildConfig.MAPS_API_KEY + "&photoreference=" + photoUrl + "&maxwidth=500");
                            }
                            String distance = calculateDistanceBetweenUserAndRestaurant(place, location);
                            place.setDistanceFromUser(distance);
                        }
                        listener.onNearbyPlacesRetrieved(places);
                    }
                }
            }
            @Override
            public void onFailure(@NotNull Call<NearbyPlacesResponse> call, @NotNull Throwable t) {
                Log.e("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    private String calculateDistanceBetweenUserAndRestaurant(Place place, Location userLocation) {
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

    public void retrievePlaceDetails(String placeID, PlaceDetailsRetrievedListener listener) {
        PlacesCalls.getPlaceDetails(placeID).enqueue(new Callback<PlaceDetailResponse>() {
            @Override
            public void onResponse(@NotNull Call<PlaceDetailResponse> call, @NotNull Response<PlaceDetailResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Place place = response.body().getPlace();
                    if (!place.getPhotos().isEmpty()) {
                        String photoReference = place.getPhotos().get(0).getPhotoReference();
                        place.setPhotoUrl("https://maps.googleapis.com/maps/api/place/photo?key=" + BuildConfig.MAPS_API_KEY + "&photoreference=" + photoReference + "&maxwidth=500");
                    }
                    listener.onPlaceDetailsRetrieved(place);
                }
            }

            @Override
            public void onFailure(@NotNull Call<PlaceDetailResponse> call, @NotNull Throwable t) {
                Log.e("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    public void retrievePlacesByUsers(List<User> users, PlacesChosenByUsersRetrievedListener listener) {
        List<Place> usersPlacesChoice = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            int actualItem = i;
            User user = users.get(i);
            String placeIDChoice = user.getPlaceIDChoice();

            if (placeIDChoice != null && !placeIDChoice.isEmpty()) {
                PlacesCalls.getPlaceDetails(user.getPlaceIDChoice()).enqueue(new Callback<PlaceDetailResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<PlaceDetailResponse> call, @NotNull Response<PlaceDetailResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            Place place = response.body().getPlace();
                            usersPlacesChoice.add(place);
                            if (isLastUser(actualItem, users.size())) {
                                listener.onPlacesChosenByUsersRetrieved(usersPlacesChoice);
                            }
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<PlaceDetailResponse> call, @NotNull Throwable t) {
                        Log.e("TAG", "onFailure: " + t.getMessage());
                    }
                });
            }
        }
    }

    private Boolean isLastUser(int userPosition, int usersSize) {
        return userPosition == usersSize -1;
    }
}
