package fr.ferrerasroca.go4lunch.ui.home.view;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.libraries.places.api.model.PlaceLikelihood;

import org.jetbrains.annotations.NotNull;

import fr.ferrerasroca.go4lunch.R;
import fr.ferrerasroca.go4lunch.data.injections.Injection;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.MapViewModel;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MapViewFragment extends Fragment {

    private GoogleMapsComponent googleMapsComponent;
    private MapViewModel mapViewModel;
    private static final int RC_LOCATION_PERM = 2903;

    public MapViewFragment() { }
    public static MapViewFragment newInstance() { return new MapViewFragment(); }

    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_map_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mapViewModel = Injection.provideMapViewModel(Injection.provideMapViewModelFactory(getContext()));
        googleMapsComponent = new GoogleMapsComponent(view);
        googleMapsComponent.getMapView().onCreate(savedInstanceState);

        this.requestLocationPermission();
        this.getPlaces();
    }

    private void getPlaces() {
        mapViewModel.placeLikelihoodLiveData.observe(getViewLifecycleOwner(), placeLikelihoods -> {
            for (PlaceLikelihood place : placeLikelihoods) {
                googleMapsComponent.addMarker(place.getPlace().getLatLng(), place.getPlace().getName(), place.getPlace().getTypes().toString(), false);
            }
        });
        mapViewModel.getPlaces();
    }

    @AfterPermissionGranted(RC_LOCATION_PERM)
    private void requestLocationPermission() {
        if (!EasyPermissions.hasPermissions(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            EasyPermissions.requestPermissions(this, getString(R.string.app_name) + getString(R.string.permission_location_request), RC_LOCATION_PERM, Manifest.permission.ACCESS_FINE_LOCATION);
        } else {
            googleMapsComponent.getLastLocation(getContext());
        }
    }

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