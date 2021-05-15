package fr.ferrerasroca.go4lunch.data.injections;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;

import fr.ferrerasroca.go4lunch.data.repositories.MapRepository;
import fr.ferrerasroca.go4lunch.data.repositories.UserRepository;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.MapViewModel;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.UserViewModel;

public class Injection {

    public static UserRepository provideUserRepository() {
        return new UserRepository();
    }

    public static MapRepository provideMapRepository() { return new MapRepository(); }

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
        MapRepository mapRepository = provideMapRepository();
        return new ViewModelFactory(mapRepository);
    }
}