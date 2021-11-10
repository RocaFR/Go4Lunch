package fr.ferrerasroca.go4lunch.ui.home.view;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.switchmaterial.SwitchMaterial;

import fr.ferrerasroca.go4lunch.R;
import fr.ferrerasroca.go4lunch.data.injections.Injection;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.UserViewModel;
import fr.ferrerasroca.go4lunch.ui.notification.DailyNotificationManager;
import fr.ferrerasroca.go4lunch.ui.notification.DailyNotificationWorkManager;

public class SettingsActivity extends AppCompatActivity {

    private SwitchMaterial switchMaterial;
    private LinearProgressIndicator progressIndicator;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        userViewModel = Injection.provideUserViewModel(Injection.provideIUserViewModelFactory());

        this.configureViews();
        this.configureLiveDataActions();
    }

    private void configureViews() {
        switchMaterial = findViewById(R.id.switchCompat_notification);
        progressIndicator = findViewById(R.id.progressbar_settings);
    }

    private void configureLiveDataActions() {
        userViewModel.getUser().observe(this, user -> {
            progressIndicator.setVisibility(View.GONE);
            switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    userViewModel.setSettingsDailyNotification(user.getUid(), isChecked, new UserViewModel.DailyNotificationChoiceListener() {
                        @Override
                        public void onDailyNotificationChoiceSetted(Boolean dailyNotificationChoice) {
                            if (dailyNotificationChoice) {
                                new DailyNotificationManager(getApplicationContext()).configureAlarmManager();
                            } else {
                                new DailyNotificationWorkManager(getApplicationContext()).cancelEnqueuedWork();
                            }
                        }
                    });
                }
            });
            switchMaterial.setChecked(user.getSettingsDailyNotification());
        });
        userViewModel.retrieveUser();
    }
}