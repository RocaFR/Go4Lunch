package fr.ferrerasroca.go4lunch.data.api.user;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import fr.ferrerasroca.go4lunch.data.models.User;

public interface UserDatabase {

    /**
     * Useful to be notify of the request's progress.<br>
     * May be null.
     * @param <T>
     */
    interface Callback<T> {
        void onSuccess(T t);
        void onFailure(Exception e);
    }

    void createUser(User userToCreate, Callback<User> callback);

   void getUserByUID(String userUid, Callback<User> callback);

   void getCurrentUser(Callback<User> callback);

   void getUsers(Callback<List<User>> callback);

    Task<QuerySnapshot> getUsersByPlaceIDChoice(String placeID, Callback<List<User>> callback);

    void setPlaceIDChoice(String userUid, String placeIDChoice, Callback<Void> callback);

    void setLikedPlaces(String userUid, List<String> likedPlaces, Callback<Void> callback);

    void setSettingsDailyNotification(String userUid, Boolean dailyNotificationChoice, Callback<Boolean> callback);

    void signOutCurrentUser();

    Boolean isCurrentUserLogged();
}
