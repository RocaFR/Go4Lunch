package fr.ferrerasroca.go4lunch.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import fr.ferrerasroca.go4lunch.data.injections.Injection;
import fr.ferrerasroca.go4lunch.data.injections.ViewModelFactory;
import fr.ferrerasroca.go4lunch.databinding.FragmentAuthenticationBinding;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.UserViewModel;

public class AuthenticationFragment extends Fragment {

    // UI
    private FragmentAuthenticationBinding viewBinding;
    private UserViewModel userViewModel;

    // Necessary for Android system.
    public AuthenticationFragment() { }

    public static AuthenticationFragment newInstance() {
        return new AuthenticationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.configureViewModel();
    }

    private void configureViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory();
        this.userViewModel = viewModelFactory.create(UserViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewBinding = FragmentAuthenticationBinding.inflate(inflater, container, false);
        return viewBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.configureListeners();
    }

    private void configureListeners() {
        viewBinding.buttonSignInGoogle.setOnClickListener(view -> userViewModel.launchGoogleSignInActivity(this));
        viewBinding.buttonSignInFacebook.setOnClickListener(view -> userViewModel.launchFacebookSignInActivity(this));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        userViewModel.createUserIfSuccess(requestCode, resultCode, data, this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewBinding = null;
    }
}