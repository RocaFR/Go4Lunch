package fr.ferrerasroca.go4lunch.ui.notification;

import static fr.ferrerasroca.go4lunch.ui.notification.DailyNotificationManager.TAG_DAILY_NOTIFICATION;

import android.content.Context;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class DailyNotificationWorkManager {

    private final WorkManager workManager;

    public DailyNotificationWorkManager(Context context) {
        this.workManager = WorkManager.getInstance(context);
    }

    public void configureAndEnqueueWork() {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build();

        PeriodicWorkRequest workRequest =
                new PeriodicWorkRequest.Builder(DailyWorker.class, 24, TimeUnit.HOURS)
                        .addTag(TAG_DAILY_NOTIFICATION)
                        .setConstraints(constraints)
                        .build();

        this.workManager.enqueueUniquePeriodicWork(TAG_DAILY_NOTIFICATION, ExistingPeriodicWorkPolicy.KEEP, workRequest);
    }

    public void cancelEnqueuedWork() {
        this.workManager.cancelAllWorkByTag(TAG_DAILY_NOTIFICATION);
    }
}
