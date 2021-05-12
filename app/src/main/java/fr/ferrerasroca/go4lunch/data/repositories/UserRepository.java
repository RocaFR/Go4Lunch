package fr.ferrerasroca.go4lunch.data.repositories;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fr.ferrerasroca.go4lunch.data.api.FacebookLoginApi;
import fr.ferrerasroca.go4lunch.data.api.GoogleIdentifiantApi;
import fr.ferrerasroca.go4lunch.data.api.UserHelper;

public class UserRepository {

    private final FacebookLoginApi facebookLoginApi;
    private final GoogleIdentifiantApi googleIdentifiantApi;
    public static final int RC_GOOGLE_SIGN_IN = 2901;

    public UserRepository(Fragment fragment) {
        this.googleIdentifiantApi = new GoogleIdentifiantApi(fragment);
        this.facebookLoginApi = new FacebookLoginApi(fragment);
    }

    public void launchFacebookSignInActivity() {
        facebookLoginApi.configureAndLaunchFacebookSignInActivity();
    }

    public void launchGoogleSignInActivity() {
        googleIdentifiantApi.configureAndLaunchGoogleSignInActivity();
    }

    public void createUserIfSuccess(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            googleIdentifiantApi.createUserIfSuccess(resultCode, data);
        } else {
            facebookLoginApi.createUserIfSuccess(requestCode, resultCode, data);
        }
    }

    public Boolean isCurrentUserLogged() {
        return UserHelper.isCurrentUserLogged();
    }

    public Fragment getContext() {
        return facebookLoginApi.getFragment();
    }
}