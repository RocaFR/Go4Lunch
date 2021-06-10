package fr.ferrerasroca.go4lunch.data.models.places;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("result")
    @Expose
    private Place place;

    public Place getPlace() { return place; }

    public void setPlace(Place place) { this.place = place; }
}