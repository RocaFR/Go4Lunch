package fr.ferrerasroca.go4lunch.ui.home.view.adaptaters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import fr.ferrerasroca.go4lunch.R;
import fr.ferrerasroca.go4lunch.data.models.User;
import fr.ferrerasroca.go4lunch.data.models.places.Place;
import fr.ferrerasroca.go4lunch.ui.home.view.viewholders.WorkmateViewHolder;

public class WorkmateAdapter extends RecyclerView.Adapter<WorkmateViewHolder> {

    private final List<User> users;
    private final List<Place> places;

    public WorkmateAdapter(List<User> users, List<Place> places) {
        this.users = users;
        this.places = places;
    }

    @NonNull
    @NotNull
    @Override
    public WorkmateViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new WorkmateViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.workmates_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull WorkmateViewHolder holder, int position) {
        Place place = this.fetchTheChoosenPlace(places, users.get(position));
        holder.updateWorkmate(users.get(position), place);
    }

    private Place fetchTheChoosenPlace(List<Place> places, User user) {
        String placeIDChoice = user.getPlaceIDChoice();
        for (Place place: places) {
            if (placeIDChoice != null) {
                if (place.getPlaceId().equals(placeIDChoice)) return place;
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
