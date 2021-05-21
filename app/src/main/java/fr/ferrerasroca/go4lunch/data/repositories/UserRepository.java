package fr.ferrerasroca.go4lunch.data.repositories;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import org.jetbrains.annotations.NotNull;

import fr.ferrerasroca.go4lunch.data.api.FacebookLoginApi;
import fr.ferrerasroca.go4lunch.data.api.GoogleIdentifiantApi;
import fr.ferrerasroca.go4lunch.data.api.UserHelper;
import fr.ferrerasroca.go4lunch.data.models.User;

public class UserRepository {

    private final FacebookLoginApi facebookLoginApi;
    private final GoogleIdentifiantApi googleIdentifiantApi;
    public static final int RC_GOOGLE_SIGN_IN = 2901;
    private User user;

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

    public void getUser(String uid, UserHelper.OnUserRetrievedListener onUserRetrievedListener) {
        UserHelper.getUser(uid, onUserRetrievedListener);
    }
}