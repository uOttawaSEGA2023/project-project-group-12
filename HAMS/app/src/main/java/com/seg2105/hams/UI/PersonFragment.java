package com.seg2105.hams.UI;

import static androidx.navigation.Navigation.findNavController;
import static com.seg2105.hams.Managers.AppointmentManager.removeAppointmentFromDatabase;
import static com.seg2105.hams.Managers.RatingManager.createNewRating;
import static com.seg2105.hams.Managers.UserManager.getCurrentUser;
import static com.seg2105.hams.Managers.UserManager.updateStatus;
import static com.seg2105.hams.Util.Util.appointmentIsPast;

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

public class PersonFragment extends Fragment implements RatingDialogFragment.OnRatingSelectedListener {

    private Person person;
    private Appointment appointment;

    public PersonFragment(){}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        TextView name = view.findViewById(R.id.name);
        TextView address = view.findViewById(R.id.address);
        TextView phoneNumber = view.findViewById(R.id.phone_number);
        TextView healthNumber = view.findViewById(R.id.health_number);
        TextView employeeNumber = view.findViewById(R.id.employee_number);
        TextView specialties = view.findViewById(R.id.specialties);
        TextView appointmentStartTime = view.findViewById(R.id.appointmentStartTime);
        TextView appointmentEndTime = view.findViewById(R.id.appointmentEndTime);
        TextView appointmentID = view.findViewById(R.id.appointmentID);
        TextView status = view.findViewById(R.id.status);
        TextView rating = view.findViewById(R.id.rating);

        // Prevents NullPointerException
        if (getArguments() != null && getArguments().size()==1) {
            person = (Person) getArguments().getSerializable("person");
        }
        else if (getArguments() != null && getArguments().size()==2) {
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
        if (person instanceof Doctor) {
            employeeNumber.setText(MessageFormat.format("Employee Number: {0}", ((Doctor) person).getEmployeeNumber()));
            specialties.setText(MessageFormat.format("Specialties: {0}", ((Doctor) person).getSpecialties().toString().replace("[", "").replace("]", "")));
            healthNumber.setVisibility(View.GONE);

        }
        if (appointment!=null) {
            appointmentStartTime.setText(String.format("Start time: %s", appointment.getStartDateTime()));
            appointmentEndTime.setText(String.format("End time: %s", appointment.getEndDateTime()));
            appointmentID.setText(String.format("Appointment ID: %s", appointment.getAppointmentID()));
            status.setText(appointment.getStatus());
            if ((appointment.getRating()!=0)) {
                rating.setVisibility(View.VISIBLE);
                rating.setText(String.format("Rating: %s", appointment.getRating()));
            }
        }




        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button back_button = view.findViewById(R.id.btn_back);
        Button accept_button = view.findViewById(R.id.btn_accept);
        Button reject_button = view.findViewById(R.id.btn_reject);
        Button cancelBtn = view.findViewById(R.id.btn_cancel);
        Button rateBtn = view.findViewById(R.id.btn_rate);



        // Hide buttons depending on status for registration
        if (appointment == null && person!=null) {
            if ("rejected".equals(person.getStatus())) reject_button.setVisibility(View.GONE);
            if ("accepted".equals(person.getStatus())) {
                reject_button.setVisibility(View.GONE);
                accept_button.setVisibility(View.GONE);
            }

        }

        // Functionality for doctor viewing their appointment
        if (appointment != null && getCurrentUser() instanceof Doctor) {
            if (!("accepted".equals(appointment.getStatus()))) {
                accept_button.setVisibility(View.VISIBLE);
                reject_button.setVisibility(View.VISIBLE);
            }
            if ("accepted".equals(appointment.getStatus())) {
                accept_button.setVisibility(View.GONE);
                reject_button.setVisibility(View.GONE);

                cancelBtn.setVisibility(View.VISIBLE);
            }
        }

        // Functionality for patient viewing their appointment
        if (appointment != null && getCurrentUser() instanceof Patient) {
            if (appointmentIsPast(appointment)&&"accepted".equals(appointment.getStatus())) {
                rateBtn.setVisibility(View.VISIBLE);
            }
            reject_button.setVisibility(View.GONE);
            accept_button.setVisibility(View.GONE);
            cancelBtn.setVisibility(View.VISIBLE);
        }



        // Update status'
        accept_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (appointment == null && person!= null){
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
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAppointmentFromDatabase(appointment, new UserCallback() {
                    @Override
                    public void onFailure(String error) {Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();}
                    @Override
                    public void onSuccess() {findNavController(view).popBackStack();}
                    @Override
                    public void onSuccess(Object object) {}
                    @Override
                    public void onListLoaded(List persons) {}
                });

            }
        });

        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRatingDialog();
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

    private void showRatingDialog() {
        RatingDialogFragment ratingDialogFragment = new RatingDialogFragment();

        // Bundle the appointment object
        Bundle bundle = new Bundle();
        bundle.putSerializable("appointment", appointment);
        ratingDialogFragment.setArguments(bundle);

        ratingDialogFragment.setOnRatingSelectedListener(PersonFragment.this);
        ratingDialogFragment.show(getChildFragmentManager(), "ratingDialog");
    }
    @Override
    public void onRatingSelected(int rating) {
        createNewRating(rating, appointment, new UserCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(requireContext(), "Successfully rated.", Toast.LENGTH_SHORT).show();
                findNavController(requireView()).popBackStack();
            }

            @Override
            public void onSuccess(Object object) {            }

            @Override
            public void onListLoaded(List list) {            }

            @Override
            public void onFailure(String error) {            }
        });
    }
}
