package fr.ferrerasroca.go4lunch.data.api.chat;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;

import fr.ferrerasroca.go4lunch.data.models.Message;

public interface MessageService {

    Query getAllMessages();

    Task<DocumentReference> createMessage(Message message);
}
