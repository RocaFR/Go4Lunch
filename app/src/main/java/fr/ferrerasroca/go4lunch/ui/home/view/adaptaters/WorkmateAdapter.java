package fr.ferrerasroca.go4lunch.ui.home.view.adaptaters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import fr.ferrerasroca.go4lunch.R;
import fr.ferrerasroca.go4lunch.data.models.User;
import fr.ferrerasroca.go4lunch.ui.home.view.viewholders.WorkmateViewHolder;

public class WorkmateAdapter extends RecyclerView.Adapter<WorkmateViewHolder> {

    private final List<User> users;

    public WorkmateAdapter(List<User> users) {
        this.users = users;
    }

    @NonNull
    @NotNull
    @Override
    public WorkmateViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new WorkmateViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.workmates_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull WorkmateViewHolder holder, int position) {
        holder.updateWorkmate(users.get(position));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
