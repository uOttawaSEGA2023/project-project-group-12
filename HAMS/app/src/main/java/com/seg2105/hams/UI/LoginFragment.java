package com.seg2105.hams.UI;

import static androidx.navigation.Navigation.findNavController;
import static com.seg2105.hams.Users.UserManager.getCurrentUser;
import static com.seg2105.hams.Users.UserManager.getUserFromDatabase;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.seg2105.hams.R;

public class LoginFragment extends Fragment {

    TextInputEditText editTextEmail, editTextPassword;
    Button buttonLogin;
    FirebaseAuth mAuth;
    TextView textView;

    public LoginFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        mAuth = FirebaseAuth.getInstance();
        editTextEmail = view.findViewById(R.id.email);
        editTextPassword = view.findViewById(R.id.password);
        buttonLogin = view.findViewById(R.id.btn_login);
        textView = view.findViewById(R.id.gotoRegister);

        // If user wishes to go to register page, do so.
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to RegisterFragment
                findNavController(view).navigate(R.id.action_login_to_register);
            }
        });

        // On login click, handle action.
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = String.valueOf(editTextEmail.getText());
                String password = String.valueOf(editTextPassword.getText());

                // Validate input fields.
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getActivity(), "Enter email.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getActivity(), "Enter password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Else, sign in with FireBase
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // Handle success and failure.
                                if (task.isSuccessful()) {

                                    // Test for deserialize
                                    if (getUserFromDatabase(mAuth.getCurrentUser().getUid())) {
                                        findNavController(view).navigate(R.id.action_login_to_home);
                                    } else {
                                        Toast.makeText(requireContext(), "Problem finding user.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                    Toast.makeText(requireContext(), "Logged in.",
                                            Toast.LENGTH_SHORT).show();

                                } else {
                                    // If sign in fails, display a message to the user.

                                }
                            }
                        });
            }
        });

        return view;
    }
}
