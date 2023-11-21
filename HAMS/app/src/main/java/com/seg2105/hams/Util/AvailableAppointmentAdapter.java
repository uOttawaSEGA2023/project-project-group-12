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

import java.text.MessageFormat;
import java.util.List;

/*
    Class used to bridge java of RecyclerView to UI
 */
public class AvailableAppointmentAdapter extends RecyclerView.Adapter<AvailableAppointmentAdapter.AvailableAppointmentViewHolder> {
    private List<Appointment> list;
    private OnItemClickListener listener;

    // Define your custom listener interface here
    public interface OnItemClickListener {
        void onItemClick(Appointment appointment);
    }

    // Constructor to initialize the adapter with data and the custom listener
    public AvailableAppointmentAdapter(List<Appointment> list, OnItemClickListener listener) {
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
        Appointment appointment = getItem(position);
        holder.bind(appointment, listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // Helper method to get the correct Appointment item based on the position
    private Appointment getItem(int position) {
        return list.get(position);
    }

    class AvailableAppointmentViewHolder extends RecyclerView.ViewHolder {
        private TextView startTimeTextView;
        private TextView endTimeTextView;

        AvailableAppointmentViewHolder(@NonNull View itemView) {
            super(itemView);
            startTimeTextView = itemView.findViewById(R.id.startTime);
            endTimeTextView = itemView.findViewById(R.id.endTime);
        }

        void bind(final Appointment Appointment, final OnItemClickListener listener) {
            startTimeTextView.setText(MessageFormat.format("Start: {0}", Appointment.getStart()));
            endTimeTextView.setText(MessageFormat.format("End: {0}",Appointment.getEnd()));

            // Set click listener

        }
    }

}
