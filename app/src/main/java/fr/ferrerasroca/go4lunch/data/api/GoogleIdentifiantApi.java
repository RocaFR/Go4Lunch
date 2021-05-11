package fr.ferrerasroca.go4lunch.data.api;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentSnapshot;

import org.jetbrains.annotations.NotNull;

import fr.ferrerasroca.go4lunch.R;
import fr.ferrerasroca.go4lunch.data.model.User;
import fr.ferrerasroca.go4lunch.ui.main.view.MainActivity;

public class GoogleIdentifiantApi implements ApiErrorsMessages {

    private final Fragment context;
    private static final int RC_SIGN_IN = 2901;

    public GoogleIdentifiantApi(Fragment context) {
        this.context = context;
    }

    public void configureAndLaunchGoogleSignInActivity() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.google_web_client_id))
                .requestEmail()
                .build();

        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(context.getActivity(), googleSignInOptions);
        Intent signInIntent = googleSignInClient.getSignInIntent();
        context.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void createUserIfSuccess(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> signedInAccountFromIntent = GoogleSignIn.getSignedInAccountFromIntent(data).addOnCompleteListener(new OnCompleteListener<GoogleSignInAccount>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<GoogleSignInAccount> task) {
                    subscribeUserIntoFirebaseAuthentication(task);
                }
            });
        }
    }

    private void subscribeUserIntoFirebaseAuthentication(Task<GoogleSignInAccount> googleSignInAccountTask) {
        try {
            GoogleSignInAccount account = googleSignInAccountTask.getResult(ApiException.class);
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            FirebaseAuth.getInstance().signInWithCredential(credential)
                    .addOnFailureListener(this::onFailureListener)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                createUserIntoFirestore(task);
                                Toast.makeText(context.getContext(), context.getString(R.string.welcome_signin), Toast.LENGTH_LONG).show();
                            }
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
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                        if (!task.getResult().exists()) {
                            UserHelper.createUser(user);
                        }
                    }
                });
    }

    @Override
    public void onFailureListener(Exception e) {
        Log.e(this.getClass().getCanonicalName(), "onFailureListener: " + e.getMessage());
        Toast.makeText(context.getContext(), context.getString(R.string.signin_error), Toast.LENGTH_LONG).show();
    }
}
