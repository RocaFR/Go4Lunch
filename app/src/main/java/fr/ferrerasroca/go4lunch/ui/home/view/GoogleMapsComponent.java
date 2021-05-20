package fr.ferrerasroca.go4lunch.ui.home.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

import org.jetbrains.annotations.NotNull;

import fr.ferrerasroca.go4lunch.R;

public class GoogleMapsComponent implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    private MapView mapView;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private static GoogleMap currentGoogleMap;

    public static final long LOCATION_INTERVAL = 300000;

    public GoogleMapsComponent(View view) {
        this.mapView = view.findViewById(R.id.mapView);
        this.mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
        googleMap.getUiSettings().setCompassEnabled(false);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setTiltGesturesEnabled(true);

        googleMap.setOnMyLocationButtonClickListener(this);
        googleMap.setOnMyLocationButtonClickListener(this);

        currentGoogleMap = googleMap;

        // Trigger when resuming activity/fragment
        if (this.lastLocation != null) {
            LatLng latLng = new LatLng(this.lastLocation.getLatitude(), this.lastLocation.getLongitude());
            this.moveGoogleMapCamera(latLng);
            this.cleanAndAddMarker(latLng, "Title", "Snipper", true);
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Log.e("TAG", "onMyLocationButtonClick: ");
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull @NotNull Location location) {
        Log.e("TAG", "onMyLocationClick: ");
    }

    public MapView getMapView() {
        return this.mapView;
    }

    public void addGoogleMapMarker(String title, LatLng latLng) {
        MarkerOptions markerToAdd = new MarkerOptions()
                .title(title)
                .position(latLng);
        currentGoogleMap.addMarker(markerToAdd);
    }

    public void moveGoogleMapCamera(LatLng latLng) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(15)
                .tilt(90)
                .build();
        currentGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @SuppressLint("MissingPermission")
    public void getLastLocation(Context context) {
        this.configureLocationRequest();

        FusedLocationProviderClient providerClient = LocationServices.getFusedLocationProviderClient(context);
        providerClient.requestLocationUpdates(locationRequest, this.locationCallback, Looper.myLooper());
    }

    private void configureLocationRequest() {
        this.locationRequest = LocationRequest.create()
                .setInterval(LOCATION_INTERVAL)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        /* todo souleymane : vérifier si nécessaire -> LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
                SettingsClient client = LocationServices.getSettingsClient(context);
        Task<LocationSettingsResponse> responseTask = client.checkLocationSettings(builder.build());*/
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull @NotNull LocationResult locationResult) {
            lastLocation = locationResult.getLastLocation();
            LatLng latLng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());

            moveGoogleMapCamera(latLng);
            cleanAndAddMarker(latLng, "Home", "Snippet", true);
        }
    };

    private void cleanAndAddMarker(LatLng latLng ,String title, String snippet, Boolean isDraggable) {
        currentGoogleMap.clear();
        currentGoogleMap.addMarker(new MarkerOptions()
                .title(title)
                .position(latLng)
                .snippet(snippet)
                .draggable(isDraggable));
    }
}
