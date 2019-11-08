package com.tech.agape4charity.dialog;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by SmasH on 6/18/2018.
 */

@SuppressLint("ValidFragment")
public class DatePicker extends DialogFragment {

    private static final String TAG = "DatePicker";
    private DatePickerCallback mCallback;

    @SuppressLint("ValidFragment")
    public DatePicker(DatePickerCallback mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(getActivity(), dateSetListener, year, month, day);    }

    private DatePickerDialog.OnDateSetListener dateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(android.widget.DatePicker datePicker, int i, int i1, int i2) {
                    mCallback.onDataSet(i,i1,i2);
                }
            };

    public interface DatePickerCallback {
        public void onDataSet(int i, int i1, int i2);

    }
    }
