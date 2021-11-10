package fr.ferrerasroca.go4lunch.data.injections;

import com.google.firebase.auth.FirebaseAuth;

import fr.ferrerasroca.go4lunch.data.api.authentication.FacebookLoginApi;
import fr.ferrerasroca.go4lunch.data.api.authentication.GoogleIdentifiantApi;
import fr.ferrerasroca.go4lunch.data.api.chat.MessageService;
import fr.ferrerasroca.go4lunch.data.api.chat.MessageServiceImpl;
import fr.ferrerasroca.go4lunch.data.api.places.PlacesService;
import fr.ferrerasroca.go4lunch.data.api.places.PlacesServiceImpl;
import fr.ferrerasroca.go4lunch.data.api.user.UserDatabase;
import fr.ferrerasroca.go4lunch.data.api.user.UserDatabaseImpl;
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
    // PROVIDE DEPENDENCIES
    // ####################
    public static UserDatabase provideUserDatabase() { return new UserDatabaseImpl(providerFirebaseAuth()); }
    public static FirebaseAuth providerFirebaseAuth() { return FirebaseAuth.getInstance(); }
    public static GoogleIdentifiantApi providerGoogleIdentifiantApi() { return new GoogleIdentifiantApi(provideUserDatabase()); }
    public static FacebookLoginApi providerFacebookLoginApi() { return new FacebookLoginApi(provideUserDatabase()); }
    public static PlacesService providePlacesService() { return new PlacesServiceImpl(); }
    public static MessageService provideMessageService() { return new MessageServiceImpl(); }

    // ####################
    // PROVIDE REPOSITORIES
    // ####################

    public static UserRepository provideUserRepository() { return new UserRepositoryImpl(provideUserDatabase()); }
    public static PlacesRepository providePlacesRepository() { return new PlacesRepositoryImpl(providePlacesService()); }
    public static ChatRepository provideChatRepository() { return new ChatRepositoryImpl(provideMessageService()); }
    public static AuthenticationRepository provideAuthenticationRepository() { return new AuthenticationRepositoryImpl(providerGoogleIdentifiantApi(), providerFacebookLoginApi()); }

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