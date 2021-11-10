package fr.ferrerasroca.go4lunch.data.api.user;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import fr.ferrerasroca.go4lunch.data.models.User;

public class UserDatabaseImpl implements UserDatabase {

    private static final String COLLECTION_USERS = "users";
    private final FirebaseAuth firebaseAuth;
    private final CollectionReference usersCollectionReference;

    public UserDatabaseImpl(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
        this.usersCollectionReference = FirebaseFirestore.getInstance().collection(COLLECTION_USERS);
    }

    @Override
    public void createUser(User userToCreate, Callback<User> callback) {
        if (userToCreate != null) {
            this.getUsersCollection().document(userToCreate.getUid()).set(userToCreate);

            if (callback != null) {
                callback.onSuccess(userToCreate);
            }
        }
    }

    @Override
    public void getUserByUID(String userUid, Callback<User> callback) {
        if (userUid != null) {
            this.getUsersCollection()
                    .document(userUid)
                    .get()
                    .addOnCompleteListener(task -> { if (callback != null) callback.onSuccess(task.getResult().toObject(User.class)); })
                    .addOnFailureListener(e -> { if (callback != null) callback.onFailure(e); });
        }
    }

    @Override
    public void getCurrentUser(Callback<User> callback) {
        this.getUsersCollection()
                .document(firebaseAuth.getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(task -> { if (callback != null) callback.onSuccess(task.getResult().toObject(User.class)); })
                .addOnFailureListener(e -> { if (callback != null) callback.onFailure(e); });
    }

    @Override
    public void getUsers(Callback<List<User>> callback) {
        this.getUsersCollection()
                .orderBy("username")
                .get()
                .addOnCompleteListener(task -> { if (callback != null) callback.onSuccess(task.getResult().toObjects(User.class)); })
                .addOnFailureListener(e -> { if (callback != null) callback.onFailure(e); });
    }

    @Override
    public Task<QuerySnapshot> getUsersByPlaceIDChoice(String placeID, Callback<List<User>> callback) {
        return this.getUsersCollection()
                .orderBy("username")
                .whereEqualTo("placeIDChoice", placeID)
                .get()
                .addOnCompleteListener(task -> { if (callback != null) {
                    List<User> users = task.getResult().toObjects(User.class);
                    callback.onSuccess(users);
                }
                })
                .addOnFailureListener(e -> { if (callback != null) callback.onFailure(e); });
    }

    @Override
    public void setPlaceIDChoice(String userUid, String placeIDChoice, Callback<Void> callback) {
        this.getUsersCollection()
                .document(userUid)
                .update("placeIDChoice", placeIDChoice)
                .addOnCompleteListener(task -> { if (callback != null)  callback.onSuccess(task.getResult()); })
                .addOnFailureListener(e -> { if (callback != null) callback.onFailure(e); });

    }

    @Override
    public void setLikedPlaces(String userUid, List<String> likedPlaces, Callback<Void> callback) {
        this.getUsersCollection()
                .document(userUid)
                .update("likedPlaces", likedPlaces)
                .addOnCompleteListener(task -> { if (callback != null)  callback.onSuccess(task.getResult()); })
                .addOnFailureListener(e -> { if (callback != null)  callback.onFailure(e); });

    }

    @Override
    public void setSettingsDailyNotification(String userUid, Boolean dailyNotificationChoice, Callback<Boolean> callback) {
        this.getUsersCollection()
                .document(userUid)
                .update("settingsDailyNotification", dailyNotificationChoice)
                .addOnCompleteListener(task -> { if (callback != null)  callback.onSuccess(dailyNotificationChoice); })
                .addOnFailureListener(e -> { if (callback != null)  callback.onFailure(e); });
    }

    @Override
    public void signOutCurrentUser() {
        if (isCurrentUserLogged()) {
            this.firebaseAuth.signOut();
        }
    }

    @Override
    public Boolean isCurrentUserLogged() {
        return this.firebaseAuth.getCurrentUser() != null;
    }

    private CollectionReference getUsersCollection() {
        return this.usersCollectionReference;
    }
}