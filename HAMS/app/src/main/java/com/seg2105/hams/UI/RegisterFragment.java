package com.seg2105.hams.UI;

import static com.seg2105.hams.Users.UserManager.putUserInDatabase;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.seg2105.hams.R;
import com.seg2105.hams.Users.Doctor;
import com.seg2105.hams.Users.Patient;
import com.seg2105.hams.Users.User;
import com.seg2105.hams.Util.Util;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RegisterFragment extends Fragment {

    TextInputEditText editTextFirstName, editTextLastName, editTextStreet, editTextCity,
            editTextProvince, editTextCountry, editTextPostalCode, editTextPhoneNumber, editTextHealthNumber,
            editTextEmployeeNumber, editTextSpecialties;
    MaterialButton doctor, patient, ageSelect;
    String role, dateOfBirth;

    TextInputEditText editTextEmail, editTextPassword;
    Button buttonReg;
    FirebaseAuth mAuth;
    TextView textView;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        doctor = view.findViewById(R.id.doctor);
        patient = view.findViewById(R.id.patient);

        editTextFirstName = view.findViewById(R.id.first_name);
        editTextLastName = view.findViewById(R.id.last_name);
        editTextStreet = view.findViewById(R.id.street);
        editTextCity = view.findViewById(R.id.city);
        editTextProvince = view.findViewById(R.id.province);
        editTextCountry = view.findViewById(R.id.country);
        editTextPostalCode = view.findViewById(R.id.postal_code);
        editTextPhoneNumber = view.findViewById(R.id.phone_number);
        editTextHealthNumber = view.findViewById(R.id.healthNumber);
        editTextEmployeeNumber = view.findViewById(R.id.employeeNumber);
        editTextSpecialties = view.findViewById(R.id.specialties);

        editTextHealthNumber.setVisibility(View.GONE);
        editTextEmployeeNumber.setVisibility(View.GONE);
        editTextSpecialties.setVisibility(View.GONE);

        ageSelect = view.findViewById(R.id.age_select);

        ageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select Date")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build();
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date(selection));
                        ageSelect.setText(MessageFormat.format("Selected Date: {0}", date));
                        dateOfBirth = date;
                    }
                });
                materialDatePicker.show(getParentFragmentManager(), "tag");
            }
        });

        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                role = "doctor";
                editTextEmployeeNumber.setVisibility(View.VISIBLE);
                editTextSpecialties.setVisibility(View.VISIBLE);
                editTextHealthNumber.setVisibility(View.GONE);
            }
        });

        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                role = "patient";
                editTextEmployeeNumber.setVisibility(View.GONE);
                editTextSpecialties.setVisibility(View.GONE);
                editTextHealthNumber.setVisibility(View.VISIBLE);
            }
        });

        mAuth = FirebaseAuth.getInstance();
        editTextEmail = view.findViewById(R.id.email);
        editTextPassword = view.findViewById(R.id.password);
        buttonReg = view.findViewById(R.id.btn_register);
        textView = view.findViewById(R.id.gotoLogin);

        // If user clicks on Go To Login, redirect to login.
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate to the LoginFragment
                Navigation.findNavController(view).navigate(R.id.action_register_to_login);
            }
        });

        // If user clicks register, handle operation.
        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get values inputted.
                String email, password, firstName, lastName, address, phoneNumber, healthNumber, employeeNumber;
                List<String> specialties = new ArrayList<>();
                Map<String, String> addressValues = new HashMap<>();

                addressValues.put("street", String.valueOf(editTextStreet.getText()));
                addressValues.put("city", String.valueOf(editTextCity.getText()));
                addressValues.put("province", String.valueOf(editTextProvince.getText()));
                addressValues.put("country", String.valueOf(editTextCountry.getText()));
                addressValues.put("postalCode", String.valueOf(editTextPostalCode.getText()));

                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());
                firstName = String.valueOf(editTextFirstName.getText());
                lastName = String.valueOf(editTextLastName.getText());
                phoneNumber = String.valueOf(editTextPhoneNumber.getText());
                address = Util.fieldsToAddress(addressValues);
                healthNumber = String.valueOf(editTextHealthNumber.getText());
                employeeNumber = String.valueOf(editTextEmployeeNumber.getText());
                for (String s : String.valueOf(editTextSpecialties.getText()).split(",")) {
                    specialties.add(s.trim());
                }

                // Validation logic.
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(requireContext(), "Enter email.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(requireContext(), "Enter password.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create account with Firebase.
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                // Handle success or failure.
                                if (task.isSuccessful()) {
                                    Toast.makeText(requireContext(), "User created.",
                                            Toast.LENGTH_SHORT).show();

                                    if ("patient".equals(role)) {
                                        User p = new Patient(FirebaseAuth.getInstance().getCurrentUser().getUid(), email, firstName, lastName, phoneNumber, address, healthNumber, "pending");
                                        putUserInDatabase(p);
                                    }
                                    if ("doctor".equals(role)) {
                                        User d = new Doctor(FirebaseAuth.getInstance().getCurrentUser().getUid(), email, firstName, lastName, phoneNumber, address, employeeNumber, specialties, "pending");
                                        putUserInDatabase(d);
                                    }

                                    // Navigate to the LoginFragment after successful registration
                                    Navigation.findNavController(view).navigate(R.id.action_register_to_login);
                                } else {
                                    // If sign-up fails, display a message to the user.
                                    Toast.makeText(requireContext(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        return view;
    }
}
