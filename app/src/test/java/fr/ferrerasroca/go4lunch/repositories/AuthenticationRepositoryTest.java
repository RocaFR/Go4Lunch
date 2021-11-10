package fr.ferrerasroca.go4lunch.repositories;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import androidx.fragment.app.Fragment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import fr.ferrerasroca.go4lunch.data.api.authentication.FacebookLoginApi;
import fr.ferrerasroca.go4lunch.data.injections.Injection;
import fr.ferrerasroca.go4lunch.data.repositories.AuthenticationRepositoryImpl;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationRepositoryTest {

    @Test
    public void canIGetStatusCodeForFacebookLoginApi() {
        AuthenticationRepositoryImpl authenticationRepository = new AuthenticationRepositoryImpl(Injection.providerGoogleIdentifiantApi(), Injection.providerFacebookLoginApi());
        FacebookLoginApi facebookLoginApi = Mockito.mock(FacebookLoginApi.class);
        Fragment fragment = Mockito.mock(Fragment.class);

        doNothing().when(facebookLoginApi).configureAndLaunchFacebookSignInActivity(fragment);

        authenticationRepository.launchFacebookSignInActivity(fragment);

        verify(facebookLoginApi).configureAndLaunchFacebookSignInActivity(any());
    }
}
