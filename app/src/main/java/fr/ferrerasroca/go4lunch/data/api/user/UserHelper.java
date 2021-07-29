package fr.ferrerasroca.go4lunch.data.api.user;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import fr.ferrerasroca.go4lunch.data.models.User;

public class UserHelper {

    public interface Listeners {

        void onRetrieved(User user);
        void onPlaceIDChoiceSetted();
        void onLikedPlacesSetted();
    }

    private static final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private static final String COLLECTION_NAME = "users";
    private static CollectionReference getUserCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    public static Task<Void> createUser(User userToCreate) {
        return getUserCollection().document(userToCreate.getUid()).set(userToCreate);
    }

    public static Task<DocumentSnapshot> getUser(String userUid) {
        return getUserCollection().document(userUid).get();
    }

    public static Task<DocumentSnapshot> getUser(String userUid, Listeners listeners) {
        return getUserCollection().document(userUid).get()
                .addOnCompleteListener(task -> listeners.onRetrieved(task.getResult().toObject(User.class)));
    }

    public static Task<QuerySnapshot> retrieveUsers() {
        return getUserCollection()
                .orderBy("username")
                .get();
    }

    public static Task<QuerySnapshot> retrieveUsersByPlaceID(String placeID) {
        return getUserCollection()
                .orderBy("username")
                .whereEqualTo("placeIDChoice", placeID)
                .get();
    }

    public static Task<Void> setPlaceIDChoice(String userUid, String placeIDChoice) {
        return getUserCollection().document(userUid).update("placeIDChoice", placeIDChoice);
    }

    public static Task<Void> setLikedPlaces(String userUid, List<String> likedPlaces) {
        return getUserCollection().document(userUid).update("likedPlaces", likedPlaces);
    }

    public static Boolean isCurrentUserLogged() {
        return firebaseAuth.getCurrentUser() != null;
    }
}
