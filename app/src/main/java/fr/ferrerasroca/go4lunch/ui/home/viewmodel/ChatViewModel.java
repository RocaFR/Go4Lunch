package fr.ferrerasroca.go4lunch.ui.home.viewmodel;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModel;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.Date;

import fr.ferrerasroca.go4lunch.data.models.Message;
import fr.ferrerasroca.go4lunch.data.models.User;
import fr.ferrerasroca.go4lunch.data.repositories.ChatRepository;

public class ChatViewModel extends ViewModel {

    public interface Callback {
        void afterMessageCreated();
    }

    private final ChatRepository chatRepository;

    public ChatViewModel(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public void createMessage(User sender, Date date, String stringMessage, Callback afterMessageCreated) {
        Message message = new Message(sender, date, stringMessage);
        chatRepository.createMessage(message, onMessageCreated, afterMessageCreated);
    }

    private final ChatRepository.Callback onMessageCreated = Callback::afterMessageCreated;

    public FirestoreRecyclerOptions<Message> generateOptionsForFirestore(LifecycleOwner lifecycleOwner) {
        return chatRepository.generateOptionsForFirestore(lifecycleOwner);
    }
}
