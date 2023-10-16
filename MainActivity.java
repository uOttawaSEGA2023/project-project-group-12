package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText nameEditText;
    private EditText roleEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameEditText = findViewById(R.id.nameEditText);
        roleEditText = findViewById(R.id.roleEditText);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the user's input for name and role
                String userName = nameEditText.getText().toString();
                String userRole = roleEditText.getText().toString();

                if (!userName.isEmpty() && !userRole.isEmpty()) {
                    // If both name and role are provided, navigate to the WelcomeActivity
                    navigateToWelcomeScreen(userName, userRole);
                }
            }
        });
    }

    private void navigateToWelcomeScreen(String name, String userRole) {
        Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
        intent.putExtra("userName", name);
        intent.putExtra("userRole", userRole);
        startActivity(intent);
    }
}
