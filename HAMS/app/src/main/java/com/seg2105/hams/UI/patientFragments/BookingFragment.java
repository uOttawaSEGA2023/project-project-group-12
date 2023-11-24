package com.seg2105.hams.UI.patientFragments;

import static androidx.navigation.Navigation.findNavController;
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

import com.google.android.material.textfield.TextInputEditText;
import com.seg2105.hams.R;
import com.seg2105.hams.Util.UserCallback;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class BookingFragment extends Fragment  {

    LocalDate date;
    TextInputEditText specialtyEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_booking, container, false);
        specialtyEditText = view.findViewById(R.id.specialty);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button specialtySearchButton = view.findViewById(R.id.specialtySearchButton);


        specialtySearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String specialty;
                specialty = String.valueOf(specialtyEditText.getText());
                if (specialty.isEmpty()) Toast.makeText(requireContext(), "Please enter a specialty to continue.", Toast.LENGTH_SHORT).show();
                else {
                    getAvailableDoctorsFromDatabase(specialty, new UserCallback() {
                        @Override
                        public void onSuccess() {                       }

                        @Override
                        public void onSuccess(Object object) {                        }

                        @Override
                        public void onListLoaded(List list) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("availableDoctors", (Serializable) list);
                            findNavController(requireView()).navigate(R.id.action_patient_to_bookingSearchResults, bundle);
                        }

                        @Override
                        public void onFailure(String error) {                        }
                    });
                }


            }
        });

    }



}
