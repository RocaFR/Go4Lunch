package fr.ferrerasroca.go4lunch.data.api.places;

import android.location.Location;

import fr.ferrerasroca.go4lunch.BuildConfig;
import fr.ferrerasroca.go4lunch.data.models.places.Results;
import retrofit2.Call;

public class PlacesCalls {

    public static Call<Results> getResults(Location location) {
        PlacesService placesService = PlacesService.retrofit.create(PlacesService.class);

        String latitude = Double.toString(location.getLatitude());
        String longitude = Double.toString(location.getLongitude());
        String stringLocation = latitude + "," + longitude;

        return placesService.getResults(stringLocation, BuildConfig.GOOGLE_API_KEY);
    }
}