package fr.ferrerasroca.go4lunch.data.injections;

import fr.ferrerasroca.go4lunch.data.repositories.AuthenticationRepository;
import fr.ferrerasroca.go4lunch.data.repositories.AuthenticationRepositoryImpl;
import fr.ferrerasroca.go4lunch.data.repositories.ChatRepository;
import fr.ferrerasroca.go4lunch.data.repositories.ChatRepositoryImpl;
import fr.ferrerasroca.go4lunch.data.repositories.PlacesRepository;
import fr.ferrerasroca.go4lunch.data.repositories.PlacesRepositoryImpl;
import fr.ferrerasroca.go4lunch.data.repositories.UserRepository;
import fr.ferrerasroca.go4lunch.data.repositories.UserRepositoryImpl;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.AuthenticationViewModel;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.ChatViewModel;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.PlacesViewModel;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.UserViewModel;

public class Injection {

    // ####################
    // PROVIDE REPOSITORIES
    // ####################

    public static UserRepository provideUserRepository() { return new UserRepositoryImpl(); }
    public static PlacesRepository providePlacesRepository() { return new PlacesRepositoryImpl(); }
    public static ChatRepository provideChatRepository() { return new ChatRepositoryImpl(); }
    public static AuthenticationRepository provideAuthenticationRepository() { return new AuthenticationRepositoryImpl(); }

    // ##################
    // PROVIDE VIEWMODELS
    // ##################

    public static UserViewModel provideUserViewModel(ViewModelFactory viewModelFactory) {
        return viewModelFactory.create(UserViewModel.class);
    }

    public static PlacesViewModel providePlacesViewModel(ViewModelFactory viewModelFactory) {
        return viewModelFactory.create(PlacesViewModel.class);
    }

    public static ChatViewModel provideChatViewModel(ViewModelFactory viewModelFactory) {
        return viewModelFactory.create(ChatViewModel.class);
    }

    public static AuthenticationViewModel provideAuthenticationViewModel(ViewModelFactory viewModelFactory) {
        return viewModelFactory.create(AuthenticationViewModel.class);
    }

    // #############################
    // PROVIDE FACTORIES
    // #############################

    public static ViewModelFactory provideIUserViewModelFactory() {
        UserRepository userRepository = provideUserRepository();
        return new ViewModelFactory(userRepository);
    }

    public static ViewModelFactory providePlacesViewModelFactory() {
        PlacesRepository placesRepository = providePlacesRepository();
        return new ViewModelFactory(placesRepository);
    }

    public static ViewModelFactory provideChatViewModelFactory() {
        ChatRepository chatRepositoryImpl = provideChatRepository();
        return new ViewModelFactory(chatRepositoryImpl);
    }

    public static ViewModelFactory provideAuthenticationViewModelFactory() {
        AuthenticationRepository authenticationRepository = provideAuthenticationRepository();
        return new ViewModelFactory(authenticationRepository);
    }
}