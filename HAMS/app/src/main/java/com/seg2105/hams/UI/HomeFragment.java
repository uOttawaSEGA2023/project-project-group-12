package com.seg2105.hams.UI;

import static androidx.navigation.Navigation.findNavController;
import static com.seg2105.hams.Users.UserManager.getCurrentUser;
import static com.seg2105.hams.Users.UserManager.isLoggedIn;
import static com.seg2105.hams.Users.UserManager.setCurrentUser;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.seg2105.hams.R;

public class HomeFragment extends Fragment {

    public HomeFragment() {};

    // Basic method called to create the view, used once.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    // Once view has been created, this method is called
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = view.findViewById(R.id.welcomeText);

        // Check if the user is not signed in, navigate to login page.
        if (!isLoggedIn()) {
            // Use findNavController(view) to get the NavController associated with this fragment.
            Navigation.findNavController(view).navigate(R.id.action_home_to_login);
            return;
        }

        Button logout_button = view.findViewById(R.id.btn_logout);
        Button admin_button = view.findViewById(R.id.btn_admin);

        // If type admin, display admin inbox button
        if ("admin".equals(getCurrentUser().getRole())) admin_button.setVisibility(View.VISIBLE);


        // Button listeners
        admin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // view and v point to the same View instance, doesn't matter which one we input
                findNavController(v).navigate(R.id.action_home_to_admin);
            }
        });

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentUser(null);
                findNavController(v).navigate(R.id.action_home_to_login);
            }
        });

        // Set basic login text
        textView.setText("Hello. You are logged in as " + getCurrentUser().getRole());

    }
}
