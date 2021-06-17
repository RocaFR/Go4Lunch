package fr.ferrerasroca.go4lunch.ui.home.view.adaptaters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import fr.ferrerasroca.go4lunch.R;
import fr.ferrerasroca.go4lunch.data.models.User;
import fr.ferrerasroca.go4lunch.ui.home.view.viewholders.WorkmateViewRestaurantHolder;

public class WorkmateRestaurantViewAdapter extends RecyclerView.Adapter<WorkmateViewRestaurantHolder> {

    private final List<User> users;

    public WorkmateRestaurantViewAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @NotNull
    @Override
    public WorkmateViewRestaurantHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new WorkmateViewRestaurantHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.workmates_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull WorkmateViewRestaurantHolder holder, int position) {
        holder.updateWorkmate(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
