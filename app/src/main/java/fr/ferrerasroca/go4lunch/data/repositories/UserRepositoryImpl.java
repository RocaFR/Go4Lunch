package fr.ferrerasroca.go4lunch.data.repositories;

import android.util.Log;

import androidx.annotation.Nullable;

import java.util.List;

import fr.ferrerasroca.go4lunch.data.api.user.UserDatabase;
import fr.ferrerasroca.go4lunch.data.models.User;
import fr.ferrerasroca.go4lunch.data.models.places.Place;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.UserViewModel;

public class UserRepositoryImpl implements UserRepository {

    private final UserDatabase userDatabase;

    public UserRepositoryImpl(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

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
    public void retrieveUser(UserDatabase.Callback<User> callback) {
        if (this.userDatabase.isCurrentUserLogged()) {
            this.userDatabase.getCurrentUser(callback);
        }
    }

    @Override
    public Boolean isCurrentUserLogged() {
        return this.userDatabase.isCurrentUserLogged();
    }

    @Override
    public void signOutCurrentUser() {
        this.userDatabase.signOutCurrentUser();
    }

    @Override
    public void retrieveUsers(@Nullable String placeID, UsersRetrievedListener listener) {
        if (placeID != null) {
            userDatabase.getUsersByPlaceIDChoice(placeID, new UserDatabase.Callback<List<User>>() {
                @Override
                public void onSuccess(List<User> users) {
                    if (users != null && !users.isEmpty()) {
                        listener.onUsersRetrieved(users);
                    }
                }

                @Override
                public void onFailure(Exception e) {
                    Log.e("TAG", "onFailure: " + e.getMessage());
                }
            });
        } else {
            this.userDatabase.getUsers(new UserDatabase.Callback<List<User>>() {
                @Override
                public void onSuccess(List<User> users) {
                    if (users != null && !users.isEmpty()) {
                        listener.onUsersRetrieved(users);
                    }
                }

                @Override
                public void onFailure(Exception e) {
                }
            });
        }
    }

    @Override
    public void retrieveParticipantsForPlaces(List<Place> places, UsersForPlacesListener listener) {
        for (int i = 0; i < places.size(); i++) {
            int actualItem = i;
            String currentPlaceId = places.get(actualItem).getPlaceId();
            userDatabase.getUsersByPlaceIDChoice(currentPlaceId, new UserDatabase.Callback<List<User>>() {
                @Override
                public void onSuccess(List<User> users) {
                    if (users != null && !users.isEmpty()) {
                        places.get(actualItem).setUsersParticipants(users);
                    }
                    if (actualItem == places.size() - 1) {
                        listener.onUsersForPlacesRetrieved(places);
                    }
                }

                @Override
                public void onFailure(Exception e) {

                }
            });
        }
    }

    @Override
    public void setPlaceIDChoice(String userUid, String placeIDChoice, UserViewModel.PlaceIDChoiceSettedListener listener) {
        userDatabase.setPlaceIDChoice(userUid, placeIDChoice, new UserDatabase.Callback<Void>() {
            @Override
            public void onSuccess(Void unused) {
                listener.onPlaceIDChoiceSetted(placeIDChoice);
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("TAG", "onFailure: " + e.getMessage());
            }
        });
    }

    @Override
    public void setLikedPlaces(String userUid, List<String> placesLiked, UserViewModel.LikedPlacesSettedListener listener) {
        userDatabase.setLikedPlaces(userUid, placesLiked, new UserDatabase.Callback<Void>() {
            @Override
            public void onSuccess(Void unused) {
                listener.onLikedPlacesSetted();
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("TAG", "onFailure: " + e.getMessage());
            }
        });
    }

    @Override
    public void setSettingsDailyNotification(String userUid, Boolean dailyNotificationChoice, UserViewModel.DailyNotificationChoiceListener listener) {
        userDatabase.setSettingsDailyNotification(userUid, dailyNotificationChoice, new UserDatabase.Callback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {
                listener.onDailyNotificationChoiceSetted(aBoolean);
            }

            @Override
            public void onFailure(Exception e) {
                Log.e("TAG", "onFailure: " + e.getMessage());
            }
        });
    }
}
