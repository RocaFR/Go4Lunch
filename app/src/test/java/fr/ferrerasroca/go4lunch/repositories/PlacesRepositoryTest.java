package fr.ferrerasroca.go4lunch.repositories;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import android.location.Location;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import fr.ferrerasroca.go4lunch.data.api.places.PlacesServiceImpl;
import fr.ferrerasroca.go4lunch.data.repositories.PlacesRepositoryImpl;

@RunWith(MockitoJUnitRunner.class)
public class PlacesRepositoryTest {

    @Test
    public void canIRetrieveNearbyPlaces() {
        PlacesServiceImpl placesService = Mockito.mock(PlacesServiceImpl.class);
        PlacesRepositoryImpl placesRepository = new PlacesRepositoryImpl(placesService);

        Location location = Mockito.mock(Location.class);
        PlacesRepositoryImpl.NearbyPlacesRetrievedListener listener = places -> {
            Log.e("TAG", "canIRetrieveNearbyPlaces: on response test");
        };

        placesRepository.retrieveNearbyPlaces(location, listener);

        verify(placesService).getNearbyPlaces(any());
    }

    @Test
    public void canIRetrievePlaceDetails() {
        PlacesServiceImpl placesService = Mockito.mock(PlacesServiceImpl.class);
        PlacesRepositoryImpl placesRepository = new PlacesRepositoryImpl(placesService);

        PlacesRepositoryImpl.PlaceDetailsRetrievedListener listener = place -> { };
        placesRepository.retrievePlaceDetails("placeID", listener);

        verify(placesService).getPlaceDetails(any());
    }
}