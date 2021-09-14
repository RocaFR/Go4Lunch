package fr.ferrerasroca.go4lunch.data.repositories;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

import fr.ferrerasroca.go4lunch.data.api.authentication.FacebookLoginApi;
import fr.ferrerasroca.go4lunch.data.api.authentication.GoogleIdentifiantApi;
import fr.ferrerasroca.go4lunch.data.api.user.UserHelper;
import fr.ferrerasroca.go4lunch.data.models.User;
import fr.ferrerasroca.go4lunch.data.models.places.Place;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.UserViewModel;

public class UserRepository {

    /**
     * Listeners to control asynchronous tasks.
     */

    public interface UsersForPlacesListener {
        void onUsersForPlacesRetrieved(List<Place> placesWithParticipants);
    }

    public interface UserRetrievedListener {
        void onUserRetrieved(User user);
    }

    public interface UsersRetrievedListener {
        void onUsersRetrieved(List<User> usersRetrieved);
    }

    public static final int RC_GOOGLE_SIGN_IN = 2901;
    private final FacebookLoginApi facebookLoginApi;
    private final GoogleIdentifiantApi googleIdentifiantApi;

    public UserRepository() {
        this.googleIdentifiantApi = new GoogleIdentifiantApi();
        this.facebookLoginApi = new FacebookLoginApi();
    }

    public void launchFacebookSignInActivity(Fragment fragment) {
        this.facebookLoginApi.configureAndLaunchFacebookSignInActivity(fragment);
    }

    public void launchGoogleSignInActivity(Fragment fragment) {
        this.googleIdentifiantApi.configureAndLaunchGoogleSignInActivity(fragment);
    }

    public void createUserIfSuccess(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data, Fragment fragment) {
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            this.googleIdentifiantApi.createUserIfSuccess(resultCode, data, fragment);
        } else {
            this.facebookLoginApi.createUserIfSuccess(requestCode, resultCode, data);
        }
    }

    public Boolean isCurrentUserLogged() {
        return UserHelper.isCurrentUserLogged();
    }

    public void retrieveUser(UserRetrievedListener listener) {
        if (UserHelper.isCurrentUserLogged())
        UserHelper.getUser(listener);
    }

    public void signOutCurrentUser() {
        UserHelper.signOutCurrentUser();
    }

    public void retrieveUsers(@Nullable String placeID, UsersRetrievedListener listener) {
        if (placeID != null) {
            UserHelper.retrieveUsersByPlaceID(placeID).addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    List<User> users = task.getResult().toObjects(User.class);
                    listener.onUsersRetrieved(users);
                }
            }).addOnFailureListener(e -> Log.e("TAG", "onFailure: " + e.getMessage()));
        } else {
            UserHelper.retrieveUsers().addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    List<User> users = task.getResult().toObjects(User.class);
                    listener.onUsersRetrieved(users);
                }
            }).addOnFailureListener(e -> Log.e("TAG", "onFailure: " + e.getMessage()));
        }
    }

    public void retrieveParticipantsForPlaces(List<Place> places, UsersForPlacesListener listener) {
        for (int i = 0; i < places.size(); i++) {
            int actualItem = i;
            String currentPlaceId = places.get(actualItem).getPlaceId();

            UserHelper.retrieveUsersByPlaceID(currentPlaceId).addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    List<User> users = task.getResult().toObjects(User.class);
                    places.get(actualItem).setUsersParticipants(users);
                    if (actualItem == places.size() - 1) {
                        listener.onUsersForPlacesRetrieved(places);
                    }
                }
            });
        }
    }

    public void setPlaceIDChoice(String userUid, String placeIDChoice, UserViewModel.PlaceIDChoiceSettedListener listener) {
        UserHelper.setPlaceIDChoice(userUid, placeIDChoice).addOnCompleteListener(task -> listener.onPlaceIDChoiceSetted(placeIDChoice));
    }

    public void setLikedPlaces(String userUid, List<String> placesLiked, UserViewModel.LikedPlacesSetted listener) {
        UserHelper.setLikedPlaces(userUid, placesLiked).addOnCompleteListener(task -> listener.onLikedPlacesSetted());
    }
}