package fr.ferrerasroca.go4lunch.data.injections;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.Fragment;

import fr.ferrerasroca.go4lunch.data.repositories.UserRepository;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.UserViewModel;

public class Injection {

    public static UserRepository provideUserRepository() {
        return new UserRepository();
    }

    public static UserViewModel provideUserViewModel(ViewModelFactory viewModelFactory) {
        return viewModelFactory.create(UserViewModel.class);
    }

    public static ViewModelFactory provideViewModelFactory() {
        UserRepository userRepository = provideUserRepository();
        return new ViewModelFactory(userRepository);
    }
}