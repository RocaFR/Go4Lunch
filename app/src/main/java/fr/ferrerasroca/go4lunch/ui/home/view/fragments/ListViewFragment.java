package fr.ferrerasroca.go4lunch.ui.home.view.fragments;

import static fr.ferrerasroca.go4lunch.ui.home.view.GoogleMapsComponent.RC_LOCATION_PERM;
import static fr.ferrerasroca.go4lunch.ui.home.view.HomeActivity.EXTRA_PLACE_ID;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.model.LatLng;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import fr.ferrerasroca.go4lunch.R;
import fr.ferrerasroca.go4lunch.data.injections.Injection;
import fr.ferrerasroca.go4lunch.data.models.places.Place;
import fr.ferrerasroca.go4lunch.ui.home.view.GoogleMapsComponent;
import fr.ferrerasroca.go4lunch.ui.home.view.RestaurantActivity;
import fr.ferrerasroca.go4lunch.ui.home.view.adaptaters.RestaurantAdapter;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.PlacesViewModel;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.UserViewModel;
import fr.ferrerasroca.go4lunch.utils.ItemClickSupport;
import fr.ferrerasroca.go4lunch.utils.LocationUtils;
import fr.ferrerasroca.go4lunch.utils.NetworkUtils;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class ListViewFragment extends Fragment {

    private PlacesViewModel placesViewModel;
    private UserViewModel userViewModel;
    private GoogleMapsComponent googleMapsComponent;
    private TextView textviewNoRestaurant;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    public ListViewFragment() { }

    public static ListViewFragment newInstance() {
        return new ListViewFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        placesViewModel = Injection.providePlacesViewModel(Injection.providePlacesViewModelFactory());
        userViewModel = Injection.provideUserViewModel(Injection.provideIUserViewModelFactory());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_view, container, false);

        this.configureViews(view);
        this.configureViewModelCalls();

        return view;
    }

    private void configureViewModelCalls() {
        placesViewModel.getPlaces().observe(getViewLifecycleOwner(), placesObserver);
        userViewModel.getPlacesWithParticipants().observe(getViewLifecycleOwner(), places1 -> configureRecyclerview(places1));
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void configureViews(View view) {
        textviewNoRestaurant = view.findViewById(R.id.textView_noRestaurant);
        progressBar = view.findViewById(R.id.progressbar_restaurants);
        recyclerView = view.findViewById(R.id.restaurant_recyclerView);
        googleMapsComponent = new GoogleMapsComponent();

    }

    @Override
    public void onStart() {
        super.onStart();

        this.requestLocationPermission();
    }

    @AfterPermissionGranted(RC_LOCATION_PERM)
    private void requestLocationPermission() {
        if (!EasyPermissions.hasPermissions(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            EasyPermissions.requestPermissions(this, getString(R.string.app_name) + getString(R.string.permission_location_request), RC_LOCATION_PERM, Manifest.permission.ACCESS_FINE_LOCATION);
        } else {
            if (LocationUtils.isLocationEnabled(requireContext())) {
                googleMapsComponent.getLocation(requireContext(), callback);
            } else {
                Toast.makeText(requireContext(), getString(R.string.location_disabled), Toast.LENGTH_LONG).show();
            }
        }
    }

    private final LocationCallback callback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull @NotNull LocationResult locationResult) {
            super.onLocationResult(locationResult);

            googleMapsComponent.setLastLocation(locationResult.getLastLocation());
            LatLng latLng = new LatLng(googleMapsComponent.getLastLocation().getLatitude(), googleMapsComponent.getLastLocation().getLongitude());
            googleMapsComponent.moveGoogleMapCamera(latLng);

            if (NetworkUtils.isNetworkAvailable(requireContext())) {
                placesViewModel.retrieveNearbyPlaces(googleMapsComponent.getLastLocation());
            }
        }
    };

    private final Observer<List<Place>> placesObserver = new Observer<List<Place>>() {
        @Override
        public void onChanged(List<Place> places) {
            if (!places.isEmpty()) {
                textviewNoRestaurant.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);
                userViewModel.retrieveParticipantsForPlaces(places);
            } else {
                textviewNoRestaurant.setVisibility(View.VISIBLE);
            }
        }
    };

    private void configureRecyclerview(List<Place> places) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(requireContext());
        RestaurantAdapter restaurantAdapter = new RestaurantAdapter(places);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(restaurantAdapter);
        configureOnClickRecyclerView(places);
    }

    private void configureOnClickRecyclerView(List<Place> places) {
        ItemClickSupport.addTo(recyclerView, R.layout.restaurant_item)
                .setOnItemClickListener((recyclerView, position, v) -> {
                    Intent intent = new Intent(requireContext(), RestaurantActivity.class);
                    intent.putExtra(EXTRA_PLACE_ID, places.get(position).getPlaceId());
                    startActivity(intent);
                });
    }

    @Override
    public void onPause() {
        super.onPause();

        googleMapsComponent.stopLocationUpdates(callback);
    }
}