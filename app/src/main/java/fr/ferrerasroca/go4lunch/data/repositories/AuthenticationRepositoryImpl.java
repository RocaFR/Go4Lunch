package fr.ferrerasroca.go4lunch.data.repositories;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fr.ferrerasroca.go4lunch.data.api.authentication.FacebookLoginApi;
import fr.ferrerasroca.go4lunch.data.api.authentication.GoogleIdentifiantApi;

public class AuthenticationRepositoryImpl implements AuthenticationRepository {

    public static final int RC_GOOGLE_SIGN_IN = 2901;
    private final FacebookLoginApi facebookLoginApi;
    private final GoogleIdentifiantApi googleIdentifiantApi;

    public AuthenticationRepositoryImpl(GoogleIdentifiantApi googleIdentifiantApi, FacebookLoginApi facebookLoginApi) {
        this.googleIdentifiantApi = googleIdentifiantApi;
        this.facebookLoginApi = facebookLoginApi;
    }

    @Override
    public void launchFacebookSignInActivity(Fragment fragment) {
        this.facebookLoginApi.configureAndLaunchFacebookSignInActivity(fragment);
    }

    @Override
    public void launchGoogleSignInActivity(Fragment fragment) {
        this.googleIdentifiantApi.configureAndLaunchGoogleSignInActivity(fragment);
    }

    @Override
    public void createUserIfSuccess(int requestCode, int resultCode, @Nullable Intent data, Fragment fragment) {
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            this.googleIdentifiantApi.createUserIfSuccess(resultCode, data, fragment);
        } else {
            this.facebookLoginApi.createUserIfSuccess(requestCode, resultCode, data);
        }
    }
}
