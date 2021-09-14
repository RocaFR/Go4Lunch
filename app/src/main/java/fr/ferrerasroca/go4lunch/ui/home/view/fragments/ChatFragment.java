package fr.ferrerasroca.go4lunch.ui.home.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.Date;

import fr.ferrerasroca.go4lunch.R;
import fr.ferrerasroca.go4lunch.data.injections.Injection;
import fr.ferrerasroca.go4lunch.data.models.User;
import fr.ferrerasroca.go4lunch.ui.home.view.adaptaters.ChatAdapter;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.ChatViewModel;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.UserViewModel;

public class ChatFragment extends Fragment {

    private EditText editTextMessageToSend;
    private ImageButton imageButtonSendMessage;
    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;

    private UserViewModel userViewModel;
    private ChatViewModel chatViewModel;

    public ChatFragment() { }

    public static ChatFragment newInstance() {
        return new ChatFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userViewModel = Injection.provideUserViewModel(Injection.provideUserViewModelFactory());
        chatViewModel = Injection.provideChatViewModel(Injection.provideChatViewModelFactory());
    }

    private void configureViewModelCalls() {
        userViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            configureRecyclerView(user);
            configureChatActionsListeners(user);
        });
        userViewModel.retrieveUser();
        chatViewModel.getMessageState().observe(getViewLifecycleOwner(), this::onChanged);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        this.configureViews(view);
        this.configureViewModelCalls();

        return view;
    }

    private void configureViews(View view) {
        this.editTextMessageToSend = view.findViewById(R.id.editText_messageToSend);
        this.imageButtonSendMessage = view.findViewById(R.id.button_sendMessage);
        this.recyclerView = view.findViewById(R.id.recyclerView_chat_messages);
    }

    private void configureRecyclerView(User user) {
        this.chatAdapter = new ChatAdapter(chatViewModel.generateOptionsForFirestore(getViewLifecycleOwner()), Glide.with(this), user.getUid(), onDataChanged);
        this.chatAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.smoothScrollToPosition(chatAdapter.getItemCount());
            }
        });
        this.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.recyclerView.setAdapter(chatAdapter);
    }

    ChatAdapter.Listener onDataChanged = () -> { };

    private void configureChatActionsListeners(User user) {
        this.imageButtonSendMessage.setOnClickListener(v -> {
            if (this.editTextMessageToSend.getText().toString().length() > 0) {
                String message = this.editTextMessageToSend.getText().toString();
                chatViewModel.createMessage(user, new Date(), message);
            }
        });
    }

    private void onChanged(Boolean aBoolean) {
        if (aBoolean) {
            this.editTextMessageToSend.setText("");
        }
    }
}