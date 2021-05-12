package fr.ferrerasroca.go4lunch.data.api;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import fr.ferrerasroca.go4lunch.R;
import fr.ferrerasroca.go4lunch.data.model.User;

public class FacebookLoginApi {

        CallbackManager callbackManager;
        LoginButton loginButton;

    public FacebookLoginApi() { callbackManager = CallbackManager.Factory.create(); }

    public void configureAndLaunchFacebookSignInActivity(Fragment fragment) {
        loginButton = fragment.getView().findViewById(R.id.button_signIn_facebook);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.setFragment(fragment);

        LoginManager.getInstance()
                .registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                subscribeUserIntoFirebaseAuthentication(loginResult.getAccessToken(), fragment);
            }
            @Override
            public void onCancel() {
                Toast.makeText(fragment.getContext(), fragment.getString(R.string.facebook_sign_in_canceled), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(fragment.getContext(), fragment.getString(R.string.facebook_sign_in_error), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void createUserIfSuccess(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void subscribeUserIntoFirebaseAuthentication(AccessToken accessToken, Fragment fragment) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    createUserIntoFirestore(task);
                    Toast.makeText(fragment.getContext(), fragment.getString(R.string.welcome_signin), Toast.LENGTH_LONG).show();
                });
    }

    private void createUserIntoFirestore(Task<AuthResult> task) {
        FirebaseUser firebaseUser = task.getResult().getUser();
        User user = new User(firebaseUser.getUid(), firebaseUser.getDisplayName(), firebaseUser.getEmail(), firebaseUser.getPhotoUrl().toString());

        UserHelper.getUser(user.getUid())
                .addOnCompleteListener(task1 -> {
                    if (!task1.getResult().exists()) {
                        UserHelper.createUser(user);
                    }
                });
    }
}