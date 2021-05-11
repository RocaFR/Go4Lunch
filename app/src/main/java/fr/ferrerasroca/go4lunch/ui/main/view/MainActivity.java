package fr.ferrerasroca.go4lunch.ui.main.view;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;

import fr.ferrerasroca.go4lunch.R;
import fr.ferrerasroca.go4lunch.databinding.ActivityMainBinding;
import fr.ferrerasroca.go4lunch.ui.BaseActivity;
import fr.ferrerasroca.go4lunch.ui.auth.AuthenticationFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding viewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        this.startAuthenticationOrHomeFragment();
    }

    private void startAuthenticationOrHomeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragment_host, new AuthenticationFragment())
                .commit();
    }
}