package fr.ferrerasroca.go4lunch.data.models.places.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import fr.ferrerasroca.go4lunch.data.models.places.Place;

public class PlaceDetailResponse {

    @SerializedName("result")
    @Expose
    private Place place;

    public Place getPlace() { return place; }

    public void setPlace(Place place) { this.place = place; }
}