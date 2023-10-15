package com.seg2105.hams;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
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
import org.json.*;

public class InfoForm extends AppCompatActivity {
    TextInputEditText editTextFirstName, editTextLastName, editTextStreet, editTextCity,
            editTextProvince, editTextCountry, editTextPostalCode, editTextPhoneNumber, editTextHealthcard,
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
        editTextHealthcard=findViewById(R.id.healthcard);
        editTextEmployeeNumber=findViewById(R.id.employeeNumber);
        editTextSpecialties=findViewById(R.id.specialties);

        editTextHealthcard.setVisibility(View.GONE);
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
            editTextHealthcard.setVisibility(View.GONE);

            }
        });

        patient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                role="patient";
                editTextEmployeeNumber.setVisibility(View.GONE);
                editTextSpecialties.setVisibility(View.GONE);
                editTextHealthcard.setVisibility(View.VISIBLE);
            }
        });
        formComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName, lastName, street, city, province, country ,postalCode, phoneNumber, healthcardNumber, employeeNumber, specialties;
                firstName = String.valueOf(editTextFirstName.getText());
                lastName = String.valueOf(editTextLastName.getText());
                street = String.valueOf(editTextStreet.getText());
                city = String.valueOf(editTextCity.getText());
                province = String.valueOf(editTextProvince.getText());
                country = String.valueOf(editTextCountry.getText());
                postalCode = String.valueOf(editTextPostalCode.getText());
                phoneNumber = String.valueOf(editTextPhoneNumber.getText());
                healthcardNumber = String.valueOf(editTextHealthcard.getText());
                employeeNumber = String.valueOf(editTextEmployeeNumber.getText());
                specialties = String.valueOf(editTextSpecialties.getText());

                if (TextUtils.isEmpty(firstName)) {
                    Toast.makeText(InfoForm.this, "Enter first name.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(lastName)) {
                    Toast.makeText(InfoForm.this, "Enter last name.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(street)) {
                    Toast.makeText(InfoForm.this, "Enter Street.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(city)) {
                    Toast.makeText(InfoForm.this, "Enter city.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(province)) {
                    Toast.makeText(InfoForm.this, "Enter province.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(country)) {
                    Toast.makeText(InfoForm.this, "Enter country.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(postalCode)) {
                    Toast.makeText(InfoForm.this, "Enter Postal Code.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(InfoForm.this, "Enter phone number.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (role==""){
                        Toast.makeText(InfoForm.this, "Please choose either doctor or patient", Toast.LENGTH_SHORT).show();
                        return;
                }
                else if (role=="patient") {
                    if (TextUtils.isEmpty(healthcardNumber)) {
                        Toast.makeText(InfoForm.this, "Enter healthcard number.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                else {
                    if (TextUtils.isEmpty(employeeNumber)) {
                        Toast.makeText(InfoForm.this, "Enter employee number.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (TextUtils.isEmpty(specialties)) {
                        Toast.makeText(InfoForm.this, "Enter specialties.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference();
                DatabaseReference usersRef = ref.child("users");
                HashMap<String, Object> user = new HashMap<>();
                HashMap<String, Object> addressInfo = new HashMap<>();

                user.put("First Name", firstName);
                user.put("Last Name", lastName);
                user.put("Email", currentUser.getEmail());

                addressInfo.put("Street", street);
                addressInfo.put("City", city);
                addressInfo.put("Province", province);
                addressInfo.put("Country", country);
                addressInfo.put("Postal Code", postalCode);

                user.put("Address", addressInfo);
                user.put("Phone Number", phoneNumber);
                user.put("Role", role);
                if (role=="patient"){
                    user.put("Healthcard Number", healthcardNumber);
                }
                else if (role=="doctor") {
                    user.put("Employee Number", employeeNumber);
                    user.put("Specialities", specialties);
                }
                usersRef.child(currentUser.getUid()).setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(InfoForm.this, "User info added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }
}