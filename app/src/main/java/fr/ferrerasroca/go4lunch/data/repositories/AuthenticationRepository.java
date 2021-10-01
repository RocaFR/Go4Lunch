package fr.ferrerasroca.go4lunch.data.repositories;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public interface AuthenticationRepository {

    void launchFacebookSignInActivity(Fragment fragment);

    void launchGoogleSignInActivity(Fragment fragment);

    void createUserIfSuccess(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data, Fragment fragment);
}
