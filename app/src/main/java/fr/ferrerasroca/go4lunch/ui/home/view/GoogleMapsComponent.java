package fr.ferrerasroca.go4lunch.ui.home.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

import fr.ferrerasroca.go4lunch.R;
import fr.ferrerasroca.go4lunch.utils.LocationUtils;

public class GoogleMapsComponent implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    /**
     * Useful to notify fragment's activity that location is retrieved.
     */
    public interface Callback {
        void onLocationRetrieved(LatLngBounds latLngBounds);
    }

    private MapView mapView;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private static GoogleMap currentGoogleMap;
    private FusedLocationProviderClient providerClient;
    public static final long LOCATION_INTERVAL = 300000;
    public static final int RC_LOCATION_PERM = 2903;

    public GoogleMapsComponent() {
    }

    public void configureGoogleMaps(View view) {
        this.mapView = view.findViewById(R.id.mapView);
        this.mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
        googleMap.getUiSettings().setCompassEnabled(false);
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);
        googleMap.getUiSettings().setTiltGesturesEnabled(true);

        googleMap.setOnMyLocationButtonClickListener(this);
        currentGoogleMap = googleMap;

        // Trigger when resuming activity/fragment
        if (this.lastLocation != null) {
            LatLng latLng = new LatLng(this.lastLocation.getLatitude(), this.lastLocation.getLongitude());
            this.moveGoogleMapCamera(latLng);
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

    public void moveGoogleMapCamera(LatLng latLng) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(15)
                .tilt(90)
                .build();

        currentGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @SuppressLint("MissingPermission")
    public void getLocation(Context context, LocationCallback callback) {
        this.configureLocationRequest();

        providerClient = LocationServices.getFusedLocationProviderClient(context);

        if (LocationUtils.isLocationEnabled(context)) {
            providerClient.requestLocationUpdates(locationRequest, callback, Looper.myLooper());
        }
    }

    private void configureLocationRequest() {
        this.locationRequest = LocationRequest.create()
                .setInterval(LOCATION_INTERVAL)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void addMarker(LatLng latLng, String title, String snippet, String placeID, boolean isThereParticipants) {
        BitmapDescriptor iconParticipants = BitmapDescriptorFactory.fromResource(R.drawable.restaurant_full);
        BitmapDescriptor iconNoParticipants = BitmapDescriptorFactory.fromResource(R.drawable.restaurant_empty);

        Marker marker = currentGoogleMap.addMarker(new MarkerOptions()
                .title(title)
                .position(latLng)
                .snippet(snippet)
                .icon(isThereParticipants ? iconParticipants : iconNoParticipants));
        if (marker != null) {
            marker.setTag(placeID);
        }
    }

    public Location getLastLocation() {
        return this.lastLocation;
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    public MapView getMapView() {
        return this.mapView;
    }

    public void stopLocationUpdates(LocationCallback locationCallback) {
        providerClient.removeLocationUpdates(locationCallback);
    }

    public void setOnInfoWindowListener(GoogleMap.OnInfoWindowClickListener onInfoWindowListener) {
        currentGoogleMap.setOnInfoWindowClickListener(onInfoWindowListener);
    }

    public GoogleMap getCurrentGoogleMap() { return currentGoogleMap; }
}
