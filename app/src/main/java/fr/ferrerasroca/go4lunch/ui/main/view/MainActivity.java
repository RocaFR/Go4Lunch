package fr.ferrerasroca.go4lunch.ui.main.view;

import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import fr.ferrerasroca.go4lunch.R;
import fr.ferrerasroca.go4lunch.databinding.ActivityMainBinding;
import fr.ferrerasroca.go4lunch.ui.BaseActivity;
import fr.ferrerasroca.go4lunch.ui.auth.AuthenticationFragment;
import fr.ferrerasroca.go4lunch.ui.home.view.HomeFragment;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding viewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityMainBinding.inflate(getLayoutInflater());
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