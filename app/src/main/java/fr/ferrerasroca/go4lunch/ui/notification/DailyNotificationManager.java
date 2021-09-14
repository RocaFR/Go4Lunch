package fr.ferrerasroca.go4lunch.ui.notification;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class DailyNotificationManager {

    public static final String TAG_DAILY_NOTIFICATION = "DAILY_NOTIFICATION";
    private final WorkManager workManager;

    public DailyNotificationManager(@NonNull Context context) {
        this.workManager = WorkManager.getInstance(context);
    }

    void configureAndEnqueueWork() {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build();

        PeriodicWorkRequest workRequest =
                new PeriodicWorkRequest.Builder(DailyWorker.class, 15, TimeUnit.MINUTES)
                        .addTag(TAG_DAILY_NOTIFICATION)
                        .setConstraints(constraints)
                        .build();

        this.workManager.enqueueUniquePeriodicWork(DailyNotificationManager.TAG_DAILY_NOTIFICATION, ExistingPeriodicWorkPolicy.KEEP, workRequest);
    }
}
