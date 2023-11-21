package com.seg2105.hams.Util;

import static com.seg2105.hams.Managers.UserManager.getUserFromDatabase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seg2105.hams.R;
import com.seg2105.hams.Users.Doctor;
import com.seg2105.hams.Users.Patient;
import com.seg2105.hams.Users.Person;

import java.text.MessageFormat;
import java.util.List;

/*
    Class used to bridge java of RecyclerView to UI
 */
public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.BookingSearchResultsViewHolder> {
    private List<Doctor> list;
    private OnItemClickListener listener;

    // Define your custom listener interface here
    public interface OnItemClickListener {
        void onItemClick(Doctor doctor, Person person);
    }

    // Constructor to initialize the adapter with data and the custom listener
    public ResultsAdapter(List<Doctor> list, OnItemClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public BookingSearchResultsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_searchresults, parent, false);
        return new BookingSearchResultsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingSearchResultsViewHolder holder, int position) {
        // Bind data and set click listener here
        Doctor doctor = getItem(position);
        holder.bind(doctor, listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // Helper method to get the correct person item based on the position
    private Doctor getItem(int position) {
        return list.get(position);
    }

    class BookingSearchResultsViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;


        BookingSearchResultsViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
        }

        Patient patient;
        void bind(final Doctor doctor, final OnItemClickListener listener) {
            nameTextView.setText(MessageFormat.format("{0} {1}", doctor.getFirstName(), doctor.getLastName()));
            // Set click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(doctor, patient);
                    }
                }
            });
        }
    }

}
