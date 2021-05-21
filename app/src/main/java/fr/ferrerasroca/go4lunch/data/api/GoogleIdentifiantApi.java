package fr.ferrerasroca.go4lunch.data.api;

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
import fr.ferrerasroca.go4lunch.data.models.User;
import fr.ferrerasroca.go4lunch.data.repositories.UserRepository;
import fr.ferrerasroca.go4lunch.ui.MainActivity;

public class GoogleIdentifiantApi  {

    public GoogleIdentifiantApi() { }

    public void configureAndLaunchGoogleSignInActivity(Fragment fragment) {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(fragment.getString(R.string.google_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(fragment.getActivity(), googleSignInOptions);
        Intent signInIntent = googleSignInClient.getSignInIntent();
        fragment.startActivityForResult(signInIntent, UserRepository.RC_GOOGLE_SIGN_IN);
    }

    public void createUserIfSuccess(int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data, Fragment fragment) {
        if (resultCode == Activity.RESULT_OK) {
            GoogleSignIn.getSignedInAccountFromIntent(data).addOnCompleteListener(task -> subscribeUserIntoFirebaseAuthentication(task, fragment));
        } else {
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
                            createUserIntoFirestore(task);
                            Toast.makeText(fragment.getContext(), fragment.getString(R.string.welcome_signin), Toast.LENGTH_LONG).show();
                        }
                    });
        } catch (ApiException e) {
            this.onFailureListener(e);
        }
    }

    private void createUserIntoFirestore(Task<AuthResult> authResultTask) {
        FirebaseUser firebaseUser = authResultTask.getResult().getUser();
        User user = new User(firebaseUser.getUid(), firebaseUser.getDisplayName(), firebaseUser.getEmail(), firebaseUser.getPhotoUrl().toString());

        UserHelper.getUser(user.getUid())
                .addOnCompleteListener(task -> {
                    if (!task.getResult().exists()) {
                        UserHelper.createUser(user);
                    }
                });
        MainActivity.cleanAuthenticationFlowAndLaunchHomeActivity();
    }

    public void onFailureListener(Exception e) {
        Log.e(this.getClass().getCanonicalName(), "onFailureListener: " + e.getMessage());
    }
}
