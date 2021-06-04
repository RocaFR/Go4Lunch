package fr.ferrerasroca.go4lunch.data.injections;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import fr.ferrerasroca.go4lunch.data.repositories.PlacesRepository;
import fr.ferrerasroca.go4lunch.data.repositories.UserRepository;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.MapViewModel;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.UserViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private UserRepository userRepository;
    private PlacesRepository placesRepository;

    public ViewModelFactory(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ViewModelFactory(PlacesRepository placesRepository) {
        this.placesRepository = placesRepository;
    }

    @NonNull
    @NotNull
    @Override
    public <T extends ViewModel> T create(@NonNull @NotNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserViewModel.class)) {
            return (T) new UserViewModel(userRepository);
        } else if (modelClass.isAssignableFrom(MapViewModel.class)) {
            return (T) new MapViewModel(placesRepository);
        }
        throw new IllegalArgumentException("Unknow Viewmodel class");
    }
}