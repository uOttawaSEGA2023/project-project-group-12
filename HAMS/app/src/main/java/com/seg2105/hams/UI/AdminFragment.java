package com.seg2105.hams.UI;

import static androidx.navigation.Navigation.findNavController;

import static com.seg2105.hams.Users.UserManager.acceptAllPending;
import static com.seg2105.hams.Users.UserManager.getPersonsFromDatabase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.seg2105.hams.R;
import com.seg2105.hams.Users.Person;
import com.seg2105.hams.Util.PersonAdapter;
import com.seg2105.hams.Util.UserCallback;

import java.util.ArrayList;
import java.util.List;

public class AdminFragment extends Fragment implements PersonAdapter.OnItemClickListener {


    public AdminFragment() {};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Code to populate list of Person status'
        List<Person> pendingList = new ArrayList<>();
        List<Person> acceptedList = new ArrayList<>();
        List<Person> rejectedList = new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_admin, container, false);
        RecyclerView recyclerViewPending = view.findViewById(R.id.recyclerViewPending);
        RecyclerView recyclerViewAccepted = view.findViewById(R.id.recyclerViewAccepted);
        RecyclerView recyclerViewRejected = view.findViewById(R.id.recyclerViewRejected);

        // Get Persons from database, and place them in corresponding RecyclerViews
        getPersonsFromDatabase(new UserCallback() {
            @Override
            public void onSuccess() {}

            @Override
            public void onPersonsLoaded(List<Person> persons) {
                for (Person p : persons) {
                    if ("pending".equals(p.getStatus())) {pendingList.add(p);}
                    if ("accepted".equals(p.getStatus())) {acceptedList.add(p);}
                    if ("rejected".equals(p.getStatus())) {rejectedList.add(p);}
                }

                PersonAdapter pendingAdapter = new PersonAdapter(pendingList, AdminFragment.this);
                recyclerViewPending.setAdapter(pendingAdapter);
                recyclerViewPending.setLayoutManager(new LinearLayoutManager(getActivity()));

                PersonAdapter acceptedAdapter = new PersonAdapter(acceptedList, AdminFragment.this);
                recyclerViewAccepted.setAdapter(acceptedAdapter);
                recyclerViewAccepted.setLayoutManager(new LinearLayoutManager(getActivity()));

                PersonAdapter rejectedAdapter = new PersonAdapter(rejectedList, AdminFragment.this);
                recyclerViewRejected.setAdapter(rejectedAdapter);
                recyclerViewRejected.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
            @Override
            public void onFailure(String error) {
                Toast.makeText(requireContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button home_button = view.findViewById(R.id.btn_home);
        Button acceptAll_button = view.findViewById(R.id.btn_acceptAll);

        // Accept all pending
        acceptAll_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                acceptAllPending(new UserCallback() {

                    @Override
                    public void onPersonsLoaded(List<Person> persons) {}

                    @Override
                    public void onFailure(String error) {}

                    @Override
                    public void onSuccess() {
                        findNavController(view).navigate(R.id.adminFragment);
                    }
                });
            }
        });

        // Navigate home on click
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findNavController(view).navigate(R.id.action_admin_to_home);
            }
        });

    }

    @Override
    public void onItemClick(Person person) {
        // Handle item click, navigate to PersonFragment passing the selected Person object in Bundle
        Bundle bundle = new Bundle();
        bundle.putSerializable("person", person);
        findNavController(requireView()).navigate(R.id.action_admin_to_person, bundle);
    }


}
