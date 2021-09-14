package fr.ferrerasroca.go4lunch;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import fr.ferrerasroca.go4lunch.data.injections.Injection;
import fr.ferrerasroca.go4lunch.ui.auth.AuthenticationActivity;
import fr.ferrerasroca.go4lunch.ui.home.view.HomeActivity;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.UserViewModel;
import fr.ferrerasroca.go4lunch.ui.notification.NotificationAlarm;

public class MainActivity extends AppCompatActivity {

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userViewModel = Injection.provideUserViewModel(Injection.provideUserViewModelFactory());

        this.scheduleNotification();
        this.launchAuthenticationActivityOrHomeActivity();
    }

    private void scheduleNotification() {
        Calendar calendar = configureAndGetCalendar();

        Intent intent = new Intent(getApplication(), NotificationAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    private Calendar configureAndGetCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 45);

        return calendar;
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