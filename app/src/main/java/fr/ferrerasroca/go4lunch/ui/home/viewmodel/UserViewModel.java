package fr.ferrerasroca.go4lunch.ui.home.viewmodel;

import androidx.annotation.Nullable;
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

    private final UserRepository userRepositoryImpl;

    private final MutableLiveData<User> _userMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<List<Place>> _mutableLiveDataPlacesWithParticipants = new MutableLiveData<>();
    private final MutableLiveData<List<User>> _mutableLiveDataUsers = new MutableLiveData<>();

    public UserViewModel(UserRepository userRepositoryImpl) {
        this.userRepositoryImpl = userRepositoryImpl;
    }

    public Boolean isCurrentUserLogged() {
        return userRepositoryImpl.isCurrentUserLogged();
    }

    public void retrieveUser() {
        userRepositoryImpl.retrieveUser(_userMutableLiveData::postValue);
    }

    public LiveData<User> getUser() {
        return this._userMutableLiveData;
    }

    public void signOutUser() {
        userRepositoryImpl.signOutCurrentUser();
    }

    public void retrieveUsers(@Nullable String placeID) {
        userRepositoryImpl.retrieveUsers(placeID, _mutableLiveDataUsers::postValue);
    }

    public LiveData<List<User>> getUsers() {
        return _mutableLiveDataUsers;
    }

    public void retrieveParticipantsForPlaces(List<Place> places) {
        userRepositoryImpl.retrieveParticipantsForPlaces(places, _mutableLiveDataPlacesWithParticipants::postValue);
    }

    public LiveData<List<Place>> getPlacesWithParticipants() {
        return _mutableLiveDataPlacesWithParticipants;
    }

    public void setPlaceIDChoice(String userUid, String placeIDChoice, PlaceIDChoiceSettedListener listener) {
        userRepositoryImpl.setPlaceIDChoice(userUid, placeIDChoice, listener);
    }

    public void setLikedPlaces(String userUid, List<String> placesLiked, LikedPlacesSettedListener listener) {
        userRepositoryImpl.setLikedPlaces(userUid, placesLiked, listener);
    }

    public void setSettingsDailyNotification(String userUid, Boolean dailyNotificationChoice, DailyNotificationChoiceListener listener) {
        userRepositoryImpl.setSettingsDailyNotification(userUid, dailyNotificationChoice, listener);
    }
}