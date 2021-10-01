package fr.ferrerasroca.go4lunch;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import android.content.Intent;

import androidx.fragment.app.Fragment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import fr.ferrerasroca.go4lunch.data.api.authentication.FacebookLoginApi;
import fr.ferrerasroca.go4lunch.data.repositories.AuthenticationRepository;
import fr.ferrerasroca.go4lunch.data.repositories.AuthenticationRepositoryImpl;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.AuthenticationViewModel;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationViewModelTest {

    @Test
    public void canILaunchFacebookSignInActivity() {
        AuthenticationRepository authenticationRepository = Mockito.mock(AuthenticationRepositoryImpl.class);
        AuthenticationViewModel authenticationViewModel = new AuthenticationViewModel(authenticationRepository);
        FacebookLoginApi facebookLoginApi = Mockito.mock(FacebookLoginApi.class);
        Fragment fragment = Mockito.mock(Fragment.class);

        authenticationViewModel.launchFacebookSignInActivity(fragment);

        verify(authenticationRepository).launchFacebookSignInActivity(any());
    }
    @Test
    public void canILaunchGoogleSignInActivity() {
        AuthenticationRepository authenticationRepository = Mockito.mock(AuthenticationRepositoryImpl.class);
        AuthenticationViewModel authenticationViewModel = new AuthenticationViewModel(authenticationRepository);

        authenticationViewModel.launchGoogleSignInActivity(new Fragment());

        verify(authenticationRepository).launchGoogleSignInActivity(any());
    }

    @Test
    public void canICreateUserIfSignInSuccess() {
        AuthenticationRepository authenticationRepository = Mockito.mock(AuthenticationRepositoryImpl.class);
        AuthenticationViewModel authenticationViewModel = new AuthenticationViewModel(authenticationRepository);

        authenticationViewModel.createUserIfSuccess(1, 2, new Intent(), new Fragment());

        verify(authenticationRepository).createUserIfSuccess(any(Integer.class), any(Integer.class), any(Intent.class), any(Fragment.class));
    }
}
