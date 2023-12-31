package com.seg2105.hams.UI.patientFragments;

import static androidx.navigation.Navigation.findNavController;
import static com.seg2105.hams.Managers.AppointmentManager.getAppointmentsFromDatabase;
import static com.seg2105.hams.Managers.UserManager.getCurrentUser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.seg2105.hams.Managers.Appointment;
import com.seg2105.hams.R;
import com.seg2105.hams.Users.Person;
import com.seg2105.hams.Util.PatientAppointmentAdapter;
import com.seg2105.hams.Util.UserCallback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatientAppointmentFragment extends Fragment implements PatientAppointmentAdapter.OnItemClickListener {

    public PatientAppointmentFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_patientappointment, container, false);

        // Code to populate list of Appointment status'
        List<Appointment> upcomingList = new ArrayList<>();
        List<Appointment> pastList = new ArrayList<>();
        RecyclerView recyclerViewUpcoming = view.findViewById(R.id.recyclerViewUpcoming);
        RecyclerView recyclerViewPast = view.findViewById(R.id.recyclerViewPast);
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
                        Date inputDate = dateFormat.parse(appointment.getStartDateTime());

                        Date currentDate = new Date();

                        if (inputDate.before(currentDate)){
                            pastList.add(appointment);
                        }
                        else {
                            upcomingList.add(appointment);
                        }
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }
                }

                PatientAppointmentAdapter upcomingAdapter = new PatientAppointmentAdapter(upcomingList, PatientAppointmentFragment.this);
                recyclerViewUpcoming.setAdapter(upcomingAdapter);
                recyclerViewUpcoming.setLayoutManager(new LinearLayoutManager(getActivity()));

                PatientAppointmentAdapter pastAdapter = new PatientAppointmentAdapter(pastList, PatientAppointmentFragment.this);
                recyclerViewPast.setAdapter(pastAdapter);
                recyclerViewPast.setLayoutManager(new LinearLayoutManager(getActivity()));

            }
            @Override
            public void onFailure(String error) {

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
                findNavController(view).navigate(R.id.action_patient_to_home);
            }
        });

    }

    @Override
    public void onItemClick(Appointment appointment, Person person) {
        // Handle item click, navigate to AppointmentFragment passing the selected Appointment object in Bundle
        Bundle bundle = new Bundle();
        bundle.putSerializable("appointment", appointment);
        bundle.putSerializable("person", person);
        findNavController(requireView()).navigate(R.id.action_patient_to_person, bundle);
    }



}
