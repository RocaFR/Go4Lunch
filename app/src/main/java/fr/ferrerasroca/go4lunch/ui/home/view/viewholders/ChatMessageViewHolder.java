package fr.ferrerasroca.go4lunch.ui.home.view.viewholders;

import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.ferrerasroca.go4lunch.R;
import fr.ferrerasroca.go4lunch.data.models.Message;
import fr.ferrerasroca.go4lunch.data.models.User;

public class ChatMessageViewHolder extends RecyclerView.ViewHolder {


    private final ImageView imageViewImageIntoMessage;
    private final TextView textViewMessage;
    private final TextView textViewUserOfThisMessage;
    private final TextView textViewHour;
    private final LinearLayout chatItemLayout;

    public ChatMessageViewHolder(@NonNull View itemView) {
        super(itemView);
        imageViewImageIntoMessage = itemView.findViewById(R.id.imageView_imageIntoMessage);
        textViewMessage = itemView.findViewById(R.id.textView_message);
        textViewUserOfThisMessage = itemView.findViewById(R.id.textView_userOfThisMessage);
        textViewHour = itemView.findViewById(R.id.textView_hour);
        chatItemLayout = itemView.findViewById(R.id.chatItem_layout);
    }

    public void updateWithMessage(Message message, RequestManager glide) {
        this.updateDesignDependingUser(message.getSender());
        if (message.getUrlImage() != null) {
            glide.load(message.getUrlImage())
                    .into(imageViewImageIntoMessage);
            this.imageViewImageIntoMessage.setVisibility(View.VISIBLE);

            if (message.getMessage().length() < 1) {
                textViewMessage.setVisibility(View.GONE);
            } else {
                textViewMessage.setVisibility(View.VISIBLE);
                textViewMessage.setText(message.getMessage());
            }
        } else {
            textViewMessage.setText(message.getMessage());
            textViewMessage.setVisibility(View.VISIBLE);
            this.imageViewImageIntoMessage.setVisibility(View.GONE);
        }

        textViewUserOfThisMessage.setText(message.getSender().getUsername());
        textViewHour.setText(this.convertDateToHour(message.getDate()));
    }

    private void updateDesignDependingUser(User sender) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser.getUid().equals(sender.getUid())) {
            textViewMessage.setBackgroundResource(R.drawable.shape_backgroun_chat_message_recipient);
            chatItemLayout.setGravity(Gravity.END);
        } else {
            textViewMessage.setBackgroundResource(R.drawable.shape_background_chat_message_sender);
            chatItemLayout.setGravity(Gravity.START);
        }
    }

    private String convertDateToHour(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(date);
    }

}
