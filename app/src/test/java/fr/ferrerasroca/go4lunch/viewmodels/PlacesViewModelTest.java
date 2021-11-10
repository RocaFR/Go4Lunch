package fr.ferrerasroca.go4lunch.viewmodels;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import android.location.Location;

import androidx.lifecycle.MutableLiveData;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import fr.ferrerasroca.go4lunch.data.repositories.PlacesRepository;
import fr.ferrerasroca.go4lunch.data.repositories.PlacesRepositoryImpl;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.PlacesViewModel;

@RunWith(MockitoJUnitRunner.class)
public class PlacesViewModelTest {

    @Test
    public void canIRetrieveNearbyPlaces() {
        PlacesRepository placesRepository = Mockito.mock(PlacesRepositoryImpl.class);
        PlacesViewModel placesViewModel = new PlacesViewModel(placesRepository);

        Location location = new Location("provider");

        placesViewModel.retrieveNearbyPlaces(location);
        verify(placesRepository).retrieveNearbyPlaces(any(), any());
    }

    @Test
    public void canIGetNearbyPlaces() {
        PlacesRepositoryImpl placesRepository = Mockito.mock(PlacesRepositoryImpl.class);
        PlacesViewModel placesViewModel = new PlacesViewModel(placesRepository);

        Assert.assertTrue(placesViewModel.getPlaces().getClass().isAssignableFrom(MutableLiveData.class));
    }

    @Test
    public void canIRetrievePlaceByID() {
        PlacesRepository placesRepository = Mockito.mock(PlacesRepositoryImpl.class);
        PlacesViewModel placesViewModel = new PlacesViewModel(placesRepository);

        placesViewModel.retrievePlaceByID("placeID");

        verify(placesRepository).retrievePlaceDetails(any(), any());
    }

    @Test
    public void canIGetPlace() {
        PlacesRepositoryImpl placesRepository = Mockito.mock(PlacesRepositoryImpl.class);
        PlacesViewModel placesViewModel = new PlacesViewModel(placesRepository);

        Assert.assertTrue(placesViewModel.getPlace().getClass().isAssignableFrom(MutableLiveData.class));
    }

    @Test
    public void canIRetrievePlacesByUsers() {
        PlacesRepository placesRepository = Mockito.mock(PlacesRepositoryImpl.class);
        PlacesViewModel placesViewModel = new PlacesViewModel(placesRepository);

        placesViewModel.retrievePlacesByUsers(new ArrayList<>());

        verify(placesRepository).retrievePlacesByUsers(any(), any());
    }

    @Test
    public void canIGetPlacesChosenByUsers() {
        PlacesRepository placesRepository = Mockito.mock(PlacesRepositoryImpl.class);
        PlacesViewModel placesViewModel = new PlacesViewModel(placesRepository);

        Assert.assertTrue(placesViewModel.getPlacesChosenByUsers().getClass().isAssignableFrom(MutableLiveData.class));
    }
}