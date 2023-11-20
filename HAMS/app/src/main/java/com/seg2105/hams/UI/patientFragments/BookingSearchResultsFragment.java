package com.seg2105.hams.UI.patientFragments;

import static com.seg2105.hams.Managers.AppointmentManager.getAvailableDoctorsFromDatabase;

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

import com.google.android.material.textfield.TextInputEditText;
import com.seg2105.hams.R;
import com.seg2105.hams.Users.Doctor;
import com.seg2105.hams.Users.Person;
import com.seg2105.hams.Util.AppointmentAdapter;
import com.seg2105.hams.Util.ResultsAdapter;
import com.seg2105.hams.Util.UserCallback;

import java.time.LocalDate;
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

    }
}
