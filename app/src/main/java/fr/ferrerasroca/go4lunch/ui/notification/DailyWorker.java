package fr.ferrerasroca.go4lunch.ui.notification;

import static fr.ferrerasroca.go4lunch.ui.notification.DailyNotification.CHANNEL_ID;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.jetbrains.annotations.NotNull;

import fr.ferrerasroca.go4lunch.R;
import fr.ferrerasroca.go4lunch.data.api.user.UserDatabase;
import fr.ferrerasroca.go4lunch.data.injections.Injection;
import fr.ferrerasroca.go4lunch.data.models.User;
import fr.ferrerasroca.go4lunch.data.repositories.UserRepository;

public class DailyWorker extends Worker {

    private final Context context;

    public DailyWorker(@NonNull @NotNull Context context, @NonNull @NotNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public Result doWork() {
        createNotificationChannel();

        UserRepository userRepository = Injection.provideUserRepository();

        userRepository.retrieveUser(new UserDatabase.Callback<User>() {
            @Override
            public void onSuccess(User user) {
                if (!user.getPlaceIDChoice().equals(User.PLACE_ID_INITIAL_VALUE)) {
                    userRepository.retrieveUsers(user.getPlaceIDChoice(), usersRetrieved -> {
                        if (!usersRetrieved.isEmpty()) {
                            StringBuilder description = new StringBuilder(context.getString(R.string.notification_description));

                            for (int i = 0; i < usersRetrieved.size(); i++) {
                                User actualUser = usersRetrieved.get(i);

                                description.append(actualUser.getUsername());
                                if (i < usersRetrieved.size() - 1) {
                                    description.append(", ");
                                }
                            }
                            DailyNotification dailyNotification = new DailyNotification(context,
                                    context.getString(R.string.daily_notification_begin_content) + user.getUsername() + context.getString(R.string.comma_space),
                                    description);
                            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
                            notificationManagerCompat.notify(1, dailyNotification.getBuilder().build());
                        }
                    });
                }
            }

            @Override
            public void onFailure(Exception e) { }
        });
        return Result.success();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = this.context.getString(R.string.daily_notification_channel_name);
            String description = this.context.getString(R.string.daily_notification_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = this.context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
