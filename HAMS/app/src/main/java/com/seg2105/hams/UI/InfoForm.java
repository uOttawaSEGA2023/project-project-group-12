package com.seg2105.hams.UI;

import static com.seg2105.hams.Users.UserManager.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.seg2105.hams.R;
import com.seg2105.hams.Users.Patient;
import com.seg2105.hams.Users.User;
import com.seg2105.hams.Util.Util;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class InfoForm extends AppCompatActivity {
    TextInputEditText editTextFirstName, editTextLastName, editTextStreet, editTextCity,
            editTextProvince, editTextCountry, editTextPostalCode, editTextPhoneNumber, editTextHealthNumber,
            editTextEmployeeNumber, editTextSpecialties;
    Button formComplete, doctor, patient;
    MaterialButton ageSelect;
    String role, dateOfBirth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_form);

        //retrieve user id and set it as the email display for info_form UI
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        TextView userEmail = findViewById(R.id.user_email);
        userEmail.setText(currentUser.getEmail(), TextView.BufferType.EDITABLE);

        role="";
        doctor=findViewById(R.id.doctor);
        patient=findViewById(R.id.patient);

        formComplete=findViewById(R.id.complete_form);
        editTextFirstName=findViewById(R.id.first_name);
        editTextLastName=findViewById(R.id.last_name);
        editTextStreet=findViewById(R.id.street);
        editTextCity=findViewById(R.id.city);
        editTextProvince=findViewById(R.id.province);
        editTextCountry=findViewById(R.id.country);
        editTextPostalCode=findViewById(R.id.postal_code);
        editTextPhoneNumber=findViewById(R.id.phone_number);
        editTextHealthNumber=findViewById(R.id.healthNumber);
        editTextEmployeeNumber=findViewById(R.id.employeeNumber);
        editTextSpecialties=findViewById(R.id.specialties);

        editTextHealthNumber.setVisibility(View.GONE);
        editTextEmployeeNumber.setVisibility(View.GONE);
        editTextSpecialties.setVisibility(View.GONE);

         ageSelect = findViewById(R.id.age_select);

        ageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select Date")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build();
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        String date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date(selection));
                        ageSelect.setText(MessageFormat.format("Selected Date: {0}", date));
                        dateOfBirth=date;
                    }
                });
                materialDatePicker.show(getSupportFragmentManager(), "tag");
            }
        });

        doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            role="doctor";
            editTextEmployeeNumber.setVisibility(View.VISIBLE);
            editTextSpecialties.setVisibility(View.VISIBLE);
            editTextHealthNumber.setVisibility(View.GONE);

            }
        });

        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                role="patient";
                editTextEmployeeNumber.setVisibility(View.GONE);
                editTextSpecialties.setVisibility(View.GONE);
                editTextHealthNumber.setVisibility(View.VISIBLE);
            }
        });
        formComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName, lastName, address, phoneNumber, healthNumber, employeeNumber, specialties;
                Map<String, String> addressValues = new HashMap<>();

                addressValues.put("street", String.valueOf(editTextStreet.getText()));
                addressValues.put("city", String.valueOf(editTextCity.getText()));
                addressValues.put("province", String.valueOf(editTextProvince.getText()));
                addressValues.put("country", String.valueOf(editTextCountry.getText()));
                addressValues.put("postalCode", String.valueOf(editTextPostalCode.getText()));

                firstName = String.valueOf(editTextFirstName.getText());
                lastName = String.valueOf(editTextLastName.getText());
                phoneNumber = String.valueOf(editTextPhoneNumber.getText());
                address = Util.fieldsToAddress(addressValues);
                healthNumber = String.valueOf(editTextHealthNumber.getText());
                employeeNumber = String.valueOf(editTextEmployeeNumber.getText());
                specialties = String.valueOf(editTextSpecialties.getText());

                User p = new Patient(currentUser.getUid(), currentUser.getEmail(), true, firstName, lastName, phoneNumber, address, healthNumber);
                putUserInFirebase(p);
//                if (putUserInFirebase(p)) {
//                    Toast.makeText(InfoForm.this, "User info added", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    Toast.makeText(InfoForm.this, "error", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }
}