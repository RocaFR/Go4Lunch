package fr.ferrerasroca.go4lunch.ui.auth;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import java.lang.ref.WeakReference;

import fr.ferrerasroca.go4lunch.R;

public class AuthenticationActivity extends AppCompatActivity {

    private static WeakReference<Activity> activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        activity = new WeakReference<>(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "onDestroy: auth activity");
    }

    public static WeakReference<Activity> getActivity() {
        return activity;
    }
}