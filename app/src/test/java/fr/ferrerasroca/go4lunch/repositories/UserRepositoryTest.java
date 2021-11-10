package fr.ferrerasroca.go4lunch.repositories;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import fr.ferrerasroca.go4lunch.data.api.user.UserDatabase;
import fr.ferrerasroca.go4lunch.data.models.places.Place;
import fr.ferrerasroca.go4lunch.data.repositories.UserRepository;
import fr.ferrerasroca.go4lunch.data.repositories.UserRepositoryImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserRepositoryTest {

    @Test
    public void canIRetrieveUser() {
        UserDatabase userDatabase = Mockito.mock(UserDatabase.class);
        UserRepository userRepository = new UserRepositoryImpl(userDatabase);

        when(userDatabase.isCurrentUserLogged()).thenReturn(true);
        userRepository.retrieveUser(null);

        verify(userDatabase).getCurrentUser(any());
    }

    @Test
    public void canISignOutUser() {
        UserDatabase userDatabase = Mockito.mock(UserDatabase.class);
        UserRepository userRepository = new UserRepositoryImpl(userDatabase);

        userRepository.signOutCurrentUser();

        verify(userDatabase).signOutCurrentUser();
    }

    @Test
    public void canIRetrieveUsers() {
        UserDatabase userDatabase = Mockito.mock(UserDatabase.class);
        UserRepository userRepository = new UserRepositoryImpl(userDatabase);

        userRepository.retrieveUsers(null, null);
        userRepository.retrieveUsers("placeID", null);

        verify(userDatabase).getUsers(any());
        verify(userDatabase).getUsersByPlaceIDChoice(any(), any());
    }

    @Test
    public void canIRetrieveParticipantsForPlace() {
        UserDatabase userDatabase = Mockito.mock(UserDatabase.class);
        UserRepositoryImpl userRepository = new UserRepositoryImpl(userDatabase);

        UserRepositoryImpl.UsersForPlacesListener listener = placesWithParticipants -> { };

        ArrayList<Place> places = new ArrayList<>();
        Place place = new Place();
        place.setPlaceId("uid");
        places.add(place);

        userRepository.retrieveParticipantsForPlaces(places, listener);

        verify(userDatabase).getUsersByPlaceIDChoice(any(), any());
    }

    @Test
    public void canISetPlaceIDChoice() {
        UserDatabase userDatabase = Mockito.mock(UserDatabase.class);
        UserRepository userRepository = new UserRepositoryImpl(userDatabase);

        userRepository.setPlaceIDChoice("uid", "placeIDChoice", null);

        verify(userDatabase).setPlaceIDChoice(any(), any(), any());
    }

    @Test
    public void canISetLikedPlace() {
        UserDatabase userDatabase = Mockito.mock(UserDatabase.class);
        UserRepository userRepository = new UserRepositoryImpl(userDatabase);

        userRepository.setLikedPlaces("uid", new ArrayList<>(), null);

        verify(userDatabase).setLikedPlaces(any(), any(), any());
    }

    @Test
    public void canISetSettingDailyNotification() {
        UserDatabase userDatabase = Mockito.mock(UserDatabase.class);
        UserRepository userRepository = new UserRepositoryImpl(userDatabase);

        userRepository.setSettingsDailyNotification("uid", true, null);

        verify(userDatabase).setSettingsDailyNotification(any(), any(), any());
    }

}
