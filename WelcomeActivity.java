package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve the user name and role passed from the authentication process
        String userName = getIntent().getStringExtra("userName");
        String userRole = getIntent().getStringExtra("userRole");

        // Display the welcome message with the user's name and role
        displayWelcomeMessage(userName, userRole);
    }

    private void displayWelcomeMessage(String name, String userRole) {
        TextView welcomeMessageTextView = findViewById(R.id.welcomeMessageTextView);

        if (name != null && !name.isEmpty() && userRole != null) {
            String message = "Welcome " + name + "! Your role is " + userRole;
            welcomeMessageTextView.setText(message);
        }
    }
}
