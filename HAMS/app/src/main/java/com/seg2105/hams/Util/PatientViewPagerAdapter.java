package com.seg2105.hams.Util;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.seg2105.hams.UI.PatientFragment;
import com.seg2105.hams.UI.patientFragments.BookingFragment;
import com.seg2105.hams.UI.patientFragments.PatientAppointmentFragment;

public class PatientViewPagerAdapter extends FragmentStateAdapter {
    public PatientViewPagerAdapter(@NonNull PatientFragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new BookingFragment();
            default:
                return new PatientAppointmentFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}