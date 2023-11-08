package com.seg2105.hams.Util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seg2105.hams.Managers.Shift;
import com.seg2105.hams.R;

import java.text.MessageFormat;
import java.util.List;

/*
    Class used to bridge java of RecyclerView to UI
 */
public class ShiftAdapter extends RecyclerView.Adapter<ShiftAdapter.ShiftViewHolder> {
    private List<Shift> list;
    private OnItemClickListener listener;

    // Define your custom listener interface here
    public interface OnItemClickListener {
        void onItemClick(Shift shift);
    }

    // Constructor to initialize the adapter with data and the custom listener
    public ShiftAdapter(List<Shift> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ShiftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shift, parent, false);
        return new ShiftViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShiftViewHolder holder, int position) {
        // Bind data and set click listener here
        Shift shift = getItem(position);
        holder.bind(shift, listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // Helper method to get the correct shift item based on the position
    private Shift getItem(int position) {
        return list.get(position);
    }

    class ShiftViewHolder extends RecyclerView.ViewHolder {
        private TextView startTimeTextView;
        private TextView endTimeTextView;
        private TextView shiftIDTextView;
        private Button cancelBtn;

        ShiftViewHolder(@NonNull View itemView) {
            super(itemView);
            startTimeTextView = itemView.findViewById(R.id.startTime);
            endTimeTextView = itemView.findViewById(R.id.endTime);
            shiftIDTextView = itemView.findViewById(R.id.shiftID);
            cancelBtn = itemView.findViewById(R.id.cancelButton);
        }

        void bind(final Shift shift, final OnItemClickListener listener) {
            startTimeTextView.setText(MessageFormat.format("Start: {0}", shift.getStart()));
            endTimeTextView.setText(MessageFormat.format("End: {0}",shift.getEnd()));
            shiftIDTextView.setText(shift.getShiftID());

            // Set click listener
            cancelBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(shift);
                    }
                }
            });
        }
    }

}
