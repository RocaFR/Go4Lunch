package fr.ferrerasroca.go4lunch.ui.home.view;

import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.google.android.material.imageview.ShapeableImageView;

import org.jetbrains.annotations.NotNull;

import fr.ferrerasroca.go4lunch.R;
import fr.ferrerasroca.go4lunch.data.models.places.Place;

public class RestaurantViewHolder extends RecyclerView.ViewHolder {

    private final TextView textviewName;
    private final TextView textviewAddress;
    private final TextView textviewOpeningHours;
    private final TextView textviewDistance;
    private final TextView textviewNumberOfParticipants;
    private final AppCompatRatingBar ratingbarStars;
    private final ShapeableImageView imageviewPicture;

    public RestaurantViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        textviewName = itemView.findViewById(R.id.textView_restaurantName);
        textviewAddress = itemView.findViewById(R.id.textView_restaurantAddress);
        textviewOpeningHours = itemView.findViewById(R.id.textView_restaurantOpeningHours);
        textviewDistance = itemView.findViewById(R.id.textView_restaurantDistance);
        textviewNumberOfParticipants = itemView.findViewById(R.id.textView_restaurantNumberOfParticipants);
        ratingbarStars = itemView.findViewById(R.id.ratingBar_restaurantStars);
        imageviewPicture = itemView.findViewById(R.id.imageView_restaurant_picture);
    }

    public void updateRestaurantWithPlace(Place place, RequestManager glide) {
        textviewName.setText(TextUtils.isEmpty(place.getName()) ? "" : place.getName());
        textviewAddress.setText(TextUtils.isEmpty(place.getVicinity()) ? "" : place.getVicinity());
        if (place.getOpeningHours() != null ) {
            textviewOpeningHours.setText(place.getOpeningHours().getOpenNow() ? "Open" : "Close");
            if (place.getOpeningHours().getOpenNow()) textviewOpeningHours.setTypeface(textviewOpeningHours.getTypeface(), Typeface.BOLD);
        }

        //textviewDistance.setText(TextUtils.isEmpty(place.getName()) ? "" : place.getName());
        //textviewNumberOfParticipants.setText(TextUtils.isEmpty(place.getName()) ? "" : place.getName());
    }
}
