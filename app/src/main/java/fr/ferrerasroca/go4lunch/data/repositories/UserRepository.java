package fr.ferrerasroca.go4lunch.data.repositories;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import fr.ferrerasroca.go4lunch.data.api.authentication.FacebookLoginApi;
import fr.ferrerasroca.go4lunch.data.api.authentication.GoogleIdentifiantApi;
import fr.ferrerasroca.go4lunch.data.api.user.UserHelper;
import fr.ferrerasroca.go4lunch.data.models.User;

public class UserRepository {

    private final FacebookLoginApi facebookLoginApi;
    private final GoogleIdentifiantApi googleIdentifiantApi;
    public static final int RC_GOOGLE_SIGN_IN = 2901;
    private final MutableLiveData<List<User>> _usersMutableLiveData = new MutableLiveData<>();
    private final LiveData<List<User>> usersLiveData = _usersMutableLiveData;

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

    public void getUser(String uid, UserHelper.Listeners listeners) {
        UserHelper.getUser(uid, listeners);
    }

    public LiveData<List<User>> getUsers() {
        return usersLiveData;
    }

    public void retrieveUsers() {
        UserHelper.retrieveUsers().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                List<User> users = new ArrayList<>();
                for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                    User user = snapshot.toObject(User.class);
                    users.add(user);
                }
                _usersMutableLiveData.postValue(users);
            }
        }).addOnFailureListener(e -> Log.e("TAG", "onFailure: " + e.getMessage()));
    }

    public void retrieveUsersByPlaceID(String placeID) {
        UserHelper.retrieveUsersByPlaceID(placeID).addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                List<User> users = new ArrayList<>();
                for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                    User user = snapshot.toObject(User.class);
                    users.add(user);
                }
                _usersMutableLiveData.postValue(users);
            }
        }).addOnFailureListener(e -> Log.e("TAG", "onFailure: " + e.getMessage()));
    }

    public void setPlaceIDChoice(String userUid, String placeIDChoice, UserHelper.Listeners listeners) {
        UserHelper.setPlaceIDChoice(userUid, placeIDChoice).addOnCompleteListener(task -> {
            listeners.onPlaceIDChoiceSetted();
        });
    }

    public void setLikedPlaces(String userUid, List<String> placesLiked, UserHelper.Listeners listeners) {
        UserHelper.setLikedPlaces(userUid, placesLiked).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {
                listeners.onLikedPlacesSetted();
            }
        });
    }
}