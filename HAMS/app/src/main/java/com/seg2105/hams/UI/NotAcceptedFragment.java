package com.seg2105.hams.UI;

import static androidx.navigation.Navigation.findNavController;
import static com.seg2105.hams.Managers.UserManager.getCurrentUser;
import static com.seg2105.hams.Managers.UserManager.isLoggedIn;
import static com.seg2105.hams.Managers.UserManager.setCurrentUser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.seg2105.hams.R;
import com.seg2105.hams.Users.Person;

public class NotAcceptedFragment extends Fragment {

    public NotAcceptedFragment() {};

    // Basic method called to create the view, used once.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notaccepted, container, false);
        return view;
    }

    // Once view has been created, this method is called
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Check if the user is not signed in, navigate to login page.
        if (!isLoggedIn()) {
            // Use findNavController(view) to get the NavController associated with this fragment.
            Navigation.findNavController(view).navigate(R.id.action_notaccepted_to_login);
            return;
        }
        Button logout_button = view.findViewById(R.id.btn_logout);
        TextView textView = view.findViewById(R.id.notAcceptedText);

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentUser(null);
                findNavController(v).navigate(R.id.action_notaccepted_to_login);
            }
        });
        if ("rejected".equals(((Person)getCurrentUser()).getStatus())) {
            textView.setText("Sorry, your application has been rejected. Please contact us at 888-555-2242 for more information.");
        }
        if ("pending".equals(((Person)getCurrentUser()).getStatus())) {
            textView.setText("Your application is still pending, please check back later.");
        }

    }
}
