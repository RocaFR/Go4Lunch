package fr.ferrerasroca.go4lunch.ui.home.viewmodel;

import android.location.Location;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import fr.ferrerasroca.go4lunch.BuildConfig;
import fr.ferrerasroca.go4lunch.data.models.places.Place;
import fr.ferrerasroca.go4lunch.data.models.places.responses.NearbyPlacesResponse;
import fr.ferrerasroca.go4lunch.data.repositories.PlacesRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlacesViewModel extends ViewModel {

    private final PlacesRepository placesRepository;

    private final MutableLiveData<List<Place>> _resultsLiveData = new MutableLiveData<>();
    private final LiveData<List<Place>> placesLiveData = _resultsLiveData;

    public PlacesViewModel(PlacesRepository placesRepository) {
        this.placesRepository = placesRepository;
    }

    public void retrievePlaces(Location location) {
        placesRepository.getResults(location).enqueue(new Callback<NearbyPlacesResponse>() {
            @Override
            public void onResponse(Call<NearbyPlacesResponse> call, Response<NearbyPlacesResponse> response) {
                NearbyPlacesResponse nearbyPlacesResponse = response.body();
                if (response.isSuccessful() && nearbyPlacesResponse != null) {
                    List<Place> places = nearbyPlacesResponse.getPlaces().stream().map(new Function<Place, Place>() {
                        @Override
                        public Place apply(Place place) {
                            if (!place.getPhotos().isEmpty()) {
                                String photoUrl = place.getPhotos().get(0).getPhotoReference();
                                place.setPhotoUrl("https://maps.googleapis.com/maps/api/place/photo?key=" + BuildConfig.GOOGLE_API_KEY + "&photoreference=" + photoUrl + "&maxwidth=500");
                            }
                            String distance = calculDistanceBetweenUserAndRestaurant(place, location);
                            place.setDistanceFromUser(distance);
                            return place;
                        }
                    }).collect(Collectors.toList());
                    _resultsLiveData.postValue(places);
                }
            }

            @Override
            public void onFailure(Call<NearbyPlacesResponse> call, Throwable t) {
                Log.e("TAG", "onFailure: " +  t.getMessage());
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
        String distance = Integer.toString(intDistance);

        return distance;
    }

    public LiveData<List<Place>> getPlaces() {
        return placesLiveData;
    }

    public void retrievePlace(String placeID) {
        placesRepository.retrievePlaceByID(placeID);
    }

    public LiveData<Place> getPlace() {
        return placesRepository.getPlace();
    }
}