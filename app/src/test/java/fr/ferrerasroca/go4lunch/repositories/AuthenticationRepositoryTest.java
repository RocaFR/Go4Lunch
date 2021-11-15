package fr.ferrerasroca.go4lunch.repositories;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import androidx.fragment.app.Fragment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import fr.ferrerasroca.go4lunch.data.api.authentication.FacebookLoginApi;
import fr.ferrerasroca.go4lunch.data.api.authentication.GoogleIdentifiantApi;
import fr.ferrerasroca.go4lunch.data.repositories.AuthenticationRepositoryImpl;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationRepositoryTest {

    @Test
    public void canILaunchFacebookSignInActivity() {
        GoogleIdentifiantApi googleIdentifiantApi = Mockito.mock(GoogleIdentifiantApi.class);
        FacebookLoginApi facebookLoginApi = Mockito.mock(FacebookLoginApi.class);
        Fragment fragment = Mockito.mock(Fragment.class);
        AuthenticationRepositoryImpl authenticationRepository = new AuthenticationRepositoryImpl(googleIdentifiantApi, facebookLoginApi);

        authenticationRepository.launchFacebookSignInActivity(fragment);

        verify(facebookLoginApi).configureAndLaunchFacebookSignInActivity(any());
    }

    @Test
    public void canILaunchGoogleSignInActivity() {
        GoogleIdentifiantApi googleIdentifiantApi = Mockito.mock(GoogleIdentifiantApi.class);
        FacebookLoginApi facebookLoginApi = Mockito.mock(FacebookLoginApi.class);
        Fragment fragment = Mockito.mock(Fragment.class);
        AuthenticationRepositoryImpl authenticationRepository = new AuthenticationRepositoryImpl(googleIdentifiantApi, facebookLoginApi);

        authenticationRepository.launchGoogleSignInActivity(fragment);

        verify(googleIdentifiantApi).configureAndLaunchGoogleSignInActivity(any());
    }
}
