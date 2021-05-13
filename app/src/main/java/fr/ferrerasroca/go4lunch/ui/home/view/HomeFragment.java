package fr.ferrerasroca.go4lunch.ui.home.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.navigation.NavigationView;

import org.jetbrains.annotations.NotNull;

import fr.ferrerasroca.go4lunch.R;

public class HomeFragment extends Fragment implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    public HomeFragment() { }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        this.configureToolbar(view);
        this.configureDrawerLayout(view);
        this.configureNavigationView(view);

        return view;
    }

    private void configureToolbar(View view) {
        this.toolbar = view.findViewById(R.id.toolbar);
        this.toolbar.setTitle(getString(R.string.app_name));
        this.toolbar.inflateMenu(R.menu.menu_fragment_home);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        this.toolbar.setOnMenuItemClickListener(item -> {
            return false;
        });

    }

    private void configureDrawerLayout(View view) {
        this.drawerLayout = (DrawerLayout) view.findViewById(R.id.fragment_host_drawerLayout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(getActivity(), this.drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        this.drawerLayout.addDrawerListener(drawerToggle);

        drawerToggle.syncState();
    }

    private void configureNavigationView(View view) {
        this.navigationView = view.findViewById(R.id.navigationView);
        this.navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_nav_drawer_your_lunch:
                Toast.makeText(getContext(), "Your lunch", Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_nav_drawer_settings:
                Toast.makeText(getContext(), "Settings", Toast.LENGTH_LONG).show();
                break;
            case R.id.menu_nav_drawer_logout:
                Toast.makeText(getContext(), "Logout", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }

        //this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }
}