package fr.ferrerasroca.go4lunch;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Date;

import fr.ferrerasroca.go4lunch.data.models.User;
import fr.ferrerasroca.go4lunch.data.repositories.ChatRepository;
import fr.ferrerasroca.go4lunch.data.repositories.ChatRepositoryImpl;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.ChatViewModel;

@RunWith(MockitoJUnitRunner.class)
public class ChatViewModelTest {

    @Test
    public void canICreateAMessage() {
        ChatRepository chatRepository = Mockito.mock(ChatRepositoryImpl.class);
        ChatViewModel chatViewModel = new ChatViewModel(chatRepository);

        chatViewModel.createMessage(new User(), new Date(), "Message");

        verify(chatRepository).createMessage(any(), any());
    }

    @Test
    public void canIGetAMessageState() {
        ChatViewModel chatViewModel = Mockito.mock(ChatViewModel.class);

        when(chatViewModel.getMessageState()).thenReturn(new MutableLiveData<>(true));
        chatViewModel.getMessageState();

        verify(chatViewModel).getMessageState();
        Assert.assertTrue(chatViewModel.getMessageState().getClass().isAssignableFrom(MutableLiveData.class));
        Assert.assertTrue(chatViewModel.getMessageState().getValue().getClass().isAssignableFrom(Boolean.class));
    }

    @Test
    public void canIGenerateOptionsForFirestore() {
        ChatRepository chatRepository = Mockito.mock(ChatRepositoryImpl.class);
        ChatViewModel chatViewModel = new ChatViewModel(chatRepository);
        LifecycleOwner lifecycleOwner = Mockito.mock(LifecycleOwner.class);

        chatViewModel.generateOptionsForFirestore(lifecycleOwner);

        verify(chatRepository).generateOptionsForFirestore(any());
    }
}
