package fr.ferrerasroca.go4lunch.data.repositories;


import android.location.Location;

import java.util.List;

import fr.ferrerasroca.go4lunch.data.models.User;

public interface PlacesRepository {

    void retrieveNearbyPlaces(Location location, PlacesRepositoryImpl.NearbyPlacesRetrievedListener listener);

    void retrievePlaceDetails(String placeID, PlacesRepositoryImpl.PlaceDetailsRetrievedListener listener);

    void retrievePlacesByUsers(List<User> users, PlacesRepositoryImpl.PlacesChosenByUsersRetrievedListener listener);

}
