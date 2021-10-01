package fr.ferrerasroca.go4lunch.ui.auth;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import fr.ferrerasroca.go4lunch.R;
import fr.ferrerasroca.go4lunch.data.injections.Injection;
import fr.ferrerasroca.go4lunch.databinding.FragmentAuthenticationBinding;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.AuthenticationViewModel;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.UserViewModel;

public class AuthenticationFragment extends Fragment {

    private FragmentAuthenticationBinding viewBinding;
    private AuthenticationViewModel authenticationViewModel;

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
        this.authenticationViewModel = Injection.provideAuthenticationViewModel(Injection.provideAuthenticationViewModelFactory());
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
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
        viewBinding.buttonSignInGoogle.setOnClickListener(view -> {
            if (isNetworkAvailable()) {
                authenticationViewModel.launchGoogleSignInActivity(this);
            } else {
                this.displayNetworkErrorMessage();
            }
        });

        viewBinding.buttonSignInFacebook.setOnClickListener(view -> {
            if (isNetworkAvailable()) {
                authenticationViewModel.launchFacebookSignInActivity(this);
            } else {
                this.displayNetworkErrorMessage();
            }
        });
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    private void displayNetworkErrorMessage() {
        Toast.makeText(getContext(), getString(R.string.network_unavailable), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        this.configureProgressbar(resultCode);
        authenticationViewModel.createUserIfSuccess(requestCode, resultCode, data, this);
    }

    private void configureProgressbar(int resultCode) {
        if (resultCode == Activity.RESULT_OK) {
            viewBinding.circularProgressbar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewBinding = null;
    }
}