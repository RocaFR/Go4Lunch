package fr.ferrerasroca.go4lunch.data.repositories;

import androidx.annotation.Nullable;

import java.util.List;

import fr.ferrerasroca.go4lunch.data.models.places.Place;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.UserViewModel;

public interface UserRepository {

    void retrieveUser(UserRepositoryImpl.UserRetrievedListener listener);

    Boolean isCurrentUserLogged();

    void signOutCurrentUser();

    void retrieveUsers(@Nullable String placeID, UserRepositoryImpl.UsersRetrievedListener listener);

    void retrieveParticipantsForPlaces(List<Place> places, UserRepositoryImpl.UsersForPlacesListener listener);

    void setPlaceIDChoice(String userUid, String placeIDChoice, UserViewModel.PlaceIDChoiceSettedListener listener);

    void setLikedPlaces(String userUid, List<String> placesLiked, UserViewModel.LikedPlacesSettedListener listener);

    void setSettingsDailyNotification(String userUid, Boolean dailyNotificationChoice, UserViewModel.DailyNotificationChoiceListener listener);

}
