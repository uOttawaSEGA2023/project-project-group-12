package com.seg2105.hams.Util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seg2105.hams.R;
import com.seg2105.hams.Users.Person;

import java.util.List;

/*
    Class used to bridge java of RecyclerView to UI
 */
public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder> {
    private final List<Person> list;
    private final OnItemClickListener listener;

    // Define your custom listener interface here
    public interface OnItemClickListener {
        void onItemClick(Person person);
    }

    // Constructor to initialize the adapter with data and the custom listener
    public PersonAdapter(List<Person> list, OnItemClickListener listener) {
        this.list = list;
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
        return list.size();
    }

    // Helper method to get the correct person item based on the position
    private Person getItem(int position) {
        return list.get(position);
    }

    class PersonViewHolder extends RecyclerView.ViewHolder {
        private final TextView nameTextView;
        private final TextView emailTextView;

        PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            emailTextView = itemView.findViewById(R.id.email);
        }

        void bind(final Person person, final OnItemClickListener listener) {
            nameTextView.setText(person.getFirstName()); // Assuming you have a getName() method in your Person class
            emailTextView.setText(person.getEmail()); // Assuming you have a getEmail() method in your Person class

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
