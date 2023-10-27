package com.seg2105.hams.UI;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.seg2105.hams.R;
import com.seg2105.hams.Users.Doctor;
import com.seg2105.hams.Users.Person;
import com.seg2105.hams.Util.PersonAdapter;

import java.util.ArrayList;
import java.util.List;

public class AdminFragment extends Fragment implements PersonAdapter.OnItemClickListener {


    public AdminFragment() {};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        List<Person> pendingList = new ArrayList<>();
        List<Person> approvedList = new ArrayList<>();
        List<Person> rejectedList = new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_admin, container, false);
        RecyclerView recyclerViewPending = view.findViewById(R.id.recyclerViewPending);



        pendingList.add(new Doctor("29219387", "John12391@gmail.com", "John", "Appleseed", "6139278133", "61 Landro rd.", "8282613", new String[]{"Hiking"}, "pending"));
        pendingList.add(new Doctor("234512523", "Loen12391@gmail.com", "Loen", "Appleseed", "61392466533", "61 Landro rd.", "4682613", new String[]{"Hiking"}, "pending"));


        PersonAdapter pendingAdapter = new PersonAdapter(pendingList, approvedList, rejectedList, this);
        recyclerViewPending.setAdapter(pendingAdapter);
        recyclerViewPending.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button home_button = view.findViewById(R.id.btn_home);

        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findNavController(view).navigate(R.id.action_admin_to_home);
            }
        });

    }

    @Override
    public void onItemClick(Person person) {
        // Handle item click, open PersonFragment passing the selected Person object
        PersonFragment personFragment = new PersonFragment();
        personFragment.setPerson(person);
        findNavController(requireView()).navigate(R.id.action_admin_to_person);
    }


}
