package fr.ferrerasroca.go4lunch.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import fr.ferrerasroca.go4lunch.data.injections.Injection;
import fr.ferrerasroca.go4lunch.ui.auth.AuthenticationActivity;
import fr.ferrerasroca.go4lunch.ui.home.view.HomeActivity;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity {

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userViewModel = Injection.provideUserViewModel(Injection.provideUserViewModelFactory());
        this.launchAuthenticationActivityOrHomeActivity();
    }

    private void launchAuthenticationActivityOrHomeActivity() {
        if (userViewModel.isCurrentUserLogged()) {
             startActivity(new Intent(this, HomeActivity.class));
             finish();
        } else  {
            startActivity(new Intent(this, AuthenticationActivity.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "onDestroy: MainActivity");
    }
}