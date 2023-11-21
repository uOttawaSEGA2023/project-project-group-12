package com.seg2105.hams.UI.patientFragments;

import static androidx.navigation.Navigation.findNavController;
import static com.seg2105.hams.Managers.UserManager.updateStatus;

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
import com.seg2105.hams.Users.Patient;
import com.seg2105.hams.Users.Person;
import com.seg2105.hams.Util.AvailableAppointmentAdapter;
import com.seg2105.hams.Util.UserCallback;

import java.text.MessageFormat;
import java.util.List;

public class AvailableAppointmentsFragment extends Fragment implements AvailableAppointmentAdapter.OnItemClickListener {

    private Doctor doctor;

    public AvailableAppointmentsFragment(){};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_availableappointments, container, false);
        TextView name = view.findViewById(R.id.name);
        TextView phoneNumber = view.findViewById(R.id.phone_number);
        TextView specialties = view.findViewById(R.id.specialties);


        // Prevents NullPointerException
        doctor = (Doctor) getArguments().getSerializable("doctor");


        // Set info in person fragment
        name.setText(MessageFormat.format("Name: {0} {1}", doctor.getFirstName(), doctor.getLastName()));
        phoneNumber.setText(MessageFormat.format("Phone Number: {0}", doctor.getPhoneNumber()));
        specialties.setText(doctor.getSpecialties().toString());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button back_button = view.findViewById(R.id.btn_back);



        // Back button navigation
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findNavController(view).popBackStack();
            }
        });
    }

    @Override
    public void onItemClick(Appointment appointment) {
        
    }
}
