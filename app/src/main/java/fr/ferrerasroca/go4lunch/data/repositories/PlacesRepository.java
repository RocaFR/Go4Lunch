package fr.ferrerasroca.go4lunch.data.repositories;

import android.location.Location;

import fr.ferrerasroca.go4lunch.data.api.places.PlacesCalls;
import fr.ferrerasroca.go4lunch.data.models.places.Results;
import retrofit2.Call;

public class PlacesRepository {


    public PlacesRepository() {}

    public Call<Results> getResults(Location location) {
        return PlacesCalls.getResults(location);
    }
}
