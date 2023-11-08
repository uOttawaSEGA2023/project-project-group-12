package com.seg2105.hams.UI;

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
import com.seg2105.hams.Util.UserCallback;

import java.text.MessageFormat;
import java.util.List;

public class PersonFragment extends Fragment {

    private Person person;
    private Appointment appointment;

    public PersonFragment(){};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        TextView name = view.findViewById(R.id.name);
        TextView address = view.findViewById(R.id.address);
        TextView phoneNumber = view.findViewById(R.id.phone_number);
        TextView healthNumber = view.findViewById(R.id.health_number);
        TextView employeeNumber = view.findViewById(R.id.employee_number);
        TextView specialties = view.findViewById(R.id.specialties);
        TextView appointmentTime = view.findViewById(R.id.appointmentTime);
        TextView appointmentID = view.findViewById(R.id.appointmentID);


        // Prevents NullPointerException
        if (getArguments() != null || getArguments().size()==1) {
            person = (Person) getArguments().getSerializable("person");
        }
        else if (getArguments() != null || getArguments().size()==2) {
            person = (Person) getArguments().getSerializable("person");
            appointment = (Appointment) getArguments().getSerializable("appointment");
        }

        // Set info in person fragment
        name.setText(MessageFormat.format("Name: {0} {1}", person.getFirstName(), person.getLastName()));
        address.setText(MessageFormat.format("Address: {0}", person.getAddress()));
        phoneNumber.setText(MessageFormat.format("Phone Number: {0}", person.getPhoneNumber()));
        if (person instanceof Patient) {
            healthNumber.setText(MessageFormat.format("Health Number: {0}", ((Patient) person).getHealthNumber()));
            employeeNumber.setVisibility(View.GONE);
            specialties.setVisibility(View.GONE);
        }
        else if (person instanceof Doctor) {
            employeeNumber.setText(MessageFormat.format("Employee Number: {0}", ((Doctor) person).getEmployeeNumber()));
            specialties.setText(MessageFormat.format("Specialties: {0}", ((Doctor) person).getSpecialties().toString().replace("[", "").replace("]", "")));
            healthNumber.setVisibility(View.GONE);
        }
        else if (appointment != null) {
            employeeNumber.setVisibility(View.GONE);
            specialties.setVisibility(View.GONE);
            healthNumber.setVisibility(View.GONE);
            appointmentTime.setText(MessageFormat.format("Appointment time: {0}", appointment.getDateTime()));
            appointmentID.setText(MessageFormat.format("Appointment ID: {0}", appointment.getAppointmentID()));

        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button back_button = view.findViewById(R.id.btn_back);
        Button accept_button = view.findViewById(R.id.btn_accept);
        Button reject_button = view.findViewById(R.id.btn_reject);

        // Hide buttons depending on status for registration
        if (appointment == null) {
            if ("rejected".equals(person.getStatus())) reject_button.setVisibility(View.GONE);
            if ("accepted".equals(person.getStatus())) {
                reject_button.setVisibility(View.GONE);
                accept_button.setVisibility(View.GONE);
            }

        }
        if (appointment != null) {
            if ("accepted".equals(appointment.getStatus())) accept_button.setVisibility(View.GONE);
        }

        // Update status'
        accept_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (appointment == null){
                    updateStatus("users" ,person.getUUID(), "accepted", new UserCallback() {
                        @Override
                        public void onFailure(String error) {Toast.makeText(requireContext(), "Error: " + error, Toast.LENGTH_SHORT).show();}
                        @Override
                        public void onSuccess() {findNavController(view).navigate(R.id.action_person_to_admin);}
                        @Override
                        public void onSuccess(Object object) {}
                        @Override
                        public void onListLoaded(List persons) {}
                    });
                }
                if (appointment != null){
                    updateStatus("appointments" ,appointment.getAppointmentID(), "accepted", new UserCallback() {
                        @Override
                        public void onFailure(String error) {Toast.makeText(requireContext(), "Error: " + error, Toast.LENGTH_SHORT).show();}
                        @Override
                        public void onSuccess() {findNavController(view).popBackStack();}
                        @Override
                        public void onSuccess(Object object) {}
                        @Override
                        public void onListLoaded(List persons) {}
                    });
                }
            }
        });
        reject_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (appointment == null) {
                    updateStatus("users" ,person.getUUID(), "rejected", new UserCallback() {
                        @Override
                        public void onFailure(String error) {Toast.makeText(requireContext(), "Error: " + error, Toast.LENGTH_SHORT).show();}
                        @Override
                        public void onSuccess() {findNavController(view).navigate(R.id.action_person_to_admin);}
                        @Override
                        public void onSuccess(Object object) {}
                        @Override
                        public void onListLoaded(List persons) {}
                    });
                }
                if (appointment != null) {
                    updateStatus("appointments" ,appointment.getAppointmentID(), "rejected", new UserCallback() {
                        @Override
                        public void onFailure(String error) {Toast.makeText(requireContext(), "Error: " + error, Toast.LENGTH_SHORT).show();}
                        @Override
                        public void onSuccess() {findNavController(view).popBackStack();}
                        @Override
                        public void onSuccess(Object object) {}
                        @Override
                        public void onListLoaded(List persons) {}
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
