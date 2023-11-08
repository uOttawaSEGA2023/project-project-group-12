package com.seg2105.hams.Util;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.seg2105.hams.UI.DoctorFragment;
import com.seg2105.hams.UI.doctorFragments.*;

public class DoctorViewPagerAdapter extends FragmentStateAdapter {
    public DoctorViewPagerAdapter(@NonNull DoctorFragment fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1:
                return new ShiftFragment();
            default:
                return new AppointmentFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}