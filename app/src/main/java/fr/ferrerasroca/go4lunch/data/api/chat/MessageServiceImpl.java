package fr.ferrerasroca.go4lunch.data.api.chat;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import fr.ferrerasroca.go4lunch.data.models.Message;

public class MessageServiceImpl implements MessageService {

    public static final String COLLECTION_NAME = "chat";
    private final FirebaseFirestore firebaseFirestore;

    public MessageServiceImpl(FirebaseFirestore firebaseFirestore) {
        this.firebaseFirestore = firebaseFirestore;
    }

    @Override
    public Query getAllMessages() {
        return getChatCollection().orderBy("date");
    }

    @Override
    public Task<DocumentReference> createMessage(Message message) {
        return getChatCollection().add(message);
    }

    private CollectionReference getChatCollection() {
        return firebaseFirestore.collection(COLLECTION_NAME);
    }
}
