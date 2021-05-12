package fr.ferrerasroca.go4lunch.ui.main.view;

import android.os.Bundle;

import androidx.fragment.app.FragmentManager;

import fr.ferrerasroca.go4lunch.R;
import fr.ferrerasroca.go4lunch.data.injections.Injection;
import fr.ferrerasroca.go4lunch.databinding.ActivityMainBinding;
import fr.ferrerasroca.go4lunch.ui.BaseActivity;
import fr.ferrerasroca.go4lunch.ui.auth.AuthenticationFragment;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.UserViewModel;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding viewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        this.startAuthenticationFragmentOrHomeFragment();
    }

    private void startAuthenticationFragmentOrHomeFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_host, new AuthenticationFragment())
                .commit();
    }
}