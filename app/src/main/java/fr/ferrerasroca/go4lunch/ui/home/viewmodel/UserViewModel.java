package fr.ferrerasroca.go4lunch.ui.home.viewmodel;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;
import java.util.Map;

import fr.ferrerasroca.go4lunch.data.api.user.UserHelper;
import fr.ferrerasroca.go4lunch.data.models.User;
import fr.ferrerasroca.go4lunch.data.models.places.Place;
import fr.ferrerasroca.go4lunch.data.repositories.UserRepository;

public class UserViewModel extends ViewModel {

    private final UserRepository userRepository;
    private final MutableLiveData<User> _userMutableLiveData = new MutableLiveData<>();
    public LiveData<User> userLiveData = _userMutableLiveData;
    private final FirebaseAuth firebaseAuth;

    public UserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
        firebaseAuth = FirebaseAuth.getInstance();
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
        if (this.firebaseAuth.getCurrentUser() != null) {
            userRepository.getUser(this.firebaseAuth.getCurrentUser().getUid(), new UserHelper.Listeners() {
                @Override
                public void onRetrieved(User user) {
                    _userMutableLiveData.postValue(user);
                }

                @Override
                public void onPlaceIDChoiceSetted() {

                }

                @Override
                public void onLikedPlacesSetted() {

                }
            });
        }
    }

    public LiveData<User> getUserLiveData() {
        return this.userLiveData;
    }

    public void signOutUser() {
        if (this.firebaseAuth.getCurrentUser() != null) {
            this.firebaseAuth.signOut();
        }
    }

    public void retrieveUsers() {
        userRepository.retrieveUsers();
    }

    public void retrieveUsersByPlaceID(String placeID) {
        userRepository.retrieveUsersByPlaceID(placeID);
    }

    public LiveData<List<User>> getUsers() {
        return userRepository.getUsers();
    }

    public void setPlaceIDChoice(String userUid, String placeIDChoice, UserHelper.Listeners listeners) {
        userRepository.setPlaceIDChoice(userUid, placeIDChoice, listeners);
    }

    public void setLikedPlaces(String userUid, List<String> placesLiked, UserHelper.Listeners listeners) {
        userRepository.setLikedPlaces(userUid, placesLiked, listeners);
    }

    public void retrieveUsersForPlaces(List<Place> places) {
        userRepository.retrieveUsersForPlaces(places);
    }

    public LiveData<Map<String, List<User>>> getUsersForPlacesLiveData() {
        return userRepository.getUsersForPlacesLiveData();
    }

}