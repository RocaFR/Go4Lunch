package fr.ferrerasroca.go4lunch.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;

import fr.ferrerasroca.go4lunch.data.injections.Injection;
import fr.ferrerasroca.go4lunch.ui.auth.AuthenticationActivity;
import fr.ferrerasroca.go4lunch.ui.home.view.HomeActivity;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity {

    private UserViewModel userViewModel;
    private static WeakReference<Activity> activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = new WeakReference<>(this);
        userViewModel = Injection.provideUserViewModel(Injection.provideViewModelFactory());
        this.launchAuthenticationActivityOrHomeActivity();
    }

    private void launchAuthenticationActivityOrHomeActivity() {
        if (userViewModel.isCurrentUserLogged()) {
             startActivity(new Intent(this, HomeActivity.class));
        } else  {
            startActivity(new Intent(this, AuthenticationActivity.class));
        }
    }

    public static void cleanAuthenticationFlowAndLaunchHomeActivity() {
        Context context = activity.get().getBaseContext();
        Intent intent = new Intent(context, HomeActivity.class);
        activity.get().startActivity(intent);

        activity.get().finish();
        finishAuthenticationActivity();
    }

    private static void finishAuthenticationActivity() {
        WeakReference<Activity> activity = AuthenticationActivity.getActivity();
        activity.get().finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "onDestroy: MainActivity");
    }
}