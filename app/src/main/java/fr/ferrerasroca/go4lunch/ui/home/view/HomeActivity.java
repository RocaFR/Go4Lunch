package fr.ferrerasroca.go4lunch.ui.home.view;

import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import fr.ferrerasroca.go4lunch.R;
import fr.ferrerasroca.go4lunch.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding viewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());

        this.configureToolbar();
        this.configureDrawerLayout();
        this.configureListeners();
    }

    private void configureToolbar() {
        viewBinding.toolbar.setTitle(getString(R.string.app_name));
        viewBinding.toolbar.inflateMenu(R.menu.menu_toolbar);
        setSupportActionBar(viewBinding.toolbar);
        viewBinding.toolbar.setOnMenuItemClickListener(item -> {
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
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, viewBinding.drawerLayout, viewBinding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        viewBinding.drawerLayout.addDrawerListener(drawerToggle);

        drawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (viewBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            viewBinding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void configureListeners() {
        this.configureNavigationViewListeners();
        this.configureBottomNavigationViewListener();
    }

    private void configureNavigationViewListeners() {
        viewBinding.navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_nav_drawer_your_lunch:
                    Toast.makeText(this, "Your lunch", Toast.LENGTH_LONG).show();
                    break;
                case R.id.menu_nav_drawer_settings:
                    Toast.makeText(this, "Settings", Toast.LENGTH_LONG).show();
                    break;
                case R.id.menu_nav_drawer_logout:
                    Toast.makeText(this, "Logout", Toast.LENGTH_LONG).show();
                    break;
            }
            viewBinding.drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void configureBottomNavigationViewListener() {
        viewBinding.bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
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