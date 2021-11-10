package fr.ferrerasroca.go4lunch.data.api.authentication;

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
import fr.ferrerasroca.go4lunch.data.api.user.UserDatabase;
import fr.ferrerasroca.go4lunch.data.models.User;
import fr.ferrerasroca.go4lunch.ui.auth.AuthenticationActivity;
import fr.ferrerasroca.go4lunch.ui.home.view.HomeActivity;

public class FacebookLoginApi {

        public enum State {
            /**
             * Use to indicate the success of the login process.
             */
            SUCCESS,

            /**
             * Use to indicate the cancel of the login process.
             */
            CANCEL,

            /**
             * Use to indicate an error was occur during the login process.
             */
            ERROR
        }

    private final CallbackManager callbackManager;
    private State loginProcessState;
    private final UserDatabase userDatabase;

    public FacebookLoginApi(UserDatabase userDatabase) {
        this.callbackManager = CallbackManager.Factory.create();
        this.userDatabase = userDatabase;
    }

    public void configureAndLaunchFacebookSignInActivity(Fragment fragment) {
        LoginButton loginButton = fragment.getView().findViewById(R.id.button_login_facebook);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.setFragment(fragment);

        LoginManager.getInstance()
                .registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginProcessState = State.SUCCESS;
                subscribeUserIntoFirebaseAuthentication(loginResult.getAccessToken(), fragment);
            }
            @Override
            public void onCancel() {
                loginProcessState = State.CANCEL;
                Toast.makeText(fragment.getContext(), fragment.getString(R.string.facebook_sign_in_canceled), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                loginProcessState = State.ERROR;
                Toast.makeText(fragment.getContext(), fragment.getString(R.string.facebook_sign_in_error), Toast.LENGTH_LONG).show();
            }
        });
        // Simulate a click on a button
        loginButton.performClick();
    }

    public void createUserIfSuccess(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void subscribeUserIntoFirebaseAuthentication(AccessToken accessToken, Fragment fragment) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> createUserIntoFirestore(task, fragment));
    }

    private void createUserIntoFirestore(Task<AuthResult> task, Fragment fragment) {
        FirebaseUser firebaseUser = task.getResult().getUser();
        User firebaseAuthenticationUser = new User(firebaseUser.getUid(), firebaseUser.getDisplayName(), firebaseUser.getEmail(), firebaseUser.getPhotoUrl().toString());

        userDatabase.getUserByUID(firebaseAuthenticationUser.getUid(), new UserDatabase.Callback<User>() {
            @Override
            public void onSuccess(User user) {
                if (user == null) {
                    userDatabase.createUser(firebaseAuthenticationUser, new UserDatabase.Callback<User>() {
                        @Override
                        public void onSuccess(User user) { }

                        @Override
                        public void onFailure(Exception e) { }
                    });
                }
                Intent intent = new Intent(fragment.getContext(), HomeActivity.class);
                fragment.startActivity(intent);
                AuthenticationActivity.cleanAuthenticationActivity();
            }

            @Override
            public void onFailure(Exception e) { }
        });
    }

    public State getLoginProcessState() {
        return loginProcessState;
    }
}