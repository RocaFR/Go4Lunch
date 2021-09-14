package fr.ferrerasroca.go4lunch.ui.notification;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import fr.ferrerasroca.go4lunch.R;

public class DailyNotification {

    public static final String CHANNEL_ID = "DAILY_NOTIFICATION";
    private Context context;
    private CharSequence title;
    private CharSequence description;
    private NotificationCompat.Builder builder;

    public DailyNotification(@NonNull Context context, CharSequence title, CharSequence description) {
        this.context = context;
        this.title = title;
        this.description = description;

        this.createNotification();
    }

    private void createNotification() {
        this.builder = new NotificationCompat.Builder(this.context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_account_circle_white_36dp)
                .setContentTitle(this.title)
                .setContentText(this.description)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

    public NotificationCompat.Builder getBuilder() {
        return this.builder;
    }
}
