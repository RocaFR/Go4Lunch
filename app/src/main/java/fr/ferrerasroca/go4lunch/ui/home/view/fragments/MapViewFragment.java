package fr.ferrerasroca.go4lunch.ui.home.view.fragments;

import android.Manifest;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.model.LatLng;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import fr.ferrerasroca.go4lunch.R;
import fr.ferrerasroca.go4lunch.data.injections.Injection;
import fr.ferrerasroca.go4lunch.data.models.places.Place;
import fr.ferrerasroca.go4lunch.ui.home.view.GoogleMapsComponent;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.PlacesViewModel;
import fr.ferrerasroca.go4lunch.utils.LocationUtils;
import fr.ferrerasroca.go4lunch.utils.NetworkUtils;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static fr.ferrerasroca.go4lunch.ui.home.view.GoogleMapsComponent.RC_LOCATION_PERM;

public class MapViewFragment extends Fragment {

    private GoogleMapsComponent googleMapsComponent;
    private PlacesViewModel placesViewModel;

    public MapViewFragment() { }
    public static MapViewFragment newInstance() { return new MapViewFragment(); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_map_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.configureGoogleMaps(view);
        googleMapsComponent.getMapView().onCreate(savedInstanceState);

        placesViewModel = Injection.providePlacesViewModel(Injection.providePlacesViewModelFactory());
    }

    private void configureGoogleMaps(View view) {
        googleMapsComponent = new GoogleMapsComponent();
        googleMapsComponent.configureGoogleMaps(view);
    }

    @AfterPermissionGranted(RC_LOCATION_PERM)
    private void requestLocationPermission() {
        if (!EasyPermissions.hasPermissions(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            EasyPermissions.requestPermissions(this, getString(R.string.app_name) + getString(R.string.permission_location_request), RC_LOCATION_PERM, Manifest.permission.ACCESS_FINE_LOCATION);
        } else {
            if (LocationUtils.isLocationEnabled(getContext())) {
                googleMapsComponent.getLocation(getContext(), callback);
            } else {
                Toast.makeText(getContext(), getString(R.string.location_disabled), Toast.LENGTH_LONG).show();
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

            getPlaces();
        }
    };

    private void getPlaces() {
        placesViewModel.getPlaces().observe(getViewLifecycleOwner(), placesObserver);
        if (NetworkUtils.isNetworkAvailable(getContext())) {
            placesViewModel.retrieveNearbyPlaces(googleMapsComponent.getLastLocation());
        }
    }

    final Observer<List<Place>> placesObserver = new Observer<List<Place>>() {
        @Override
        public void onChanged(List<Place> places) {
            for (Place place : places) {
                Double lat = place.getGeometry().getLocation().getLat();
                Double lng = place.getGeometry().getLocation().getLng();
                LatLng latLng = new LatLng(lat, lng);

                googleMapsComponent.addMarker(latLng, place.getName(), place.getVicinity(), false);
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onViewStateRestored(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            googleMapsComponent.getMapView().onSaveInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        this.requestLocationPermission();
        googleMapsComponent.getMapView().onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        googleMapsComponent.getMapView().onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        googleMapsComponent.getMapView().onPause();
        googleMapsComponent.stopLocationUpdates(callback);
    }

    @Override
    public void onStop() {
        super.onStop();
        googleMapsComponent.getMapView().onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        googleMapsComponent.getMapView().onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        googleMapsComponent.getMapView().onDestroy();
    }
}