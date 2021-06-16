package fr.ferrerasroca.go4lunch.ui.home.view;

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

import java.util.ArrayList;
import java.util.List;

import fr.ferrerasroca.go4lunch.R;
import fr.ferrerasroca.go4lunch.data.api.user.UserHelper;
import fr.ferrerasroca.go4lunch.data.injections.Injection;
import fr.ferrerasroca.go4lunch.data.models.User;
import fr.ferrerasroca.go4lunch.data.models.places.Place;
import fr.ferrerasroca.go4lunch.databinding.ActivityRestaurantBinding;
import fr.ferrerasroca.go4lunch.ui.home.view.adaptaters.WorkmateAdapter;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.PlacesViewModel;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.UserViewModel;
import fr.ferrerasroca.go4lunch.utils.PlaceUtils;

import static fr.ferrerasroca.go4lunch.ui.home.view.HomeActivity.EXTRA_PLACE_ID;

public class RestaurantActivity extends AppCompatActivity {

    private ActivityRestaurantBinding viewBinding;
    private PlacesViewModel placesViewModel;
    private UserViewModel userViewModel;
    private RecyclerView recyclerView;
    private WorkmateAdapter workmateAdapter;
    private List<User> users = new ArrayList<>();
    private Place currentPlace;
    private String placeID;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewBinding = ActivityRestaurantBinding.inflate(getLayoutInflater());

        placesViewModel = Injection.providePlacesViewModel(Injection.providePlacesViewModelFactory());
        userViewModel = Injection.provideUserViewModel(Injection.provideUserViewModelFactory());

        placeID = getIntent().getStringExtra(EXTRA_PLACE_ID);
        this.retrievePlaceDetails(placeID);
        this.retrievePlaceUsers(placeID );

        setContentView(viewBinding.getRoot());
    }

    private void retrievePlaceDetails(String placeID) {
        placesViewModel.getPlace().observe(this, this::configureViews);
        placesViewModel.retrievePlaceByID(placeID);
    }

    private void retrievePlaceUsers(String placeID) {
        userViewModel.getUsers().observe(this, this::configureRecyclerView);
        userViewModel.retrieveUsersByPlaceID(placeID);
    }

    private void configureViews(Place place) {
        currentPlace = place;
        viewBinding.textViewRestaurantName.setText(TextUtils.isEmpty(currentPlace.getName()) ? "" : currentPlace.getName());
        viewBinding.textViewRestaurantAddress.setText(TextUtils.isEmpty(currentPlace.getVicinity()) ? "" : currentPlace.getVicinity());
        Glide.with(this).load(currentPlace.getPhotoUrl()).error(R.drawable.ic_baseline_broken_image_24).into(viewBinding.imageViewRestaurantPictureBanner);
        this.configureRating();
        this.getUser();
        this.configureOnClickListeners();
    }

    private void configureRating() {
        if (currentPlace.isRated()) {
            Float convertedRating = PlaceUtils.convertRating(5f, 3f, currentPlace.getRating());
            viewBinding.ratingBarRestaurant.setRating(convertedRating);
        } else {
            viewBinding.ratingBarRestaurant.setVisibility(View.INVISIBLE);
        }
    }

    private void getUser() {
        userViewModel.getUserLiveData().observe(this, user -> {
            this.user = user;
            this.configureFABPlaceIDChoice(user);
            this.configureImageButtonLike(user);
        });
        userViewModel.retrieveUser();
    }

    private void configureOnClickListeners() {
        this.configurePlaceIDChoiceListener();
        this.configureLikedPlacesListener();
    }

    private void configurePlaceIDChoiceListener() {
        viewBinding.fabUserChoice.setOnClickListener(v -> {
            if (this.user != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                if (user.getPlaceIDChoice() == null || !user.getPlaceIDChoice().equals(placeID)) {
                    builder.setTitle(getString(R.string.title_choice_confirmation))
                            .setMessage(getString(R.string.message_choice_confirmation) + currentPlace.getName() + getString(R.string.interrogation))
                            .setNegativeButton(R.string.button_negative, null)
                            .setPositiveButton(R.string.button_positive, (dialog, which) -> userViewModel.setPlaceIDChoice(user.getUid(), placeID, callback))
                            .show();
                } else {
                    builder.setTitle(getString(R.string.title_choice_cancel))
                            .setMessage(R.string.message_choice_cancel)
                            .setNegativeButton(R.string.button_negative, null)
                            .setPositiveButton(R.string.button_positive, (dialog, which) -> {
                                user.setPlaceIDChoice(null);
                                userViewModel.setPlaceIDChoice(user.getUid(), "", callbackCancel); })
                            .show();
                }
            }
        });
    }

    UserHelper.Listeners callback = new UserHelper.Listeners() {
        @Override
        public void onRetrieved(User user) {

        }

        @Override
        public void onPlaceIDChoiceSetted() {
            user.setPlaceIDChoice(placeID);
            configureFABPlaceIDChoice(user);

            userViewModel.retrieveUsersByPlaceID(placeID);
            configureRecyclerView(users);

            Snackbar.make(viewBinding.getRoot(), getString(R.string.restaurant_setted), BaseTransientBottomBar.LENGTH_LONG).show();
        }

        @Override
        public void onLikedPlacesSetted() {

        }
    };

    UserHelper.Listeners callbackCancel = new UserHelper.Listeners() {
        @Override
        public void onRetrieved(User user) {

        }

        @Override
        public void onPlaceIDChoiceSetted() {
            user.setPlaceIDChoice("");
            configureFABPlaceIDChoice(user);

            userViewModel.retrieveUsersByPlaceID(placeID);
            configureRecyclerView(users);
        }

        @Override
        public void onLikedPlacesSetted() {

        }
    };

    private void configureLikedPlacesListener() {
        viewBinding.imageButtonLike.setOnClickListener(v -> {
            if (this.user != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                if (!user.getLikedPlaces().contains(placeID)) {
                    builder.setTitle(getString(R.string.title_likeAPlace))
                            .setMessage(getString(R.string.message_likeAPlace) + currentPlace.getName() + getString(R.string.interrogation))
                            .setNegativeButton(R.string.button_negative, null)
                            .setPositiveButton(R.string.button_positive, (dialog, which) -> {
                                user.addPlaceToLike(placeID);
                                userViewModel.setLikedPlaces(user.getUid(), user.getLikedPlaces(), callbackLikedPlaces); })
                            .show();
                } else {
                    builder.setTitle(getString(R.string.title_dislikeAPlace))
                            .setMessage(getString(R.string.message_disklikeAPlace) + currentPlace.getName() + getString(R.string.interrogation))
                            .setNegativeButton(R.string.button_negative, null)
                            .setPositiveButton(R.string.button_positive, (dialog, which) -> {
                                user.removeALikedPlace(placeID);
                                userViewModel.setLikedPlaces(user.getUid(), user.getLikedPlaces(), callbackRemoveALikedPlaces); })
                            .show();
                }
            }

        });
    }

    UserHelper.Listeners callbackLikedPlaces = new UserHelper.Listeners() {
        @Override
        public void onRetrieved(User user) {

        }

        @Override
        public void onPlaceIDChoiceSetted() {

        }

        @Override
        public void onLikedPlacesSetted() {
            configureImageButtonLike(user);
            Snackbar.make(viewBinding.getRoot(), getString(R.string.snackbar_placeLiked), BaseTransientBottomBar.LENGTH_LONG).show();
        }
    };

    UserHelper.Listeners callbackRemoveALikedPlaces = new UserHelper.Listeners() {
        @Override
        public void onRetrieved(User user) {

        }

        @Override
        public void onPlaceIDChoiceSetted() {

        }

        @Override
        public void onLikedPlacesSetted() {
            configureImageButtonLike(user);
            Snackbar.make(viewBinding.getRoot(),getString(R.string.snackbar_dislikeAPlace) + currentPlace.getName() + getString(R.string.snackbar_disLikeAPlace_end), BaseTransientBottomBar.LENGTH_LONG).show();
        }
    };

    private void configureRecyclerView(List<User> users) {
        if (workmateAdapter == null) {
            if (!users.isEmpty()) {
                this.users = users;
                viewBinding.imageViewNoWorkmates.setVisibility(View.GONE);

                recyclerView = findViewById(R.id.recyclerView_workmates);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
                workmateAdapter = new WorkmateAdapter(this.users);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(workmateAdapter);
            } else {
                viewBinding.imageViewNoWorkmates.setVisibility(View.VISIBLE);
            }
        } else {
            this.users = users;
            workmateAdapter = new WorkmateAdapter(this.users);
            recyclerView.setAdapter(workmateAdapter);
        }
    }

    private void configureImageButtonLike(User user) {
        Drawable drawable;
        if (user.getLikedPlaces().contains(placeID)) {
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
            if (placeIDChoice.equals(placeID)) {
                drawable = AppCompatResources.getDrawable(this, R.drawable.ic_baseline_check_circle_50);
            } else {
                drawable = AppCompatResources.getDrawable(this, R.drawable.ic_baseline_check_disable_circle_50);
            }
            viewBinding.fabUserChoice.setImageDrawable(drawable);
        }
    }
}