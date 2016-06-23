package com.example.jegansbeast.fazt.settings;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.preference.DialogPreference;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by JEGAN'S BEAST on 6/22/2016.
 */
public class TimePreference extends DialogPreference {

    int hour,min;
    String time;
    TimePicker picker;

    public TimePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public static int getHour(String time) {
        String[] pieces=time.split(":");

        return(Integer.parseInt(pieces[0]));
    }

    public static int getMinute(String time) {
        String[] pieces=time.split(":");

        return(Integer.parseInt(pieces[1]));
    }

    @Override
    protected View onCreateDialogView() {
        picker = new TimePicker(getContext());
        setPositiveButtonText("Ok");
        setNegativeButtonText("cancel");
        return picker;
    }

    @Override
    protected void onBindDialogView(View view) {
        super.onBindDialogView(view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            picker.setHour(hour);
            picker.setMinute(min);
        }
        else{
            picker.setCurrentHour(hour);
            picker.setCurrentMinute(min);
        }
    }

    @Override
    protected void onDialogClosed(boolean positiveResult) {
        super.onDialogClosed(positiveResult);
        if(positiveResult){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                hour=picker.getHour();
                min = picker.getMinute();
            }
            else {
                hour = picker.getCurrentHour();
                min = picker.getCurrentMinute();
            }

            time=String.valueOf(hour)+":"+String.valueOf(min);

            if(callChangeListener(time))
                persistString(time);
        }

        setSummary(getSummary());
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue) {

        if(restorePersistedValue){
            if(defaultValue==null){
                time = getPersistedString("09:00");
            }
            else{
                time = getPersistedString(defaultValue.toString());
            }
        }
        else {
            time = defaultValue.toString();
        }

        hour = getHour(time);
        min = getMinute(time);

        setSummary(getSummary());
    }

    @Override
    public CharSequence getSummary() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH, hour, min);
        return DateFormat.getTimeFormat(getContext()).format(new Date(cal.getTimeInMillis()));
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        return a.getIndex(index);
    }
}
