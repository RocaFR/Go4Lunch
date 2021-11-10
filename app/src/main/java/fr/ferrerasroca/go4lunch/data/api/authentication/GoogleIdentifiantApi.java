package fr.ferrerasroca.go4lunch.data.api.authentication;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import fr.ferrerasroca.go4lunch.R;
import fr.ferrerasroca.go4lunch.data.api.user.UserDatabase;
import fr.ferrerasroca.go4lunch.data.models.User;
import fr.ferrerasroca.go4lunch.data.repositories.AuthenticationRepositoryImpl;
import fr.ferrerasroca.go4lunch.ui.auth.AuthenticationActivity;
import fr.ferrerasroca.go4lunch.ui.home.view.HomeActivity;

public class GoogleIdentifiantApi  {

    public enum State {
        /**
         * Use to indicate the success of the login process.
         */
        SUCCESS,

        /**
         * Use to indicate the cancel of the login process.
         */
        CANCEL
    }

    private State loginProcessState;
    private final UserDatabase userDatabase;

    public GoogleIdentifiantApi(UserDatabase userDatabase) {
        this.userDatabase = userDatabase;
    }

    public void configureAndLaunchGoogleSignInActivity(Fragment fragment) {
        if (fragment != null) {
            GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(fragment.getString(R.string.google_web_client_id))
                    .requestEmail()
                    .build();

            GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(fragment.getActivity(), googleSignInOptions);
            Intent signInIntent = googleSignInClient.getSignInIntent();
            fragment.startActivityForResult(signInIntent, AuthenticationRepositoryImpl.RC_GOOGLE_SIGN_IN);
        }
    }

    public void createUserIfSuccess(int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data, Fragment fragment) {
        if (resultCode == Activity.RESULT_OK) {
            this.loginProcessState = State.SUCCESS;
            GoogleSignIn.getSignedInAccountFromIntent(data).addOnCompleteListener(task -> subscribeUserIntoFirebaseAuthentication(task, fragment));
        } else {
            this.loginProcessState = State.CANCEL;
            Toast.makeText(fragment.getContext(), fragment.getString(R.string.google_connexion_canceled), Toast.LENGTH_LONG).show();
        }
    }

    private void subscribeUserIntoFirebaseAuthentication(Task<GoogleSignInAccount> googleSignInAccountTask, Fragment fragment) {
        try {
            GoogleSignInAccount account = googleSignInAccountTask.getResult(ApiException.class);
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnFailureListener(this::onFailureListener)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            createUserIntoFirestore(task, fragment);
                        }
                    });
        } catch (ApiException e) {
            this.onFailureListener(e);
        }
    }

    private void createUserIntoFirestore(Task<AuthResult> authResultTask, Fragment fragment) {
        FirebaseUser firebaseUser = authResultTask.getResult().getUser();
        User userFromAuthentication = new User(firebaseUser.getUid(), firebaseUser.getDisplayName(), firebaseUser.getEmail(), firebaseUser.getPhotoUrl().toString());

        userDatabase.getUserByUID(userFromAuthentication.getUid(), new UserDatabase.Callback<User>() {
            @Override
            public void onSuccess(User user) {
                if (user == null) {
                    userDatabase.createUser(userFromAuthentication, new UserDatabase.Callback<User>() {
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

    public void onFailureListener(Exception e) {
        Log.e(this.getClass().getCanonicalName(), "onFailureListener: " + e.getMessage());
    }

    public State getLoginProcessState() {
        return loginProcessState;
    }
}
