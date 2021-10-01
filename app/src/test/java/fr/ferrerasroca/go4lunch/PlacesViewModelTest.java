package fr.ferrerasroca.go4lunch;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        PlacesViewModel placesViewModel = Mockito.mock(PlacesViewModel.class);

        when(placesViewModel.getPlaces()).thenReturn(new MutableLiveData<>());
        placesViewModel.getPlaces();

        verify(placesViewModel).getPlaces();
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
        PlacesViewModel placesViewModel = Mockito.mock(PlacesViewModel.class);

        when(placesViewModel.getPlace()).thenReturn(new MutableLiveData<>());
        placesViewModel.getPlace();

        verify(placesViewModel).getPlace();
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
        PlacesViewModel placesViewModel = Mockito.mock(PlacesViewModel.class);

        when(placesViewModel.getPlacesChosenByUsers()).thenReturn(new MutableLiveData<>());
        placesViewModel.getPlacesChosenByUsers();

        verify(placesViewModel).getPlacesChosenByUsers();
        Assert.assertTrue(placesViewModel.getPlacesChosenByUsers().getClass().isAssignableFrom(MutableLiveData.class));
    }
}