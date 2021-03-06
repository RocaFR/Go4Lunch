package fr.ferrerasroca.go4lunch.data.injections;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.jetbrains.annotations.NotNull;

import fr.ferrerasroca.go4lunch.data.repositories.AuthenticationRepository;
import fr.ferrerasroca.go4lunch.data.repositories.ChatRepository;
import fr.ferrerasroca.go4lunch.data.repositories.PlacesRepository;
import fr.ferrerasroca.go4lunch.data.repositories.UserRepository;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.AuthenticationViewModel;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.ChatViewModel;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.PlacesViewModel;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.UserViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    // ############
    // REPOSITORIES
    // ############

    private UserRepository userRepository;
    private PlacesRepository placesRepository;
    private ChatRepository chatRepositoryImpl;
    private AuthenticationRepository authenticationRepository;

    public ViewModelFactory(UserRepository userRepository) { this.userRepository = userRepository; }
    public ViewModelFactory(PlacesRepository placesRepository) { this.placesRepository = placesRepository; }
    public ViewModelFactory(ChatRepository chatRepositoryImpl) {this.chatRepositoryImpl = chatRepositoryImpl; }
    public ViewModelFactory(AuthenticationRepository authenticationRepository) { this.authenticationRepository = authenticationRepository; }

    @NonNull
    @NotNull
    @Override
    public <T extends ViewModel> T create(@NonNull @NotNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserViewModel.class)) {
            return (T) new UserViewModel(userRepository);
        } else if (modelClass.isAssignableFrom(PlacesViewModel.class)) {
            return (T) new PlacesViewModel(placesRepository);
        } else if (modelClass.isAssignableFrom(ChatViewModel.class)) {
            return (T) new ChatViewModel(chatRepositoryImpl);
        } else if (modelClass.isAssignableFrom(AuthenticationViewModel.class)) {
            return (T) new AuthenticationViewModel(authenticationRepository);
        }
        throw new IllegalArgumentException("Unknow Viewmodel class");
    }
}