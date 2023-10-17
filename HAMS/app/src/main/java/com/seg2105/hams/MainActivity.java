package com.seg2105.hams;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    Button button;
    Button form_button;
    TextView textView;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        button = findViewById(R.id.btn_logout);
        form_button = findViewById(R.id.form_redirect);
        textView = findViewById(R.id.welcomeText);
        user = auth.getCurrentUser();

        // If user not signed in, redirect to login page.
        if (user == null){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        } else {
            // Get role from database (Temporary, inefficient)
            DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");

            // Query the database to get the role for the specific UID
            usersRef.child(user.getUid()).child("Role").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // Handle the data change event
                    if (dataSnapshot.exists()) {
                        String role = dataSnapshot.getValue(String.class);
                        textView.setText("Welcome! You are logged in as a " + role + ".");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Handle errors, if any occurred during the operation
                }
            });


        }

        // Handle sign-out and form button.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        form_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InfoForm.class);
                startActivity(intent);
                finish();
            }
        });
    }
}