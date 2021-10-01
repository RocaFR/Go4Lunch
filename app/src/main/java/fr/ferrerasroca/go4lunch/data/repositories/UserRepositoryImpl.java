package fr.ferrerasroca.go4lunch.data.repositories;

import android.util.Log;

import androidx.annotation.Nullable;

import java.util.List;

import fr.ferrerasroca.go4lunch.data.api.user.UserHelper;
import fr.ferrerasroca.go4lunch.data.models.User;
import fr.ferrerasroca.go4lunch.data.models.places.Place;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.UserViewModel;

public class UserRepositoryImpl implements UserRepository {

     public interface UsersForPlacesListener {
        void onUsersForPlacesRetrieved(List<Place> placesWithParticipants);
    }

    public interface UserRetrievedListener {
        void onUserRetrieved(User user);
    }

    public interface UsersRetrievedListener {
        void onUsersRetrieved(List<User> usersRetrieved);
    }

    @Override
    public void retrieveUser(UserRetrievedListener listener) {
        if (UserHelper.isCurrentUserLogged()) {
            UserHelper.getUser(listener);
        }
    }

    @Override
    public Boolean isCurrentUserLogged() {
        return UserHelper.isCurrentUserLogged();
    }

    @Override
    public void signOutCurrentUser() {
        UserHelper.signOutCurrentUser();
    }

    @Override
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

    @Override
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

    @Override
    public void setPlaceIDChoice(String userUid, String placeIDChoice, UserViewModel.PlaceIDChoiceSettedListener listener) {
        UserHelper.setPlaceIDChoice(userUid, placeIDChoice).addOnCompleteListener(task -> listener.onPlaceIDChoiceSetted(placeIDChoice));
    }

    @Override
    public void setLikedPlaces(String userUid, List<String> placesLiked, UserViewModel.LikedPlacesSettedListener listener) {
        UserHelper.setLikedPlaces(userUid, placesLiked).addOnCompleteListener(task -> listener.onLikedPlacesSetted());
    }

    @Override
    public void setSettingsDailyNotification(String userUid, Boolean dailyNotificationChoice, UserViewModel.DailyNotificationChoiceListener listener) {
        UserHelper.setSettingsDailyNotification(userUid, dailyNotificationChoice).addOnCompleteListener(task -> listener.onDailyNotificationChoiceSetted(dailyNotificationChoice));
    }
}
