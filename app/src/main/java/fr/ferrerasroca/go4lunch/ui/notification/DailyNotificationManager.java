package fr.ferrerasroca.go4lunch.ui.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class DailyNotificationManager {

    public static final String TAG_DAILY_NOTIFICATION = "DAILY_NOTIFICATION";
    public static final int DAILY_NOTIFICATION_HOUR = 11;
    public static final int DAILY_NOTIFICATION_MINUTE = 45;
    private final WorkManager workManager;
    private final Context context;

    public DailyNotificationManager(@NonNull Context context) {
        this.workManager = WorkManager.getInstance(context);
        this.context = context;
    }

    public void configureAlarmManager() {
        Intent intent = new Intent(this.context, NotificationAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.context, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) this.context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, configureAndGetCalendar().getTimeInMillis(), pendingIntent);
    }

    private Calendar configureAndGetCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, DAILY_NOTIFICATION_HOUR);
        calendar.set(Calendar.MINUTE, DAILY_NOTIFICATION_MINUTE);

        return calendar;
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

        this.workManager.enqueueUniquePeriodicWork(DailyNotificationManager.TAG_DAILY_NOTIFICATION, ExistingPeriodicWorkPolicy.KEEP, workRequest);
    }

    public void cancelEnqueuedWork() {
        this.workManager.cancelAllWorkByTag(TAG_DAILY_NOTIFICATION);
    }
}
