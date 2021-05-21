package fr.ferrerasroca.go4lunch.ui.home.viewmodel;

import android.content.Intent;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

import fr.ferrerasroca.go4lunch.data.api.UserHelper;
import fr.ferrerasroca.go4lunch.data.models.User;
import fr.ferrerasroca.go4lunch.data.repositories.UserRepository;

public class UserViewModel extends ViewModel {

    private final UserRepository userRepository;
    private MutableLiveData<User> _userMutableLiveData = new MutableLiveData<>();
    public LiveData<User> userLiveData = _userMutableLiveData;

    public UserViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
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

    public void getUser() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            userRepository.getUser(firebaseAuth.getCurrentUser().getUid(), new UserHelper.OnUserRetrievedListener() {
                @Override
                public void onUserRetrieved(User user) {
                    _userMutableLiveData.postValue(user);
                }
            });
        }
    }
}