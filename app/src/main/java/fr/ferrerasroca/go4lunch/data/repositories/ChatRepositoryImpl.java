package fr.ferrerasroca.go4lunch.data.repositories;

import androidx.lifecycle.LifecycleOwner;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import fr.ferrerasroca.go4lunch.data.api.chat.MessageService;
import fr.ferrerasroca.go4lunch.data.models.Message;

public class ChatRepositoryImpl implements ChatRepository  {

    private MessageService messageService;

    public interface MessageCreatedListener {
        void onMessageCreated();
    }

    public ChatRepositoryImpl(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void createMessage(Message message, MessageCreatedListener listener) {
        messageService.createMessage(message).addOnCompleteListener(task -> listener.onMessageCreated());
    }

    @Override
    public FirestoreRecyclerOptions<Message> generateOptionsForFirestore(LifecycleOwner lifecycleOwner) {
        return new FirestoreRecyclerOptions.Builder<Message>()
                .setQuery(messageService.getAllMessages(), Message.class)
                .setLifecycleOwner(lifecycleOwner)
                .build();
    }
}