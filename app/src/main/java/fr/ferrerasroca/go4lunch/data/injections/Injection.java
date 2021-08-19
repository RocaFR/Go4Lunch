package fr.ferrerasroca.go4lunch.data.injections;

import fr.ferrerasroca.go4lunch.data.repositories.ChatRepository;
import fr.ferrerasroca.go4lunch.data.repositories.PlacesRepository;
import fr.ferrerasroca.go4lunch.data.repositories.UserRepository;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.ChatViewModel;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.PlacesViewModel;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.UserViewModel;

public class Injection {

    public static UserRepository provideUserRepository() {
        return new UserRepository();
    }
    public static PlacesRepository providePlacesRepository() {
        return new PlacesRepository();
    }
    public static ChatRepository provideChatRepository() { return new ChatRepository(); }

    public static UserViewModel provideUserViewModel(ViewModelFactory viewModelFactory) {
        return viewModelFactory.create(UserViewModel.class);
    }

    public static PlacesViewModel providePlacesViewModel(ViewModelFactory viewModelFactory) {
        return viewModelFactory.create(PlacesViewModel.class);
    }

    public static ChatViewModel provideChatViewModel(ViewModelFactory viewModelFactory) {
        return viewModelFactory.create(ChatViewModel.class);
    }

    public static ViewModelFactory provideUserViewModelFactory() {
        UserRepository userRepository = provideUserRepository();
        return new ViewModelFactory(userRepository);
    }

    public static ViewModelFactory providePlacesViewModelFactory() {
        PlacesRepository placesRepository = providePlacesRepository();
        return new ViewModelFactory(placesRepository);
    }

    public static ViewModelFactory provideChatViewModelFactory() {
        ChatRepository chatRepository = provideChatRepository();
        return new ViewModelFactory(chatRepository);
    }
}