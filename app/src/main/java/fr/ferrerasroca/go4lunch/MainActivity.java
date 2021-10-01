package fr.ferrerasroca.go4lunch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import fr.ferrerasroca.go4lunch.data.injections.Injection;
import fr.ferrerasroca.go4lunch.ui.auth.AuthenticationActivity;
import fr.ferrerasroca.go4lunch.ui.home.view.HomeActivity;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.UserViewModel;
import fr.ferrerasroca.go4lunch.ui.notification.DailyNotificationManager;
import fr.ferrerasroca.go4lunch.ui.notification.DailyNotificationWorkManager;

public class MainActivity extends AppCompatActivity {

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userViewModel = Injection.provideUserViewModel(Injection.provideIUserViewModelFactory());


        this.configureLiveDataActions();
    }

    private void configureLiveDataActions() {
        userViewModel.getUser().observe(this, user -> {
            Boolean isDailyNotificationEnabled = user.getSettingsDailyNotification();

            if (isDailyNotificationEnabled) {
                new DailyNotificationManager(this).configureAlarmManager();
            } else {
                new DailyNotificationWorkManager(this).cancelEnqueuedWork();
            }
        });
        userViewModel.retrieveUser();
        launchAuthenticationActivityOrHomeActivity();
    }

    private void launchAuthenticationActivityOrHomeActivity() {
        if (userViewModel.isCurrentUserLogged()) {
             startActivity(new Intent(this, HomeActivity.class));
        } else  {
            startActivity(new Intent(this, AuthenticationActivity.class));
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "onDestroy: MainActivity");
    }
}