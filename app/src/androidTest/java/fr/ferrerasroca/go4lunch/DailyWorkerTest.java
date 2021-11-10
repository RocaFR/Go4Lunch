package fr.ferrerasroca.go4lunch;

import android.content.Context;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.work.Configuration;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.testing.SynchronousExecutor;
import androidx.work.testing.WorkManagerTestInitHelper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.ferrerasroca.go4lunch.ui.notification.DailyWorker;

@RunWith(AndroidJUnit4.class)
public class DailyWorkerTest  {

    private Context context;

    @Before
    public void setup() {
        context = ApplicationProvider.getApplicationContext();
        Configuration config = new Configuration.Builder()
                .setMinimumLoggingLevel(Log.DEBUG)
                .setExecutor(new SynchronousExecutor())
                .build();

        WorkManagerTestInitHelper.initializeTestWorkManager(
                context, config);
    }

    @Test
    public void dailyWorkerTest() throws Exception {
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(DailyWorker.class).build();

        WorkManager workManager = WorkManager.getInstance(context);
        workManager.enqueue(request).getResult().get();
        WorkInfo workInfo = workManager.getWorkInfoById(request.getId()).get();

        Assert.assertEquals(WorkInfo.State.SUCCEEDED, workInfo.getState());
    }
}
