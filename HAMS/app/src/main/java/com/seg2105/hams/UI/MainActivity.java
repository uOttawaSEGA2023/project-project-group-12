package com.seg2105.hams.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;

import com.seg2105.hams.R;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavController navController;

        // Find the NavController
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        // Get the NavController from the NavHostFragment and store it
        navController = navHostFragment.getNavController();
        // Navigate to homeFragment

        navController.navigate(R.id.homeFragment);
    }

}


