package fr.ferrerasroca.go4lunch.data.models.places.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import fr.ferrerasroca.go4lunch.data.models.places.Place;

public class NearbyPlacesResponse {

    @SerializedName("results")
    @Expose
    private List<Place> places = null;

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }
}