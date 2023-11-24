package com.seg2105.hams.UI.patientFragments;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.seg2105.hams.R;
import com.seg2105.hams.Users.Doctor;
import com.seg2105.hams.Users.Person;
import com.seg2105.hams.Util.ResultsAdapter;

import java.util.List;

public class BookingSearchResultsFragment extends Fragment implements ResultsAdapter.OnItemClickListener{

    List<Doctor> availableDoctors;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookingsearchresults, container, false);

        if (getArguments() != null) {
            availableDoctors = (List<Doctor>) getArguments().getSerializable("availableDoctors");
        }

        RecyclerView recyclerViewBookingSearchResults = view.findViewById(R.id.recyclerViewResults);


        ResultsAdapter resultsAdapter = new ResultsAdapter(availableDoctors, BookingSearchResultsFragment.this);
        recyclerViewBookingSearchResults.setAdapter(resultsAdapter);
        recyclerViewBookingSearchResults.setLayoutManager(new LinearLayoutManager(getActivity()));


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onItemClick(Doctor doctor, Person person) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("doctor", doctor);
        findNavController(requireView()).navigate(R.id.action_bookingSearchResultsFragment_to_availableBookings, bundle);
    }
}
