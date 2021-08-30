package fr.ferrerasroca.go4lunch.data.repositories;

import androidx.lifecycle.LifecycleOwner;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import fr.ferrerasroca.go4lunch.data.api.chat.MessageHelper;
import fr.ferrerasroca.go4lunch.data.models.Message;

public class ChatRepository {

    public interface Callback {
        void onMessageCreated();
    }

    public void createMessage(Message message, Callback callbackCreatedMessage) {
        MessageHelper.createMessage(message).addOnCompleteListener(task -> callbackCreatedMessage.onMessageCreated());
    }

    public FirestoreRecyclerOptions<Message> generateOptionsForFirestore(LifecycleOwner lifecycleOwner) {
        return new FirestoreRecyclerOptions.Builder<Message>()
                .setQuery(MessageHelper.getAllMessages(), Message.class)
                .setLifecycleOwner(lifecycleOwner)
                .build();
    }
}