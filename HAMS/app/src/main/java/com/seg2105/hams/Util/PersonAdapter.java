package com.seg2105.hams.Util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seg2105.hams.R;
import com.seg2105.hams.Users.Person;

import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {
    private List<Person> pendingList;
    private List<Person> approvedList;
    private List<Person> rejectedList;
    private OnItemClickListener listener;

    // Define your custom listener interface here
    public interface OnItemClickListener {
        void onItemClick(Person person);
    }

    // Constructor to initialize the adapter with data and the custom listener
    public PersonAdapter(List<Person> pendingList, List<Person> approvedList, List<Person> rejectedList, OnItemClickListener listener) {
        this.pendingList = pendingList;
        this.approvedList = approvedList;
        this.rejectedList = rejectedList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_person, parent, false);
        return new PersonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        // Bind data and set click listener here
        Person person = getItem(position);
        holder.bind(person, listener);
    }

    @Override
    public int getItemCount() {
        return pendingList.size() + approvedList.size() + rejectedList.size();
    }

    // Helper method to get the correct person item based on the position
    private Person getItem(int position) {
        if (position < pendingList.size()) {
            return pendingList.get(position);
        } else if (position < pendingList.size() + approvedList.size()) {
            return approvedList.get(position - pendingList.size());
        } else {
            return rejectedList.get(position - pendingList.size() - approvedList.size());
        }
    }

    // ViewHolder class
    class PersonViewHolder extends RecyclerView.ViewHolder {
        // Initialize views here

        PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize views and set click listener
        }

        // Bind data and set click listener for the item
        void bind(final Person person, final OnItemClickListener listener) {
            // Bind data to views here

            // Set click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClick(person);
                    }
                }
            });
        }
    }
}
