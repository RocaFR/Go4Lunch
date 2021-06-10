package fr.ferrerasroca.go4lunch.ui.home.view;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import fr.ferrerasroca.go4lunch.R;
import fr.ferrerasroca.go4lunch.data.injections.Injection;
import fr.ferrerasroca.go4lunch.data.models.places.Place;
import fr.ferrerasroca.go4lunch.databinding.ActivityRestaurantBinding;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.PlacesViewModel;
import fr.ferrerasroca.go4lunch.utils.PlaceUtils;

import static fr.ferrerasroca.go4lunch.ui.home.view.HomeActivity.EXTRA_PLACE_ID;

public class RestaurantActivity extends AppCompatActivity {

    private fr.ferrerasroca.go4lunch.databinding.ActivityRestaurantBinding viewBinding;
    private PlacesViewModel placesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewBinding = ActivityRestaurantBinding.inflate(getLayoutInflater());
        placesViewModel = Injection.providePlacesViewModel(Injection.providePlacesViewModelFactory());

        this.retrievePlaceDetails(getIntent().getStringExtra(EXTRA_PLACE_ID));

        setContentView(viewBinding.getRoot());
    }

    private void retrievePlaceDetails(String placeID) {
        placesViewModel.getPlace().observe(this, this::configureViews);
        placesViewModel.retrievePlaceByID(placeID);
    }

    private void configureViews(Place place) {
        viewBinding.textViewRestaurantName.setText(TextUtils.isEmpty(place.getName()) ? "" : place.getName());
        viewBinding.textViewRestaurantAddress.setText(TextUtils.isEmpty(place.getVicinity()) ? "" : place.getVicinity());
        Glide.with(this).load(place.getPhotoUrl()).error(R.drawable.ic_baseline_broken_image_24).into(viewBinding.imageViewRestaurantPictureBanner);
        this.configureRating(place);
    }

    private void configureRating(Place place) {
        if (place.isRated()) {
            Float convertedRating = PlaceUtils.convertRating(5f, 3f, place.getRating());
            viewBinding.ratingBarRestaurant.setRating(convertedRating);
        } else {
            viewBinding.ratingBarRestaurant.setVisibility(View.INVISIBLE);
        }
    }
}