package fr.ferrerasroca.go4lunch.data.repositories;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import fr.ferrerasroca.go4lunch.data.api.places.PlacesServiceImpl;
import fr.ferrerasroca.go4lunch.data.models.User;
import fr.ferrerasroca.go4lunch.data.models.places.Photo;
import fr.ferrerasroca.go4lunch.data.models.places.Place;
import fr.ferrerasroca.go4lunch.data.models.places.responses.PlaceDetailResponse;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.mock.Calls;

@RunWith(MockitoJUnitRunner.class)
public class PlacesRepositoryTest {

    @Test
    public void canIRetrievePlaceDetails() {
        PlacesServiceImpl placesService = Mockito.mock(PlacesServiceImpl.class);
        PlacesRepositoryImpl placesRepository = new PlacesRepositoryImpl(placesService);

        when(placesService.getPlaceDetails("placeID"))
                .thenAnswer((Answer<Call<PlaceDetailResponse>>) invocation -> Calls.response(Response.success(generatePlaceDetailsSuccessResponse())));

        PlacesRepositoryImpl.PlaceDetailsRetrievedListener listener = Mockito.mock(PlacesRepositoryImpl.PlaceDetailsRetrievedListener.class);
        placesRepository.retrievePlaceDetails("placeID", listener);

        verify(placesService).getPlaceDetails(any());
        verify(listener).onPlaceDetailsRetrieved(any());
    }

    private PlaceDetailResponse generatePlaceDetailsSuccessResponse() {
        PlaceDetailResponse placeDetailResponse = new PlaceDetailResponse();
        Place place = new Place();
        List<Photo> photos = new ArrayList<>();
        Photo photo = new Photo();
        photo.setPhotoReference("reference");
        photos.add(photo);
        place.setPhotos(photos);
        placeDetailResponse.setPlace(place);

        return placeDetailResponse;
    }

    @Test
    public void canIRetrievePlaceByUsers() {
        PlacesServiceImpl placesService = Mockito.mock(PlacesServiceImpl.class);
        PlacesRepositoryImpl placesRepository = new PlacesRepositoryImpl(placesService);

        when(placesService.getPlaceDetails("placeID"))
                .thenAnswer((Answer<Call<PlaceDetailResponse>>) invocation -> Calls.response(Response.success(generatePlaceDetailsSuccessResponse())));

        User user = new User();
        user.setPlaceIDChoice("placeID");

        List<User> users = new ArrayList<>();
        users.add(user);

        PlacesRepositoryImpl.PlacesChosenByUsersRetrievedListener listener = Mockito.mock(PlacesRepositoryImpl.PlacesChosenByUsersRetrievedListener.class);
        placesRepository.retrievePlacesByUsers(users, listener);

        verify(placesService).getPlaceDetails(any());
        verify(listener).onPlacesChosenByUsersRetrieved(any());
    }
}