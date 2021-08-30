package fr.ferrerasroca.go4lunch.ui.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import fr.ferrerasroca.go4lunch.MainActivity;
import fr.ferrerasroca.go4lunch.R;
import fr.ferrerasroca.go4lunch.data.injections.Injection;
import fr.ferrerasroca.go4lunch.data.models.User;
import fr.ferrerasroca.go4lunch.databinding.ActivityHomeBinding;
import fr.ferrerasroca.go4lunch.ui.home.view.fragments.ChatFragment;
import fr.ferrerasroca.go4lunch.ui.home.view.fragments.ListViewFragment;
import fr.ferrerasroca.go4lunch.ui.home.view.fragments.MapViewFragment;
import fr.ferrerasroca.go4lunch.ui.home.view.fragments.WorkmatesFragment;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.UserViewModel;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding activityBinding;
    private UserViewModel userViewModel;
    private TextView textViewUsername;
    private TextView textViewUserEmail;
    private ImageView imageViewUserProfilePicture;
    public static final String EXTRA_PLACE_ID = "EXTRA_PLACE_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(activityBinding.getRoot());

        userViewModel = Injection.provideUserViewModel(Injection.provideUserViewModelFactory());
        this.configureNavigationHeaderViews();
        this.configureToolbar();
        this.configureDrawerLayout();
    }

    private void configureNavigationHeaderViews() {
        View headerView = activityBinding.navigationView.getHeaderView(0);
        this.textViewUsername = headerView.findViewById(R.id.textView_username);
        this.textViewUserEmail = headerView.findViewById(R.id.textView_email);
        this.imageViewUserProfilePicture = headerView.findViewById(R.id.imageView_profilePicture);

    }

    private void displayUserDetails(User user) {
        this.textViewUsername.setText(user.getUsername());
        this.textViewUserEmail.setText(user.getEmail());
        Glide.with(this)
                .load(user.getProfilePictureUrl())
                .centerCrop()
                .into(this.imageViewUserProfilePicture);
        this.configureNavigationViewListeners(user.getPlaceIDChoice());
    }

    private void configureYourRestaurantButton(String placeIDChoice) {
        if (placeIDChoice != null) {
            if (!placeIDChoice.isEmpty()) {

            }
        }
    }

    private void configureToolbar() {
        activityBinding.toolbar.setTitle(getString(R.string.app_name));
        activityBinding.toolbar.inflateMenu(R.menu.menu_toolbar);
        setSupportActionBar(activityBinding.toolbar);
        activityBinding.toolbar.setOnMenuItemClickListener(item -> {
            Toast.makeText(this, "Click on search", Toast.LENGTH_LONG).show();
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void configureDrawerLayout() {
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, activityBinding.drawerLayout, activityBinding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        activityBinding.drawerLayout.addDrawerListener(drawerToggle);

        drawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (activityBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            activityBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void configureListeners() {
        this.configureBottomNavigationViewListener();
        userViewModel.getUser().observe(this, this::displayUserDetails);
        userViewModel.retrieveUser();
    }

    private void configureNavigationViewListeners(String placeIDChoice) {
        activityBinding.navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_nav_drawer_your_lunch:
                    this.launchOrNotYourRestaurant(placeIDChoice);
                    break;
                case R.id.menu_nav_drawer_settings:
                    Intent settingsIntent = new Intent(this, SettingsActivity.class);
                    startActivity(settingsIntent);
                    break;
                case R.id.menu_nav_drawer_logout:
                    userViewModel.signOutUser();
                    Intent MainActivityIntent = new Intent(this, MainActivity.class);
                    startActivity(MainActivityIntent);
                    break;
                default:
                    finish();
            }
            activityBinding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void launchOrNotYourRestaurant(String placeIDChoice) {
        if (placeIDChoice != null) {
            if (!placeIDChoice.isEmpty()) {
                Intent intent = new Intent(getApplicationContext(), RestaurantActivity.class);
                intent.putExtra(HomeActivity.EXTRA_PLACE_ID, placeIDChoice);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "T'a rien choisi ma gueule", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "T'a rien choisi ma gueule", Toast.LENGTH_LONG).show();
        }
    }

    private void configureBottomNavigationViewListener() {
        activityBinding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_fragment_mapView:
                    this.loadFragment(MapViewFragment.newInstance());
                    return true;
                case R.id.menu_fragment_listView:
                    this.loadFragment(ListViewFragment.newInstance());
                    return true;
                case R.id.menu_fragment_workmates:
                    this.loadFragment(WorkmatesFragment.newInstance());
                    return true;
                case R.id.menu_fragment_chat:
                    this.loadFragment(ChatFragment.newInstance());
                    return true;
                default:
                    return true;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_home_host, fragment)
                .commit();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.e("TAG", "onPostResume: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.configureListeners();
        Log.e("TAG", "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("TAG", "onPause: ");
    }
}