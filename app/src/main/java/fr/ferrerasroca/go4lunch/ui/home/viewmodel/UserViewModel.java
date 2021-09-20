package fr.ferrerasroca.go4lunch.ui.home.viewmodel;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import fr.ferrerasroca.go4lunch.data.models.User;
import fr.ferrerasroca.go4lunch.data.models.places.Place;
import fr.ferrerasroca.go4lunch.data.repositories.UserRepository;

public class UserViewModel extends ViewModel {

    public interface PlaceIDChoiceSettedListener {
        void onPlaceIDChoiceSetted(String placeID);
    }

    public interface LikedPlacesSettedListener {
        void onLikedPlacesSetted();
    }

    public interface DailyNotificationChoiceListener {
        void onDailyNotificationChoiceSetted(Boolean dailyNotificationChoice);
    }

    private final UserRepository userRepository;

    private final MutableLiveData<User> _userMutableLiveData = new MutableLiveData<>();
    public LiveData<User> userLiveData = _userMutableLiveData;
    private final MutableLiveData<List<Place>> _mutableLiveDataPlacesWithParticipants = new MutableLiveData<>();
    private final LiveData<List<Place>> liveDataPlacesWithParticipants = _mutableLiveDataPlacesWithParticipants;
    private final MutableLiveData<List<User>> _mutableLiveDataUsers = new MutableLiveData<>();
    private final LiveData<List<User>> liveDataUsers = _mutableLiveDataUsers;

    public UserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void launchFacebookSignInActivity(Fragment fragment) {
        userRepository.launchFacebookSignInActivity(fragment);
    }

    public void launchGoogleSignInActivity(Fragment fragment) {
        userRepository.launchGoogleSignInActivity(fragment);
    }

    public void createUserIfSuccess(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data, Fragment fragment) {
        userRepository.createUserIfSuccess(requestCode, resultCode, data, fragment);
    }

    public Boolean isCurrentUserLogged() {
        return userRepository.isCurrentUserLogged();
    }

    public void retrieveUser() {
        userRepository.retrieveUser(_userMutableLiveData::postValue);
    }

    public LiveData<User> getUser() {
        return this.userLiveData;
    }

    public void signOutUser() {
        userRepository.signOutCurrentUser();
    }

    public void retrieveUsers(@Nullable String placeID) {
        userRepository.retrieveUsers(placeID, _mutableLiveDataUsers::postValue);
    }

    public LiveData<List<User>> getUsers() {
        return liveDataUsers;
    }

    public void retrieveParticipantsForPlaces(List<Place> places) {
        userRepository.retrieveParticipantsForPlaces(places, _mutableLiveDataPlacesWithParticipants::postValue);
    }

    public LiveData<List<Place>> getPlacesWithParticipants() {
        return liveDataPlacesWithParticipants;
    }

    public void setPlaceIDChoice(String userUid, String placeIDChoice, PlaceIDChoiceSettedListener listener) {
        userRepository.setPlaceIDChoice(userUid, placeIDChoice, listener);
    }

    public void setLikedPlaces(String userUid, List<String> placesLiked, LikedPlacesSettedListener listener) {
        userRepository.setLikedPlaces(userUid, placesLiked, listener);
    }

    public void setSettingsDailyNotification(String userUid, Boolean dailyNotificationChoice, DailyNotificationChoiceListener listener) {
        userRepository.setSettingsDailyNotification(userUid, dailyNotificationChoice, listener);
    }

}