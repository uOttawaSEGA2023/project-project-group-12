package com.seg2105.hams.UI;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.seg2105.hams.R;

public class RatingDialogFragment extends DialogFragment {

    private OnRatingSelectedListener onRatingSelectedListener;

    public interface OnRatingSelectedListener {
        void onRatingSelected(int rating);
    }

    public void setOnRatingSelectedListener(OnRatingSelectedListener listener) {
        this.onRatingSelectedListener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_rating_dialog, null);
        builder.setView(view);

        // Set up star click listeners
        setUpStarClickListeners(view);

        return builder.create();
    }

    private void setUpStarClickListeners(View view) {
        int[] starIds = {R.id.star1, R.id.star2, R.id.star3, R.id.star4, R.id.star5};

        for (int i = 0; i < starIds.length; i++) {
            ImageView star = view.findViewById(starIds[i]);
            final int rating = i + 1;

            star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onRatingSelectedListener != null) {
                        onRatingSelectedListener.onRatingSelected(rating);
                    }
                    dismiss();
                }
            });
        }
    }
}
