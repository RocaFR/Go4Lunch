package fr.ferrerasroca.go4lunch.ui.home.viewmodel;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import fr.ferrerasroca.go4lunch.data.repositories.AuthenticationRepository;

public class AuthenticationViewModel extends ViewModel {

    private final AuthenticationRepository authenticationRepository;

    public AuthenticationViewModel(AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
    }

    public void launchFacebookSignInActivity(Fragment fragment) {
        this.authenticationRepository.launchFacebookSignInActivity(fragment);
    }

    public void launchGoogleSignInActivity(Fragment fragment) {
        this.authenticationRepository.launchGoogleSignInActivity(fragment);
    }

    public void createUserIfSuccess(int requestCode, int resultCode, @Nullable Intent data, Fragment fragment) {
        this.authenticationRepository.createUserIfSuccess(requestCode, resultCode, data, fragment);
    }
}