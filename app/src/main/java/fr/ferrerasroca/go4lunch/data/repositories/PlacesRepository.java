package fr.ferrerasroca.go4lunch.data.repositories;

import android.location.Location;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import fr.ferrerasroca.go4lunch.BuildConfig;
import fr.ferrerasroca.go4lunch.data.api.places.PlacesCalls;
import fr.ferrerasroca.go4lunch.data.models.places.Place;
import fr.ferrerasroca.go4lunch.data.models.places.Result;
import fr.ferrerasroca.go4lunch.data.models.places.Results;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlacesRepository {

    private MutableLiveData<Place> _placeMutableLiveData = new MutableLiveData<>();
    private LiveData<Place> placeLiveData = _placeMutableLiveData;

    public PlacesRepository() {}

    public Call<Results> getResults(Location location) {
        return PlacesCalls.getResults(location);
    }

    public void retrievePlaceByID(String placeID) {
        PlacesCalls.getPlaceByID(placeID).enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
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
            public void onFailure(Call<Result> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    public LiveData<Place> getPlace() {
        return this.placeLiveData;
    }
}
