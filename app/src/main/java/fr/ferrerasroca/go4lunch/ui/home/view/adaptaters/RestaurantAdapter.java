package fr.ferrerasroca.go4lunch.ui.home.view.adaptaters;

import android.location.Location;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import fr.ferrerasroca.go4lunch.R;
import fr.ferrerasroca.go4lunch.data.models.places.Place;
import fr.ferrerasroca.go4lunch.ui.home.view.viewholders.RestaurantViewHolder;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantViewHolder> {

    private final List<Place> places;
    private final Location userLocation;

    public RestaurantAdapter(List<Place> places, Location userLocation) {
        this.places = places;
        this.userLocation = userLocation;
    }

    @NonNull
    @NotNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new RestaurantViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RestaurantViewHolder holder, int position) {
        holder.updateRestaurantWithPlace(places.get(position), userLocation);
    }

    @Override
    public int getItemCount() {
        return places.size();
    }
}
