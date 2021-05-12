package fr.ferrerasroca.go4lunch.data.injections;

import android.content.Context;

import androidx.fragment.app.Fragment;

import fr.ferrerasroca.go4lunch.data.repositories.UserRepository;

public class Injection {

    public static UserRepository provideUserRepository(Fragment context) {
        return new UserRepository(context);
    }

    public static ViewModelFactory provideViewModelFactory(Fragment context) {
        UserRepository userRepository = provideUserRepository(context);
        return new ViewModelFactory(userRepository);
    }
}
