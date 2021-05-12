package fr.ferrerasroca.go4lunch.ui.main.view;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;

import fr.ferrerasroca.go4lunch.data.injections.Injection;
import fr.ferrerasroca.go4lunch.data.injections.ViewModelFactory;
import fr.ferrerasroca.go4lunch.databinding.ActivityMainBinding;
import fr.ferrerasroca.go4lunch.ui.BaseActivity;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.UserViewModel;

public class MainActivity extends BaseActivity {

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding viewBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        this.configureUserViewModel();
        this.startAuthenticationFragmentOrHomeFragment();
    }

    private void configureUserViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory();
        this.userViewModel = viewModelFactory.create(UserViewModel.class);
    }

    private void startAuthenticationFragmentOrHomeFragment() {
        if (userViewModel.isCurrentUserLogged()) {
            // launch home fragment
            Log.e("TAG", "startAuthenticationFragmentOrHomeFragment: user logged, welcome " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        } else  {
            // launch authentication fragment
            Log.e("TAG", "startAuthenticationFragmentOrHomeFragment: user not  logged");
        }

        /*FragmentManager supportFragmentManager = getSupportFragmentManager();
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_host, new HomeFragment())
                .commit();*/
    }
}