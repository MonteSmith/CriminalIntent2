package com.example.student.criminalintent;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DatePickerFragment extends DialogFragment {

    public final static String START_DATE = "com.nhocki.criminalintent.start_date";
    private Date mDate;

    public static DatePickerFragment newInstance(Date date){
        Bundle bundle = new Bundle();
        bundle.putSerializable(START_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDate = (Date)getArguments().getSerializable(START_DATE);
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_date, null);
        setupDatePicker(v);

        return new AlertDialog.Builder(getActivity()).
                setView(v).
                setTitle(R.string.date_picker_title).
                setPositiveButton(
                        android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                setResult(Activity.RESULT_OK);
                            }
                        }
                ).
                create();
    }

    private void setResult(int resultCode){
        Fragment target = getTargetFragment();
        if(target == null) return ;

        Intent intent = new Intent();
        intent.putExtra(START_DATE, mDate);
        target.onActivityResult(getTargetRequestCode(), resultCode, intent);
    }

    private void setupDatePicker(View v){
        DatePicker datePicker = (DatePicker)v.findViewById(R.id.dialog_date_datePicker);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);

        int day   = calendar.get(Calendar.DAY_OF_MONTH);
        int year  = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
                mDate = new GregorianCalendar(year,  month, day).getTime();

                // If the device rotates, don't lose the new date
                getArguments().putSerializable(START_DATE, mDate);
            }
        });

    }
}
