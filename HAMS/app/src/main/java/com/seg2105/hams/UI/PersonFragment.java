package com.seg2105.hams.UI;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.seg2105.hams.R;
import com.seg2105.hams.Users.Person;

public class PersonFragment extends Fragment {

    private Person person;

    public PersonFragment(){};

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);
        TextView name = view.findViewById(R.id.name);
        TextView address = view.findViewById(R.id.address);

        name.setText(person.getFirstName()+" "+ person.getLastName());
        address.setText(person.getAddress());
        return view;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
