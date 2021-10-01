package fr.ferrerasroca.go4lunch.data.repositories;

import androidx.lifecycle.LifecycleOwner;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import fr.ferrerasroca.go4lunch.data.models.Message;

public interface ChatRepository {

    void createMessage(Message message, ChatRepositoryImpl.MessageCreatedListener listener);

    FirestoreRecyclerOptions<Message> generateOptionsForFirestore(LifecycleOwner lifecycleOwner);
}
