package com.seg2105.hams.UI;

import static androidx.navigation.Navigation.findNavController;

import static com.seg2105.hams.Users.UserManager.updateStatus;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.seg2105.hams.R;
import com.seg2105.hams.Users.Doctor;
import com.seg2105.hams.Users.Patient;
import com.seg2105.hams.Users.Person;
import com.seg2105.hams.Util.UserCallback;

import java.util.List;

public class PersonFragment extends Fragment {

    private Person person;

    public PersonFragment(){};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        TextView name = view.findViewById(R.id.name);
        TextView address = view.findViewById(R.id.address);
        TextView phoneNumber = view.findViewById(R.id.phone_number);
        TextView healthNumber = view.findViewById(R.id.health_number);
        TextView employeeNumber = view.findViewById(R.id.employee_number);
        TextView specialties = view.findViewById(R.id.specialties);


        // Prevents NullPointerException
        if (getArguments() != null) {
            person = (Person) getArguments().getSerializable("person");
        }

        // Set info in person fragment
        name.setText("Name: " + person.getFirstName()+" "+ person.getLastName());
        address.setText("Address: " + person.getAddress());
        phoneNumber.setText("Phone Number: " + person.getPhoneNumber());
        if (person instanceof Patient) {
            healthNumber.setText("Health Number: " + ((Patient) person).getHealthNumber());
            employeeNumber.setVisibility(View.GONE);
            specialties.setVisibility(View.GONE);
        }
        else if (person instanceof Doctor) {
            employeeNumber.setText("Employee Number: " + ((Doctor) person).getEmployeeNumber());
            specialties.setText("Specialties Number: " + ((Doctor) person).getSpecialties());
            healthNumber.setVisibility(View.GONE);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button back_button = view.findViewById(R.id.btn_back);
        Button accept_button = view.findViewById(R.id.btn_accept);
        Button reject_button = view.findViewById(R.id.btn_reject);

        // Hide buttons depending on status
        if ("rejected".equals(person.getStatus())) reject_button.setVisibility(View.GONE);
        if ("accepted".equals(person.getStatus())) {
            reject_button.setVisibility(View.GONE);
            accept_button.setVisibility(View.GONE);
        }

        // Update status'
        accept_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStatus(person.getUUID(), "accepted", new UserCallback() {
                    @Override
                    public void onPersonsLoaded(List<Person> persons) {}
                    @Override
                    public void onFailure(String error) {Toast.makeText(requireContext(), "Error: " + error, Toast.LENGTH_SHORT).show();}
                    @Override
                    public void onSuccess() {
                        findNavController(view).navigate(R.id.action_person_to_admin);
                    }
                });
            }
        });
        reject_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateStatus(person.getUUID(), "rejected", new UserCallback() {
                    @Override
                    public void onPersonsLoaded(List<Person> persons) {}
                    @Override
                    public void onFailure(String error) {Toast.makeText(requireContext(), "Error: " + error, Toast.LENGTH_SHORT).show();}
                    @Override
                    public void onSuccess() {
                        findNavController(view).navigate(R.id.action_person_to_admin);
                    }
                });
            }
        });

        // Back button navigation
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findNavController(view).navigate(R.id.action_person_to_admin);
            }
        });
    }
}
