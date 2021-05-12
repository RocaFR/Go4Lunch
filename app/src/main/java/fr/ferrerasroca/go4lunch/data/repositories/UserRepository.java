package fr.ferrerasroca.go4lunch.data.repositories;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fr.ferrerasroca.go4lunch.data.api.FacebookLoginApi;
import fr.ferrerasroca.go4lunch.data.api.GoogleIdentifiantApi;

public class UserRepository {

    private final FacebookLoginApi facebookLoginApi;
    private final GoogleIdentifiantApi googleIdentifiantApi;
    public static final int RC_GOOGLE_SIGN_IN = 2901;

    public UserRepository(Fragment context) {
        this.googleIdentifiantApi = new GoogleIdentifiantApi(context);
        this.facebookLoginApi = new FacebookLoginApi(context);
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

    public Fragment getContext() {
        return facebookLoginApi.getContext();
    }
}