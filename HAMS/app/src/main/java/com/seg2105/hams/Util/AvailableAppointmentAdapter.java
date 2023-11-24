package com.seg2105.hams.Util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seg2105.hams.Managers.Appointment;
import com.seg2105.hams.Managers.Appointment;
import com.seg2105.hams.R;
import com.seg2105.hams.Users.Doctor;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/*
    Class used to bridge java of RecyclerView to UI
 */
public class AvailableAppointmentAdapter extends RecyclerView.Adapter<AvailableAppointmentAdapter.AvailableAppointmentViewHolder> {
    private List<ArrayList<String>> list;
    private OnItemClickListener listener;

    // Define your custom listener interface here
    public interface OnItemClickListener {
        void onItemClick(ArrayList<String> availableAppointment);
    }

    // Constructor to initialize the adapter with data and the custom listener
    public AvailableAppointmentAdapter(List<ArrayList<String>> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AvailableAppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_appointment, parent, false);
        return new AvailableAppointmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AvailableAppointmentViewHolder holder, int position) {
        // Bind data and set click listener here
        ArrayList<String> availableShift = getItem(position);
        holder.bind(availableShift, listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // Helper method to get the correct Appointment item based on the position
    private ArrayList<String> getItem(int position) {
        return list.get(position);
    }

    class AvailableAppointmentViewHolder extends RecyclerView.ViewHolder {
        private TextView startTimeTextView;
        private TextView endTimeTextView;
        private TextView nameTextView;
        private TextView appointmentIDTextView;

        AvailableAppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            startTimeTextView = itemView.findViewById(R.id.start);
            endTimeTextView = itemView.findViewById(R.id.end);
            nameTextView = itemView.findViewById(R.id.name);
            appointmentIDTextView = itemView.findViewById(R.id.appointmentID);
        }

        void bind(final ArrayList<String> availableAppointment, final OnItemClickListener listener) {

            startTimeTextView.setText(MessageFormat.format("Start: {0}", availableAppointment.get(0)));
            endTimeTextView.setText(MessageFormat.format("End: {0}", availableAppointment.get(1)));
            nameTextView.setVisibility(View.GONE);
            appointmentIDTextView.setVisibility(View.GONE);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(availableAppointment);
                    }
                }
            });

        }
    }

}
