package fr.ferrerasroca.go4lunch.ui.home.viewmodel;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;

import fr.ferrerasroca.go4lunch.data.model.User;
import fr.ferrerasroca.go4lunch.data.repositories.UserRepository;

public class UserViewModel extends ViewModel {

    private UserRepository userRepository;
    private User currentUser;

    public UserViewModel(Fragment context) {
        this.userRepository = new UserRepository(context);
    }

    public void launchGoogleSignInActivity() {
        userRepository.launchGoogleSignInActivity();
    }

    public void createUserIfSuccess(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        userRepository.createGoogleUserIfSuccess(requestCode, resultCode, data);
    }
}