package com.example.jegansbeast.fazt.settings;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.RingtonePreference;

import com.example.jegansbeast.fazt.R;


/**
 * Created by JEGAN'S BEAST on 6/22/2016.
 */
public class MyPreferences extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        Preference preference = findPreference("alarmtone");
        preference.setOnPreferenceChangeListener(this);
        preference = findPreference("alarm");
        preference.setOnPreferenceChangeListener(this);

    }


    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

        if(preference instanceof RingtonePreference){

            RingtonePreference ringtonePreference = (RingtonePreference) preference;
            Ringtone ringtone = RingtoneManager.getRingtone(getActivity(), Uri.parse(newValue.toString()));
            if(ringtone!=null){
                String title = ringtone.getTitle(getActivity());
                if(!title.equalsIgnoreCase("unknown ringtone"))
                ringtonePreference.setSummary(title);
                else
                    ringtonePreference.setSummary("none");
            }
            else
                ringtonePreference.setSummary("");

        }
        else if(preference instanceof CheckBoxPreference){
            CheckBoxPreference checkBoxPreference = (CheckBoxPreference) preference;
            if((Boolean) newValue){

                findPreference("alarmtone").setEnabled(true);
                findPreference("alarm_time").setEnabled(true);
            }
        }
        return true;
    }
}
