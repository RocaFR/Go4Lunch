package fr.ferrerasroca.go4lunch.ui.home.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.LinearProgressIndicator;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import fr.ferrerasroca.go4lunch.R;
import fr.ferrerasroca.go4lunch.data.injections.Injection;
import fr.ferrerasroca.go4lunch.data.models.User;
import fr.ferrerasroca.go4lunch.data.models.places.Place;
import fr.ferrerasroca.go4lunch.ui.home.view.adaptaters.WorkmateAdapter;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.PlacesViewModel;
import fr.ferrerasroca.go4lunch.ui.home.viewmodel.UserViewModel;

public class WorkmatesFragment extends Fragment {

    private UserViewModel userViewModel;
    private PlacesViewModel placesViewModel;
    private LinearProgressIndicator progressIndicator;
    private RecyclerView recyclerView;

    public WorkmatesFragment() { }

    public static WorkmatesFragment newInstance() {
        return new WorkmatesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userViewModel = Injection.provideUserViewModel(Injection.provideUserViewModelFactory());
        placesViewModel = Injection.providePlacesViewModel(Injection.providePlacesViewModelFactory());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_workmates, container, false);

        this.configureViews(view);
        this.configureViewModelCalls();

        return  view;
    }

    private void configureViewModelCalls() {
        userViewModel.getUsers().observe(getViewLifecycleOwner(), users -> {
            configureProgressbar(users);
            placesViewModel.getPlacesChosenByUsers().observe(getViewLifecycleOwner(), places -> configureRecyclerView(users, places));
            placesViewModel.retrievePlacesByUsers(users);
        });
    }

    private void configureViews(View view) {
        progressIndicator = view.findViewById(R.id.progressbar_workmates);
        recyclerView = view.findViewById(R.id.workmates_recyclerView);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userViewModel.retrieveUsers(null);
    }

    private void configureProgressbar(List<User> users) {
        if (!users.isEmpty()) {
            progressIndicator.setVisibility(View.GONE);
        } else {
            progressIndicator.setVisibility(View.VISIBLE);
        }
    }

    private void configureRecyclerView(List<User> users, List<Place> places) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        WorkmateAdapter workmateAdapter = new WorkmateAdapter(users, places);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(workmateAdapter);
    }
}