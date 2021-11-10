package fr.ferrerasroca.go4lunch.viewmodels;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import androidx.lifecycle.MutableLiveData;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import fr.ferrerasroca.go4lunch.data.repositories.UserRepository;
import fr.ferrerasroca.go4lunch.data.repositories.UserRepositoryImpl;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.UserViewModel;

@RunWith(MockitoJUnitRunner.class)
public class UserViewModelTest {

    @Test
    public void canIRetrieveUser() {
        UserRepository userRepository = Mockito.mock(UserRepositoryImpl.class);
        UserViewModel userViewModel = new UserViewModel(userRepository);

        userViewModel.retrieveUser();

        verify(userRepository).retrieveUser(any());
    }

    @Test
    public void canIGetUser() {
        UserRepositoryImpl userRepository = Mockito.mock(UserRepositoryImpl.class);
        UserViewModel userViewModel = new UserViewModel(userRepository);

        Assert.assertTrue(userViewModel.getUser().getClass().isAssignableFrom(MutableLiveData.class));
    }

    @Test
    public void canIRetrieveUsers() {
        UserRepository userRepository = Mockito.mock(UserRepositoryImpl.class);
        UserViewModel userViewModel = new UserViewModel(userRepository);

        userViewModel.retrieveUsers("placeID");

        verify(userRepository).retrieveUsers(any(), any());
    }

    @Test
    public void canIGetUsers() {
        UserRepositoryImpl userRepository = Mockito.mock(UserRepositoryImpl.class);
        UserViewModel userViewModel = new UserViewModel(userRepository);

        Assert.assertTrue(userViewModel.getUsers().getClass().isAssignableFrom(MutableLiveData.class));
    }

    @Test
    public void canIRetrieveParticipantsForPlaces() {
        UserRepository userRepository = Mockito.mock(UserRepositoryImpl.class);
        UserViewModel userViewModel = new UserViewModel(userRepository);

        userViewModel.retrieveParticipantsForPlaces(new ArrayList<>());

        verify(userRepository).retrieveParticipantsForPlaces(any(), any());
    }

    @Test
    public void canIGetPlacesWithParticipants() {
        UserRepositoryImpl userRepository = Mockito.mock(UserRepositoryImpl.class);
        UserViewModel userViewModel =  new UserViewModel(userRepository);

        Assert.assertTrue(userViewModel.getPlacesWithParticipants().getClass().isAssignableFrom(MutableLiveData.class));
    }

    @Test
    public void canISetPlaceIDChoice() {
        UserRepository userRepository = Mockito.mock(UserRepositoryImpl.class);
        UserViewModel userViewModel = new UserViewModel(userRepository);

        userViewModel.setPlaceIDChoice("uid", "placeID", null);

        verify(userRepository).setPlaceIDChoice(any(), any(), any());
    }

    @Test
    public void canISetLikedPlaces() {
        UserRepository userRepository = Mockito.mock(UserRepositoryImpl.class);
        UserViewModel userViewModel = new UserViewModel(userRepository);

        userViewModel.setLikedPlaces("uid", new ArrayList<>(), null);

        verify(userRepository).setLikedPlaces(any(), any(), any());
    }

    @Test
    public void canISetSettingsDailyNotification() {
        UserRepository userRepository = Mockito.mock(UserRepositoryImpl.class);
        UserViewModel userViewModel = new UserViewModel(userRepository);

        userViewModel.setSettingsDailyNotification("uid", true, null);

        verify(userRepository).setSettingsDailyNotification(any(), any(), any());
    }

    @Test
    public void canISignOutUser() {
        UserRepositoryImpl userRepository = Mockito.mock(UserRepositoryImpl.class);
        UserViewModel userViewModel = new UserViewModel(userRepository);

        userViewModel.signOutUser();

        verify(userRepository).signOutCurrentUser();
    }

    @Test
    public void canICheckIfCurrentUserIsLogged() {
        UserRepositoryImpl userRepository = Mockito.mock(UserRepositoryImpl.class);
        UserViewModel userViewModel = new UserViewModel(userRepository);

        userViewModel.isCurrentUserLogged();

        verify(userRepository).isCurrentUserLogged();
    }
}