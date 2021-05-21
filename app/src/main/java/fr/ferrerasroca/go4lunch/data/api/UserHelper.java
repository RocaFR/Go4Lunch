package fr.ferrerasroca.go4lunch.data.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import fr.ferrerasroca.go4lunch.data.models.User;

public class UserHelper {

    private static final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private static final String COLLECTION_NAME = "users";

    private static CollectionReference getUserCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    protected static Task<Void> createUser(User userToCreate) {
        return getUserCollection().document(userToCreate.getUid()).set(userToCreate);
    }

    protected static Task<DocumentSnapshot> getUser(String userUid) {
        return getUserCollection().document(userUid).get();
    }

    public static Boolean isCurrentUserLogged() {
        return firebaseAuth.getCurrentUser() != null;
    }
}
