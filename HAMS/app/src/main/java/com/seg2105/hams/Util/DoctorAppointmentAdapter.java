package com.seg2105.hams.Util;

import static com.seg2105.hams.Managers.UserManager.getUserFromDatabase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seg2105.hams.Managers.Appointment;
import com.seg2105.hams.R;
import com.seg2105.hams.Users.Patient;
import com.seg2105.hams.Users.Person;

import java.text.MessageFormat;
import java.util.List;

/*
    Class used to bridge java of RecyclerView to UI
 */
public class DoctorAppointmentAdapter extends RecyclerView.Adapter<DoctorAppointmentAdapter.AppointmentViewHolder> {
    private final List<Appointment> list;
    private final OnItemClickListener listener;

    // Define your custom listener interface here
    public interface OnItemClickListener {
        void onItemClick(Appointment appointment, Person person);
    }

    // Constructor to initialize the adapter with data and the custom listener
    public DoctorAppointmentAdapter(List<Appointment> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment, parent, false);
        return new AppointmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentViewHolder holder, int position) {
        // Bind data and set click listener here
        Appointment appointment = getItem(position);
        holder.bind(appointment, listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // Helper method to get the correct person item based on the position
    private Appointment getItem(int position) {
        return list.get(position);
    }

    class AppointmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView startTextView;
        private final TextView endTextView;
        private final TextView appointmentIDTextView;

        AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            startTextView = itemView.findViewById(R.id.start);
            endTextView = itemView.findViewById(R.id.end);
            appointmentIDTextView = itemView.findViewById(R.id.appointmentID);
        }

        Patient patient;
        void bind(final Appointment appointment, final OnItemClickListener listener) {
            getUserFromDatabase(appointment.getPatientUUID(), new UserCallback() {
                @Override
                public void onSuccess() {                }

                @Override
                public void onSuccess(Object object) {
                    patient = (Patient)object;

                    nameTextView.setText(MessageFormat.format("{0} {1}", patient.getFirstName(), patient.getLastName()));
                    startTextView.setText(appointment.getStartDateTime());
                    endTextView.setText(appointment.getEndDateTime());
                    appointmentIDTextView.setText(appointment.getAppointmentID());
                }
                @Override
                public void onListLoaded(List list) {                }

                @Override
                public void onFailure(String error) {                }
            });


            // Set click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(appointment, patient);
                    }
                }
            });
        }
    }

}
