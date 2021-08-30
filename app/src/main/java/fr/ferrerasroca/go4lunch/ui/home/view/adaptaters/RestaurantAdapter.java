package fr.ferrerasroca.go4lunch.ui.home.view.adaptaters;

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

    public RestaurantAdapter(List<Place> places) {
        this.places = places;
    }

    @NonNull
    @NotNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new RestaurantViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RestaurantViewHolder holder, int position) {
        holder.updateRestaurantWithPlace(places.get(position));
    }

    @Override
    public int getItemCount() {
        return places.size();
    }
}
