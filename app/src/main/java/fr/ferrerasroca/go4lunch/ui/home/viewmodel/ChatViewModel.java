package fr.ferrerasroca.go4lunch.ui.home.viewmodel;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.Date;

import fr.ferrerasroca.go4lunch.data.models.Message;
import fr.ferrerasroca.go4lunch.data.models.User;
import fr.ferrerasroca.go4lunch.data.repositories.ChatRepository;

public class ChatViewModel extends ViewModel {

    private final ChatRepository chatRepository;
    private final MutableLiveData<Boolean> _mutableLiveDataMessageState = new MutableLiveData<>(false);
    private final LiveData<Boolean> liveDataMessageState = _mutableLiveDataMessageState;

    public ChatViewModel(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public void createMessage(User sender, Date date, String stringMessage) {
        Message message = new Message(sender, date, stringMessage);
        chatRepository.createMessage(message, onMessageCreated);
    }

    private final ChatRepository.Callback onMessageCreated = () -> this._mutableLiveDataMessageState.postValue(true);

    public LiveData<Boolean> getMessageState() {
        return this.liveDataMessageState;
    }

    public FirestoreRecyclerOptions<Message> generateOptionsForFirestore(LifecycleOwner lifecycleOwner) {
        return chatRepository.generateOptionsForFirestore(lifecycleOwner);
    }
}
