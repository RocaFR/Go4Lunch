package fr.ferrerasroca.go4lunch.data.injections;

import android.content.Context;

import androidx.fragment.app.Fragment;

import fr.ferrerasroca.go4lunch.data.repositories.UserRepository;

public class Injection {

    public static UserRepository provideUserRepository() {
        return new UserRepository();
    }

    public static ViewModelFactory provideViewModelFactory() {
        UserRepository userRepository = provideUserRepository();
        return new ViewModelFactory(userRepository);
    }
}
