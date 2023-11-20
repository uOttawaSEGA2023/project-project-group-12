package com.seg2105.hams.UI.doctorFragments;

import static androidx.navigation.Navigation.findNavController;
import static com.seg2105.hams.Managers.AppointmentManager.getAppointmentsFromDatabase;
import static com.seg2105.hams.Managers.UserManager.getCurrentUser;

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

import com.seg2105.hams.Managers.Appointment;
import com.seg2105.hams.R;
import com.seg2105.hams.Users.Person;
import com.seg2105.hams.Util.AppointmentAdapter;
import com.seg2105.hams.Util.UserCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DoctorAppointmentFragment extends Fragment implements AppointmentAdapter.OnItemClickListener {

    public DoctorAppointmentFragment() {};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Code to populate list of Appointment status'
        List<Appointment> pendingList = new ArrayList<>();
        List<Appointment> acceptedList = new ArrayList<>();
        List<Appointment> pastList = new ArrayList<>();

        View view = inflater.inflate(R.layout.fragment_doctorappointment, container, false);
        RecyclerView recyclerViewPending = view.findViewById(R.id.recyclerViewPending);
        RecyclerView recyclerViewAccepted = view.findViewById(R.id.recyclerViewAccepted);
        RecyclerView recyclerViewPast = view.findViewById(R.id.recyclerViewPast);

        // Get Appointments from database, and place them in corresponding RecyclerViews
        getAppointmentsFromDatabase((Person)getCurrentUser(),new UserCallback() {
            @Override
            public void onSuccess() {}

            @Override
            public void onSuccess(Object object) {

            }

            @Override
            public void onListLoaded(List appointments) {
                for (Object a : appointments) {
                    Appointment appointment = (Appointment)a;

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    try {
                        Date inputDate = dateFormat.parse(appointment.getDateTime());

                        Date currentDate = new Date();

                        if (inputDate.before(currentDate)){
                            pastList.add(appointment);
                        }
                        else {
                            if ("pending".equals(appointment.getStatus())) {pendingList.add(appointment);}
                            if ("accepted".equals(appointment.getStatus())) {acceptedList.add(appointment);}
                        }
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }

                AppointmentAdapter pendingAdapter = new AppointmentAdapter(pendingList, DoctorAppointmentFragment.this);
                recyclerViewPending.setAdapter(pendingAdapter);
                recyclerViewPending.setLayoutManager(new LinearLayoutManager(getActivity()));

                AppointmentAdapter acceptedAdapter = new AppointmentAdapter(acceptedList, DoctorAppointmentFragment.this);
                recyclerViewAccepted.setAdapter(acceptedAdapter);
                recyclerViewAccepted.setLayoutManager(new LinearLayoutManager(getActivity()));

                AppointmentAdapter pastAdapter = new AppointmentAdapter(pastList, DoctorAppointmentFragment.this);
                recyclerViewPast.setAdapter(pastAdapter);
                recyclerViewPast.setLayoutManager(new LinearLayoutManager(getActivity()));

;
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

        // Navigate home on click
        home_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findNavController(view).navigate(R.id.action_doctor_to_home);
            }
        });

    }

    @Override
    public void onItemClick(Appointment appointment, Person person) {
        // Handle item click, navigate to AppointmentFragment passing the selected Appointment object in Bundle
        Bundle bundle = new Bundle();
        bundle.putSerializable("appointment", appointment);
        bundle.putSerializable("person", person);
        findNavController(requireView()).navigate(R.id.action_doctor_to_person, bundle);
    }


}
