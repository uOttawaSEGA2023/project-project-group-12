package com.seg2105.hams.UI.doctorFragments;

import static androidx.navigation.Navigation.findNavController;
import static com.seg2105.hams.Managers.ShiftManager.getAllShifts;
import static com.seg2105.hams.Managers.ShiftManager.removeShiftFromDataBase;
import static com.seg2105.hams.Managers.UserManager.getCurrentUser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.seg2105.hams.Managers.Shift;
import com.seg2105.hams.Managers.ShiftManager;
import com.seg2105.hams.R;
import com.seg2105.hams.Users.Doctor;
import com.seg2105.hams.Util.ShiftAdapter;
import com.seg2105.hams.Util.UserCallback;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class ShiftFragment extends Fragment implements ShiftAdapter.OnItemClickListener {

    LocalDate date;
    TextInputEditText startTimeEditText, endTimeEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        List<Shift> upcomingShifts = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_shift, container, false);

        startTimeEditText = view.findViewById(R.id.startTime);
        endTimeEditText = view.findViewById(R.id.endTime);


        RecyclerView recyclerViewUpcoming = view.findViewById(R.id.recyclerViewUpcoming);
        getAllShifts((Doctor) getCurrentUser(), new UserCallback() {
            @Override
            public void onSuccess() {
            }

            @Override
            public void onListLoaded(List shifts) {
                ShiftAdapter pendingAdapter = new ShiftAdapter(upcomingShifts, ShiftFragment.this);
                recyclerViewUpcoming.setAdapter(pendingAdapter);
                recyclerViewUpcoming.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button dateSelect = view.findViewById(R.id.dateSelect);
        Button homeBtn = view.findViewById(R.id.btnHome);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        dateSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Select Date")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build();
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        long daysSinceEpoch = selection / (1000 * 60 * 60 * 24); // Convert milliseconds to days
                        date = LocalDate.ofEpochDay(daysSinceEpoch);
                        dateSelect.setText(MessageFormat.format("Selected Date: {0}", date));
                    }
                });
                materialDatePicker.show(getParentFragmentManager(), "tag");
            }
        });


        Button btnSubmit = view.findViewById(R.id.submitShift);

        btnSubmit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String startInput, endInput;
                startInput = String.valueOf(startTimeEditText.getText());
                endInput = String.valueOf(endTimeEditText.getText());
                try {
                    LocalTime sTime = LocalTime.parse(startInput, formatter);
                    LocalTime eTime = LocalTime.parse(endInput, formatter);
                    switch (checkShiftRequirements(view, date, sTime, eTime)){
                        case 1:
                            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                        case 2:
                            Toast.makeText(requireContext(), "Please choose 30 minute intervals.", Toast.LENGTH_SHORT).show();
                        case 3:
                            Toast.makeText(requireContext(), "Please a shift in the future.", Toast.LENGTH_SHORT).show();
                        case 4:
                            Toast.makeText(requireContext(), "Your shift must end after it starts.", Toast.LENGTH_SHORT).show();
                        case 0:
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

                            LocalDateTime startDateTime = LocalDateTime.of(date, sTime);
                            LocalDateTime endDateTime = LocalDateTime.of(date, eTime);

                            String startTimeString = formatter.format(Date.from(startDateTime.atZone(ZoneId.systemDefault()).toInstant()));
                            String endTimeString = formatter.format(Date.from(endDateTime.atZone(ZoneId.systemDefault()).toInstant()));

                            Shift s = new Shift(startTimeString, endTimeString);
                            ShiftManager.putShiftInDatabase(s, new UserCallback(){
                                public void onSuccess() {
                                    FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                                    fragmentTransaction.detach(ShiftFragment.this).attach(ShiftFragment.this).commit();
                                    Toast.makeText(requireContext(), "Shift added successfully", Toast.LENGTH_SHORT).show();
                                }
                                public void onListLoaded(List shift) {}

                                @Override
                                public void onFailure(String error) {
                                    Toast.makeText(requireContext(), "Error putting shift in database", Toast.LENGTH_SHORT).show();
                                }
                            });
                    }

                } catch (Exception e) {
                    Toast.makeText(requireContext(), "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findNavController(view).navigate(R.id.action_doctor_to_home);
            }
        });
    }

    private int checkShiftRequirements(View view, LocalDate date, LocalTime startTime, LocalTime endTime) {
        //Check all requirements
        if (date==null||startTime==null||endTime==null) return 1;
        if (!((startTime.getMinute() == 0) || (startTime.getMinute() == 30))) return 2;
        if (!((endTime.getMinute() == 0) || (endTime.getMinute() == 30))) return 2;

        // Combine LocalTime and LocalDate into LocalDateTime
        LocalDateTime combinedDateTime = LocalDateTime.of(date, startTime);

        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();

        if (!combinedDateTime.isAfter(currentDateTime)) return 3;

        if (startTime.isAfter(endTime)) return 4;
        return 0;
    }

    @Override
    public void onItemClick(Shift shift) {
        // Handle item click, navigate to PersonFragment passing the selected Person object in Bundle
        removeShiftFromDataBase(shift, new UserCallback() {
            @Override
            public void onSuccess() {
                FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
                fragmentTransaction.detach(ShiftFragment.this).attach(ShiftFragment.this).commit();
                Toast.makeText(requireContext(), "Shift deleted successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onListLoaded(List list) {            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(requireContext(), "Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
