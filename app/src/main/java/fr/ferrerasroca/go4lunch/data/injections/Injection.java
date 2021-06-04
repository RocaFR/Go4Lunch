package fr.ferrerasroca.go4lunch.data.injections;

import android.content.Context;

import com.google.android.libraries.places.api.Places;

import fr.ferrerasroca.go4lunch.BuildConfig;
import fr.ferrerasroca.go4lunch.data.api.places.PlacesApi;
import fr.ferrerasroca.go4lunch.data.repositories.PlacesRepository;
import fr.ferrerasroca.go4lunch.data.repositories.UserRepository;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.MapViewModel;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.UserViewModel;

public class Injection {

    public static UserRepository provideUserRepository() {
        return new UserRepository();
    }

    public static PlacesRepository providePlacesRepository() {
        return new PlacesRepository();
    }

    public static PlacesApi provideGooglePlacesApi(Context context) {
        Places.initialize(context, BuildConfig.GOOGLE_API_KEY);
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

    public static ViewModelFactory provideMapViewModelFactory() {
        PlacesRepository placesRepository = providePlacesRepository();
        return new ViewModelFactory(placesRepository);
    }
}