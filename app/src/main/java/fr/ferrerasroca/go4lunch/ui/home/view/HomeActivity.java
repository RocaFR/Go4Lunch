package fr.ferrerasroca.go4lunch.ui.home.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.util.Arrays;
import java.util.List;

import fr.ferrerasroca.go4lunch.BuildConfig;
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

public class HomeActivity extends AppCompatActivity implements GoogleMapsComponent.Callback {

    private ActivityHomeBinding activityBinding;
    private UserViewModel userViewModel;
    private TextView textViewUsername;
    private TextView textViewUserEmail;
    private ImageView imageViewUserProfilePicture;
    private Fragment actualFragment;
    private Menu menu;
    public static final String EXTRA_PLACE_ID = "EXTRA_PLACE_ID";
    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(activityBinding.getRoot());

        userViewModel = Injection.provideUserViewModel(Injection.provideIUserViewModelFactory());
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

    private void configureToolbar() {
        activityBinding.toolbar.setTitle(getString(R.string.app_name));
        activityBinding.toolbar.inflateMenu(R.menu.menu_toolbar);
        setSupportActionBar(activityBinding.toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
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
                Toast.makeText(getApplicationContext(), R.string.no_restaurant_choose, Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), R.string.no_restaurant_choose, Toast.LENGTH_LONG).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        handleAutocompleteResponse(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleAutocompleteResponse(int requestCode, int resultCode, Intent data) {
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String id = Autocomplete.getPlaceFromIntent(data).getId();
                Intent intent = new Intent(this, RestaurantActivity.class);
                intent.putExtra(EXTRA_PLACE_ID, id);
                startActivity(intent);
            }
            return;
        }
    }

    private void loadFragment(Fragment fragment) {
        this.actualFragment = fragment;

        this.handleSearchButtonVisibility();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_home_host, this.actualFragment)
                .commit();

    }

    private void handleSearchButtonVisibility() {
        this.menu.findItem(R.id.menu_fragment_home_search)
                .setVisible(!(this.actualFragment instanceof ChatFragment || this.actualFragment instanceof WorkmatesFragment));
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.configureListeners();
    }

    @Override
    public void onLocationRetrieved(LatLngBounds latLngBounds) {
        configurePlaceAutocomplete(latLngBounds);
    }

    private void configurePlaceAutocomplete(LatLngBounds latLngBounds) {
        activityBinding.toolbar.setOnMenuItemClickListener(item -> {
            if (!Places.isInitialized()) {
                Places.initialize(getApplicationContext(), BuildConfig.MAPS_API_KEY);
            }

            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);
            RectangularBounds rectangularBounds = RectangularBounds.newInstance(latLngBounds);

            Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
                    .setTypeFilter(TypeFilter.ESTABLISHMENT)
                    .setLocationBias(rectangularBounds)
                    .build(this);
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
            return true;
        });
    }
}