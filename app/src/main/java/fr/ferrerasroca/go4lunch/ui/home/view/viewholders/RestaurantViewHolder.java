package fr.ferrerasroca.go4lunch.ui.home.view.viewholders;

import android.annotation.SuppressLint;
import android.graphics.Typeface;
import android.location.Location;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.imageview.ShapeableImageView;

import org.jetbrains.annotations.NotNull;

import fr.ferrerasroca.go4lunch.R;
import fr.ferrerasroca.go4lunch.data.models.places.Place;
import fr.ferrerasroca.go4lunch.utils.PlaceUtils;

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
        ratingbarStars = itemView.findViewById(R.id.ratingBar_restaurant);
        imageviewPicture = itemView.findViewById(R.id.imageView_restaurant_picture);
    }

    @SuppressLint("SetTextI18n")
    public void updateRestaurantWithPlace(Place place, Location userLocation) {
        textviewName.setText(TextUtils.isEmpty(place.getName()) ? "" : place.getName());
        textviewAddress.setText(TextUtils.isEmpty(place.getVicinity()) ? "" : place.getVicinity());
        Glide.with(itemView.getContext()).load(place.getPhotoUrl()).error(R.drawable.ic_baseline_broken_image_24).into(imageviewPicture);
        textviewDistance.setText(place.getDistanceFromUser() + itemView.getContext().getString(R.string.distance_unit));

        this.configureOpeningHours(place);
        this.configureRating(place);

        //textviewNumberOfParticipants.setText(TextUtils.isEmpty(place.getName()) ? "" : place.getName());
    }

    private void configureOpeningHours(Place place) {
        if (place.getOpeningHours() != null ) {
            textviewOpeningHours.setText(place.getOpeningHours().getOpenNow() ? "Open" : "Close");
            if (place.getOpeningHours().getOpenNow()) textviewOpeningHours.setTypeface(textviewOpeningHours.getTypeface(), Typeface.BOLD);
        }
    }

    private void configureRating(Place place) {
        if (place.isRated()) {
            Float convertedRating = PlaceUtils.convertRating(5f, 3f, place.getRating());
            ratingbarStars.setRating(convertedRating);
        } else {
            ratingbarStars.setVisibility(View.INVISIBLE);
        }
    }
}