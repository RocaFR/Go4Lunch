package fr.ferrerasroca.go4lunch.ui.home.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;

import fr.ferrerasroca.go4lunch.R;
import fr.ferrerasroca.go4lunch.data.injections.Injection;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.MapViewModel;

public class MapViewFragment extends Fragment {

    private MapViewModel mapViewModel;

    public MapViewFragment() { }
    public static MapViewFragment newInstance() { return new MapViewFragment(); }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map_view, container, false);
        mapViewModel = Injection.provideMapViewModel(Injection.provideMapViewModelFactory());

        this.configureMap(view, savedInstanceState);

        return view;
    }

    private void configureMap(View view, Bundle savedInstanceState) {
        mapViewModel.configureMap(view);
        mapViewModel.getMapView().onCreate(savedInstanceState);
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
        mapViewModel.getMapView().onPause();
        super.onPause();
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
        mapViewModel.getMapView().onDestroy();
        super.onDestroy();
    }
}