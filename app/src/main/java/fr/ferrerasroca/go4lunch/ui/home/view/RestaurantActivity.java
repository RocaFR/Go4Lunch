package fr.ferrerasroca.go4lunch.ui.home.view;

import static fr.ferrerasroca.go4lunch.ui.home.view.HomeActivity.EXTRA_PLACE_ID;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import fr.ferrerasroca.go4lunch.R;
import fr.ferrerasroca.go4lunch.data.injections.Injection;
import fr.ferrerasroca.go4lunch.data.models.User;
import fr.ferrerasroca.go4lunch.data.models.places.Place;
import fr.ferrerasroca.go4lunch.databinding.ActivityRestaurantBinding;
import fr.ferrerasroca.go4lunch.ui.home.view.adaptaters.WorkmateRestaurantViewAdapter;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.PlacesViewModel;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.UserViewModel;
import fr.ferrerasroca.go4lunch.utils.PlaceUtils;

public class RestaurantActivity extends AppCompatActivity {

    private ActivityRestaurantBinding viewBinding;
    private RecyclerView recyclerView;
    private WorkmateRestaurantViewAdapter workmateAdapter;
    private PlacesViewModel placesViewModel;
    private UserViewModel userViewModel;

    //todo below need to be remove
    private User user;
    private String actualPlaceID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewBinding = ActivityRestaurantBinding.inflate(getLayoutInflater());

        actualPlaceID = getIntent().getStringExtra(EXTRA_PLACE_ID);

        placesViewModel = Injection.providePlacesViewModel(Injection.providePlacesViewModelFactory());
        userViewModel = Injection.provideUserViewModel(Injection.provideIUserViewModelFactory());


        this.configureViewModelCalls();

        setContentView(viewBinding.getRoot());
    }

    private void configureViewModelCalls() {
        placesViewModel.getPlace().observe(this, this::configureViews);
        placesViewModel.retrievePlaceByID(actualPlaceID);
        userViewModel.getUsers().observe(this, this::configureRecyclerView);
        userViewModel.retrieveUsers(actualPlaceID);
    }

    private void configureViews(Place place) {
        viewBinding.textViewRestaurantName.setText(TextUtils.isEmpty(place.getName()) ? "" : place.getName());
        viewBinding.textViewRestaurantAddress.setText(TextUtils.isEmpty(place.getVicinity()) ? "" : place.getVicinity());
        Glide.with(this).load(place.getPhotoUrl()).error(R.drawable.ic_baseline_broken_image_24).into(viewBinding.imageViewRestaurantPictureBanner);
        this.configureRating(place);
        this.getUser();
        this.configureOnClickListeners(place);
    }

    private void configureRating(Place place) {
        if (place.isRated()) {
            Float convertedRating = PlaceUtils.convertRating(5f, 3f, place.getRating());
            viewBinding.ratingBarRestaurant.setRating(convertedRating);
        } else {
            viewBinding.ratingBarRestaurant.setVisibility(View.INVISIBLE);
        }
    }

    private void getUser() {
        userViewModel.getUser().observe(this, user -> {
            this.user = user;
            this.configureFABPlaceIDChoice(user);
            this.configureImageButtonLike(user);
        });
        userViewModel.retrieveUser();
    }

    private void configureOnClickListeners(Place place) {
        this.configurePlaceIDChoiceListener(place);
        this.configureLikedPlacesListener(place);
    }

    private void configurePlaceIDChoiceListener(Place place) {
        viewBinding.fabUserChoice.setOnClickListener(v -> {
            if (this.user != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                if (user.getPlaceIDChoice() == null || !user.getPlaceIDChoice().equals(actualPlaceID)) {
                    builder.setTitle(getString(R.string.title_choice_confirmation))
                            .setMessage(getString(R.string.message_choice_confirmation) + place.getName() + getString(R.string.interrogation))
                            .setNegativeButton(R.string.button_negative, null)
                            .setPositiveButton(R.string.button_positive, (dialog, which) -> userViewModel.setPlaceIDChoice(user.getUid(), actualPlaceID, callback))
                            .show();
                } else {
                    builder.setTitle(getString(R.string.title_choice_cancel))
                            .setMessage(R.string.message_choice_cancel)
                            .setNegativeButton(R.string.button_negative, null)
                            .setPositiveButton(R.string.button_positive, (dialog, which) -> {
                                user.setPlaceIDChoice(null);
                                userViewModel.setPlaceIDChoice(user.getUid(), User.PLACE_ID_INITIAL_VALUE, callback); })
                            .show();
                }
            }
        });
    }

    UserViewModel.PlaceIDChoiceSettedListener callback = new UserViewModel.PlaceIDChoiceSettedListener() {
        @Override
        public void onPlaceIDChoiceSetted(String placeID) {
            user.setPlaceIDChoice(placeID);
            configureFABPlaceIDChoice(user);

            userViewModel.retrieveUsers(actualPlaceID);

            if (!placeID.isEmpty()) {
                Snackbar.make(viewBinding.getRoot(), getString(R.string.restaurant_setted), BaseTransientBottomBar.LENGTH_LONG).show();
            }
        }
    };

    private void configureLikedPlacesListener(Place place) {
        viewBinding.imageButtonLike.setOnClickListener(v -> {
            if (this.user != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                if (!user.getLikedPlaces().contains(actualPlaceID)) {
                    builder.setTitle(getString(R.string.title_likeAPlace))
                            .setMessage(getString(R.string.message_likeAPlace) + place.getName() + getString(R.string.interrogation))
                            .setNegativeButton(R.string.button_negative, null)
                            .setPositiveButton(R.string.button_positive, (dialog, which) -> {
                                user.addPlaceToLike(actualPlaceID);
                                userViewModel.setLikedPlaces(user.getUid(), user.getLikedPlaces(), callbackLikedPlaces); })
                            .show();
                } else {
                    builder.setTitle(getString(R.string.title_dislikeAPlace))
                            .setMessage(getString(R.string.message_disklikeAPlace) + place.getName() + getString(R.string.interrogation))
                            .setNegativeButton(R.string.button_negative, null)
                            .setPositiveButton(R.string.button_positive, (dialog, which) -> {
                                user.removeALikedPlace(actualPlaceID);
                                userViewModel.setLikedPlaces(user.getUid(), user.getLikedPlaces(), callbackRemoveALikedPlaces); })
                            .show();
                }
            }

        });
    }

    UserViewModel.LikedPlacesSettedListener callbackLikedPlaces = new UserViewModel.LikedPlacesSettedListener() {
        @Override
        public void onLikedPlacesSetted() {
            configureImageButtonLike(user);
            Snackbar.make(viewBinding.getRoot(), getString(R.string.snackbar_placeLiked), BaseTransientBottomBar.LENGTH_LONG).show();
        }
    };

    UserViewModel.LikedPlacesSettedListener callbackRemoveALikedPlaces = new UserViewModel.LikedPlacesSettedListener() {
        @Override
        public void onLikedPlacesSetted() {
            configureImageButtonLike(user);
            Snackbar.make(viewBinding.getRoot(),
                    getString(R.string.snackbar_dislikeAPlace),
                    BaseTransientBottomBar.LENGTH_LONG).show();
        }
    };

    private void configureRecyclerView(List<User> users) {
        if (workmateAdapter == null) {
            if (!users.isEmpty()) {
                //this.users = users;
                viewBinding.imageViewNoWorkmates.setVisibility(View.GONE);

                recyclerView = findViewById(R.id.recyclerView_workmates);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                workmateAdapter = new WorkmateRestaurantViewAdapter(users);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(workmateAdapter);
            } else {
                viewBinding.imageViewNoWorkmates.setVisibility(View.VISIBLE);
            }
        } else {
            workmateAdapter = new WorkmateRestaurantViewAdapter(users);
            recyclerView.setAdapter(workmateAdapter);
        }
    }

    private void configureImageButtonLike(User user) {
        Drawable drawable;
        if (user.getLikedPlaces().contains(actualPlaceID)) {
            drawable = AppCompatResources.getDrawable(this, R.drawable.ic_baseline_star_30);
        } else {
            drawable = AppCompatResources.getDrawable(this, R.drawable.ic_baseline_star_unlike_30);
        }
        viewBinding.imageButtonLike.setImageDrawable(drawable);
    }

    private void configureFABPlaceIDChoice(User user) {
        String placeIDChoice = user.getPlaceIDChoice();
        if (placeIDChoice != null) {
            Drawable drawable;
            if (placeIDChoice.equals(actualPlaceID)) {
                drawable = AppCompatResources.getDrawable(this, R.drawable.ic_baseline_check_circle_50);
            } else {
                drawable = AppCompatResources.getDrawable(this, R.drawable.ic_baseline_check_disable_circle_50);
            }
            viewBinding.fabUserChoice.setImageDrawable(drawable);
        }
    }
}