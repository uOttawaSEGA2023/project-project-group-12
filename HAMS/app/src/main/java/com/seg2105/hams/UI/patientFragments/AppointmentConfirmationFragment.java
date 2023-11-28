package com.seg2105.hams.UI.patientFragments;

import static androidx.navigation.Navigation.findNavController;
import static com.seg2105.hams.Managers.AppointmentManager.putAppointmentInDatabase;
import static com.seg2105.hams.Managers.UserManager.getCurrentUser;
import static com.seg2105.hams.Managers.UserManager.getUserFromDatabase;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.seg2105.hams.Managers.Appointment;
import com.seg2105.hams.R;
import com.seg2105.hams.Users.Doctor;
import com.seg2105.hams.Util.UserCallback;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class AppointmentConfirmationFragment extends Fragment {

    private ArrayList<String> availableAppointment;
    private String shiftID;

    public AppointmentConfirmationFragment(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_appointmentconfirmation, container, false);
        TextView name = view.findViewById(R.id.name);
        TextView specialties = view.findViewById(R.id.specialties);
        TextView appointmentStartTime = view.findViewById(R.id.appointmentStartTime);
        TextView appointmentEndTime = view.findViewById(R.id.appointmentEndTime);

        if (getArguments() != null) {
            availableAppointment = (ArrayList<String>) getArguments().getSerializable("availableAppointment");
        }

        if (availableAppointment!= null){
            getUserFromDatabase(availableAppointment.get(3), new UserCallback() {
                @Override
                public void onSuccess() {}
                @Override
                public void onSuccess(Object object) {
                    Doctor doctor = (Doctor) object;
                    name.setText(MessageFormat.format("Name: {0} {1}", doctor.getFirstName(), doctor.getLastName()));
                    specialties.setText(MessageFormat.format("Specialties: {0}", doctor.getSpecialties().toString().replace("[", "").replace("]", "")));
                    appointmentStartTime.setText(MessageFormat.format("Appointment start time: {0}", availableAppointment.get(0)));
                    appointmentEndTime.setText(MessageFormat.format("Appointment end time: {0}", availableAppointment.get(1)));
                }
                @Override
                public void onListLoaded(List list) {}
                @Override
                public void onFailure(String error) {}
            });
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button back_button = view.findViewById(R.id.btn_back);
        Button confirm_button = view.findViewById(R.id.btn_confirm);

        confirm_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (availableAppointment!=null) {
                    Appointment a = new Appointment(availableAppointment.get(0), availableAppointment.get(1),availableAppointment.get(3), getCurrentUser().getUUID(), "pending", availableAppointment.get(4));
                    putAppointmentInDatabase(a, new UserCallback() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(requireContext(), "Successfully booked appointment.", Toast.LENGTH_SHORT).show();
                            findNavController(view).navigate(R.id.action_confirmationFragment_to_patient);
                        }

                        @Override
                        public void onSuccess(Object object) {

                        }

                        @Override
                        public void onListLoaded(List list) {

                        }

                        @Override
                        public void onFailure(String error) {
                            Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        // Back button navigation
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findNavController(view).popBackStack();
            }
        });
    }
}
