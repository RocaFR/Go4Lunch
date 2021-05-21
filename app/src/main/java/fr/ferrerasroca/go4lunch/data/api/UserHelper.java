package fr.ferrerasroca.go4lunch.data.api;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import fr.ferrerasroca.go4lunch.data.models.User;

public class UserHelper {

    public interface OnUserRetrievedListener {
        void onUserRetrieved(User user);
    }

    private static final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private static final String COLLECTION_NAME = "users";

    private static CollectionReference getUserCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    public static Task<Void> createUser(User userToCreate) {
        return getUserCollection().document(userToCreate.getUid()).set(userToCreate);
    }

    public static Task<DocumentSnapshot> getUser(String userUid, OnUserRetrievedListener onUserRetrievedListener) {
        return getUserCollection().document(userUid).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                            onUserRetrievedListener.onUserRetrieved(task.getResult().toObject(User.class));
                    }
                });
    }

    public static Boolean isCurrentUserLogged() {
        return firebaseAuth.getCurrentUser() != null;
    }
}
