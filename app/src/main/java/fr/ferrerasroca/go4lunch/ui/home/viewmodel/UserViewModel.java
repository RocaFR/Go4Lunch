package fr.ferrerasroca.go4lunch.ui.home.viewmodel;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import fr.ferrerasroca.go4lunch.data.repositories.UserRepository;

public class UserViewModel extends ViewModel {

    private final UserRepository userRepository;

    public UserViewModel(Fragment fragment) {
        this.userRepository = new UserRepository(fragment);
    }

    public void launchFacebookSignInActivity() {
        userRepository.launchFacebookSignInActivity();
    }

    public void launchGoogleSignInActivity() {
        userRepository.launchGoogleSignInActivity();
    }

    public void createUserIfSuccess(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        userRepository.createUserIfSuccess(requestCode, resultCode, data);
    }

    public Boolean isCurrentUserLogged() {
        return userRepository.isCurrentUserLogged();
    }
}