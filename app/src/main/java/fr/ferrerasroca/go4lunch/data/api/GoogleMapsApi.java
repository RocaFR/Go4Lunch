package fr.ferrerasroca.go4lunch.data.api;

import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

import fr.ferrerasroca.go4lunch.R;

public class GoogleMapsApi implements OnMapReadyCallback {

    private MapView mapView;

    public GoogleMapsApi() { }

    public void configureGoogleMaps(View view) {
        this.mapView = view.findViewById(R.id.mapView);
        this.mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {
        LatLng position = new LatLng(44.62717819213867, -1.1435431241989136);
        googleMap.getUiSettings().setCompassEnabled(false);
        googleMap.addMarker(new MarkerOptions()
                .title("Yolo")
                .position(position));
        googleMap.getUiSettings().setTiltGesturesEnabled(true);

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(position)
                .zoom(15)
                .tilt(90)
                .build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public MapView getMapView() {
        return this.mapView;
    }

}
