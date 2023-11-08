package com.seg2105.hams.Util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seg2105.hams.Managers.Appointment;
import com.seg2105.hams.R;
import com.seg2105.hams.Users.Person;

import java.util.List;

/*
    Class used to bridge java of RecyclerView to UI
 */
public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder> {
    private List<Appointment> list;
    private OnItemClickListener listener;

    // Define your custom listener interface here
    public interface OnItemClickListener {
        void onItemClick(Appointment appointment);
    }

    // Constructor to initialize the adapter with data and the custom listener
    public AppointmentAdapter(List<Appointment> list, OnItemClickListener listener) {
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
        private TextView nameTextView;
        private TextView emailTextView;

        AppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            emailTextView = itemView.findViewById(R.id.email);
        }

        void bind(final Appointment appointment, final OnItemClickListener listener) {
//            nameTextView.setText(appointment.getFirstName()); // Assuming you have a getName() method in your Person class
//            emailTextView.setText(appointment.getEmail()); // Assuming you have a getEmail() method in your Person class

            // Set click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(appointment);
                    }
                }
            });
        }
    }

}
