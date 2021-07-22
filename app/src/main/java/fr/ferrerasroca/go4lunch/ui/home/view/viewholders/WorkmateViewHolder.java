package fr.ferrerasroca.go4lunch.ui.home.view.viewholders;

import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import org.jetbrains.annotations.NotNull;

import fr.ferrerasroca.go4lunch.R;
import fr.ferrerasroca.go4lunch.data.models.User;
import fr.ferrerasroca.go4lunch.data.models.places.Place;

public class WorkmateViewHolder extends RecyclerView.ViewHolder {

    private final ShapeableImageView imageviewWorkmate;
    private final MaterialTextView textviewWorkmateChoice;

    public WorkmateViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        imageviewWorkmate = itemView.findViewById(R.id.imageView_workmate);
        textviewWorkmateChoice = itemView.findViewById(R.id.textView_workmateChoice);
    }

    public void updateWorkmate(User user, Place place) {
        Glide.with(itemView.getContext()).load(user.getProfilePictureUrl()).into(imageviewWorkmate);

        if (place != null) {
            textviewWorkmateChoice.setText(TextUtils.isEmpty(user.getUsername()) ? "" : user.getUsername() + itemView.getContext().getString(R.string.workmate_is_eating) + place.getName() + ".");
        } else {
            textviewWorkmateChoice.setText(TextUtils.isEmpty(user.getUsername()) ? "" : user.getUsername() + itemView.getContext().getString(R.string.workmate_not_eating) + ".");
        }

    }
}
