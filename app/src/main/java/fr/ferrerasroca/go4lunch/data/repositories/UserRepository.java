package fr.ferrerasroca.go4lunch.data.repositories;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import fr.ferrerasroca.go4lunch.data.api.GoogleIdentifiantApi;

public class UserRepository {

    private final GoogleIdentifiantApi googleIdentifiantApi;

    public UserRepository(Fragment context) {
        this.googleIdentifiantApi = new GoogleIdentifiantApi(context);
    }

    public void launchGoogleSignInActivity() {
        googleIdentifiantApi.configureAndLaunchGoogleSignInActivity();
    }

    public void createGoogleUserIfSuccess(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        googleIdentifiantApi.createUserIfSuccess(requestCode, resultCode, data);
    }
}