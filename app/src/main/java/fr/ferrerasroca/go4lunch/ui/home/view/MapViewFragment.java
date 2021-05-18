package fr.ferrerasroca.go4lunch.ui.home.view;

import android.Manifest;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;

import fr.ferrerasroca.go4lunch.R;
import fr.ferrerasroca.go4lunch.data.injections.Injection;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.MapViewModel;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MapViewFragment extends Fragment {

    private MapViewModel mapViewModel;
    private static final int RC_LOCATION_PERM = 2903;

    public MapViewFragment() { }
    public static MapViewFragment newInstance() { return new MapViewFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mapViewModel = Injection.provideMapViewModel(Injection.provideMapViewModelFactory());

        this.requestLocationPermission();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_view, container, false);

        this.configureMap(view, savedInstanceState);

        return view;
    }

    private void configureMap(View view, Bundle savedInstanceState) {
        mapViewModel.configureMap(view);
        mapViewModel.getMapView().onCreate(savedInstanceState);
    }

    @AfterPermissionGranted(RC_LOCATION_PERM)
    private void requestLocationPermission() {
        if (!EasyPermissions.hasPermissions(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            EasyPermissions.requestPermissions(this, getString(R.string.app_name) + getString(R.string.permission_location_request), RC_LOCATION_PERM, Manifest.permission.ACCESS_FINE_LOCATION);
        } else {
            mapViewModel.getLastLocation(getContext());
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
        mapViewModel.getMapView().onSaveInstanceState(savedInstanceState);
    }



    @Override
    public void onStart() {
        super.onStart();
        mapViewModel.getMapView().onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapViewModel.getMapView().onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapViewModel.getMapView().onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapViewModel.getMapView().onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapViewModel.getMapView().onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapViewModel.getMapView().onDestroy();
    }
}