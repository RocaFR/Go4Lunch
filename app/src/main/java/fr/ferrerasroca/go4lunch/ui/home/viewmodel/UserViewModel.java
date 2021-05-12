package fr.ferrerasroca.go4lunch.ui.home.viewmodel;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import fr.ferrerasroca.go4lunch.data.repositories.UserRepository;

public class UserViewModel extends ViewModel {

    private final UserRepository userRepository;

    public UserViewModel() {
        this.userRepository = new UserRepository();
    }

    public void launchFacebookSignInActivity(Fragment fragment) {
        userRepository.launchFacebookSignInActivity(fragment);
    }

    public void launchGoogleSignInActivity(Fragment fragment) {
        userRepository.launchGoogleSignInActivity(fragment);
    }

    public void createUserIfSuccess(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data, Fragment fragment) {
        userRepository.createUserIfSuccess(requestCode, resultCode, data, fragment);
    }

    public Boolean isCurrentUserLogged() {
        return userRepository.isCurrentUserLogged();
    }
}