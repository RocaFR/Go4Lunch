package fr.ferrerasroca.go4lunch.ui.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationAlarm extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        DailyNotificationManager dailyNotificationManager = new DailyNotificationManager(context);
        dailyNotificationManager.configureAndEnqueueWork();
    }
}
