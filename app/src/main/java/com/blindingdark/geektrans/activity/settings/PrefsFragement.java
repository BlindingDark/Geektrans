package com.blindingdark.geektrans.activity.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;

import com.blindingdark.geektrans.R;
import com.blindingdark.geektrans.activity.TransActivity;

/**
 * Created by BlindingDark on 2016/12/1 0001.
 */

public class PrefsFragement extends PreferenceFragment {

    public Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getActivity();
        addPreferencesFromResource(R.xml.preferences_other_settings);

        findPreference("NOW_TOAST_STYLE").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {

            @Override
            public boolean onPreferenceChange(Preference preference, Object choose) {
              Intent transIntent = new Intent();
                transIntent.putExtra("req", "test");
                transIntent.setClass(context, TransActivity.class);

                context.startActivity(transIntent);
                return true;
            }
        });


    }
}
