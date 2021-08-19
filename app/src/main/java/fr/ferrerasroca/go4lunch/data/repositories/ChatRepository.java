package fr.ferrerasroca.go4lunch.data.repositories;

import androidx.lifecycle.LifecycleOwner;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import fr.ferrerasroca.go4lunch.data.api.chat.MessageHelper;
import fr.ferrerasroca.go4lunch.data.models.Message;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.ChatViewModel;

public class ChatRepository {

    public interface Callback {
        void onMessageCreated(ChatViewModel.Callback callback);
    }

    public ChatRepository() { }

    public void createMessage(Message message, Callback callbackCreatedMessage, ChatViewModel.Callback afterMessageCreated) {
        MessageHelper.createMessage(message).addOnCompleteListener(task -> callbackCreatedMessage.onMessageCreated(afterMessageCreated));
    }

    public FirestoreRecyclerOptions<Message> generateOptionsForFirestore(LifecycleOwner lifecycleOwner) {
        return new FirestoreRecyclerOptions.Builder<Message>()
                .setQuery(MessageHelper.getAllMessages(), Message.class)
                .setLifecycleOwner(lifecycleOwner)
                .build();
    }
}
