package fr.ferrerasroca.go4lunch.ui.home.view;

import android.content.Intent;
import android.os.Bundle;
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

import fr.ferrerasroca.go4lunch.R;
import fr.ferrerasroca.go4lunch.data.injections.Injection;
import fr.ferrerasroca.go4lunch.data.models.User;
import fr.ferrerasroca.go4lunch.databinding.ActivityHomeBinding;
import fr.ferrerasroca.go4lunch.MainActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(activityBinding.getRoot());

        userViewModel = Injection.provideUserViewModel(Injection.provideUserViewModelFactory());
        this.configureNavigationHeaderViews();
        this.configureToolbar();
        this.configureDrawerLayout();
        this.configureListeners();
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
        this.configureNavigationViewListeners();
        this.configureBottomNavigationViewListener();
        userViewModel.userLiveData.observe(this, this::displayUserDetails);
        userViewModel.getUser();
    }

    private void configureNavigationViewListeners() {
        activityBinding.navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_nav_drawer_your_lunch:
                    Toast.makeText(this, "Your lunch", Toast.LENGTH_LONG).show();
                    break;
                case R.id.menu_nav_drawer_settings:
                    Toast.makeText(this, "Settings", Toast.LENGTH_LONG).show();
                    break;
                case R.id.menu_nav_drawer_logout:
                    userViewModel.signOutUser();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
            activityBinding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
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
}