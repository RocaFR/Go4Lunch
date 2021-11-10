package fr.ferrerasroca.go4lunch.repositories;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.util.Date;

import fr.ferrerasroca.go4lunch.data.api.chat.MessageServiceImpl;
import fr.ferrerasroca.go4lunch.data.models.Message;
import fr.ferrerasroca.go4lunch.data.models.User;
import fr.ferrerasroca.go4lunch.data.repositories.ChatRepositoryImpl;

@RunWith(JUnit4.class)
public class ChatRepositoryTest {

    @Test
    public void canICreateMessage() {
        MessageServiceImpl messageService = Mockito.mock(MessageServiceImpl.class);
        ChatRepositoryImpl chatRepository = new ChatRepositoryImpl(messageService);
        Message message = new Message(new User(), new Date(), "Message");

        chatRepository.createMessage(message, null);

        verify(messageService).createMessage(any());
    }

    @Test
    public void canIGenerateOptionsForeFirestore() {
        ChatRepositoryImpl chatRepository = Mockito.mock(ChatRepositoryImpl.class);
        FirestoreRecyclerOptions firestoreRecyclerOptions = Mockito.mock(FirestoreRecyclerOptions.class);


        when(chatRepository.generateOptionsForFirestore(null))
                .thenReturn(firestoreRecyclerOptions);

        Assert.assertTrue(chatRepository.generateOptionsForFirestore(any()).getClass().isAssignableFrom(FirestoreRecyclerOptions.class));
    }
}
