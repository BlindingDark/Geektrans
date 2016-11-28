package com.blindingdark.geektrans.activity.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import com.blindingdark.geektrans.R;
import com.blindingdark.geektrans.global.StringMainSettings;
import com.blindingdark.geektrans.tools.Number;
import com.blindingdark.geektrans.trans.jinshan.Jinshan;
import com.blindingdark.geektrans.trans.jinshan.StringJinshanSettings;

public class JinshanSettingsActivity extends AppCompatActivity {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    EditText editTextJinshanKey;
    EditText editTextJinshanToastTime;
    Switch switchJinshanSoundRemix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jinshan_settings);

        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        editTextJinshanKey = (EditText) findViewById(R.id.editTextJinshanKey);
        String jinshanKey = preferences.getString(StringJinshanSettings.jinshanKey, "");
        editTextJinshanKey.setText(jinshanKey);
        editTextJinshanKey.addTextChangedListener(jinshanKeyTextWatcher);

        editTextJinshanToastTime = (EditText) findViewById(R.id.editTextJinshanToastTime);
        String jinshanToastTime = preferences.getString(StringJinshanSettings.jinshanToastTime, StringJinshanSettings.defJinshanToastTime);
        editTextJinshanToastTime.setText(jinshanToastTime);
        editTextJinshanToastTime.addTextChangedListener(jinshanToastTimeTextWatcher);

        switchJinshanSoundRemix = (Switch) findViewById(R.id.switchJinshanSoundRemix);
        String nowSoundEngine = preferences.getString(StringMainSettings.DEFAULT_SOUND_ENGINE, "");
        if (nowSoundEngine.equals(Jinshan.ENGINE_NAME)) {
            switchJinshanSoundRemix.setChecked(true);
        } else {
            switchJinshanSoundRemix.setChecked(false);
        }

        //  2016/8/24 0024 当改变时的动作
        switchJinshanSoundRemix.setOnCheckedChangeListener(jinshanSoundRemixChangeListener);

    }

    CompoundButton.OnCheckedChangeListener jinshanSoundRemixChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                editor.putString(StringMainSettings.DEFAULT_SOUND_ENGINE, Jinshan.ENGINE_NAME);
            }
            if (!isChecked) {
                if (preferences.getString(StringMainSettings.DEFAULT_SOUND_ENGINE, "").equals(Jinshan.ENGINE_NAME)) {
                    editor.putString(StringMainSettings.DEFAULT_SOUND_ENGINE, "");
                }
            }
            editor.commit();

        }
    };

    TextWatcher jinshanKeyTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            editor.putString(StringJinshanSettings.jinshanKey, editTextJinshanKey.getText().toString());
            editor.commit();
        }
    };

    TextWatcher jinshanToastTimeTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            String time = editTextJinshanToastTime.getText().toString();

            // 检测是否是合法数字
            if (Number.isLegalToastTime(time)) {
                editor.putString(StringJinshanSettings.jinshanToastTime, time);
                editor.commit();
            }


        }
    };

    public void jinshanLogoOnClick(View view) {
        Uri uri = Uri.parse("http://open.iciba.com/?c=api");
        startActivity(new Intent(Intent.ACTION_VIEW, uri));

    }
}
