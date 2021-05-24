package fr.ferrerasroca.go4lunch.data.injections;

import android.content.Context;
import android.view.View;

import com.google.android.libraries.places.api.Places;

import fr.ferrerasroca.go4lunch.R;
import fr.ferrerasroca.go4lunch.ui.home.view.GoogleMapsComponent;
import fr.ferrerasroca.go4lunch.data.api.PlacesApi;
import fr.ferrerasroca.go4lunch.data.repositories.PlacesRepository;
import fr.ferrerasroca.go4lunch.data.repositories.UserRepository;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.MapViewModel;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.UserViewModel;

public class Injection {

    public static UserRepository provideUserRepository() {
        return new UserRepository();
    }

    public static PlacesRepository providePlacesRepository(PlacesApi placesApi) {
        return new PlacesRepository(placesApi);
    }

    public static PlacesApi provideGooglePlacesApi(Context context) {
        Places.initialize(context, "AIzaSyA7Bz7oYJDd0eiWUbdaPgNOxSwyMZI-kJk");
        return new PlacesApi(Places.createClient(context));
    }

    public static UserViewModel provideUserViewModel(ViewModelFactory viewModelFactory) {
        return viewModelFactory.create(UserViewModel.class);
    }

    public static MapViewModel provideMapViewModel(ViewModelFactory viewModelFactory) {
        return viewModelFactory.create(MapViewModel.class);
    }

    public static ViewModelFactory provideUserViewModelFactory() {
        UserRepository userRepository = provideUserRepository();
        return new ViewModelFactory(userRepository);
    }

    public static ViewModelFactory provideMapViewModelFactory(Context context) {
        PlacesRepository placesRepository = providePlacesRepository(provideGooglePlacesApi(context));
        return new ViewModelFactory(placesRepository);
    }
}